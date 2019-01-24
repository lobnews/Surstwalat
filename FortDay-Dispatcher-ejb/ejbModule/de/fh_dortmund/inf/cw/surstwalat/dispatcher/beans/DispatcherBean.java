package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Action;
import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.common.model.RollActionResult;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.interfaces.DispatcherLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.ActionRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.DiceRollLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.EventHelperLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.GameRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.PlayerRepositoryLocal;

/**
 * Bean for various dispatcher functions
 * The class manages the Game sequence and state
 * 
 * @author Johannes Heiderich
 */
@Stateless
public class DispatcherBean implements DispatcherLocal {

	@EJB
	private ActionRepositoryLocal actionRepository;
	@EJB
	private GameRepositoryLocal gameRepository;
	@EJB
	private PlayerRepositoryLocal playerRepository;
	@EJB
	private EventHelperLocal eventHelper;
	@EJB
	private DiceRollLocal diceRoll;

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
	public void createPlayers(int gameId) {
		// TODO Auto-generated method stub
		Game game = gameRepository.findById(gameId);
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
			gameRepository.save(game);
			for (Player p : players) {
				eventHelper.triggerAssignPlayerEvent(p.getGame().getId(), p.getAccountId(), p.getPlayerNo());
			}
		}
	}

	/**
	 * @see DispatcherLocal#playerRoll(int, Dice)
	 */
	@Override
	public void playerRoll(int playerId, Dice dice) {
		Player player = playerRepository.findById(playerId);
		if (player != null) {
			
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
			playerRepository.save(player);
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
	public void dispatch(int gameId) {
		Game game = gameRepository.findById(gameId);
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
					gameRepository.save(game);
					eventHelper.triggerStartRoundEvent(gameId, game.getCurrentRound());
				} else {
					Player nextPlayer = alivePlayers.first();
					eventHelper.triggerAssignActivePlayerEvent(gameId, nextPlayer.getId(), nextPlayer.getPlayerNo());
				}
			}
		}
	}

	/**
	 * @see DispatcherLocal#onPlayerDeath(int)
	 */
	@Override
	public void onPlayerDeath(int playerId) {
		Player p = playerRepository.findById(playerId);
		if (p != null) {
			p.setAlive(false);
			p = playerRepository.save(p);
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
		return playerRepository.save(p);
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

}
