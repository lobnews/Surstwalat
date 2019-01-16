package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.ActionResult;

@Local
public interface ActionResultRepositoryLocal extends IDispatcherRepository<ActionResult, Long> {

}
