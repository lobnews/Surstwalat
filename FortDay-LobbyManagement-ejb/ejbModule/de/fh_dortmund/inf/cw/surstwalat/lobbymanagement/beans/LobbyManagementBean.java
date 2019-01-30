package de.fh_dortmund.inf.cw.surstwalat.lobbymanagement.beans;

import java.util.List;

import javax.annotation.Resource;
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
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.lobbymanagement.beans.interfaces.LobbyManagementLocal;

/**
 * Implementation of the given interfaces (Local/Remote). Implements the logic for the Game-Lobbies (until Game-start).
 * Also handles disconnects or timeout of players if they are in the lobby.
 * @author Niklas Sprenger
 *
 */
@Stateless
public class LobbyManagementBean implements LobbyManagementLocal{
	private static int maxNumberOfPlayersInGame = 4;
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	@PersistenceContext(unitName = "FortDayDB")
	private EntityManager em;
	
	/**
	 * Post Construct method, that runs a test if activated and informs about the start of this bean.
	 */
	//@PostConstruct
	public void init() {
		System.out.println("@@@FortDayLobbyManagementBean started");
		//runTests();
	}
	

	/**
	 * This method gets triggered if a user is logged in. It adds him to the users that are waiting in the lobby.
	 * @param userID ID of the user that logged in.
	 */
	@Override
	public void userLoggedIn(int userID) {
		Account user = getAccountByUserID(userID);
		user.setInLobby(true);
		em.persist(user);
	}
	
	/**
	 * This method gets triggered if a user disconnects. It removes him from the lobby-users and every game he is in currently.
	 * @param userID ID of the user that disconnected.
	 */
	@Override
	public void userDisconnected(int userID) {
		Account user = getAccountByUserID(userID);
		user.setInLobby(false);
		em.persist(user);
		removeHumanPlayerFromGames(userID);
	}
	
	/**
	 * This method gets triggered if a user times out. It removes him from the lobby-users and every game he is in currently.
	 * @param userID ID of the user that timed out.
	 */
	@Override
	public void userTimedOut(int userID) {
		Account user = getAccountByUserID(userID);
		user.setInLobby(false);
		em.persist(user);
		removeHumanPlayerFromGames(userID);
	}
	
