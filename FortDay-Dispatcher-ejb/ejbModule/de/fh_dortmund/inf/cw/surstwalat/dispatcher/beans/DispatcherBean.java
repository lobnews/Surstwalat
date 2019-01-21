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
 * Session Bean implementation class DispatcherBean
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

	@Override
	public void playerRoll(int playerId, Dice dice) {
		Player player = playerRepository.findById(playerId);
		if (player != null) {
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
			if (alivePlayers.size() > 0) {
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

	@Override
	public void onPlayerDeath(int playerId) {
		Player p = playerRepository.findById(playerId);
		if (p != null) {
			p.setAlive(false);
			p = playerRepository.save(p);
			eventHelper.triggerEliminatePlayerEvent(p.getGame().getId(), p.getId(), p.getPlayerNo());
		}
	}

	private Player createPlayer(int id, int no, Game game, boolean isHuman) {
		Player p = new Player();
		p.setAccountId(id);
		p.setHuman(isHuman);
		p.setGame(game);
		p.setAlive(true);
		p.setPlayerNo(no);
		return playerRepository.save(p);
	}

	private List<Action> filterActions(List<Action> actions, ActionType type) {
		List<Action> filtered = new ArrayList<>();
		for (Action a : actions) {
			if (a.getActionType() == type)
				filtered.add(a);
		}
		return filtered;
	}

}
