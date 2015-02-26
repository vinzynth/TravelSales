package at.tug.oad.travelsales.model;

import java.util.Date;
import java.util.List;

import at.chrl.nutils.interfaces.INestedCollection;
import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 18:51:15
 * 
 */
public interface ILevel extends INestedCollection<List<IBaseGame>, IBaseGame>, IPersistable {

	/**
	 * @return creator
	 */
	public IUser getCreator();
	
	/**
	 * @return Name of this {@link ILevel}
	 */
	public String getName();
	
	/**
	 * Sets name to given parameter
	 * @param name
	 * 			new name to set
	 */
	public void setName(String name);
	
	/**
	 * @return account creation time
	 */
	public Date getCreateDate();
}
