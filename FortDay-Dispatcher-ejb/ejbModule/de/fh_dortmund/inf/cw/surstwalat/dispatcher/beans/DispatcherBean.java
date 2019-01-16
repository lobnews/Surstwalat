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
	public void assignPlayer(int gameId, int playerId) {
		// TODO Auto-generated method stub
		
	}
    
    
    

}
