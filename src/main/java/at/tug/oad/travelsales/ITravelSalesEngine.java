package at.tug.oad.travelsales;

import java.util.Collection;
import java.util.function.Consumer;

import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.ILevel;
import at.tug.oad.travelsales.model.ILine;
import at.tug.oad.travelsales.model.INotification;
import at.tug.oad.travelsales.model.IPlayStatistic;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.utils.TravelSalesException;

/**
 * @author Leopold Christian - 1331948 19.11.2014 - 19:07:39
 *
 */
public interface ITravelSalesEngine {

	public void start();

	public void shutdown();

	public IUser checkLoginData(String username, String password) throws TravelSalesException;

	public void createUser(IUser user) throws TravelSalesException;

	public void getGamesByAccountName(String username, Consumer<Collection<IGame>> gamesConsumer);

	public void getAllPublicGames(Consumer<Collection<IGame>> gamesConsumer);

	public void getRecommendedGames(IUser user, Consumer<Collection<IGame>> gamesConsumer);

	public void getLevelsByGame(IGame game, Consumer<Collection<ILevel>> levelConsumer);

	public void getBaseGamesByLevel(ILevel level, Consumer<Collection<IBaseGame>> BaseGameConsumer);

	public void getGlobalHighScore(Consumer<Collection<IUser>> userConsumer, Consumer<Collection<Integer>> scoreConsumer);

	public void newGame(IGame newGame);

	public void newPoint(IPoint newPoint);

	public void newLevel(ILevel newLevel);

	public void newBaseGame(IBaseGame newBaseGame);

	public void saveLines(Collection<ILine> lines);

	public void clearLines(IUser user, IBaseGame baseGame);

	public void editGame(IGame game, Consumer<IGame> gameConsumer);

	public void editUser(IUser user, Consumer<IUser> userConsumer);

	public void editLevel(ILevel level, Consumer<ILevel> levelConsumer);

	public void editBaseGame(IBaseGame game, Consumer<IBaseGame> baseGameConsumer);

	public void inviteUserToFriendList(IUser user, IUser newFriend);

	public void inviteUserToFriendList(IUser user, IUser newFriend, String message);

	public void removeUserFromFriendList(IUser user, IUser removeMe);

	public void acceptFriendInvitation(INotification notification);

	public void declineFriendInvitation(INotification notification);

	public void notifyUser(IUser sender, IUser recipient, String content);

	public void getNotificationsRecived(IUser user, Consumer<Collection<INotification>> notificationConsumer);

	public void getNotificationsForwarded(IUser user, Consumer<Collection<INotification>> notificationConsumer);

	public void readNotification(INotification notification);

	public void deactivateUser(IUser user);

	public void activateUser(IUser user);

	public void deleteGame(IGame game);

	public void updatePlayStatistic(IUser player, IBaseGame baseGame, long playedTime, boolean finishedGame, long usedTurns, int score);

	public void resetPlayStatistic(IUser player, IBaseGame baseGame);
	
	public void getPlayStatistics(IUser player, IBaseGame baseGame, Consumer<Collection<IPlayStatistic>> playStatisticConsumer);
	
	public void getPlayStatistics(IUser player, Consumer<Collection<IPlayStatistic>> playStatisticConsumer);
	
	public void getPlayStatistics(IBaseGame baseGame, Consumer<Collection<IPlayStatistic>> playStatisticConsumer);

	public void rateGame(IUser user, IGame game, double rating);

	public void rateLevel(IUser user, ILevel level, double rating);

	public void rateBaseGame(IUser user, IBaseGame baseGame, double rating);

	public void sendMail(IUser user, String title, String content);

	public void resetPassword(IUser user);

	public void editPoint(IPoint point, Consumer<IPoint> pointConsumer);

	public IUser getUserFromDatabase(final String accountname);

        public IUser getUserById(final int id);

	public void getGamesByName(final String name, Consumer<Collection<IGame>> gameConsumer);

}
