/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package at.tug.oad.travelsales.view.gui;

import java.awt.CardLayout;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import at.tug.oad.travelsales.controller.TravelSalesViewController;
import at.tug.oad.travelsales.controller.ViewControllerState;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.ILevel;
import at.tug.oad.travelsales.model.INotification;
import at.tug.oad.travelsales.model.IPlayStatistic;

/**
 *
 * @author Martin
 */
public class TravelSalesMan_View extends javax.swing.JFrame {

	/**
	 * Creates new form TravelSalesMan_View
	 */
	public TravelSalesMan_View() {
		initComponents();
	}

	public void show(ViewControllerState state) {
		if (state == ViewControllerState.LOGIN || state == ViewControllerState.REGISTRATION) {
			pnNorth.setVisible(false);
		} else {
			pnNorth.setVisible(true);
		}

		CardLayout cards = (CardLayout) pnView.getLayout();
		cards.show(pnView, state.name());
	}

	public void showMessage(String title, String text, MessageType type) {
		System.out.println(">>>>> Fehler: " + text);
		int msgType = JOptionPane.INFORMATION_MESSAGE;

		switch (type) {
			case SUCCESS:
				msgType = JOptionPane.INFORMATION_MESSAGE;
				break;
			case ERROR:
				msgType = JOptionPane.ERROR_MESSAGE;
				break;
			case WARNING:
				msgType = JOptionPane.WARNING_MESSAGE;
				break;
			default:
		}
		JOptionPane.showMessageDialog(this, text, title, msgType);
	}

	public void setMyGames(Collection<IGame> mygames) {
		this.selectGamePanel1.setMyGames(mygames);
	}

	public void setAllGames(Collection<IGame> games) {
		this.selectGamePanel1.setAllGames(games);
	}

	public void setLevels(Collection<ILevel> levels) {
		this.level_BaseGame_SelectPanel1.setLevels(levels);
	}

	public void setRecommendedGames(Collection<IGame> games) {
		selectGamePanel1.setRecommendedGames(games);
	}

	public void setUsername(String username) {
		this.selectGamePanel1.setWelcomeText("Welcome " + username + "!");
	}

