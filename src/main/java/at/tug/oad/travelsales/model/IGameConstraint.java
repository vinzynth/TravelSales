package at.tug.oad.travelsales.model;

import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 19:09:45
 * 
 */
public abstract interface IGameConstraint extends IPersistable {

	/**
	 * Checks if {@link IUser} can connect to given {@link IPoint}
	 * 
	 * @param baseGame
	 * 			{@link IBaseGame} player is playing
	 * @param point
	 * 			given {@link IPoint}
	 * @return
	 * 			<code>true</code> if turn is valid
	 * 			<code>false</code> otherwise
	 */
	public boolean validate(IBaseGame baseGame, IPoint point);
}
