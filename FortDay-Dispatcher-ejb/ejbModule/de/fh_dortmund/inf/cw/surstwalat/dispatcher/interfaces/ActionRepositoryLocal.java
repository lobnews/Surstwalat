package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Action;

@Local
public interface ActionRepositoryLocal extends IDispatcherRepository<Action, Integer> {

}
