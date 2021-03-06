/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package at.tug.oad.travelsales.view.gui;

import java.util.Collection;

import at.tug.oad.travelsales.controller.TravelSalesViewController;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.ILevel;

/**
 *
 * @author Martin
 */
public class Level_BaseGame_SelectPanel extends javax.swing.JPanel {

	/**
	 * Creates new form Level_BaseGame_SelectPanel
	 */
	public Level_BaseGame_SelectPanel() {
		initComponents();
	}

	public void setLevels(Collection<ILevel> levels) {
		this.levelView.setLevels(levels);
	}

	public void setBaseGames(Collection<IBaseGame> basegames) {
		this.baseGameView.setBaseGames(basegames);
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

		pnCenter = new javax.swing.JPanel();
		pnLevelSelect = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		levelView = new at.tug.oad.travelsales.view.gui.GameView();
		pnBaseGameSelect = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		baseGameView = new at.tug.oad.travelsales.view.gui.GameView();
		jPanel1 = new javax.swing.JPanel();
		pnSouth = new javax.swing.JPanel();
		btPlay = new javax.swing.JButton();
		jButton1 = new javax.swing.JButton();
		btEdit = new javax.swing.JButton();
		btCancel = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		lbSelectedLevel = new javax.swing.JLabel();
		lbSelectedBaseGame = new javax.swing.JLabel();

		setLayout(new java.awt.BorderLayout());

		pnCenter.setLayout(new java.awt.GridLayout(1, 0));

		pnLevelSelect.setBorder(javax.swing.BorderFactory.createTitledBorder("Please select a Level:"));
		pnLevelSelect.setLayout(new java.awt.BorderLayout());

		jScrollPane1.setViewportView(levelView);

		pnLevelSelect.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		pnCenter.add(pnLevelSelect);

		pnBaseGameSelect.setBorder(javax.swing.BorderFactory.createTitledBorder("Select a Base-Game:"));
		pnBaseGameSelect.setLayout(new java.awt.BorderLayout());

		jScrollPane2.setViewportView(baseGameView);

		pnBaseGameSelect.add(jScrollPane2, java.awt.BorderLayout.CENTER);

		pnCenter.add(pnBaseGameSelect);

		add(pnCenter, java.awt.BorderLayout.CENTER);

		jPanel1.setLayout(new java.awt.BorderLayout());

		btPlay.setText("Play");
		btPlay.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onPlay(evt);
			}
		});
		pnSouth.add(btPlay);

		jButton1.setText("Edit Level");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onEditLevel(evt);
			}
		});
		pnSouth.add(jButton1);

		btEdit.setText("Edit Base-Game");
		btEdit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onEdit(evt);
			}
		});
		pnSouth.add(btEdit);

		btCancel.setText("Cancel");
		btCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onCancel(evt);
			}
		});
		pnSouth.add(btCancel);

		jPanel1.add(pnSouth, java.awt.BorderLayout.SOUTH);

		jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

		lbSelectedLevel.setText("Selected Level: -");
		jPanel2.add(lbSelectedLevel);

		lbSelectedBaseGame.setText("Selected Base-Game: -");
		jPanel2.add(lbSelectedBaseGame);

		jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

		add(jPanel1, java.awt.BorderLayout.SOUTH);
	}// </editor-fold>//GEN-END:initComponents

	private void onCancel(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onCancel
		TravelSalesViewController.getInstance().cancel();
	}// GEN-LAST:event_onCancel

	private void onPlay(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onPlay
		TravelSalesViewController.getInstance().playSelectedBaseGame();
	}// GEN-LAST:event_onPlay

	private void onEdit(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onEdit
		TravelSalesViewController.getInstance().editSelectedBaseGame();
	}// GEN-LAST:event_onEdit

	private void onEditLevel(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onEditLevel
		TravelSalesViewController.getInstance().editSelectedLevel();
	}// GEN-LAST:event_onEditLevel

	public void setSelectedLevel(ILevel level) {
		lbSelectedLevel.setText("<HTML><b>Selected Level: </b>" + level.getName() + "</HTML>");
	}

	public void setSelectedBaseGame(IBaseGame bgame) {
		lbSelectedBaseGame.setText("<HTML><b>Selected Base-Game: </b>" + bgame.getName() + "</HTML>");
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private at.tug.oad.travelsales.view.gui.GameView baseGameView;
	private javax.swing.JButton btCancel;
	private javax.swing.JButton btEdit;
	private javax.swing.JButton btPlay;
	private javax.swing.JButton jButton1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JLabel lbSelectedBaseGame;
	private javax.swing.JLabel lbSelectedLevel;
	private at.tug.oad.travelsales.view.gui.GameView levelView;
	private javax.swing.JPanel pnBaseGameSelect;
	private javax.swing.JPanel pnCenter;
	private javax.swing.JPanel pnLevelSelect;
	private javax.swing.JPanel pnSouth;
	// End of variables declaration//GEN-END:variables
}
