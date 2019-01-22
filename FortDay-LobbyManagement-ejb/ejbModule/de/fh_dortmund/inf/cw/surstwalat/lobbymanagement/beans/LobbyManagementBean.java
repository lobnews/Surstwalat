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
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.exceptions.GameIsFullException;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.lobbymanagement.beans.interfaces.LobbyManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;

/**
 * Implementation of the given interfaces (Local/Remote). Implements the logic for the Game-Lobbies (until Game-start)
 * @author Niklas Sprenger
 *
 */
@Startup
@Singleton
public class LobbyManagementBean implements LobbyManagementLocal{
	private static int maxNumberOfPlayersInLobby = 4;
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	@PersistenceContext(unitName = "FortDayDB")
	private EntityManager em;
	
	@PostConstruct
	public void init() {
		System.out.println("@@@FortDayLobbyManagementBean started");
		//runTests();
	}
	

	
	@Override
	public void userLoggedIn(int userID) {
		Account user = getAccountByUserID(userID);
		user.setInLobby(true);
		em.persist(user);
	}
	
	@Override
	public void userDisconnected(int userID) {
		Account user = getAccountByUserID(userID);
		user.setInLobby(false);
		em.persist(user);
		removeHumanPlayerFromGames(userID);
	}
	
	@Override
	public void userTimedOut(int userID) {
		Account user = getAccountByUserID(userID);
		user.setInLobby(false);
		em.persist(user);
		removeHumanPlayerFromGames(userID);
	}
	
	@Override
	public void userCreatesGame(int userID) {
		Account user = getAccountByUserID(userID);
		Game game = createNewGame();
		game.addHumanUserToOpenGame(user);
		game.setAiPlayerCount(4-game.getHumanUsersInGame().size());
		user.setInLobby(false);
		em.persist(game);
		em.persist(user);
		
		sendGameCreatedMessage(game.getId(), user.getId());
	}
	
	@Override
	public void userJoinsGame(int userID, int gameID) throws GameIsFullException{
		Account user = getAccountByUserID(userID);
		Game game = getGameByGameID(gameID);
		if(game.getHumanUsersInGame().size() >= maxNumberOfPlayersInLobby) {
			throw new GameIsFullException();
		}else {
			game.addHumanUserToOpenGame(user);
			game.setAiPlayerCount(4-game.getHumanUsersInGame().size());
			user.setInLobby(false);
			em.persist(game);
			em.persist(user);
		}
	}
	
	private void sendGameCreatedMessage(int gameId, int userId) {
		ObjectMessage msg = jmsContext.createObjectMessage();
		try {
			msg.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.GAME_CREATED);
			msg.setIntProperty(PropertyType.GAME_ID, gameId);
			msg.setIntProperty(PropertyType.USER_ID, userId);
			jmsContext.createProducer().send(eventTopic, msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startGame(int gameId, int fieldsize) {
		try {
			Game g = getGameByGameID(gameId);
			g.setGameStarted(true);
			em.persist(g);

			sendGameStartedMessage(g.getId(), g.getHumanUsersInGame(), fieldsize);
		}catch (NullPointerException e) {
			
		}

	}
	
	private void sendGameStartedMessage(int gameId, List<Account> users, int fieldsize) {
		ObjectMessage msg = jmsContext.createObjectMessage();
		try {
			msg.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.GAME_STARTED);
			msg.setIntProperty(PropertyType.GAME_ID, gameId);
			msg.setIntProperty(PropertyType.GAME_FIELDSIZE, fieldsize);
			msg.setStringProperty(PropertyType.DISPLAY_MESSAGE, "Das Spiel wurde gestartet!");
			jmsContext.createProducer().send(eventTopic, msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Account> getUserInLobby(){
		TypedQuery<Account> query = em.createNamedQuery("Account.getInLobby",Account.class);
		return query.getResultList();
	}
	
	public List<Game> getOpenGames(){
		TypedQuery<Game> query = em.createNamedQuery("Game.getOpen",Game.class);
		return query.getResultList();
	}
	
	//create new game in db and return it (with correct id)
	private Game createNewGame() {
		Game g = new Game();
		em.persist(g);
		em.flush();
		return g;
	}
	
	private Game getGameByGameID(int gameid) {
		TypedQuery<Game> query = em.createNamedQuery("Game.getById",Game.class);
		query.setParameter("id", gameid);
		return query.getSingleResult();
	}
	
	private List<Game> getAllGames(){
		TypedQuery<Game> query = em.createNamedQuery("Game.getAll",Game.class);
		return query.getResultList();
	}
	
	private Account getAccountByUserID(int userid) {
		TypedQuery<Account> query = em.createNamedQuery("Account.getById",Account.class);
		query.setParameter("id", userid);
		return query.getSingleResult();
	}
	
	//removes the account with the given id from all games and closes the ones without other
	//human players, if not started yet
	private void removeHumanPlayerFromGames(int userid) {
		List<Game> allGames = getAllGames();
		Account account = getAccountByUserID(userid);
		for(Game game:allGames) {
			if(!game.isGameStarted() && game.getHumanUsersInGame().contains(account)) {
				game.removeHumanUserFromOpenGame(account);
				game.setAiPlayerCount(4-game.getHumanUsersInGame().size());
				if(game.getHumanUsersInGame().size() == 0) {
					em.remove(game);
				}
			}
		}
	}
	
	private void runTests() {
		//test create game
		Game g = createNewGame();
		System.out.println("@1 Gameid: "+g);
		
		//test number of open games
		List<Game> open = getOpenGames();
		System.out.println("@2 opengamesCount: "+open.size());
		System.out.println("@2.1 opengamesFirst: "+open.get(0).getId());
		
		//create game in name of player
		Account a = new Account();
		em.persist(a);
		System.out.println("@3 userinlobby(0): " + getUserInLobby().size());
		userLoggedIn(a.getId());
		System.out.println("@4 userinlobby(1): " + getUserInLobby().size());
		userDisconnected(a.getId());
		System.out.println("@5 userinlobby(0): " + getUserInLobby().size());
		userLoggedIn(a.getId());
		userCreatesGame(a.getId());
		System.out.println("@6 userinlobby(0): " + getUserInLobby().size());
		System.out.println("@7 opengamesCount(2): "+ getOpenGames().size());
		startGame(g.getId(), 2);
		System.out.println("@8 opengamesCount(1): "+ getOpenGames().size());

		Account a2 = new Account();
		em.persist(a2);
		userLoggedIn(a2.getId());
		userCreatesGame(a2.getId());
		System.out.println("@9 opengamesCount(2): "+ getOpenGames().size());
		userDisconnected(a2.getId());
		System.out.println("@10 opengamesCount(1): "+ getOpenGames().size());
	}
}