	public void adminFunctionsVisible(boolean visible) {
		this.selectGamePanel1.adminFunctionsVisible(visible);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		pnView = new javax.swing.JPanel();
		loginPanel1 = new at.tug.oad.travelsales.view.gui.LoginPanel();
		registrationPanel1 = new at.tug.oad.travelsales.view.gui.RegistrationPanel();
		selectGamePanel1 = new at.tug.oad.travelsales.view.gui.SelectGamePanel();
		level_BaseGame_SelectPanel1 = new at.tug.oad.travelsales.view.gui.Level_BaseGame_SelectPanel();
		newGamePanel1 = new at.tug.oad.travelsales.view.gui.newGamePanel();
		playGamePanel1 = new at.tug.oad.travelsales.view.gui.PlayGamePanel();
		edit_BaseGame_Panel1 = new at.tug.oad.travelsales.view.gui.Edit_BaseGame_Panel();
		resetPasswordPanel1 = new at.tug.oad.travelsales.view.gui.ResetPasswordPanel();
		editLevelPanel1 = new at.tug.oad.travelsales.view.gui.EditLevelPanel();
		editGamePanel1 = new at.tug.oad.travelsales.view.gui.EditGamePanel();
		messagePanel1 = new at.tug.oad.travelsales.view.gui.MessagePanel();
		pnNorth = new javax.swing.JPanel();
		btMessages = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel1 = new javax.swing.JLabel();
		tfMusicPath = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("TravelSalesMan");
		setMinimumSize(new java.awt.Dimension(800, 400));
		setPreferredSize(new java.awt.Dimension(800, 400));
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				onWindowClosing(evt);
			}
		});

		pnView.setLayout(new java.awt.CardLayout());
		pnView.add(loginPanel1, "LOGIN");
		pnView.add(registrationPanel1, "REGISTRATION");
		pnView.add(selectGamePanel1, "SELECT_GAME");
		pnView.add(level_BaseGame_SelectPanel1, "SELECT_LEVEL_BASEGAME");
		pnView.add(newGamePanel1, "NEW_GAME");
		pnView.add(playGamePanel1, "PLAY_GAME");
		pnView.add(edit_BaseGame_Panel1, "EDIT_BASE_GAME");
		pnView.add(resetPasswordPanel1, "RESET_PASSWORD");
		pnView.add(editLevelPanel1, "EDIT_LEVEL");
		pnView.add(editGamePanel1, "EDIT_GAME");
		pnView.add(messagePanel1, "MESSAGE");

		getContentPane().add(pnView, java.awt.BorderLayout.CENTER);

		btMessages.setText("Messages");
		btMessages.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onMessage(evt);
			}
		});
		pnNorth.add(btMessages);

		jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
		pnNorth.add(jSeparator1);

		jLabel1.setText("Backgroundmusic:");
		pnNorth.add(jLabel1);

		tfMusicPath.setColumns(10);
		pnNorth.add(tfMusicPath);

		jButton1.setText("...");
		jButton1.setMaximumSize(new java.awt.Dimension(25, 23));
		jButton1.setMinimumSize(new java.awt.Dimension(25, 23));
		jButton1.setPreferredSize(new java.awt.Dimension(25, 23));
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onSearchMusic(evt);
			}
		});
		pnNorth.add(jButton1);

		jButton2.setText("Play");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onPlay(evt);
			}
		});
		pnNorth.add(jButton2);

		jButton3.setText("Stop");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onStop(evt);
			}
		});
		pnNorth.add(jButton3);

		getContentPane().add(pnNorth, java.awt.BorderLayout.NORTH);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void onWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_onWindowClosing
		TravelSalesViewController.getInstance().shutdown(); // To close the
															// DB-Connection
	}// GEN-LAST:event_onWindowClosing

	private void onMessage(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onMessage
		TravelSalesViewController.getInstance().showMessages();
	}// GEN-LAST:event_onMessage

	private void onPlay(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onPlay
		TravelSalesViewController.getInstance().playSound(tfMusicPath.getText());
	}// GEN-LAST:event_onPlay

	private void onStop(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onStop
		TravelSalesViewController.getInstance().stopSound();
	}// GEN-LAST:event_onStop

	private void onSearchMusic(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onSearchMusic
		JFileChooser chooser = new JFileChooser(tfMusicPath.getText());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Music-Files *.wav", "wav");
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			tfMusicPath.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}// GEN-LAST:event_onSearchMusic

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btMessages;
	private at.tug.oad.travelsales.view.gui.EditGamePanel editGamePanel1;
	private at.tug.oad.travelsales.view.gui.EditLevelPanel editLevelPanel1;
	private at.tug.oad.travelsales.view.gui.Edit_BaseGame_Panel edit_BaseGame_Panel1;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JSeparator jSeparator1;
	private at.tug.oad.travelsales.view.gui.Level_BaseGame_SelectPanel level_BaseGame_SelectPanel1;
	private at.tug.oad.travelsales.view.gui.LoginPanel loginPanel1;
	private at.tug.oad.travelsales.view.gui.MessagePanel messagePanel1;
	private at.tug.oad.travelsales.view.gui.newGamePanel newGamePanel1;
	private at.tug.oad.travelsales.view.gui.PlayGamePanel playGamePanel1;
	private javax.swing.JPanel pnNorth;
	private javax.swing.JPanel pnView;
	private at.tug.oad.travelsales.view.gui.RegistrationPanel registrationPanel1;
	private at.tug.oad.travelsales.view.gui.ResetPasswordPanel resetPasswordPanel1;
	private at.tug.oad.travelsales.view.gui.SelectGamePanel selectGamePanel1;
	private javax.swing.JTextField tfMusicPath;

	// End of variables declaration//GEN-END:variables

	public void setPlayingBaseGame(IBaseGame selectedBaseGame) {
		this.playGamePanel1.setBaseGame(selectedBaseGame);
	}

	public void setBaseGames(Collection<IBaseGame> basegames) {
		this.level_BaseGame_SelectPanel1.setBaseGames(basegames);
	}

	public void restartGame() {
		this.playGamePanel1.clearLines();
	}

	public void setBaseGameToEdit(IBaseGame selectedBaseGame) {
		edit_BaseGame_Panel1.setBaseGame(selectedBaseGame);
	}

	public void setLevelToEdit(ILevel level) {
		editLevelPanel1.setLevel(level);
	}

	public void setGameToEdit(IGame game) {
		editGamePanel1.setGame(game);
	}

	public void setActualLineLength(double length) {
		playGamePanel1.setActualLineLenght(length);
	}

	public void setSelectedLevel(ILevel level) {
		level_BaseGame_SelectPanel1.setSelectedLevel(level);
	}

	public void setSelectedBaseGame(IBaseGame game) {
		level_BaseGame_SelectPanel1.setSelectedBaseGame(game);
	}

	public void setMessages(Collection<INotification> n) {
		messagePanel1.setMessages(n);
	}

	public void setNumberUnreadMessages(int nr) {
		this.btMessages.setText("<HTML>Messages <b>(" + nr + ")</b></HTML>");
	}

	public void setPlayStatistics(IPlayStatistic p) {
		playGamePanel1.setPlayStatistic(p);
	}
}
