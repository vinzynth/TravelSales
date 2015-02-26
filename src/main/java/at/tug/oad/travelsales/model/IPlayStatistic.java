package at.tug.oad.travelsales.model;

import java.util.Date;

import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 19:22:42
 * 
 */
public interface IPlayStatistic extends IPersistable{
	
	/**
	 * @return userId
	 */
	public int getUserId();
	
	/**
	 * @return playedTime
	 */
	public long getPlayedTime();
	
	/**
	 * @return baseGameFinished
	 */
	public boolean isBaseGameFinished();
	
	/**
	 * @return usedTurns
	 */
	public long getUsedTurns();
	
	/**
	 * @return score for highscore lists
	 */
	public int getScore();
	
	/**
	 * @return playedDate
	 */
	public Date getPlayedDate();
	
	/**
	 * @return the baseGameId
	 */
	public int getBaseGameId();

	/**
	 * @param baseGameId the baseGameId to set
	 */
	public void setBaseGameId(int baseGameId);
	
	/**
	 * @param playedTime
	 */
	public void setPlayedTime(long playedTime);
	
	/**
	 * @param baseGameFinished
	 */
	public void setBaseGameFinished(boolean baseGameFinished);
	
	/**
	 * @param usedTurns
	 */
	public void setUsedTurns(long usedTurns);
	
	/**
	 * @param playedDate
	 */
	public void setPlayedDate(Date playedDate);
	
	/**
	 * @param score
	 */
	public void setScore(int score);
}
