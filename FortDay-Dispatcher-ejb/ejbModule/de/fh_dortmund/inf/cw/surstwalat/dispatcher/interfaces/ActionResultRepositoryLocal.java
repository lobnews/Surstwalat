package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionResult;

@Local
public interface ActionResultRepositoryLocal extends IDispatcherRepository<ActionResult, Integer> {

}
