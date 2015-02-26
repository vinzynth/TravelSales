/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package at.tug.oad.travelsales.view.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import at.tug.oad.travelsales.controller.TravelSalesViewController;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;
import at.tug.oad.travelsales.view.state.EditState;

/**
 *
 * @author Martin
 */
public class EditBaseGamePanel extends javax.swing.JPanel {

	private BufferedImage background = null;
	private BufferedImage scaledBackground;
	private int x0 = 0, y0 = 0, width = 0, height = 0;
	private Insets insets;
	private IPoint selectedPoint = null;

	private int pointDrawDiameter = 1; // in % of width
	private PointInfoPanel panelToUpdate = null;
	private IBaseGame game;

	private EditState editstate;

	/**
	 * Creates new form EditGamePanel
	 */
	public EditBaseGamePanel() {
		initComponents();
	}

	@Override
	public void paint(Graphics grphcs) {
		super.paint(grphcs);

		Graphics2D g = (Graphics2D) grphcs;

		// Calcualte Panel size for drawing:
		insets = super.getInsets();
		x0 = insets.left;
		y0 = insets.top;
		width = super.getWidth() - insets.left - insets.right - 1;
		height = super.getHeight() - insets.top - insets.bottom - 1;
		pointDrawDiameter = 1 * width / 100; // Diameter of Circle in %
		// If there is an backgroundimage given, draw it:
		if (background != null) {
			if (scaledBackground == null) {
				scaleBackgroundImage();
			}
			g.drawImage(scaledBackground, x0, y0, this);
		}
		if (game != null) {
			// Draw all existing points in game
			for (IPoint pnt : game.getNestedCollection()) {
				int x = (int) (pnt.getX() * width);
				int y = (int) (pnt.getY() * height);
				try {
					if (pnt.getIconPath() != null && !pnt.getIconPath().isEmpty() && Files.probeContentType(new File(pnt.getIconPath()).toPath()).contains("image")) { // its
																																										// an
																																										// image
						Image icon = Toolkit.getDefaultToolkit().getImage(pnt.getIconPath());
						g.drawImage(icon, x - icon.getWidth(this) / 2, y - icon.getHeight(this) / 2, this);
					} else {

						g.setColor(Color.RED);
						g.fillOval(x0 + x - pointDrawDiameter / 2, y0 + y - pointDrawDiameter / 2, pointDrawDiameter, pointDrawDiameter);
					}
					if (pnt.equals(selectedPoint)) {
						g.setColor(Color.yellow);
						g.setStroke(new BasicStroke(3f));
						g.drawOval(x0 + x - pointDrawDiameter / 2, y0 + y - pointDrawDiameter / 2, pointDrawDiameter, pointDrawDiameter);
						g.setColor(Color.RED);
					}
				} catch (Exception e) {
					TravelSalesViewController.getInstance().showMessageDialog("Error", "ImageIcon could not be loaded\n" + e.getMessage(), MessageType.ERROR);
				}
			}
		}
	}

	/**
	 * Changes the background to the image given by path.
	 *
	 * @param path
	 *            The path to the image
	 * @throws TravelSalesException
	 *             If image does not exist
	 */
	private void setBackgroundImage(String path) throws TravelSalesException {
		try {
			// Toolkit toolkit = Toolkit.getDefaultToolkit();
			// background = toolkit.getImage(path);
			background = ImageIO.read(new File(path));
			if (background == null) {
				throw new TravelSalesException(TSExceptionType.INVALID_FILE, "Selected image does not exist!\n" + path);
			}

			scaleBackgroundImage(); // Scale Image to actual panel size
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Cannot open background image", e);
		}

	}

	/**
	 * Scales the given Backgroundimage to actual panel size.
	 */
	private void scaleBackgroundImage() {
		if (background != null && width != 0 && height != 0) {
			// BufferedImage tmp = (BufferedImage) background;
			scaledBackground = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			scaledBackground.getGraphics().drawImage(background.getScaledInstance(width, height, BufferedImage.SCALE_DEFAULT), 0, 0, null);
		}
	}

	public void setGame(IBaseGame game) throws TravelSalesException {
		this.game = game;
		if (game.getFigurePath() != null) {
			this.setBackgroundImage(game.getFigurePath());
		}
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

		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				onMouseClicked(evt);
			}
		});
		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				onResize(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
	}// </editor-fold>//GEN-END:initComponents

	private void onMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_onMouseClicked

		int x = evt.getX();
		int y = evt.getY();

		switch (editstate) {
			case NEW_POINT:
				double xp,
				yp;
				xp = (double) x / width;
				yp = (double) y / height;

				IPoint p = TravelSalesViewController.getInstance().newPoint("P", xp, yp);
				// IPoint p = new Point("P", xp, yp);

				TravelSalesViewController.getInstance().editBaseGame(game, g -> g.add(p));

				this.updateUI();
				break;

			case SELECT_POINT:
				TravelSalesViewController.getInstance().editBaseGame(game, g -> g.forEach(pnt -> {
					// for (IPoint pnt : game.getNestedCollection()) {
					if (checkSelected(x, y, pnt)) {
						selectedPoint = pnt;
						if (panelToUpdate != null) {
							panelToUpdate.setPoint(pnt);
						}
						this.updateUI();

					}
					// }
				}));
				break;

			case DELETE_POINT:
				TravelSalesViewController.getInstance().editBaseGame(game, g -> g.forEach(pnt -> {
					// for (IPoint pnt : game.getNestedCollection()) {
					if (checkSelected(x, y, pnt)) {

						if (JOptionPane.showConfirmDialog(this, "Sure you want to delete this point?", "Please confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							game.remove(pnt);
						}
						this.updateUI();

					}
					// }
				}));
				break;

		}
	}// GEN-LAST:event_onMouseClicked

	private void onResize(java.awt.event.ComponentEvent evt) {// GEN-FIRST:event_onResize
		scaleBackgroundImage();
	}// GEN-LAST:event_onResize

	private boolean checkSelected(int x, int y, IPoint point) {
		int px, py;
		px = (int) (point.getX() * width + x0);
		py = (int) (point.getY() * height + y0);

		if ((x >= px - pointDrawDiameter / 2 && x <= px + pointDrawDiameter / 2) && (y >= py - pointDrawDiameter / 2 && y <= py + pointDrawDiameter / 2)) {
			return true;
		}

		return false;
	}

	public void setPointInfoPanel(PointInfoPanel panelToUpdate) {
		this.panelToUpdate = panelToUpdate;
		panelToUpdate.setPanelToUpdate(this);
	}

	public void setEditState(EditState state) {
		this.editstate = state;
	}
	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables
}