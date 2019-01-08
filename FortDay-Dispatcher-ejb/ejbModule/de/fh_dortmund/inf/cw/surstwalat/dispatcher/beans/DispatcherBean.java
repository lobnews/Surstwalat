package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Game;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Player;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.ActionRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.ActionResultRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.DispatcherLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.EventHelperLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.GameRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.PlayerRepositoryLocal;

/**
 * Session Bean implementation class DispatcherBean
 * @author Johannes Heiderich
 */
@Stateless
public class DispatcherBean implements DispatcherLocal {

	
	@EJB
	private ActionRepositoryLocal actionRepository;
	@EJB
	private ActionResultRepositoryLocal actionResultRepository;
	@EJB
	private GameRepositoryLocal gameRepository;
	@EJB
	private PlayerRepositoryLocal playerRepository;
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
		Game game = new Game();
		game.setId(new Long(gameId));
		List<Player> players = game.getPlayers() != null ? game.getPlayers() : new ArrayList<>();
		for(int uId : userIds) {
			Player player = new Player();
			player.setUserId(uId);
			players.add(player);
		}
		for (int i = 0; i < kiCount; i++) {
			Player player = new Player();
			player.setUserId(-1);
			players.add(player);
		}
		game.setPlayers(players);
		gameRepository.save(game);
	}

	@Override
	public void addPlayer(int gameId, int playerNo) {
		Game game = gameRepository.findById(new Long(gameId));
		Player player = null;
		for(Player p : game.getPlayers()) {
			if(p.getPlayerNo() == null) {
				player = p;
				break;
			}
		}
		if(player != null) {
			player.setPlayerNo(playerNo);
			playerRepository.save(player);
			eventHelper.triggerAssignPlayerEvent(gameId, player.getUserId(), player.getPlayerNo());
		}
	}

}
