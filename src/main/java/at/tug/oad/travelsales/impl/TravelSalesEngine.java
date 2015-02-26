package at.tug.oad.travelsales.impl;

import gnu.trove.map.hash.THashMap;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import at.chrl.callbacks.util.JavaAgentUtils;
import at.chrl.nutils.PasswordHash;
import at.chrl.nutils.Rnd;
import at.tug.oad.travelsales.ITravelSalesEngine;
import at.tug.oad.travelsales.controller.TravelSalesViewController;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.ILevel;
import at.tug.oad.travelsales.model.ILine;
import at.tug.oad.travelsales.model.INotification;
import at.tug.oad.travelsales.model.IPlayStatistic;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.model.NotificationType;
import at.tug.oad.travelsales.model.impl.BaseGame;
import at.tug.oad.travelsales.model.impl.Game;
import at.tug.oad.travelsales.model.impl.Level;
import at.tug.oad.travelsales.model.impl.Line;
import at.tug.oad.travelsales.model.impl.Notification;
import at.tug.oad.travelsales.model.impl.PlayStatistic;
import at.tug.oad.travelsales.model.impl.Point;
import at.tug.oad.travelsales.model.impl.RatingStatistic;
import at.tug.oad.travelsales.model.impl.User;
import at.tug.oad.travelsales.utils.TSConfig;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;
import at.tug.oad.travelsales.utils.database.HibernateSession;
import at.tug.oad.travelsales.utils.database.IPersistable;
import at.tug.oad.travelsales.utils.database.TSHibernateConfig;
import at.tug.oad.travelsales.utils.mail.MailService;

import com.bravestone.hibernate.HibernateService;

/**
 * @author Leopold Christian - 1331948 23.11.2014 - 23:59:39
 *
 */
public class TravelSalesEngine implements ITravelSalesEngine {

    @Override
    public void editPoint(IPoint point, Consumer<IPoint> pointConsumer) {
        HibernateSession.edit(point, pointConsumer);
    }

    @Override
    public IUser getUserById(int id) {
        try (HibernateSession session = new HibernateSession()) {
                IUser uniqueResult = (IUser) session.createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
                return uniqueResult;
        } catch (Exception e) {
                throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error | Maybe corrupt data set: " + e.getMessage(), e);
        }
    }

    
	/**
	 * PasswordResetEntry for delayed queue
	 * 
	 * @author Leopold Christian - 1331948 06.01.2015 - 13:48:23
	 *
	 */
	private static class PasswordResetEntry implements Delayed {
		private final int userId;
		private final long expireTime;
		private final String passwordHash;

