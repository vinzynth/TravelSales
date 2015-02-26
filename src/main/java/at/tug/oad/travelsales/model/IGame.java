package at.tug.oad.travelsales.model;

import java.util.Date;
import java.util.List;

import at.chrl.nutils.interfaces.INestedCollection;
import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 18:51:42
 * 
 */
public interface IGame extends INestedCollection<List<ILevel>, ILevel>, IPersistable{
	
	/**
	 * @return creator
	 */
	public IUser getCreator();
	
	/**
	 * @return Name of this {@link Gane}
	 */
	public String getName();
	
	/**
	 * Sets name to given parameter
	 * @param name
	 * 			new name to set
	 */
	public void setName(String name);
	
	/**
	 * @return if game is public (<code>true</code>) or private(<code>false</code>)
	 */
	public boolean isVisible();
	
	/**
	 * @param visibility
	 * 			new game visibility state
	 */
	public void setVisible(boolean visibility);
	
	/**
	 * @return path to game logo
	 */
	public String getLogoPath();
	
	/**
	 * @param logoPath
	 * 			new logoPath
	 */
	public void setLogoPath(String logoPath);
	
	/**
	 * @return account creation time
	 */
	public Date getCreateDate();
}
