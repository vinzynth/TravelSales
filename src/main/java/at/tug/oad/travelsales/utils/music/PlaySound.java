/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.tug.oad.travelsales.utils.music;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 *
 * @author Martin
 */
public class PlaySound extends Thread{

    private Clip clip;
    private final String musicPath;
    
    public PlaySound(String musicPath) {
        this.musicPath = musicPath;
    }
    
    @Override
    public void run(){
        play();
    }
    
    public void interrupt()
    {
        clip.stop();
    }
    
    private void play() {
        Object lock = new Object();

        String soundFile = (musicPath!=null && !musicPath.isEmpty())?musicPath:"D:/music.wav";

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundFile))) {

            clip = AudioSystem.getClip();
            clip.open(ais);
            
            clip.addLineListener((e) -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            });

            clip.start();

            synchronized (lock) {
                lock.wait();
            }
        } catch (Exception e) {
            System.err.println("Fehler: "+e.getMessage());
        }
    }
    
    
}
