package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Action;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.ActionRepositoryLocal;

/**
 * Session Bean implementation class ActionRepositoryBean
 * @author Johannes Heiderich
 */
@Stateless
public class ActionRepositoryBean extends DispatcherRepository<Action, Integer> implements ActionRepositoryLocal {

    /**
     * Default constructor. 
     */
    public ActionRepositoryBean() {
        super(Action.class);
    }
       
}
