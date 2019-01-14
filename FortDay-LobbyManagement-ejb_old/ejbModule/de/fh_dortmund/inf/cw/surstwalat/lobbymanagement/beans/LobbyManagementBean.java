package de.fh_dortmund.inf.cw.surstwalat.lobbymanagement.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.exceptions.GameIsFullException;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.LobbyManagementLocal;




/**
 * Implementation of the given interfaces (Local/Remote). Implements the logic for the Game-Lobbies (until Game-start)
 * @author Niklas Sprenger
 *
 */
@Stateless
public class LobbyManagementBean implements LobbyManagementLocal{
	@PersistenceContext(unitName = "ChatDB")
	private EntityManager em;
	
	private static int maxNumberOfPlayersInLobby = 4;
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	private Set<Account> userInLobby;
	private Map<Game,List<Account>> openGames; //mapping -> Game -> List of user
	
	
	@PostConstruct
	public void init() {
		userInLobby = new HashSet<Account>();
		openGames = new HashMap<Game,List<Account>>();
	}
	
	public void userLoggedIn(int userID) {
		Account user = getAccountByUserID(userID);
		if(!userInLobby.contains(user)) {
			userInLobby.add(user);
		}
	}
	
	public void userDisconnected(int userID) {
		Account user = getAccountByUserID(userID);
		if(userInLobby.contains(user)) {
			userInLobby.remove(user);
		}
	}
	
	public void userTimedOut(int userID) {
		Account user = getAccountByUserID(userID);
		if(userInLobby.contains(user)) {
			userInLobby.remove(user);
		}
	}
	
	public void userCreatesGame(int userID) {
		Account user = getAccountByUserID(userID);
		Game game = createNewGame();
		openGames.put(game, new ArrayList<Account>());
	}
	
	public void userJoinsGame(int userID, int gameID) throws GameIsFullException{
		Account user = getAccountByUserID(userID);
		Game game = getGameByGameID(gameID);
		List<Account> currentUserInGame = openGames.get(game);
		if(currentUserInGame.size() >= maxNumberOfPlayersInLobby) {
			throw new GameIsFullException();
		}else {
			currentUserInGame.add(user);
			openGames.put(game, currentUserInGame);
		}
	}
	
	private void sendGameCreatedMessage(int gameId, int userId, String username) {
		ObjectMessage msg = jmsContext.createObjectMessage();
		try {
			msg.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.GAME_CREATED);
			msg.setIntProperty(PropertyType.GAME_ID, gameId);
			msg.setIntProperty(PropertyType.USER_ID, userId);
			//jmsContext.createProducer().send(eventTopic, msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendGameStartedMessage(int gameId, List<Account> users, int fieldsize) {
		ObjectMessage msg = jmsContext.createObjectMessage();
		try {
			msg.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.GAME_STARTED);
			msg.setIntProperty(PropertyType.GAME_ID, gameId);
			msg.setObjectProperty(PropertyType.USER_IDS, users);
			msg.setObjectProperty(PropertyType.GAME_FIELDSIZE, fieldsize);
			msg.setStringProperty(PropertyType.DISPLAY_MESSAGE, "Game startet!");
			//jmsContext.createProducer().send(eventTopic, msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Set<Account> getUserInLobby(){
		return userInLobby;
	}
	
	public Map<Game,List<Account>> getOpenGames(){
		return openGames;
	}
	
	//temp to create game
	private Game createNewGame() {
		return new Game();
	}
	
	//temp to load user
	private Account getAccountByUserID(int userid) {
		return new Account();
	}

	//temp to load game
	private Game getGameByGameID(int gameid) {
		return new Game();
	}
	
}
