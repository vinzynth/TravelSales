package at.tug.oad.travelsales.model.impl;

import java.util.Date;






import at.chrl.callbacks.metadata.ObjectCallback;
import at.tug.oad.travelsales.model.IPlayStatistic;
import at.tug.oad.travelsales.utils.database.PersistCallback;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:02:14
 * 
 */
public final class PlayStatistic implements IPlayStatistic {

	private int id;
	private int userId;
	private int baseGameId;
	private long playedTime;
	private boolean baseGameFinished;
	private long usedTurns;
	private int score;
	private Date playedDate;
	
	@SuppressWarnings("unused")
	private PlayStatistic() {}
	
	/**
	 * 
	 * @param userId
	 * @param playedTime
	 * @param finished
	 * @param usedTurns
	 * @param score
	 */
	public PlayStatistic(final int userId, final int baseGameId, final long playedTime, final boolean finished, final long usedTurns, final int score){
		this.setBaseGameId(baseGameId);
		this.playedDate = new Date();
		this.playedTime = playedTime;
		this.baseGameFinished = finished;
		this.usedTurns = usedTurns;
		this.score = score;
		this.userId = userId;
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
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#getUserId()
	 */
	@Override
	public int getUserId() {
		return userId;
	}
	
	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#getPlayedTime()
	 */
	@Override
	public long getPlayedTime() {
		return playedTime;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#isBaseGameFinished()
	 */
	@Override
	public boolean isBaseGameFinished() {
		return baseGameFinished;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#getUsedTurns()
	 */
	@Override
	public long getUsedTurns() {
		return usedTurns;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#getScore()
	 */
	@Override
	public int getScore() {
		return score;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#getPlayedDate()
	 */
	@Override
	public Date getPlayedDate() {
		return playedDate;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#setPlayedTime(long)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setPlayedTime(long playedTime) {
		this.playedTime = playedTime;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#isBaseGameFinished(boolean)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setBaseGameFinished(boolean baseGameFinished) {
		this.baseGameFinished = baseGameFinished;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#setUsedTurns(long)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setUsedTurns(long usedTurns) {
		this.usedTurns = usedTurns;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#setPlayedDate(java.util.Date)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setPlayedDate(Date playedDate) {
		this.playedDate = playedDate;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IPlayStatistic#setScore(int)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * @return the baseGameId
	 */
	public int getBaseGameId() {
		return baseGameId;
	}

	/**
	 * @param baseGameId the baseGameId to set
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setBaseGameId(int baseGameId) {
		this.baseGameId = baseGameId;
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " | ID: " + this.id + " | user: " + this.userId + " | " + this.playedDate + " | " + this.score + " | " + this.usedTurns + " | " + this.playedTime;
	}
	

	@SuppressWarnings("unused")
	private void setId(int id){
		this.id = id;
	}

	/**
	 * @param userId the userId to set
	 */
	@SuppressWarnings("unused")
	private void setUserId(int userId) {
		this.userId = userId;
	}
}