	/**
	 * This method gets triggered if a creates a game. It creates a new game entity, initializes it and adds the creator to it.
	 * @param userID ID of the user that created a game.
	 */
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
		if(game.getHumanUsersInGame().size() >= maxNumberOfPlayersInGame) {
			throw new GameIsFullException();
		}else {
			game.addHumanUserToOpenGame(user);
			game.setAiPlayerCount(4-game.getHumanUsersInGame().size());
			user.setInLobby(false);
			em.persist(game);
			em.persist(user);
			sendUserJoinedGameMessage(userID,gameID);
		}
	}
	
	public List<Account> getUsersInOpenGame(int gameid) {
		Game g = getGameByGameID(gameid);
		return g.getHumanUsersInGame();
	}
	
	/**
	 * This method is used to send out a message that a user joined a game.
	 * @param gameId The ID of the games that has been started.
	 * @param fieldsize The fieldsize of the gamefield in this game.
	 */
	private void sendUserJoinedGameMessage(int userId, int gameId) {
		ObjectMessage msg = jmsContext.createObjectMessage();
		try {
			msg.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.USER_JOINGAME);
			msg.setIntProperty(PropertyType.GAME_ID, gameId);
			msg.setIntProperty(PropertyType.USER_ID, userId);
			jmsContext.createProducer().send(eventTopic, msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method gets triggered if a game has been created successful.
	 * @param userID ID of the user that created the game.
	 * @param gameID ID of the game that has been created.
	 */
	private void sendGameCreatedMessage(int gameId, int userId) {
		ObjectMessage msg = jmsContext.createObjectMessage();
		try {
            System.out.println("[LOBBYMANAGEMENT] Game with ID " + gameId + " created by user with id " + userId + "." );    
			msg.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.GAME_CREATED);
			msg.setIntProperty(PropertyType.GAME_ID, gameId);
			msg.setIntProperty(PropertyType.USER_ID, userId);
			jmsContext.createProducer().send(eventTopic, msg);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method gets triggered if the host wants to start his game.
	 * @param gameID ID of the game to be started.
	 * @param fieldsize The size of the field the gamehost chose.
	 */
	@Override
	public void startGame(int gameId, int fieldsize) {
		try {
			Game g = getGameByGameID(gameId);
			g.setGameStarted(true);
			em.persist(g);

			sendGameStartedMessage(g.getId(), fieldsize);
		}catch (NullPointerException e) {
			
		}

	}
	
	/**
	 * This method is used to send out a message that a game has been started.
	 * @param gameId The ID of the games that has been started.
	 * @param fieldsize The fieldsize of the gamefield in this game.
	 */
	private void sendGameStartedMessage(int gameId, int fieldsize) {
		ObjectMessage msg = jmsContext.createObjectMessage();
		try {
            System.out.println("[LOBBYMANAGEMENT] Game with ID " + gameId + " started." );    
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
	
	/**
	 * This method returns a list of all users that are in the lobby (not in a game right
	 * now but logged in).
	 * @return The list of users in the lobby right now.
	 */
	@Override
	public List<Account> getUserInLobby(){
		TypedQuery<Account> query = em.createNamedQuery("Account.getInLobby",Account.class);
		return query.getResultList();
	}
	
	/**
	 * This method returns a list of all open games. Open games are games that are not
	 * started right now.
	 * @return The list of open games.
	 */
	@Override
	public List<Game> getOpenGames(){
		TypedQuery<Game> query = em.createNamedQuery("Game.getOpen",Game.class);
		return query.getResultList();
	}
	
	/**
	 * This method creates and initializes a new game. It also persists it to the db
	 * and get its id.
	 * @return The created game.
	 */
	private Game createNewGame() {
		Game g = new Game();
		em.persist(g);
		em.flush();
		return g;
	}
	
	/**
	 * This method uses named queries to load a game with a given ID from
	 * the DB and return it.
	 * @param gameid The ID of the game to be loaded.
	 * @return The loaded game.
	 */
	private Game getGameByGameID(int gameid) {
		TypedQuery<Game> query = em.createNamedQuery("Game.getById",Game.class);
		query.setParameter("id", gameid);
		return query.getSingleResult();
	}
	
	/**
	 * This method uses named queries to get all games from the DB and returns them.
	 * @return A list of all Games in the Database.
	 */
	private List<Game> getAllGames(){
		TypedQuery<Game> query = em.createNamedQuery("Game.getAll",Game.class);
		return query.getResultList();
	}
	
	/**
	 * This method uses name queries to load a user with a given id from the DB
	 * and return it.
	 * @param userid The id of the user to be loaded.
	 * @return The loaded user
	 */
	private Account getAccountByUserID(int userid) {
		TypedQuery<Account> query = em.createNamedQuery("Account.getById",Account.class);
		query.setParameter("id", userid);
		return query.getSingleResult();
	}
	
	/**
	 * This method is called if a user disconnects or times out for example. It removes him
	 * from all games he is in and closes them if he was the last human player in this game
	 * and it didnt start yet.
	 * @param userid The ID of the user to be removed from games.
	 */
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
	
	/**
	 * This method just runs a few simple tests to check
	 * general functionality while development. The test results need to be checked
	 * manually. (no junit)
	 */
	private void runTests() {
		//create two accounts if they dont exist yet
		Account a1 = null;
		Account a2 = null;
		TypedQuery<Account> query = em.createNamedQuery("Account.getByName",Account.class);
		query.setParameter("name", "a1");
		try {
			a1 = query.getSingleResult();
		}catch (Exception e) {
			a1 = new Account();
			a1.setName("a1");
			a1.setEmail("a1@example.com");
			a1.setPassword("a1a1a1a1");
			em.persist(a1);
		}
		query = em.createNamedQuery("Account.getByName",Account.class);
		query.setParameter("name", "a2");
		try {
			a2 = query.getSingleResult();
		}catch (Exception e) {
			a2 = new Account();
			a2.setName("a1");
			a2.setEmail("a1@example.com");
			a2.setPassword("a1a1a1a1");
			em.persist(a2);
		}
		
		//test create game
		Game g = createNewGame();
		System.out.println("@1 Gameid: "+g);
		
		//test number of open games
		List<Game> open = getOpenGames();
		System.out.println("@2 opengamesCount: "+open.size());
		System.out.println("@2.1 opengamesFirst: "+open.get(0).getId());

		System.out.println("@3 userinlobby(0): " + getUserInLobby().size());
		userLoggedIn(a1.getId());
		System.out.println("@4 userinlobby(1): " + getUserInLobby().size());
		userDisconnected(a1.getId());
		System.out.println("@5 userinlobby(0): " + getUserInLobby().size());
		userLoggedIn(a1.getId());
		userCreatesGame(a1.getId());
		System.out.println("@6 userinlobby(0): " + getUserInLobby().size());
		System.out.println("@7 opengamesCount(2): "+ getOpenGames().size());
		startGame(g.getId(), 2);
		System.out.println("@8 opengamesCount(1): "+ getOpenGames().size());


		userLoggedIn(a2.getId());
		userCreatesGame(a2.getId());
		System.out.println("@9 opengamesCount(2): "+ getOpenGames().size());
		userDisconnected(a2.getId());
		System.out.println("@10 opengamesCount(1): "+ getOpenGames().size());
	}
}
