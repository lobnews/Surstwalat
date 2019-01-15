package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Game;

@Local
public interface GameRepositoryLocal extends IDispatcherRepository<Game, Long> {

}