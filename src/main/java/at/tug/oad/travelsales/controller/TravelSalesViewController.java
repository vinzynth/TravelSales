package at.tug.oad.travelsales.controller;

import java.util.Collection;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import at.tug.oad.travelsales.ITravelSalesEngine;
import at.tug.oad.travelsales.impl.TravelSalesEngine;
import at.tug.oad.travelsales.model.IBaseGame;
import at.tug.oad.travelsales.model.IGame;
import at.tug.oad.travelsales.model.ILevel;
import at.tug.oad.travelsales.model.ILine;
import at.tug.oad.travelsales.model.INotification;
import at.tug.oad.travelsales.model.IPlayStatistic;
import at.tug.oad.travelsales.model.IPoint;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.model.UserType;
import at.tug.oad.travelsales.model.impl.BaseGame;
import at.tug.oad.travelsales.model.impl.Game;
import at.tug.oad.travelsales.model.impl.Level;
import at.tug.oad.travelsales.model.impl.Point;
import at.tug.oad.travelsales.model.impl.User;
import at.tug.oad.travelsales.utils.TSExceptionType;
import at.tug.oad.travelsales.utils.TravelSalesException;
import at.tug.oad.travelsales.utils.music.PlaySound;
import at.tug.oad.travelsales.view.gui.EnterNewPasswordDlg;
import at.tug.oad.travelsales.view.gui.MessageType;
import at.tug.oad.travelsales.view.gui.TravelSalesMan_View;

/**
 * @author Leopold Christian - 1331948 24.11.2014 - 00:01:11
 *
 * #Singelton
 */
public final class TravelSalesViewController {

    public void setActualLineLength(double length) {
        frame.setActualLineLength(length);
    }

    public void getGamesByName(String name, Consumer<Collection<IGame>> gameConsumer) {
        engine.getGamesByName(name, gameConsumer);
    }

    public void finishedGame(double length) {
        String text = "Congratulation!\nYou scored: " + String.format("%.2f", length);
        showMessageDialog("You finished!", text, MessageType.SUCCESS);
        finishedGame = true;
        engine.updatePlayStatistic(activeUser, selectedBaseGame, 0, true, 0, (int) (length * 100));

        setState(ViewControllerState.RATE_GAME);
    }

    public void saveRating(int value) {
        engine.rateBaseGame(activeUser, selectedBaseGame, value);
        cancel(); // to come back to Game-Screen
    }

    public void newPassword(IUser user) {
        activeUser = user;
        EnterNewPasswordDlg dlg = new EnterNewPasswordDlg(frame, true);
        dlg.setVisible(true);
    }

    public void setNewPassword(String p1) {
        engine.editUser(activeUser, u -> u.newPassword(p1));
    }

    private static class SingletonHolder {

        private final static TravelSalesViewController instance
                = new TravelSalesViewController();
    }

    public final static TravelSalesViewController getInstance() {
        return SingletonHolder.instance;
    }

    private final ITravelSalesEngine engine;
    private ViewControllerState state;
    private ViewControllerState lastState;
    TravelSalesMan_View frame;
    private IUser activeUser;
    private ILevel selectedLevel;
    private IBaseGame selectedBaseGame;
    private boolean editGameMode = false;
    private int numberUnreadMessages;
    private PlaySound playSound;
    private boolean finishedGame = false;

    public void registrate() {
        setState(ViewControllerState.REGISTRATION);
    }

    /*
     * If User clicks a cancel Button, go back to last state!
     */
    public void cancel() {
        switch (state) {
            case PLAY_GAME:
                setState(ViewControllerState.SELECT_GAME);
                break;
            case SELECT_LEVEL_BASEGAME:
                setState(ViewControllerState.SELECT_GAME);
                break;
            default:
                setState(lastState);
        }

    }

    public void playSound(String path) {
        stopSound();
        playSound = new PlaySound(path);
        playSound.start();
    }

    public void stopSound() {
        if (playSound != null) {
            playSound.interrupt();
        }
    }

