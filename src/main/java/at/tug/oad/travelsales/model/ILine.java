package at.tug.oad.travelsales.model;

import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 08.12.2014 - 11:45:34
 * 
 */
public interface ILine extends IPersistable {

	public IPoint getPointA();
	public IPoint getPointB();
	public IUser getUser();
	public IBaseGame getBaseGame();
        public double getLength();
}
