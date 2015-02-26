package at.tug.oad.travelsales.model.impl.constraints;

import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGameConstraint;
import at.tug.oad.travelsales.model.IPoint;

/**
 * @author Leopold Christian - 1331948
 * 24.11.2014 - 20:40:18
 * 
 */
public class GameConstraint implements IGameConstraint {

	protected int id;

	protected GameConstraint() {}
	
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
	 * @see at.tug.oad.travelsales.model.IGameConstraint#validate(at.tug.oad.travelsales.model.IBaseGame, at.tug.oad.travelsales.model.IPoint)
	 */
	@Override
	public boolean validate(IBaseGame baseGame, IPoint point) {
		return false;
	}


	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

}