    public void updatePoint(IPoint point, Consumer<IPoint> pointConsumer) {
        engine.editPoint(point, pointConsumer);
    }

    public void editBaseGame(IBaseGame game, Consumer<IBaseGame> baseGameConsumer) {
        engine.editBaseGame(game, baseGameConsumer);
    }

    public void editLevel(ILevel level, Consumer<ILevel> levelConsumer) {
        engine.editLevel(level, levelConsumer);
    }

    public void editGame(IGame game, Consumer<IGame> gameConsumer) {
        engine.editGame(game, gameConsumer);
    }

    public IBaseGame newBaseGame(String name) {
        IBaseGame game = new BaseGame(name, activeUser, "");
        engine.newBaseGame(game);
        return game;
    }

    public ILevel newLevel(String name) {
        ILevel lvl = new Level(name, activeUser);
        engine.newLevel(lvl);
        return lvl;
    }

    public IPoint newPoint(String name, double x, double y) {
        IPoint point = new Point(name, x, y);
        engine.newPoint(point);
        return point;
    }

    public void resetPassword(String username, String email) {
        try {
            IUser user = engine.getUserFromDatabase(username);
            if (user == null) {
                throw new TravelSalesException(TSExceptionType.USER_DOES_NOT_EXIST_IN_DATABASE, "User " + username + " doesn't exist!");
            }

            if (!email.equals(user.getMailAddress())) {
                throw new TravelSalesException(TSExceptionType.INVALID_MAIL_ADDRESS, "The e-mail address you entered is not associated with your account!");
            }

            engine.resetPassword(user);
        } catch (Exception e) {
            showMessageDialog("Error", e.getMessage(), MessageType.ERROR);
        }
    }

    public void sendNotification(String recipientUserName, String text) throws TravelSalesException {
        IUser recipient = engine.getUserFromDatabase(recipientUserName);

        engine.notifyUser(activeUser, recipient, text);
    }

    public void forgotPassword() {
        setState(ViewControllerState.RESET_PASSWORD);
    }

    public void showMessageDialog(String title, String text, MessageType type) {
        frame.showMessage(title, text, type);
    }

    public void deactivateUser(String username) {
        IUser user = engine.getUserFromDatabase(username);
        engine.deactivateUser(user);
        showMessageDialog("Success", "User " + username + " deactivated!", MessageType.SUCCESS);
    }

    public void deleteGame(IGame game) {
        engine.deleteGame(game);
        showMessageDialog("Success", "Game " + game.getName() + " deleted!", MessageType.SUCCESS);
    }

    public void saveRegistration(String firstname, String lastname, String accountname, String password1, String password2, String email) throws Exception {
        //check validity of user input:
        if (firstname.isEmpty()) {
            throw new Exception("Please fill in your first name!");
        }
        if (lastname.isEmpty()) {
            throw new Exception("Please fill in your last name!");
        }
        if (accountname.isEmpty()) {
            throw new Exception("Please fill in a account name!");
        }
        if (!password1.equals(password2)) {
            throw new Exception("Your passwords don't match!");
        }
        if (email.isEmpty()) {
            throw new Exception("Please fill in your E-Mail adress!");
        }

        //input ok => save User
        IUser user = new User(password1, accountname,
                firstname + " " + lastname, email, UserType.NORMAL);
        engine.createUser(user);
        //If no Exception occured, user was successfully saved.
        frame.showMessage("User registration", "Your account was successfully created!", MessageType.SUCCESS);

        setState(ViewControllerState.LOGIN);
    }

    /**
     *
     */
    private TravelSalesViewController() {

        this.engine = new TravelSalesEngine();
        engine.start();
        frame = new TravelSalesMan_View();
        state = ViewControllerState.LOGIN;

    }

    public void start() {
        setState(ViewControllerState.LOGIN);
        frame.setVisible(true);
    }

