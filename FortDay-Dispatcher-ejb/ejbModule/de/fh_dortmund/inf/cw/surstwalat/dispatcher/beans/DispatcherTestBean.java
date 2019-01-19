package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.DispatcherTestLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.GameRepositoryLocal;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Session Bean implementation class DispatcherTestBean
 */
@Singleton
@Startup
public class DispatcherTestBean implements DispatcherTestLocal {

	
	@EJB
	private GameRepositoryLocal gameRepository;
	
    /**
     * Default constructor. 
     */
    public DispatcherTestBean() {
        // TODO Auto-generated constructor stub
    }
    
    private void testGameRepo() {
    	gameRepository.findById(1);
    }

}