		private PasswordResetEntry(IUser user, String password) {
			try {
				this.passwordHash = PasswordHash.createHash(password);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new TravelSalesException(TSExceptionType.INVALID_PASSWORD, "Password couldn't be hashed", e);
			}
			this.expireTime = System.currentTimeMillis() + (TSConfig.PASSWORD_RESET_EXPIRE_TIME * (1000 * 60));
			this.userId = user.getId();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.concurrent.Delayed#compareTo(Delayed)
		 */
		@Override
		public int compareTo(Delayed o) {
			PasswordResetEntry other = (PasswordResetEntry) o;
			long my_delay = getDelay(TimeUnit.MILLISECONDS), other_delay = other.getDelay(TimeUnit.MILLISECONDS);
			return my_delay < other_delay ? -1 : my_delay > other_delay ? 1 : 0;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
		 */
		@Override
		public long getDelay(TimeUnit unit) {
			long delay = expireTime - System.currentTimeMillis();
			return unit.convert(delay, TimeUnit.MILLISECONDS);
		}
	}

	private SecureRandom scr;
	private Queue<PasswordResetEntry> resetedPasswords;

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#start()
	 */
	@Override
	public void start() {
		System.out.println("Check Java Agent");

		JavaAgentUtils.isConfigured();

		scr = new SecureRandom(("Super Secure Random byte seed sequence" + System.nanoTime()).getBytes());
		resetedPasswords = new PriorityQueue<>();

		System.out.println("Connectiong to database: ");
		HibernateService.getInstance().connect(TSHibernateConfig.getInstance());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#shutdown()
	 */
	@Override
	public void shutdown() {
		System.out.println("Closing database connection");
		HibernateService.getInstance().disconnect(TSHibernateConfig.getInstance());
		System.out.println("Database connection closed");
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getUserFromDatabase(java.lang.String)
	 */
	@Override
	public IUser getUserFromDatabase(final String accountname) {
		try (HibernateSession session = new HibernateSession()) {
			IUser uniqueResult = (IUser) session.createCriteria(User.class).add(Restrictions.eq("accountName", accountname)).uniqueResult();
			return uniqueResult;
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error | Maybe corrupt data set: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#checkLoginData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public IUser checkLoginData(String username, String password) throws TravelSalesException {
		while (Objects.nonNull(resetedPasswords.peek()) && resetedPasswords.peek().getDelay(TimeUnit.MILLISECONDS) < 0)
			resetedPasswords.poll();
		IUser userFromDatabase = getUserFromDatabase(username);
		if (Objects.isNull(userFromDatabase) || !userFromDatabase.isActive())
			throw new TravelSalesException(TSExceptionType.INVALID_PASSWORD, "User does not exist");

		try {
			if (!PasswordHash.validatePassword(password, userFromDatabase.getPassword())) {
				for (PasswordResetEntry passwordResetEntry : resetedPasswords)
					if (userFromDatabase.getId() == passwordResetEntry.userId)
						if (PasswordHash.validatePassword(password, passwordResetEntry.passwordHash)){
                                                    TravelSalesViewController.getInstance().newPassword(userFromDatabase);
                                                    return userFromDatabase;
                                                }
				throw new TravelSalesException(TSExceptionType.INVALID_PASSWORD, "Invalid password");
			}
			PasswordResetEntry pre = null;
			for (PasswordResetEntry passwordResetEntry : resetedPasswords)
				if (userFromDatabase.getId() == passwordResetEntry.userId) {
					pre = passwordResetEntry;
					break;
				}
			if (Objects.nonNull(pre))
				resetedPasswords.remove(pre);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Error hashing password");
		}
		return userFromDatabase;
	}

	/**
	 * Syncs given user with database <br>
	 * 
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#createUser(at.tug.oad.travelsales.model.IUser)
	 * 
	 * @throws TravelSalesException
	 *             with type {@link TSExceptionType#ACCOUNT_NAME_ALREADY_IN_USE}
	 *             if account name is already taken. <br>
	 *             with type {@link TSExceptionType#INTERNAL_ERROR} if database
	 *             related errors occur.
	 */
	@Override
	public void createUser(IUser user) throws TravelSalesException {
		if (Objects.nonNull(getUserFromDatabase(user.getAccountName())))
			throw new TravelSalesException(TSExceptionType.ACCOUNT_NAME_ALREADY_IN_USE, "Account " + user.getAccountName() + " already exists");
		try (HibernateSession session = new HibernateSession()) {
			session.sync(user);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getGamesByAccountName(java.lang.String,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void getGamesByAccountName(String accountname, Consumer<Collection<IGame>> gamesConsumer) {
		IUser userFromDatabase = getUserFromDatabase(accountname);
		if (Objects.isNull(userFromDatabase))
			throw new TravelSalesException(TSExceptionType.USER_DOES_NOT_EXIST_IN_DATABASE, "Account " + accountname + " does not exist.");

		try (HibernateSession session = new HibernateSession()) {
			@SuppressWarnings("unchecked")
			List<IGame> list = session.createCriteria(Game.class).add(Restrictions.eq("creator", userFromDatabase)).list();

			/*
			 * If no games are present for this user, generate private samples
			 */
			if (list.isEmpty()) {
				for (int i = 0; i < 5; i++) {
					Game g = new Game("Game " + i, "", userFromDatabase, false);
					session.sync(g);

					for (int j = 0; j < 5; j++) {
						Level l = new Level("Level " + j, userFromDatabase);
						session.sync(l);
						g.add(l);

						for (int k = 0; k < 5; k++) {
							BaseGame bg = new BaseGame("Base Game " + k, userFromDatabase, "");
							session.sync(bg);
							l.add(bg);

							for (int m = 0; m < 5; m++) {
								Point p = new Point("P" + m, Rnd.nextDouble(), Rnd.nextDouble());
								session.sync(p);
								bg.add(p);
							}
						}
					}

					list.add(g);
				}
			}

			gamesConsumer.accept(list);

		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getAllPublicGames(java.util.function.Consumer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void getAllPublicGames(Consumer<Collection<IGame>> gamesConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			gamesConsumer.accept(session.createCriteria(Game.class).add(Restrictions.eq("visible", true)).list());
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	@Override
	public void getLevelsByGame(IGame game, Consumer<Collection<ILevel>> levelConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(game);
			levelConsumer.accept(game.getNestedCollection());
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	@Override
	public void getBaseGamesByLevel(ILevel level, Consumer<Collection<IBaseGame>> BaseGameConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(level);
			BaseGameConsumer.accept(level.getNestedCollection());
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	@Override
	public void newGame(IGame newGame) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(newGame);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#saveLines(java.util.Collection)
	 */
	@Override
	public void saveLines(Collection<ILine> lines) {
		try (HibernateSession session = new HibernateSession()) {
			lines.forEach(session::sync);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#clearLines(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IBaseGame)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clearLines(IUser user, IBaseGame baseGame) {
		try (HibernateSession session = new HibernateSession()) {
			((Collection<IPersistable>) session.createCriteria(Line.class).add(Restrictions.eq("user", user)).add(Restrictions.eq("baseGame", baseGame)).list()).forEach(session::delete);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#editGame(at.tug.oad.travelsales.model.IGame,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void editGame(IGame game, Consumer<IGame> gameConsumer) {
		HibernateSession.edit(game, gameConsumer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#newLevel(at.tug.oad.travelsales.model.ILevel)
	 */
	@Override
	public void newLevel(ILevel newLevel) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(newLevel);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#newBaseGame(at.tug.oad.travelsales.model.IBaseGame)
	 */
	@Override
	public void newBaseGame(IBaseGame newBaseGame) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(newBaseGame);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#inviteUserToFriendList(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void inviteUserToFriendList(IUser user, IUser newFriend) {
		inviteUserToFriendList(user, newFriend, user.getUserName() + " invited you to his friendlist!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#inviteUserToFriendList(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void inviteUserToFriendList(IUser user, IUser newFriend, String message) {
		try (HibernateSession session = new HibernateSession()) {

			@SuppressWarnings("rawtypes")
			List list = session.createCriteria(Notification.class).add(Restrictions.eq("recipient", newFriend)).add(Restrictions.eq("sender", user)).add(Restrictions.eq("notificationType", NotificationType.FRIEND_INVITE)).list();
			if (list.size() > 0)
				throw new TravelSalesException(TSExceptionType.FRIEND_LIST_INVITATION_PENDING, "You already sent a invitation to this player!");

			session.sync(user);
			session.sync(newFriend);

			if (!user.getFriendList().contains(newFriend)) {
				INotification friendInvite = new Notification(NotificationType.FRIEND_INVITE, user, newFriend, message);
				session.sync(friendInvite);
			} else
				throw new TravelSalesException(TSExceptionType.USER_IS_ALREADY_IN_FRIEND_LIST, newFriend.getUserName() + " is already in your friendlist!");
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#removeUserFromFriendList(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void removeUserFromFriendList(IUser user, IUser removeMe) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(user);
			session.sync(removeMe);

			if (user.getFriendList().contains(removeMe))
				user.getFriendList().remove(removeMe);

		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#notifyUser(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IUser, java.lang.String)
	 */
	@Override
	public void notifyUser(IUser sender, IUser recipient, String content) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(new Notification(NotificationType.PERSONAL_MESSAGE, sender, recipient, content));
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getNotificationsRecived(at.tug.oad.travelsales.model.IUser,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void getNotificationsRecived(IUser user, Consumer<Collection<INotification>> notificationConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			@SuppressWarnings("unchecked")
			List<INotification> list = (List<INotification>) session.createCriteria(Notification.class).add(Restrictions.eq("recipient", user)).add(Restrictions.eq("readTime", null)).list();
			list.sort((n1, n2) -> n2.getSendTime().compareTo(n1.getSendTime()));
			notificationConsumer.accept(list);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getNotificationsForwarded(at.tug.oad.travelsales.model.IUser,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void getNotificationsForwarded(IUser user, Consumer<Collection<INotification>> notificationConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			@SuppressWarnings("unchecked")
			List<INotification> list = (List<INotification>) session.createCriteria(Notification.class).add(Restrictions.eq("sender", user)).list();
			list.sort((n1, n2) -> n2.getSendTime().compareTo(n1.getSendTime()));
			notificationConsumer.accept(list);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#readNotification(at.tug.oad.travelsales.model.INotification)
	 */
	@Override
	public void readNotification(INotification notification) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(notification);
			notification.setReadTime(new Date());
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#acceptFriendInvitation(at.tug.oad.travelsales.model.INotification)
	 */
	@Override
	public void acceptFriendInvitation(INotification notification) {
		if (!notification.getNotificationType().equals(NotificationType.FRIEND_INVITE))
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Try to accept friend invite with NotificationType: " + notification.getNotificationType().name());
		try (HibernateSession session = new HibernateSession()) {
			session.sync(notification);
			notification.setReadTime(new Date());

			session.sync(notification.getSender());
			session.sync(notification.getRecipient());

			notification.getSender().getFriendList().add(notification.getRecipient());
			notification.getRecipient().getFriendList().add(notification.getSender());

			INotification systemMessage = new Notification(NotificationType.SYSTEM_MESSAGE, notification.getRecipient(), notification.getSender(), notification.getRecipient().getUserName() + " accepted your friendlist invitation!");

			session.sync(systemMessage);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#declineFriendInvitation(at.tug.oad.travelsales.model.INotification)
	 */
	@Override
	public void declineFriendInvitation(INotification notification) {
		if (!notification.getNotificationType().equals(NotificationType.FRIEND_INVITE))
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Try to decline friend invite with NotificationType: " + notification.getNotificationType().name());
		try (HibernateSession session = new HibernateSession()) {
			session.sync(notification);
			notification.setReadTime(new Date());

			INotification systemMessage = new Notification(NotificationType.SYSTEM_MESSAGE, notification.getRecipient(), notification.getSender(), notification.getRecipient().getUserName() + " declined your friendlist invitation!");

			session.sync(systemMessage);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#deactivateUser(at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void deactivateUser(IUser user) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(user);
			user.setActive(false);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#activateUser(at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void activateUser(IUser user) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(user);
			user.setActive(true);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#editLevel(at.tug.oad.travelsales.model.ILevel,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void editLevel(ILevel level, Consumer<ILevel> levelConsumer) {
		HibernateSession.edit(level, levelConsumer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#editBaseGame(at.tug.oad.travelsales.model.IBaseGame,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void editBaseGame(IBaseGame game, Consumer<IBaseGame> baseGameConsumer) {
		HibernateSession.edit(game, baseGameConsumer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#rateGame(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IGame, double)
	 */
	@Override
	public void rateGame(IUser user, IGame game, double rating) {
		game.forEach(level -> rateLevel(user, level, rating));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#rateLevel(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.ILevel, double)
	 */
	@Override
	public void rateLevel(IUser user, ILevel level, double rating) {
		level.forEach(bg -> rateBaseGame(user, bg, rating));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#rateBaseGame(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IBaseGame, double)
	 */
	@Override
	public void rateBaseGame(IUser user, IBaseGame baseGame, double rating) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(user);
			session.sync(baseGame);

			RatingStatistic uniqueResult = (RatingStatistic) session.createCriteria(RatingStatistic.class).add(Restrictions.eq("userId", user.getId())).add(Restrictions.eq("baseGameId", baseGame.getId())).uniqueResult();

			if (Objects.isNull(uniqueResult))
				session.sync(new RatingStatistic(user.getId(), baseGame.getId(), rating));
			else
				uniqueResult.setRating(rating);

		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#deleteGame(at.tug.oad.travelsales.model.IGame)
	 */
	@Override
	public void deleteGame(IGame game) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(game);
			session.delete(game);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#updatePlayStatistic(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IBaseGame, long, boolean, long, int)
	 */
	@Override
	public void updatePlayStatistic(IUser player, IBaseGame baseGame, long playedTime, boolean finishedGame, long usedTurns, int score) {
		try (HibernateSession session = new HibernateSession()) {
			PlayStatistic uniqueResult = (PlayStatistic) session.createCriteria(PlayStatistic.class).add(Restrictions.eq("userId", player.getId())).add(Restrictions.eq("baseGameId", baseGame.getId())).uniqueResult();
                        if(Objects.isNull(uniqueResult)){
                            uniqueResult = new PlayStatistic(player.getId(), 0, 0, false, 0, 0);
                            session.sync(uniqueResult);
                        }
			uniqueResult.setPlayedDate(new Date());
			uniqueResult.setPlayedTime(uniqueResult.getPlayedTime() + playedTime);
			uniqueResult.setScore(score);
			uniqueResult.setUsedTurns(uniqueResult.getUsedTurns() + usedTurns);
			uniqueResult.setBaseGameFinished(finishedGame);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#resetPlayStatistic(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IBaseGame)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void resetPlayStatistic(IUser player, IBaseGame baseGame) {
		try (final HibernateSession session = new HibernateSession()) {
			((List<IPersistable>) session.createCriteria(PlayStatistic.class).add(Restrictions.eq("userId", player.getId())).add(Restrictions.eq("baseGameId", baseGame.getId())).list()).forEach(session::delete);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#editUser(at.tug.oad.travelsales.model.IUser,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void editUser(IUser user, Consumer<IUser> userConsumer) {
		HibernateSession.edit(user, userConsumer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#sendMail(at.tug.oad.travelsales.model.IUser,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMail(IUser user, String title, String content) {
		try (HibernateSession session = new HibernateSession()) {
			session.sync(user);
			MailService.getInstance().sendEmail(user, title, content);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#resetPassword(at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void resetPassword(IUser user) {
		String rndPwd = "";
		try (HibernateSession session = new HibernateSession()) {
			session.sync(user);
			for (PasswordResetEntry passwordResetEntry : resetedPasswords) {
				if (user.getId() == passwordResetEntry.userId)
					throw new TravelSalesException(TSExceptionType.PASSWORD_ALREADY_RESETED, "Your Password was already reseted. Wait " + passwordResetEntry.getDelay(TimeUnit.SECONDS) + " secounds before you try again.");
			}
			rndPwd = new BigInteger(130, scr).toString(32);
			PasswordResetEntry passwordResetEntry = new PasswordResetEntry(user, rndPwd);
			resetedPasswords.add(passwordResetEntry);
		} catch (TravelSalesException e) {
			throw e;
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
		sendMail(user, "TravelSales: Password Reset", "Account: " + user.getAccountName() + System.lineSeparator() + "Password: " + rndPwd);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getRecommendedGames(at.tug.oad.travelsales.model.IUser,
	 *      java.util.function.Consumer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void getRecommendedGames(IUser user, Consumer<Collection<IGame>> gamesConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			List<PlayStatistic> list = session.createCriteria(PlayStatistic.class).add(Restrictions.eq("userId", user.getId())).list();
			Set<Integer> baseGameIds = new TreeSet<>();
			list.stream().map(PlayStatistic::getBaseGameId).forEach(baseGameIds::add);

			Criteria crit = session.createCriteria(PlayStatistic.class);
			Criterion[] criterions = baseGameIds.stream().map(i -> Restrictions.eq("baseGameId", i)).toArray(Criterion[]::new);

			List<PlayStatistic> playedGames = crit.add(Restrictions.or(criterions)).list();

			Map<Integer, List<PlayStatistic>> collected = playedGames.stream().collect(Collectors.groupingBy(PlayStatistic::getUserId));
			Map<Integer, BitSet> valued = new THashMap<>();
			collected.entrySet().forEach(e -> {
				BitSet bs = new BitSet();

				e.getValue().forEach(ps -> bs.set(ps.getBaseGameId()));
				valued.put(e.getKey(), bs);
			});
			Optional<Entry<Integer, BitSet>> max = valued.entrySet().stream().max((e1, e2) -> e2.getValue().cardinality() - e1.getValue().cardinality());

			if (max.isPresent())
				gamesConsumer.accept(session.createCriteria(Game.class).add(Restrictions.eq("creator", getUserById(max.get().getKey()))).list());
			else
				gamesConsumer.accept(Collections.emptyList());

		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getGlobalHighScore(java.util.function.Consumer,
	 *      java.util.function.Consumer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void getGlobalHighScore(Consumer<Collection<IUser>> userConsumer, Consumer<Collection<Integer>> scoreConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			List<PlayStatistic> list = session.createCriteria(PlayStatistic.class).list();
			Map<Integer, List<PlayStatistic>> collect = list.stream().collect(Collectors.groupingBy(PlayStatistic::getUserId));
			Map<Integer, Integer> scoreList = new THashMap<>();
			collect.forEach((k, v) -> scoreList.put(k, v.stream().mapToInt(PlayStatistic::getScore).sum()));

			TreeSet<Entry<Integer, Integer>> sortedByScore = new TreeSet<>((e1, e2) -> e2.getValue() - e1.getValue());
			scoreList.entrySet().forEach(sortedByScore::add);

			Criteria crit = session.createCriteria(User.class);
			Criterion[] criterions = scoreList.entrySet().stream().map(i -> Restrictions.eq("userId", i.getKey())).toArray(Criterion[]::new);

			List<User> users = crit.add(Restrictions.or(criterions)).list();

			List<IUser> sorted = new ArrayList<>(users.size());
			for (Entry<Integer, Integer> entry : sortedByScore) {
				IUser userToAdd = null;
				for (User user : users) {
					if (entry.getKey().equals(user.getId())) {
						userToAdd = user;
						break;
					}
				}
				if (Objects.nonNull(userToAdd)) {
					users.remove(userToAdd);
					sorted.add(userToAdd);
				}
			}

			List<Integer> sortedScore = new ArrayList<>(users.size());
			sortedByScore.forEach(e -> sortedScore.add(e.getValue()));

			userConsumer.accept(sorted);
			scoreConsumer.accept(sortedScore);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#newPoint(at.tug.oad.travelsales.model.IPoint)
	 */
	@Override
	public void newPoint(IPoint newPoint) {
    	try (HibernateSession session = new HibernateSession()) {
			session.sync(newPoint);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getGamesByName(java.lang.String, java.util.function.Consumer)
	 */
	@Override
	public void getGamesByName(String name, Consumer<Collection<IGame>> gameConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			@SuppressWarnings("unchecked")
			List<IGame> list = (List<IGame>) session.createCriteria(Game.class).add(Restrictions.eq("name", name)).list();
			gameConsumer.accept(list);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getPlayStatistics(at.tug.oad.travelsales.model.IUser, at.tug.oad.travelsales.model.IBaseGame, java.util.function.Consumer)
	 */
	@Override
	public void getPlayStatistics(IUser player, IBaseGame baseGame, Consumer<Collection<IPlayStatistic>> playStatisticConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			@SuppressWarnings("unchecked")
			List<IPlayStatistic> list = (List<IPlayStatistic>) session.createCriteria(PlayStatistic.class)
			.add(Restrictions.eq("userId", player.getId()))
			.add(Restrictions.eq("baseGameId", baseGame.getId()))
			.list();
                        Collections.sort(list, (p1,p2) -> p2.getScore() - p1.getScore());
			playStatisticConsumer.accept(list);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getPlayStatistics(at.tug.oad.travelsales.model.IUser, java.util.function.Consumer)
	 */
	@Override
	public void getPlayStatistics(IUser player, Consumer<Collection<IPlayStatistic>> playStatisticConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			@SuppressWarnings("unchecked")
			List<IPlayStatistic> list = (List<IPlayStatistic>) session.createCriteria(PlayStatistic.class)
			.add(Restrictions.eq("userId", player.getId()))
			.list();
                        Collections.sort(list, (p1,p2) -> p2.getScore() - p1.getScore());
			playStatisticConsumer.accept(list);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getPlayStatistics(at.tug.oad.travelsales.model.IBaseGame, java.util.function.Consumer)
	 */
	@Override
	public void getPlayStatistics(IBaseGame baseGame, Consumer<Collection<IPlayStatistic>> playStatisticConsumer) {
		try (HibernateSession session = new HibernateSession()) {
			@SuppressWarnings("unchecked")
			List<IPlayStatistic> list = (List<IPlayStatistic>) session.createCriteria(PlayStatistic.class)
			.add(Restrictions.eq("baseGameId", baseGame.getId()))
			.list();
                        Collections.sort(list, (p1,p2) -> p2.getScore() - p1.getScore());
			playStatisticConsumer.accept(list);
		} catch (Exception e) {
			throw new TravelSalesException(TSExceptionType.INTERNAL_ERROR, "Internal Error: " + e.getMessage(), e);
		}
	}

}
