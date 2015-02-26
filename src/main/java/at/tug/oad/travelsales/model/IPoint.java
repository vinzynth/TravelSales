package at.tug.oad.travelsales.model;

import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 18:50:22
 * 
 */
public interface IPoint extends IPersistable {

	/**
	 * Returns name of this point
	 * 
	 * @return name of point
	 */
	public String getName();
	
	/**
	 * Sets given name 
	 */
	public void setName(String name);
	
	/**
	 * Returns name of this point
	 * 
	 * @return name of point
	 */
	public String getIconPath();
	
	/**
	 * Sets given name 
	 */
	public void setIconPath(String iconPath);
	
	/**
	 * Returns x coordinate of point between 0 and 1
	 * 
	 * @return x coordinate of point
	 */
	public double getX();
	
	/**
	 * Sets given x coordinate
	 */
	public void setX(double x);
	
	/**
	 * Returns y coordinate of point between 0 and 1
	 * 
	 * @return y coordinate of point
	 */
	public double getY();
	
	/**
	 * Sets given y coordinate
	 */
	public void setY(double y);
	
	/**
	 * Returns z coordinate of point between 0 and 1
	 * 
	 * @default returns 0d
	 * 
	 * @return z coordinate of point
	 */
	public default double getZ(){
		return 0d;
	}
	
	/**
	 * Sets given z coordinate
	 * 
	 * @default does nothing
	 */
	public default void setZ(double z){}
}
