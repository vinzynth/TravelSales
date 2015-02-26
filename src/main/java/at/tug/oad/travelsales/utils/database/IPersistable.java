package at.tug.oad.travelsales.utils.database;



/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 18:57:19
 * 
 */
public abstract interface IPersistable {

	/**
	 * Returns unique database id of this object
	 *  
	 * @return unique Id
	 */
	public int getId();
}
