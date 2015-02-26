package at.tug.oad.travelsales.model;

import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 19:24:46
 * 
 */
public interface IRatingStatistic extends IPersistable{
	
	/**
	 * @return UserId
	 */
	public int getUserId();
	
	/**
	 * @return BaseGameId
	 */
	public int getBaseGameId();
	
	/**
	 * @return Rating
	 */
	public double getRating();
	
	/**
	 * @param rating
	 *			new rating to set
	 */
	public void setRating(double rating);
	
}
