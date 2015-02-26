package at.tug.oad.travelsales.model;

import java.util.Date;
import java.util.Set;

import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 19:10:01
 * 
 */
public interface IUser extends IPersistable {

	/**
	 * @return account name - for login 
	 */
	public String getAccountName();
	
	/**
	 * @return user name
	 */
	public String getUserName();
	
	/**
	 * @return user name
	 */
	public void setUserName(String userName);
	
	/**
	 * @return mail address
	 */
	public String getMailAddress();
	
	/**
	 * @param mailAddress
	 * 			new mail address
	 */
	public void setMailAddress(String mailAddress);
	
	/**
	 * @return hashed password
	 */
	public String getPassword();
	
	/**
	 * @param password
	 * 			new user password
         *                      used by hibernate
	 */
	public void setPassword(String password);
	
        /**
	 * @param password
	 * 			new user password
	 */
	public void newPassword(String password);
        
	/**
	 * @return profile information
	 */
	public String getProfileInformation();
	
	/**
	 * @param profileInformation
	 * 			new profile Information
	 */
	public void setProfileInformation(String profileInformation);
	
	
	/**
	 * @return account creation time
	 */
	public Date getCreateDate();
	
	/**
	 * @return if account is active
	 */
	public boolean isActive();
	
	/**
	 * @param active
	 */
	public void setActive(boolean active);
	
	/**
	 * Returns user relations
	 * @return
	 */
	public Set<IUser> getFriendList();
	
	/**
	 * @return
	 * 		users {@link UserType}
	 */
	public UserType getUserType();
	
	/**
	 * Sets given {@link UserType}
	 * 
	 * @param type
	 * 			new {@link UserType} to set
	 */
	public void setUserType(UserType type);
}
