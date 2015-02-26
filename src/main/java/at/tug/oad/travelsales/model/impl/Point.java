package at.tug.oad.travelsales.model.impl;

import static java.util.Objects.isNull;

import java.io.File;

import at.chrl.callbacks.metadata.ObjectCallback;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;
import at.tug.oad.travelsales.utils.database.PersistCallback;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:01:23
 * 
 */
public final class Point implements IPoint {

	private int id;
	private String name;
	private double x;
	private double y;
	private String iconPath;

	@SuppressWarnings("unused")
	private Point() {}
	
	/**
	 * @param name
	 * @param x
	 * @param y
	 * 
	 * @throws IllegalArgumentException <br>
	 * 				- if name is null or a empty string or too long
	 * 				- if x or y is < 0d or > 1d
	 */
	public Point(final String name, final String iconPath, final double x, final double y) {
		if(isNull(name))
			throw new IllegalArgumentException("Parameter name is null");

		if(name.isEmpty())
			throw new IllegalArgumentException("Parameter name is a empty string");
		if(iconPath.isEmpty())
			throw new IllegalArgumentException("Parameter iconPath is a empty string");

		if(iconPath.length() >= 255)
			throw new IllegalArgumentException("Parameter iconPath is too long");
		if(name.length() >= 255)
			throw new IllegalArgumentException("Parameter name is too long");
		
		if(x < 0d || x > 1d)
			throw new IllegalArgumentException("X coordinate is out of bounds(should by >= 0d && <= 1d: " + x);
		if(y < 0d || y > 1d)
			throw new IllegalArgumentException("Y coordinate is out of bounds(should by >= 0d && <= 1d: " + y);
		
		File f;
		if(!(f = new File(iconPath)).canRead() || f.isDirectory())
			this.iconPath = "";
//			try {
//				throw new FileNotFoundException("Given logo file does not exist: " + f.getAbsolutePath());
//			} catch (FileNotFoundException e) {
//				throw new TravelSalesException(TSExceptionType.INVALID_FILE, e.getMessage(), e);
//			}
		else
			try {
				String s = f.getCanonicalPath();
				if(s.length() >= 255)
					throw new IllegalArgumentException("Parameter iconPath is too long");
				this.iconPath = s;
			} catch (Exception e) {
				throw new TravelSalesException(TSExceptionType.INVALID_FILE, e.getMessage(), e);
			}
		
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @param name
	 * @param x
	 * @param y
	 * 
	 * @throws IllegalArgumentException <br>
	 * 				- if name is null or a empty string or too long
	 * 				- if x or y is < 0d or > 1d
	 */
	public Point(final String name, final double x, final double y) {
		if(isNull(name))
			throw new IllegalArgumentException("Parameter name is null");
		
		if(name.isEmpty())
			throw new IllegalArgumentException("Parameter name is a empty string");

		if(name.length() >= 255)
			throw new IllegalArgumentException("Parameter name is too long");
		
		if(x < 0d || x > 1d)
			throw new IllegalArgumentException("X coordinate is out of bounds(should by >= 0d && <= 1d: " + x);
		if(y < 0d || y > 1d)
			throw new IllegalArgumentException("Y coordinate is out of bounds(should by >= 0d && <= 1d: " + y);
		
		this.name = name;
		this.x = x;
		this.y = y;
		this.iconPath = "";
	}
	
	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.utils.database.IPersistable#getId()
	 */
	@Override
	public int getId() {
		return id;
	}


	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPoint#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPoint#setName(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPoint#getX()
	 */
	@Override
	public double getX() {
		return x;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPoint#setX(double)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPoint#getY()
	 */
	@Override
	public double getY() {
		return y;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPoint#setY(double)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setY(double y) {
		this.y = y;
	}
	

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPoint#getIconPath()
	 */
	@Override
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPoint#setIconPath(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " | ID: " + this.id + " | Name: " + this.name + " | x/y: " + this.x + "/" + this.y + " | iconPath: " + this.iconPath;
	}

	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

}
