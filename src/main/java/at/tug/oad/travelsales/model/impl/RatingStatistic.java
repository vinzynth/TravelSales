package at.tug.oad.travelsales.model.impl;

import at.chrl.callbacks.metadata.ObjectCallback;
import at.tug.oad.travelsales.model.IRatingStatistic;
import at.tug.oad.travelsales.utils.database.PersistCallback;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:02:29
 * 
 */
public final class RatingStatistic implements IRatingStatistic {

	private int id;
	private int userId;
	private int baseGameId;
	private double rating;

	@SuppressWarnings("unused")
	private RatingStatistic() {}
	
	/**
	 * @param userId
	 * @param baseGameId
	 * @param rating
	 * 
	 * @throws IllegalArgumentException if rating is < 0d or > 1d
	 */
	public RatingStatistic(final int userId, final int baseGameId, final double rating){
		if(rating < 0d || rating > 1d)
			throw new IllegalArgumentException("Rating is out of bounds(should by >= 0d && <= 1d: " + rating);
		
		this.rating = rating;
		this.userId = userId;
		this.baseGameId = baseGameId;
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
	 * @see at.tug.oad.travelsales.model.IRatingStatistic#getUserId()
	 */
	@Override
	public int getUserId() {
		return userId;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IRatingStatistic#getBaseGameId()
	 */
	@Override
	public int getBaseGameId() {
		return baseGameId;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IRatingStatistic#getRating()
	 */
	@Override
	public double getRating() {
		return rating;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IRatingStatistic#setRating(double)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setRating(double rating) {
		this.rating = rating;
	}

	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private void setUserId(int userId) {
		this.userId = userId;
	}

	@SuppressWarnings("unused")
	private void setBaseGameId(int baseGameId) {
		this.baseGameId = baseGameId;
	}
	
}
