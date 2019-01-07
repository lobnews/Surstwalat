package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Game;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Player;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.DispatcherBeanLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.EventHelperLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.GameRepositoryLocal;

/**
 * Session Bean implementation class DispatcherBean
 * @author Johannes Heiderich
 */
@Stateless
public class DispatcherBean implements DispatcherBeanLocal {

	
	@EJB
	private GameRepositoryLocal gameRepository;
	@EJB
	private EventHelperLocal eventHelper; 
	
    /**
     * Default constructor. 
     */
    public DispatcherBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addGame(int gameId, int kiCount, int... userIds) {
		Game game = createGameIfNotExist(new Long(gameId));
		List<Player> players = game.getPlayers() != null ? game.getPlayers() : new ArrayList<>();
		for (int i = 0; i < userIds.length; i++) {
			if(i >= players.size()) {
				players.add(new Player());
			}
			players.get(i).setUserId(userIds[i]);
		}
		for (int i = 0; i < kiCount; i++) {
			if(i >= players.size()) {
				players.add(new Player());
			}
			players.get(i).setUserId(-1);
		}
		game.setPlayers(players);
		gameRepository.save(game);
		for(Player p : game.getPlayers()) {
			sendPlayerAssignedIfComplete(gameId, p);
		}
	}

	@Override
	public void addPlayer(int gameId, int playerNo) {
		Game game = createGameIfNotExist(new Long(gameId));
		List<Player> players = game.getPlayers() != null ? game.getPlayers() : new ArrayList<>();
		Player player = null;
		for(Player p : players) {
			if(p.getPlayerNo() == null) {
				player = p;
				break;
			}
		}
		if(player == null) {
			player = new Player();
			players.add(player);
		}
		player.setPlayerNo(playerNo);
		gameRepository.save(game);
		sendPlayerAssignedIfComplete(gameId, player);
	}
	
	private void sendPlayerAssignedIfComplete(int gameId, Player player) {
		if(player.getPlayerNo() != null && player.getUserId() != null) {
			eventHelper.triggerAssignPlayerEvent(gameId, player.getUserId(), player.getPlayerNo());
		}
	}
	
	private Game createGameIfNotExist(Long gameId) {
		Game game = gameRepository.findById(new Long(gameId));
		if(game == null) {
			game = new Game();
			game.setId(gameId);
		}
		return game;
	}

}
