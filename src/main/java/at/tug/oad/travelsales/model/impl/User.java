package at.tug.oad.travelsales.model.impl;

import static java.util.Objects.isNull;
import gnu.trove.set.hash.THashSet;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Set;

import at.chrl.callbacks.metadata.ObjectCallback;
import at.chrl.nutils.PasswordHash;
import at.chrl.utils.StringUtils;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.model.UserType;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;
import at.tug.oad.travelsales.utils.database.PersistCallback;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:00:58
 * 
 */
public final class User implements IUser {

	private int id;
	private String password;
	private Date createDate;
	private String accountName;
	private String userName;
	private String mailAddress;
	private String profileInformation;
	private boolean active;
	private UserType userType;
	private Set<User> friendList;

	
	@SuppressWarnings("unused")
	private User() {}
	
	/**
	 * @param password
	 * @param accountName
	 * @param userName
	 * @param mailAddress
	 * @param type
	 * 
	 * @throws IllegalArgumentException <br>
	 * 				- if type is null <br>
	 * 				- if password is null or a empty string or too long<br>
	 * 				- if accountname is null or a empty string or too long<br>
	 * 				- if username is null or a empty string or too long<br>
	 * 				- if mailAddress is null or a empty string or too long<br>
	 * @throws TravelSalesException <br>
	 * 				- if email address is invalid (type {@link TSExceptionType#INVALID_MAIL_ADDRESS}) <br>
	 * 				- if password could not be hashed properly (type {@link TSExceptionType#INVALID_PASSWORD})
	 */
	public User(final String password, final String accountName, final String userName, final String mailAddress, final UserType type) {
		if(isNull(accountName))
			throw new IllegalArgumentException("Parameter accountName is null");
		if(isNull(userName))
			throw new IllegalArgumentException("Parameter userName is null");
		if(isNull(type))
			throw new IllegalArgumentException("Parameter type is null");
		if(isNull(mailAddress))
			throw new IllegalArgumentException("Parameter mailAddress is null");
		if(isNull(password))
			throw new IllegalArgumentException("Parameter password is null");
		
		if(accountName.isEmpty())
			throw new IllegalArgumentException("Parameter accountName is a empty string");
		if(userName.isEmpty())
			throw new IllegalArgumentException("Parameter userName is a empty string");
		if(mailAddress.isEmpty())
			throw new IllegalArgumentException("Parameter mailAddress is a empty string");
		if(password.isEmpty())
			throw new IllegalArgumentException("Parameter password is a empty string");
		
		if(accountName.length() >= 255)
			throw new IllegalArgumentException("Parameter accountName is too long");
		if(userName.length() >= 255)
			throw new IllegalArgumentException("Parameter userName is too long");
		if(mailAddress.length() >= 255)
			throw new IllegalArgumentException("Parameter mailAddress is too long");
		if(password.length() >= 255)
			throw new IllegalArgumentException("Parameter password is too long");
		
		if(StringUtils.countMatches(mailAddress, '@') != 1)
			throw new TravelSalesException(TSExceptionType.INVALID_MAIL_ADDRESS, "Invalid Mail address format: " + mailAddress);
		
		if(StringUtils.countMatches(mailAddress.substring(mailAddress.indexOf('@')), ".") != 1)
			throw new TravelSalesException(TSExceptionType.INVALID_MAIL_ADDRESS, "Invalid Mail address format: " + mailAddress);
		
		try {
			this.password = PasswordHash.createHash(password);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new TravelSalesException(TSExceptionType.INVALID_PASSWORD, "Password couldn't be hashed", e);
		}
		
		// FIXME: check if account name already exists
		this.createDate = new Date();
		this.friendList = new THashSet<>();
		this.accountName = accountName;
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.userType = type;
		this.active = true;
	}
	
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
	 * @see at.tug.oad.travelsales.model.IUser#getAccountName()
	 */
	@Override
	public String getAccountName() {
		return accountName;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#getUserName()
	 */
	@Override
	public String getUserName() {
		return userName;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#setUserName(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#getMailAddress()
	 */
	@Override
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#setMailAddress(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#setPassword(int)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setPassword(String password) {
            this.password = password;
	}
        
	public void newPassword(String password){
                try {
                    System.out.println(this.password);
                    System.out.println(password);
			setPassword(PasswordHash.createHash(password));
                        System.out.println(this.password);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new TravelSalesException(TSExceptionType.INVALID_PASSWORD, "Password couldn't be hashed", e);
		}
        }

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#getCreateDate()
	 */
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#isActive()
	 */
	@Override
	public boolean isActive() {
		return active;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#setActive(boolean)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setActive(boolean active) {
		this.active = active;

	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#getFriendList()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set getFriendList() {
		return friendList;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#getUserType()
	 */
	@Override
	public UserType getUserType() {
		return userType;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#setUserType(at.tug.oad.travelsales.model.UserType)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setUserType(UserType type) {
		this.userType = type;
	}
	

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#getProfileInformation()
	 */
	@Override
	public String getProfileInformation() {
		return profileInformation;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.IUser#setProfileInformation(java.lang.String)
	 */
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setProfileInformation(String profileInformation) {
		this.profileInformation = profileInformation;
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " | ID: " + this.id + " | Account Name: " + this.accountName + " | User Name: " + this.userName + " | Mail Address: " + this.mailAddress + " | Is Active:" + this.active +  " | Password Hash: " + this.password;
	}

	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@SuppressWarnings("unused")
	private void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@SuppressWarnings("unused")
	private void setType(UserType type) {
		this.userType = type;
	}

	@SuppressWarnings({ "unused", "unchecked" , "rawtypes"})
	private void setFriendList(Set friendList) {
		this.friendList = friendList;
	}
}
