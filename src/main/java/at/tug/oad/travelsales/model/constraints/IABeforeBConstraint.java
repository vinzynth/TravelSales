package at.tug.oad.travelsales.model.constraints;

import at.tug.oad.travelsales.model.IGameConstraint;
import at.tug.oad.travelsales.model.IPoint;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 19:57:00
 * 
 */
public interface IABeforeBConstraint extends IGameConstraint{
	
	public IPoint getPointA();
	
	public IPoint getPointB();
}
