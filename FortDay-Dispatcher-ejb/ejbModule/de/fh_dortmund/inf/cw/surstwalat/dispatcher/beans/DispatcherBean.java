package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Action;
import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.DispatcherTimerInfo;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.common.model.RollActionResult;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.interfaces.DispatcherLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.DiceRollLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.EventHelperLocal;

/**
 * Bean for various dispatcher functions
 * The class manages the Game sequence and state
 * 
 * @author Johannes Heiderich
 */
@Stateless
public class DispatcherBean implements DispatcherLocal {
	
	private static final String REMINDER_TIMER = "REMINDER_TIMER";
	private static final String TIMEOUT_TIMER = "TIMEOUT_TIMER";

	@EJB
	private EventHelperLocal eventHelper;
	@EJB
	private DiceRollLocal diceRoll;
	
	@PersistenceContext(unitName = "FortDayDB")
	protected EntityManager entityManager;
	
	@Resource
	private TimerService timerService;
	
	@Resource(name = "playerTimeoutSeconds")
	private Integer playerTimeoutSeconds;

	/**
	 * Default constructor.
	 */
	public DispatcherBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see DispatcherLocal#createPlayers(int)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createPlayers(int gameId) {
		// TODO Auto-generated method stub
		Game game = entityManager.find(Game.class, gameId, LockModeType.PESSIMISTIC_READ);
		if (game != null && (game.getPlayers() == null || game.getPlayers().size() == 0)) {
			List<Player> players = new ArrayList<>();
			int playerNumber = 1;
			if (game.getHumanUsersInGame() != null) {
				for (Account account : game.getHumanUsersInGame()) {
					players.add(createPlayer(account.getId(), playerNumber++, game, true));
				}
			}
			for (int i = 0; i < game.getAiPlayerCount(); i++) {
				players.add(createPlayer(-1, playerNumber++, game, false));
			}
			
			game.setPlayers(players);
			for (Player p : game.getPlayers()) {
				eventHelper.triggerAssignPlayerEvent(p.getGame().getId(), p.getAccountId(), p.getId(), p.getPlayerNo());
			}
		}
	}

	/**
	 * @see DispatcherLocal#playerRoll(int, Dice)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void playerRoll(int playerId, Dice dice) {
		Player player = entityManager.find(Player.class, playerId, LockModeType.PESSIMISTIC_READ);
		if (player != null) {
			resetPlayerTimeout(player.getGame().getId());
			if(dice == null) {
				dice = new Dice();
				dice.setLabel("Default Dice");
				dice.setNumbers(new int[] {1,2,3,4,5,6});
			}
			
			Action action = new Action();
			action.setActionType(ActionType.ROLL);
			action.setItem(dice);
			RollActionResult result = new RollActionResult();
			result.setValue(diceRoll.roll(dice));
			action.setResult(result);
			player.getActions().add(action);
			eventHelper.triggerPlayerRollEvent(player.getGame().getId(), player.getPlayerNo(), result.getValue());
		}
	}

	/**
	 * @see DispatcherLocal#dispatch(int)
	 * creates a list of currently alive player objects and sorts them by number of roll actions and player number
	 * if the list has size greater than one the first player (lowest number of roll actions and the lowest
	 * player number) is set as the next player 
	 * If the next player action count equals the current game round the game round attribute of the game is incremented
	 * and a message of type START_ROUND is triggered
	 * otherwise an ASSIGN_ACTIVE_PLAYER message is triggered
	 * If the list of alive players equals 1 a PLAYER_WINS message is triggered
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void dispatch(int gameId) {
		Game game = entityManager.find(Game.class, gameId, LockModeType.PESSIMISTIC_READ);
		TreeSet<Player> alivePlayers = new TreeSet<>(new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {

				int p1RollsCount = filterActions(p1.getActions(), ActionType.ROLL).size();
				int p2RollsCount = filterActions(p2.getActions(), ActionType.ROLL).size();

				int c = Integer.compare(p1RollsCount, p2RollsCount);
				if (c == 0) {
					return Integer.compare(p1.getPlayerNo(), p2.getPlayerNo());
				}
				return c;
			}
		});
		if (game.getPlayers().size() > 0) {
			for (Player p : game.getPlayers()) {
				if (p.isAlive())
					alivePlayers.add(p);
			}
			if(alivePlayers.size() == 1) {
				Player winner = alivePlayers.first();
				eventHelper.triggerPlayerWinsEvent(gameId, winner.getId(), winner.getPlayerNo());
			} else if (alivePlayers.size() > 1) {
				int firstPlayerRollsCount = filterActions(alivePlayers.first().getActions(), ActionType.ROLL).size();

				if (firstPlayerRollsCount == game.getCurrentRound()) {
					game.setCurrentRound(game.getCurrentRound() + 1);
					eventHelper.triggerStartRoundEvent(gameId, game.getCurrentRound());
				} else {
					Player nextPlayer = alivePlayers.first();
					createPlayerTimeout(gameId, nextPlayer.getId(), nextPlayer.getPlayerNo());
					eventHelper.triggerAssignActivePlayerEvent(gameId, nextPlayer.getId(), nextPlayer.getPlayerNo(), playerTimeoutSeconds);
				}
			}
		}
	}

	/**
	 * @see DispatcherLocal#onPlayerDeath(int)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void onPlayerDeath(int playerId) {
		Player p = entityManager.find(Player.class, playerId, LockModeType.PESSIMISTIC_READ);
		if (p != null) {
			p.setAlive(false);
			eventHelper.triggerEliminatePlayerEvent(p.getGame().getId(), p.getId(), p.getPlayerNo());
		}
	}

	/**
	 * Creates a player object
	 * @param id the user id
	 * @param no number of the player
	 * @param game the game object
	 * @param isHuman if player is human
	 * @return a Player object
	 */
	private Player createPlayer(int id, int no, Game game, boolean isHuman) {
		Player p = new Player();
		p.setAccountId(id);
		p.setHuman(isHuman);
		p.setGame(game);
		p.setAlive(true);
		p.setPlayerNo(no);
		entityManager.persist(p);
		return p;
	}

	/**
	 * Creates a filtered list of actions that have the given @see de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType
	 * @param actions the unfiltered list
	 * @param type the type criteria
	 * @return list of filtered actions
	 */
	private List<Action> filterActions(List<Action> actions, ActionType type) {
		List<Action> filtered = new ArrayList<>();
		for (Action a : actions) {
			if (a.getActionType() == type)
				filtered.add(a);
		}
		return filtered;
	}
	
	
	/**
	 * Creates a timeout for the players turn
	 * @param gameId the id of the game
	 * @param playerId the id of the player
	 * @param playerNo the number of the player (1-4)
	 */
	private void createPlayerTimeout(int gameId, int playerId, int playerNo) {
		if(playerTimeoutSeconds >= 45) {
			TimerConfig reminderTimerConfig = new TimerConfig();
			reminderTimerConfig.setInfo(new DispatcherTimerInfo(REMINDER_TIMER, gameId, playerId, playerNo, 30));
			reminderTimerConfig.setPersistent(true);
			timerService.createSingleActionTimer((playerTimeoutSeconds-30)*1000, reminderTimerConfig);
		}
		
		TimerConfig timeoutTimerConfig = new TimerConfig();
		timeoutTimerConfig.setInfo(new DispatcherTimerInfo(TIMEOUT_TIMER, gameId, playerId, playerNo, 0));
		timeoutTimerConfig.setPersistent(true);
		timerService.createSingleActionTimer(playerTimeoutSeconds*1000, timeoutTimerConfig);
	}
	
	/**
	 * Resets the Timeout for the players turn
	 * @param gameId the ID of the game
	 */
	private void resetPlayerTimeout(int gameId) {	
		for (Timer timer : timerService.getTimers()) {
			if(timer.getInfo() != null && timer.getInfo() instanceof DispatcherTimerInfo) {
				DispatcherTimerInfo info = (DispatcherTimerInfo)timer.getInfo();
				if ((TIMEOUT_TIMER.equals(info.getId()) || REMINDER_TIMER.equals(info.getId())) && info.getGameId() == gameId) {
					timer.cancel();
				}
			}
		}		
	}
	
	/**
	 * handles the timeout for the players turn
	 */
	@Timeout
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void handlePlayerTimeout(Timer timer) {
		if(timer.getInfo() != null && timer.getInfo() instanceof DispatcherTimerInfo) {
			DispatcherTimerInfo info = (DispatcherTimerInfo)timer.getInfo();
			if(TIMEOUT_TIMER.equals(info.getId())) {
				Player player = entityManager.find(Player.class, info.getPlayerId());
				Action action = new Action();
				action.setActionType(ActionType.ROLL);
				action.setPlayer(player);
				player.getActions().add(action);
				entityManager.merge(player);
				dispatch(info.getGameId());
			}
			if(REMINDER_TIMER.equals(info.getId())) {
				eventHelper.triggerPlayerTimeoutReminderEvent(info.getGameId(), info.getPlayerId(), info.getPlayerNo(), info.getSecondsLeft());
			}
		}
		
	}
	

}
