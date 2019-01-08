package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Action;

@Local
public interface ActionRepositoryLocal extends IDispatcherRepository<Action, Long> {

}
