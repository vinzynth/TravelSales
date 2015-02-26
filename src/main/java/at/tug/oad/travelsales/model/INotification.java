package at.tug.oad.travelsales.model;

import java.util.Collection;
import java.util.Date;

import at.tug.oad.travelsales.utils.database.IPersistable;

/**
 * @author Leopold Christian - 1331948
 * 19.11.2014 - 19:36:13
 * 
 */
public interface INotification extends IPersistable{

	/**
	 * @return notification recipient
	 */
	public IUser getRecipient();
	
	/**
	 * @return notification sender
	 */
	public IUser getSender();
	
	/**
	 * @return recommendet games
	 */
	public Collection<IGame> getRecommendedGames();
	
	/**
	 * @return time notfication was sent
	 */
	public Date getSendTime();
	
	/**
	 * @return time notification was read or null if it wasn't yet
	 */
	public Date getReadTime();
	
	/**
	 * sets new read time
	 * @param readTime
	 */
	public void setReadTime(Date readTime);
	
	/**
	 * @return notification type
	 */
	public NotificationType getNotificationType();
	
	/**
	 * @return Notification content
	 */
	public String getContent();
}
