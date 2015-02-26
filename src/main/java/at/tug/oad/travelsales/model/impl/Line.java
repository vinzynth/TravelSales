package at.tug.oad.travelsales.model.impl;

import static java.util.Objects.isNull;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.ILine;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.model.IUser;

/**
 * @author Leopold Christian - 1331948
 * 08.12.2014 - 11:46:26
 * 
 */
public class Line implements ILine {	
	
	private int id;
	private IPoint pointA;
	private IPoint pointB;
	private IUser user;
	private IBaseGame baseGame;

	@SuppressWarnings("unused")
	private Line(){}
	
	/**
	 * @param user
	 * @param baseGame
	 * @param pointA
	 * @param pointB
	 * 
	 * @throws IllegalArgumentException if a parameter is null
	 */
	public Line(final IUser user, final IBaseGame baseGame, final IPoint pointA, final IPoint pointB){
		if(isNull(user))
			throw new IllegalArgumentException("Parameter user is null");
		if(isNull(baseGame))
			throw new IllegalArgumentException("Parameter baseGame is null");
		if(isNull(pointA))
			throw new IllegalArgumentException("Parameter pointA is null");
		if(isNull(pointB))
			throw new IllegalArgumentException("Parameter pointB is null");
		
		this.user = user;
		this.baseGame = baseGame;
		this.pointA = pointA;
		this.pointB = pointB;
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
	 * @see at.tug.oad.travelsales.model.ILine#getPointA()
	 */
	@Override
	public IPoint getPointA() {
		return pointA;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.ILine#getPointB()
	 */
	@Override
	public IPoint getPointB() {
		return pointB;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.ILine#getUser()
	 */
	@Override
	public IUser getUser() {
		return user;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.ILine#getBaseGame()
	 */
	@Override
	public IBaseGame getBaseGame() {
		return baseGame;
	}

        @Override
        public double getLength() {
            double deltaX, deltaY;
            
            deltaX = pointA.getX() - pointB.getX();
            deltaY = pointA.getY() - pointB.getY();
            
            return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        }

        
	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private void setPointA(IPoint pointA) {
		this.pointA = pointA;
	}

	@SuppressWarnings("unused")
	private void setPointB(IPoint pointB) {
		this.pointB = pointB;
	}

	@SuppressWarnings("unused")
	private void setUser(IUser user) {
		this.user = user;
	}

	@SuppressWarnings("unused")
	private void setBaseGame(IBaseGame baseGame) {
		this.baseGame = baseGame;
	}
}
