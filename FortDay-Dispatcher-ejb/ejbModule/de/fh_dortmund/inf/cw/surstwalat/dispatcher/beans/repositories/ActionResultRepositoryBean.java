package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionResult;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.ActionResultRepositoryLocal;

/**
 * Session Bean implementation class ActionResultRepositoryBean
 */
@Stateless
public class ActionResultRepositoryBean extends DispatcherRepository<ActionResult, Integer> implements ActionResultRepositoryLocal {

    /**
     * Default constructor. 
     */
    public ActionResultRepositoryBean() {
        super(ActionResult.class);
    }

}
