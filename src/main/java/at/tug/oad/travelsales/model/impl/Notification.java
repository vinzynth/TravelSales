package at.tug.oad.travelsales.model.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import at.chrl.callbacks.metadata.ObjectCallback;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.INotification;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.model.NotificationType;
import at.tug.oad.travelsales.utils.database.PersistCallback;

/**
 * @author Leopold Christian - 1331948
 * 21.11.2014 - 00:01:51
 * 
 */
public final class Notification implements INotification {

	private int id;
	private IUser recipient;
	private IUser sender;
	private Collection<IGame> recommendedGames;
	private Date sendTime;
	private Date readTime;
	private NotificationType notificationType;
	private String content;

	
	@SuppressWarnings("unused")
	private Notification(){};
	
	/**
	 * @param type
	 * @param sender
	 * @param recipient
	 * @param content
	 * 
	 * @throws IllegalArgumentException <br>
	 * 				- if one parameter is null <br>
	 * 				- if content string is empty or too long
	 */
	public Notification(final NotificationType type, final IUser sender, final IUser recipient, final String content){
		if(isNull(content))
			throw new IllegalArgumentException("Parameter content is null");
		if(isNull(type))
			throw new IllegalArgumentException("Parameter type is null");
		if(isNull(sender))
			throw new IllegalArgumentException("Parameter sender is null");
		if(isNull(recipient))
			throw new IllegalArgumentException("Parameter recipient is null");
		
		if(content.isEmpty())
			throw new IllegalArgumentException("Parameter content is a empty string");
		
		if(content.length() >= 65535)
			throw new IllegalArgumentException("Parameter content is too long");
		
		
		this.sendTime = new Date();
		this.recommendedGames = new ArrayList<>();
		
		this.notificationType = type;
		this.sender = sender;
		this.recipient = recipient;
		this.content = content;
		
	};
	
	
	
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
	 * @see at.tug.oad.travelsales.model.INotification#getRecipient()
	 */
	@Override
	public IUser getRecipient() {
		return recipient;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.INotification#getSender()
	 */
	@Override
	public IUser getSender() {
		return sender;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.INotification#getRecommendedGames()
	 */
	@Override
	public Collection<IGame> getRecommendedGames() {
		return recommendedGames;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.INotification#getSendTime()
	 */
	@Override
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.INotification#getReadTime()
	 */
	@Override
	public Date getReadTime() {
		return readTime;
	}
	
	@Override
	@ObjectCallback(PersistCallback.class)
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.INotification#getNotificationType()
	 */
	@Override
	public NotificationType getNotificationType() {
		return notificationType;
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.model.INotification#getContent()
	 */
	@Override
	public String getContent() {
		return content;
	}

	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private void setRecipient(IUser recipient) {
		this.recipient = recipient;
	}

	@SuppressWarnings("unused")
	private void setSender(IUser sender) {
		this.sender = sender;
	}

	@SuppressWarnings("unused")
	private void setRecommendedGames(Collection<IGame> recommendedGames) {
		this.recommendedGames = recommendedGames;
	}

	@SuppressWarnings("unused")
	private void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@SuppressWarnings("unused")
	private void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	@SuppressWarnings("unused")
	private void setContent(String content) {
		this.content = content;
	}
}
