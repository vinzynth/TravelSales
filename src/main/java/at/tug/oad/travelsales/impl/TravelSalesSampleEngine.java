package at.tug.oad.travelsales.impl;

import at.tug.oad.travelsales.ITravelSalesEngine;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.ILevel;
import at.tug.oad.travelsales.model.ILine;
import at.tug.oad.travelsales.model.INotification;
import at.tug.oad.travelsales.model.IPlayStatistic;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.model.UserType;
import at.tug.oad.travelsales.model.impl.Game;
import at.tug.oad.travelsales.model.impl.User;
import at.tug.oad.travelsales.utils.TravelSalesException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Leopold Christian - 1331948 23.11.2014 - 23:59:50
 * 
 */
public class TravelSalesSampleEngine implements ITravelSalesEngine {

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#shutdown()
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#checkLoginData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public IUser checkLoginData(String username, String password) throws TravelSalesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(IUser user) throws TravelSalesException {
		// throw new UnsupportedOperationException("Not supported yet."); //To
		// change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void getGamesByAccountName(String username, Consumer<Collection<IGame>> gamesConsumer) {
		ArrayList<IGame> list = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			list.add(new Game("Game " + (i + 1), "/vcredist.bmp", new User("1234", "bla", "bla", "bla@bla.at", UserType.NORMAL), true));
		}
		gamesConsumer.accept(list);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getAllPublicGames(java.util.function.Consumer)
	 */
	@Override
	public void getAllPublicGames(Consumer<Collection<IGame>> gamesConsumer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLevelsByGame(IGame game, Consumer<Collection<ILevel>> levelConsumer) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void getBaseGamesByLevel(ILevel level, Consumer<Collection<IBaseGame>> BaseGameConsumer) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void newGame(IGame newGame) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#saveLines(java.util.Collection)
	 */
	@Override
	public void saveLines(Collection<ILine> lines) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#clearLines(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IBaseGame)
	 */
	@Override
	public void clearLines(IUser user, IBaseGame baseGame) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#editGame(at.tug.oad.travelsales.model.IGame,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void editGame(IGame game, Consumer<IGame> gameConsumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#newLevel(at.tug.oad.travelsales.model.ILevel)
	 */
	@Override
	public void newLevel(ILevel newLevel) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#newBaseGame(at.tug.oad.travelsales.model.IBaseGame)
	 */
	@Override
	public void newBaseGame(IBaseGame newBaseGame) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#inviteUserToFriendList(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void inviteUserToFriendList(IUser user, IUser newFriend) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#inviteUserToFriendList(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IUser, java.lang.String)
	 */
	@Override
	public void inviteUserToFriendList(IUser user, IUser newFriend, String message) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#removeUserFromFriendList(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void removeUserFromFriendList(IUser user, IUser removeMe) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#notifyUser(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IUser, java.lang.String)
	 */
	@Override
	public void notifyUser(IUser sender, IUser recipient, String content) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#readNotification(at.tug.oad.travelsales.model.INotification)
	 */
	@Override
	public void readNotification(INotification notification) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#acceptFriendInvitation(at.tug.oad.travelsales.model.INotification)
	 */
	@Override
	public void acceptFriendInvitation(INotification notification) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#declineFriendInvitation(at.tug.oad.travelsales.model.INotification)
	 */
	@Override
	public void declineFriendInvitation(INotification notification) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getNotificationsRecived(at.tug.oad.travelsales.model.IUser,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void getNotificationsRecived(IUser user, Consumer<Collection<INotification>> notificationConsumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getNotificationsForwarded(at.tug.oad.travelsales.model.IUser,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void getNotificationsForwarded(IUser user, Consumer<Collection<INotification>> notificationConsumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#editUser(at.tug.oad.travelsales.model.IUser,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void editUser(IUser user, Consumer<IUser> userConsumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#editLevel(at.tug.oad.travelsales.model.ILevel,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void editLevel(ILevel level, Consumer<ILevel> levelConsumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#editBaseGame(at.tug.oad.travelsales.model.IBaseGame,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void editBaseGame(IBaseGame game, Consumer<IBaseGame> baseGameConsumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#deactivateUser(at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void deactivateUser(IUser user) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#activateUser(at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void activateUser(IUser user) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#deleteGame(at.tug.oad.travelsales.model.IGame)
	 */
	@Override
	public void deleteGame(IGame game) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#updatePlayStatistic(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IBaseGame, long, boolean, long, int)
	 */
	@Override
	public void updatePlayStatistic(IUser player, IBaseGame baseGame, long playedTime, boolean finishedGame, long usedTurns, int score) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#resetPlayStatistic(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IBaseGame)
	 */
	@Override
	public void resetPlayStatistic(IUser player, IBaseGame baseGame) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#rateGame(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IGame, double)
	 */
	@Override
	public void rateGame(IUser user, IGame game, double rating) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#rateLevel(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.ILevel, double)
	 */
	@Override
	public void rateLevel(IUser user, ILevel level, double rating) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#rateBaseGame(at.tug.oad.travelsales.model.IUser,
	 *      at.tug.oad.travelsales.model.IBaseGame, double)
	 */
	@Override
	public void rateBaseGame(IUser user, IBaseGame baseGame, double rating) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#sendMail(at.tug.oad.travelsales.model.IUser,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMail(IUser user, String title, String content) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#resetPassword(at.tug.oad.travelsales.model.IUser)
	 */
	@Override
	public void resetPassword(IUser user) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getRecommendedGames(at.tug.oad.travelsales.model.IUser,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void getRecommendedGames(IUser user, Consumer<Collection<IGame>> gamesConsumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getGlobalHighScore(java.util.function.Consumer,
	 *      java.util.function.Consumer)
	 */
	@Override
	public void getGlobalHighScore(Consumer<Collection<IUser>> userConsumer, Consumer<Collection<Integer>> scoreConsumer) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#newPoint(at.tug.oad.travelsales.model.IPoint)
	 */
	@Override
	public void newPoint(IPoint newPoint) {
		// TODO Auto-generated method stub

	}

    @Override
    public void editPoint(IPoint point, Consumer<IPoint> pointConsumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IUser getUserFromDatabase(String accountname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getGamesByName(java.lang.String, java.util.function.Consumer)
	 */
	@Override
	public void getGamesByName(String name, Consumer<Collection<IGame>> gameConsumer) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getPlayStatistics(at.tug.oad.travelsales.model.IUser, at.tug.oad.travelsales.model.IBaseGame, java.util.function.Consumer)
	 */
	@Override
	public void getPlayStatistics(IUser player, IBaseGame baseGame, Consumer<Collection<IPlayStatistic>> playStatisticConsumer) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getPlayStatistics(at.tug.oad.travelsales.model.IUser, java.util.function.Consumer)
	 */
	@Override
	public void getPlayStatistics(IUser player, Consumer<Collection<IPlayStatistic>> playStatisticConsumer) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 * @see at.tug.oad.travelsales.ITravelSalesEngine#getPlayStatistics(at.tug.oad.travelsales.model.IBaseGame, java.util.function.Consumer)
	 */
	@Override
	public void getPlayStatistics(IBaseGame baseGame, Consumer<Collection<IPlayStatistic>> playStatisticConsumer) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public IUser getUserById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
