/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package at.tug.oad.travelsales.view.gui;

import gnu.trove.set.hash.THashSet;

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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import at.tug.oad.travelsales.controller.PointSelection;
import at.tug.oad.travelsales.controller.TravelSalesViewController;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.ILine;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;

/**
 *
 * @author Martin
 */
public class GamePanel extends javax.swing.JPanel {

	private IBaseGame game;
	private PointSelection pointselection;

	private BufferedImage background = null;
	private BufferedImage scaledBackground;
	private int x0 = 0, y0 = 0, width = 0, height = 0;
	private Insets insets;
	private IPoint selectedPoint = null;

	private int pointDrawDiameter = 2; // in % of width

	/**
	 * Creates new form GamePanel
	 */
	public GamePanel() {
		initComponents();
	}

	public void setGame(IBaseGame game) {
		pointselection = new PointSelection();
		this.game = game;
		loadBackgroundImage();
		// updateView();
	}

	private void loadBackgroundImage() {
		try {
			if (game.getFigurePath().isEmpty()) {
				background = null;
			} else {
				background = ImageIO.read(new File(game.getFigurePath()));
				if (background == null) {
					throw new TravelSalesException(TSExceptionType.INVALID_FILE, "Selected image does not exist!\n" + game.getFigurePath());

				}

				scaleBackgroundImage(); // Scale Image to actual panel size
			}
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Cannot open background image\n" + e.getMessage(), e);
		}
	}

	public Collection<ILine> getLines() {
		return pointselection.getLines();
	}

	public void clearLines() {
		pointselection.clearLines();
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

		// Draw all existing lines in game
		for (ILine line : pointselection.getLines()) {
			g.setStroke(new BasicStroke(3f));

			int xa, ya, xb, yb;
			IPoint pointA = line.getPointA();
			xa = (int) (x0 + pointA.getX() * width);
			ya = (int) (y0 + pointA.getY() * height);

			IPoint pointB = line.getPointB();
			xb = (int) (x0 + pointB.getX() * width);
			yb = (int) (y0 + pointB.getY() * height);

			g.drawLine(xa, ya, xb, yb);
		}

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
				if (pnt.equals(pointselection.getSelectedPoint())) {
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

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		pnPoints = new javax.swing.JPanel();

		addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
			public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
			}

			public void ancestorResized(java.awt.event.HierarchyEvent evt) {
				onResize(evt);
			}
		});
		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				onMouseClicked(evt);
			}
		});
		setLayout(new java.awt.BorderLayout());

		pnPoints.setBackground(new java.awt.Color(204, 255, 255));
		pnPoints.setLayout(null);
		add(pnPoints, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	private void onMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_onMouseClicked
		int x = evt.getX();
		int y = evt.getY();

		TravelSalesViewController.getInstance().editBaseGame(game, g -> g.forEach(pnt -> {
			if (checkSelected(x, y, pnt)) {
				pointselection.selectPoint(pnt);
				this.updateUI();
			}
		}));
		TravelSalesViewController.getInstance().setActualLineLength(calculateLineLength());
		checkEnd();

	}// GEN-LAST:event_onMouseClicked

	private void checkEnd() {
		/*
		 * boolean foundPointInLine = false;
		 * 
		 * for (IPoint pnt : game) { for (ILine line :
		 * pointselection.getLines()){ if(pnt.equals(line.getPointA()) ||
		 * pnt.equals(line.getPointB())){ foundPointInLine = true; break; } }
		 * if(foundPointInLine == false) //Point not connected return; }
		 */

		Map<IPoint, List<ILine>> groupedByStartingPoint = pointselection.getLines().stream().collect(Collectors.groupingBy(l -> l.getPointA()));
		IPoint randomPoint = game.stream().findAny().get();
		Set<IPoint> checkedPoints = new THashSet<>();
		checkedPoints.add(randomPoint);
		while (true) {
			List<ILine> lines = groupedByStartingPoint.get(randomPoint);
			if (lines == null || lines.size() <= 0) {
				break;
			}
			randomPoint = lines.get(0).getPointB();
			checkedPoints.add(randomPoint);
		}
		if (checkedPoints.size() == game.size()) {
			TravelSalesViewController.getInstance().finishedGame(calculateLineLength());
		}
	}

	private double calculateLineLength() {
		double length = 0;
		for (ILine line : pointselection.getLines()) {
			length += line.getLength();
		}
		return length;
	}

	private void onResize(java.awt.event.HierarchyEvent evt) {// GEN-FIRST:event_onResize
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

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel pnPoints;
	// End of variables declaration//GEN-END:variables
}
