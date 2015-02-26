package at.tug.oad.travelsales.model.impl.constraints;

import java.util.Objects;

import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.model.constraints.IABeforeBConstraint;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:05:39
 * 
 */
public class ABeforeBConstraint extends GameConstraint implements IABeforeBConstraint {

	private IPoint pointA;
	private IPoint pointB;

	@SuppressWarnings("unused")
	private ABeforeBConstraint() {}
	
	/**
	 * @param pointA
	 * @param pointB
	 * 
	 * @throws IllegalArgumentException if one parameter is null
	 */
	public ABeforeBConstraint(final IPoint pointA, final IPoint pointB){
		if(Objects.isNull(pointA))
			throw new IllegalArgumentException("Parametr point A is null");
		if(Objects.isNull(pointB))
			throw new IllegalArgumentException("Parametr point B is null");
		
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IGameConstraint#validate(at.tug.oad.travelsales.model.IBaseGame, at.tug.oad.travelsales.model.IPoint)
	 */
	@Override
	public boolean validate(IBaseGame baseGame, IPoint point) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.constraints.IABeforeBConstraint#getPointA()
	 */
	@Override
	public IPoint getPointA() {
		return pointA;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.constraints.IABeforeBConstraint#getPointB()
	 */
	@Override
	public IPoint getPointB() {
		return pointB;
	}

}
