/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package at.tug.oad.travelsales.view.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import at.tug.oad.travelsales.controller.TravelSalesViewController;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.ILevel;

/**
 *
 * @author Martin
 */
public class GameButton extends JButton {

	public GameButton(IGame game) {
		this.setText(game.getName());
		ImageIcon icon = new ImageIcon(game.getLogoPath());
		this.setIcon(icon);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);

		setSize();
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				TravelSalesViewController.getInstance().selectGame(game);
			}
		});
	}

	public GameButton(ILevel level) {
		this.setText(level.getName());

		setSize();
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				TravelSalesViewController.getInstance().selectLevel(level);
			}
		});
	}

	public GameButton(IBaseGame game) {
		this.setText(game.getName());

		// setSize();
		this.setSize(100, 75);
		Dimension d = new Dimension(100, 75);
		setMinimumSize(d);
		setPreferredSize(d);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				TravelSalesViewController.getInstance().selectBaseGame(game);
			}
		});
	}

	private void setSize() {
		this.setSize(75, 75);
		Dimension d = new Dimension(75, 75);
		setMinimumSize(d);
		setPreferredSize(d);
	}

}