    public void login(String user, String password) {
        try {
            activeUser = engine.checkLoginData(user, password);
            numberUnreadMessages = 0;
            engine.getNotificationsRecived(activeUser, n -> n.forEach(mess -> {
                if (mess.getReadTime() == null) {
                    numberUnreadMessages++;
                }
            }));
            if (numberUnreadMessages != 0) {
                frame.setNumberUnreadMessages(numberUnreadMessages);
            }

            engine.getGamesByAccountName(user, games -> frame.setMyGames(games));
            engine.getAllPublicGames(games -> frame.setAllGames(games));
            engine.getRecommendedGames(activeUser, games -> frame.setRecommendedGames(games));
            frame.setUsername(activeUser.getUserName()); //Set name for "Welcome <name>!"
            frame.adminFunctionsVisible(activeUser.getUserType() == UserType.ADMIN);
            setState(ViewControllerState.SELECT_GAME);

        } catch (TravelSalesException e) {
            switch (e.getType()) {
                case INVALID_PASSWORD:
                    JOptionPane.showMessageDialog(frame, "Username or password incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, e.getMessage(), "Internal Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Internal Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void showMessages() {
        setState(ViewControllerState.MESSAGE);
        engine.getNotificationsRecived(activeUser, n -> {
            frame.setMessages(n);
        });
    }

    public String readMessage(INotification message) {
        engine.readNotification(message);

        return message.getContent();
    }

    public void setEditGameMode(boolean activated) {
        this.editGameMode = activated;
    }

    public void selectGame(IGame game) {
        if (!editGameMode) {
            setState(ViewControllerState.SELECT_LEVEL_BASEGAME);
            engine.getLevelsByGame(game, levels -> frame.setLevels(levels));
        } else {
            setState(ViewControllerState.EDIT_GAME);
            frame.setGameToEdit(game);
        }

    }

    public void selectLevel(ILevel level) {
        this.selectedLevel = level;
        frame.setSelectedLevel(level);
        engine.getBaseGamesByLevel(level, basegames -> frame.setBaseGames(basegames));
    }

    public void selectBaseGame(IBaseGame game) {
        this.selectedBaseGame = game;
        frame.setSelectedBaseGame(game);
    }

    public void playSelectedBaseGame() {
        frame.setPlayingBaseGame(selectedBaseGame);
        setState(ViewControllerState.PLAY_GAME);
        // TODO set playstatistic
        engine.getPlayStatistics(selectedBaseGame, c -> {
            if (!c.isEmpty()) {
                IPlayStatistic[] stat = c.toArray(new IPlayStatistic[c.size()]);
                frame.setPlayStatistics(stat[0]);
            }
        });
        finishedGame = false;
    }

    public void createGame() {
        setState(ViewControllerState.NEW_GAME);
    }

    public void newGame(String name, String logoPath, boolean visible) {
        try {
            IGame newGame = new Game(name, logoPath, activeUser, visible);
            engine.newGame(newGame);
            engine.getAllPublicGames(games -> frame.setAllGames(games));
            engine.getGamesByAccountName(activeUser.getAccountName(), games -> frame.setMyGames(games));
            setState(ViewControllerState.SELECT_GAME);

        } catch (Exception e) {
            frame.showMessage("Error", e.getMessage(), MessageType.ERROR);
        }

    }

    public void editSelectedBaseGame() {
        setState(ViewControllerState.EDIT_BASE_GAME);
        frame.setBaseGameToEdit(this.selectedBaseGame);
    }

    public void editSelectedLevel() {
        setState(ViewControllerState.EDIT_LEVEL);
        frame.setLevelToEdit(this.selectedLevel);
    }

    public void restartGame() {
        frame.restartGame();
    }

    public void quit(Collection<ILine> lines) {
        engine.saveLines(lines);
        engine.updatePlayStatistic(activeUser, selectedBaseGame, 0, finishedGame, 0, 0);
        setState(ViewControllerState.SELECT_GAME);
    }

    public void shutdown() {
        engine.shutdown();
    }

    public void setState(ViewControllerState nextState) {
        this.lastState = state;
        this.state = nextState;
        frame.show(state);
    }

    public IUser getActiveUser() {
        return activeUser;
    }

    public IBaseGame getSelectedBaseGame() {
        return selectedBaseGame;
    }
}
