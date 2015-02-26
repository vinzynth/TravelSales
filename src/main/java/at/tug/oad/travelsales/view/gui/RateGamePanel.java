/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package at.tug.oad.travelsales.view.gui;

import at.tug.oad.travelsales.controller.TravelSalesViewController;

/**
 *
 * @author Martin
 */
public class RateGamePanel extends javax.swing.JPanel {

	/**
	 * Creates new form RateGamePanel
	 */
	public RateGamePanel() {
		initComponents();
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

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		slStars = new javax.swing.JSlider();
		jPanel2 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();

		setLayout(new java.awt.BorderLayout());

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jLabel1.setText("You just finished the game! Please rate on a scale of 1 to 5 Stars:");
		jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

		slStars.setMajorTickSpacing(1);
		slStars.setMaximum(5);
		slStars.setMinimum(1);
		slStars.setMinorTickSpacing(1);
		slStars.setPaintLabels(true);
		slStars.setPaintTicks(true);
		slStars.setSnapToTicks(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
		jPanel1.add(slStars, gridBagConstraints);

		add(jPanel1, java.awt.BorderLayout.CENTER);

		jButton1.setText("Save & Back");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onSave(evt);
			}
		});
		jPanel2.add(jButton1);

		add(jPanel2, java.awt.BorderLayout.SOUTH);
	}// </editor-fold>//GEN-END:initComponents

	private void onSave(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_onSave
		TravelSalesViewController.getInstance().saveRating(slStars.getValue());
	}// GEN-LAST:event_onSave

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JSlider slStars;
	// End of variables declaration//GEN-END:variables
}
