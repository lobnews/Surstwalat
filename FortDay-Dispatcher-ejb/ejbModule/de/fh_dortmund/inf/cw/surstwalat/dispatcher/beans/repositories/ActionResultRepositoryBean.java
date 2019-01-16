package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.ActionResult;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.ActionResultRepositoryLocal;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class ActionResultRepositoryBean
 */
@Stateless
public class ActionResultRepositoryBean extends DispatcherRepository<ActionResult, Long> implements ActionResultRepositoryLocal {

    /**
     * Default constructor. 
     */
    public ActionResultRepositoryBean() {
        super(ActionResult.class);
    }

}
