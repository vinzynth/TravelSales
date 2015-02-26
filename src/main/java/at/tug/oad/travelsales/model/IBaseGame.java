package at.tug.oad.travelsales.model;

import java.util.Date;
import java.util.List;

import at.chrl.nutils.interfaces.INestedCollection;
import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 18:50:46
 * 
 */
public interface IBaseGame extends INestedCollection<List<IPoint>, IPoint>, IPersistable {
	
	/**
	 * @return creator
	 */
	public IUser getCreator();
	
	/**
	 * @return Name of this {@link IBaseGame}
	 */
	public String getName();
	
	/**
	 * Sets name to given parameter
	 * @param name
	 * 			new name to set
	 */
	public void setName(String name);
	
	/**
	 * @return path to base game background figure
	 */
	public String getFigurePath();
	
	/**
	 * @param figurePath
	 * 			new figurePath
	 */
	public void setFigurePath(String figurePath);
	
	/**
	 * @return Collection with game constraints
	 */
	public List<IGameConstraint> getGameConstraints();
	
	/**
	 * @return account creation time
	 */
	public Date getCreateDate();
}
