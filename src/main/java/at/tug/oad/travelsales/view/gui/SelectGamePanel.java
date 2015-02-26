/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package at.tug.oad.travelsales.view.gui;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;

import at.tug.oad.travelsales.controller.TravelSalesViewController;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;

/**
 *
 * @author Martin
 */
public class SelectGamePanel extends javax.swing.JPanel {
	private ArrayList<IGame> gamesToDelete;

	/**
	 * Creates new form SelectGamePanel
	 */
	public SelectGamePanel() {
		initComponents();
	}

	public void setMyGames(Collection<IGame> myGames) {
		this.gvMyGames.setGames(myGames);
	}

	public void setAllGames(Collection<IGame> allGames) {
		this.gvAllGames.setGames(allGames);
	}

	void setRecommendedGames(Collection<IGame> games) {
		this.gvRecommendedGames.setGames(games);
	}

	public void setWelcomeText(String text) {
		lbWelcomeText.setText(text);
	}

	public void adminFunctionsVisible(boolean visible) {
		pnManage.setEnabled(visible);

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
		java.awt.GridBagConstraints gridBagConstraints;

		pnStatus = new javax.swing.JPanel();
		lbWelcomeText = new javax.swing.JLabel();
		pnTabs = new javax.swing.JTabbedPane();
		pnMyGames = new javax.swing.JPanel();
		gvMyGames = new at.tug.oad.travelsales.view.gui.GameView();
		jPanel4 = new javax.swing.JPanel();
		btCreateGame = new javax.swing.JButton();
		jLabel5 = new javax.swing.JLabel();
		tbEditMode = new javax.swing.JToggleButton();
		pnRecommendedGames = new javax.swing.JPanel();
		gvRecommendedGames = new at.tug.oad.travelsales.view.gui.GameView();
		pnAllGames = new javax.swing.JPanel();
		gvAllGames = new at.tug.oad.travelsales.view.gui.GameView();
		pnManage = new javax.swing.JTabbedPane();
		pnDeleteUser = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		tfUserToDeactivate = new javax.swing.JTextField();
		jButton2 = new javax.swing.JButton();
		pnDeleteGame = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jButton3 = new javax.swing.JButton();
		tfGameToDelete = new javax.swing.JTextField();
		jButton4 = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		liGamesToDelete = new javax.swing.JList();
		pnAdminNotification = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		taMessage = new javax.swing.JTextArea();
		pnUserSelect = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		tfUsername = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();

		setLayout(new java.awt.BorderLayout());

		lbWelcomeText.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		lbWelcomeText.setText("Welcome <username>!");
		pnStatus.add(lbWelcomeText);

		add(pnStatus, java.awt.BorderLayout.NORTH);

		pnTabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);

		pnMyGames.setLayout(new java.awt.BorderLayout());
		pnMyGames.add(gvMyGames, java.awt.BorderLayout.CENTER);

		jPanel4.setLayout(new java.awt.BorderLayout());

