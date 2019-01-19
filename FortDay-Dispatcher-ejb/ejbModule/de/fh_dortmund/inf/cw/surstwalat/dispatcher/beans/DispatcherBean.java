package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
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
	public void createPlayers(int gameId) {
		// TODO Auto-generated method stub
		Game game =  gameRepository.findById(gameId);
		List<Player> players = new ArrayList<>();
		
		for(Account account : game.getHumanUsersInGame()) {
			players.add(createPlayer(account.getId(), game, true));
		}
		for (int i = 0; i < game.getAIPlayerCount(); i++) {
			players.add(createPlayer(-1, game, false));
		}
		int playerNumber = 1;
		for(Player p : players) {
			eventHelper.triggerAssignPlayerEvent(p.getGame().getId(), p.getAccountId(), playerNumber++);
		}
	}
	
	private Player createPlayer(int id, Game game, boolean isHuman) {
		Player p = new Player();
		p.setAccountId(id);
		p.setHuman(isHuman);
		p.setGame(game);
		return playerRepository.save(p);
	}
    
    
    

}