		btCreateGame.setText("Create Game");
		btCreateGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onCreateGame(evt);
			}
		});
		jPanel4.add(btCreateGame, java.awt.BorderLayout.SOUTH);

		jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel5.setText("Activate edit-mode and select a game to edit:");
		jPanel4.add(jLabel5, java.awt.BorderLayout.LINE_START);

		tbEditMode.setText("Edit mode");
		tbEditMode.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onEditMode(evt);
			}
		});
		jPanel4.add(tbEditMode, java.awt.BorderLayout.CENTER);

		pnMyGames.add(jPanel4, java.awt.BorderLayout.SOUTH);

		pnTabs.addTab("My Games", pnMyGames);

		pnRecommendedGames.setLayout(new java.awt.BorderLayout());
		pnRecommendedGames.add(gvRecommendedGames, java.awt.BorderLayout.PAGE_START);

		pnTabs.addTab("Recommended Games", pnRecommendedGames);

		pnAllGames.setLayout(new java.awt.BorderLayout());
		pnAllGames.add(gvAllGames, java.awt.BorderLayout.CENTER);

		pnTabs.addTab("All Games", pnAllGames);

		pnDeleteUser.setLayout(new java.awt.BorderLayout());

		jPanel2.setLayout(new java.awt.GridBagLayout());

		jLabel3.setText("Username:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		jPanel2.add(jLabel3, gridBagConstraints);

		tfUserToDeactivate.setColumns(20);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel2.add(tfUserToDeactivate, gridBagConstraints);

		jButton2.setText("Deactivate");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onDeactivateUser(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		jPanel2.add(jButton2, gridBagConstraints);

		pnDeleteUser.add(jPanel2, java.awt.BorderLayout.CENTER);

		pnManage.addTab("Deactivate User", pnDeleteUser);

		pnDeleteGame.setLayout(new java.awt.BorderLayout());

		jPanel3.setLayout(new java.awt.GridBagLayout());

		jLabel4.setText("Name:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		jPanel3.add(jLabel4, gridBagConstraints);

		jButton3.setText("Delete Game");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onDeleteGame(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		jPanel3.add(jButton3, gridBagConstraints);

		tfGameToDelete.setColumns(20);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel3.add(tfGameToDelete, gridBagConstraints);

		jButton4.setText("Search");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onSearchGamesToDelete(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel3.add(jButton4, gridBagConstraints);

		liGamesToDelete.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jScrollPane2.setViewportView(liGamesToDelete);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel3.add(jScrollPane2, gridBagConstraints);

		pnDeleteGame.add(jPanel3, java.awt.BorderLayout.CENTER);

		pnManage.addTab("Delete Game", pnDeleteGame);

		pnAdminNotification.setLayout(new java.awt.BorderLayout());

		jPanel1.setLayout(new java.awt.BorderLayout());

		taMessage.setColumns(20);
		taMessage.setRows(5);
		jScrollPane1.setViewportView(taMessage);

		jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		pnUserSelect.setLayout(new javax.swing.BoxLayout(pnUserSelect, javax.swing.BoxLayout.PAGE_AXIS));

		jLabel2.setText("Enter a username:");
		pnUserSelect.add(jLabel2);

		tfUsername.setColumns(20);
		pnUserSelect.add(tfUsername);

		jPanel1.add(pnUserSelect, java.awt.BorderLayout.PAGE_END);

		pnAdminNotification.add(jPanel1, java.awt.BorderLayout.CENTER);

		jLabel1.setText("Please enter a message:");
		pnAdminNotification.add(jLabel1, java.awt.BorderLayout.PAGE_START);

		jButton1.setText("Send message to user");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onSendAdminNotification(evt);
			}
		});
		pnAdminNotification.add(jButton1, java.awt.BorderLayout.PAGE_END);

		pnManage.addTab("Send notification to user", pnAdminNotification);

		pnTabs.addTab("Manage <Admin only>", pnManage);

		add(pnTabs, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	private void onCreateGame(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onCreateGame
		TravelSalesViewController.getInstance().createGame();
	}// GEN-LAST:event_onCreateGame

	private void onSendAdminNotification(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onSendAdminNotification
		TravelSalesViewController.getInstance().sendNotification(tfUsername.getText(), taMessage.getText());
	}// GEN-LAST:event_onSendAdminNotification

	private void onDeactivateUser(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onDeactivateUser
		TravelSalesViewController.getInstance().deactivateUser(tfUserToDeactivate.getText());
	}// GEN-LAST:event_onDeactivateUser

	private void onSearchGamesToDelete(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onSearchGamesToDelete
		String name = tfGameToDelete.getText();
		DefaultListModel model = new DefaultListModel();
		liGamesToDelete.setModel(model);

		TravelSalesViewController.getInstance().getGamesByName(name, g -> g.forEach(game -> {
			model.addElement(game.getName() + " (Creator: " + game.getCreator().getUserName() + ")");
		}));
	}// GEN-LAST:event_onSearchGamesToDelete

	private void onDeleteGame(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onDeleteGame
		try {
			int index = liGamesToDelete.getSelectedIndex();
			if (index < 0) {
				throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "No game selected!");
			}
			gamesToDelete = new ArrayList<>();

			TravelSalesViewController.getInstance().getGamesByName(tfGameToDelete.getText(), g -> g.forEach(gme -> {
				gamesToDelete.add(gme);
			}));

			TravelSalesViewController.getInstance().deleteGame(gamesToDelete.get(index));
		} catch (Exception e) {
			TravelSalesViewController.getInstance().showMessageDialog("Error", e.getMessage(), MessageType.ERROR);
		}
	}// GEN-LAST:event_onDeleteGame

	private void onEditMode(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onEditMode
		TravelSalesViewController.getInstance().setEditGameMode(tbEditMode.isSelected());
		if (tbEditMode.isSelected()) {
			tbEditMode.setText("Edit mode (activated)");

		} else {
			tbEditMode.setText("Edit mode (deactivated)");
		}
	}// GEN-LAST:event_onEditMode

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btCreateGame;
	private at.tug.oad.travelsales.view.gui.GameView gvAllGames;
	private at.tug.oad.travelsales.view.gui.GameView gvMyGames;
	private at.tug.oad.travelsales.view.gui.GameView gvRecommendedGames;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JLabel lbWelcomeText;
	private javax.swing.JList liGamesToDelete;
	private javax.swing.JPanel pnAdminNotification;
	private javax.swing.JPanel pnAllGames;
	private javax.swing.JPanel pnDeleteGame;
	private javax.swing.JPanel pnDeleteUser;
	private javax.swing.JTabbedPane pnManage;
	private javax.swing.JPanel pnMyGames;
	private javax.swing.JPanel pnRecommendedGames;
	private javax.swing.JPanel pnStatus;
	private javax.swing.JTabbedPane pnTabs;
	private javax.swing.JPanel pnUserSelect;
	private javax.swing.JTextArea taMessage;
	private javax.swing.JToggleButton tbEditMode;
	private javax.swing.JTextField tfGameToDelete;
	private javax.swing.JTextField tfUserToDeactivate;
	private javax.swing.JTextField tfUsername;
	// End of variables declaration//GEN-END:variables

}