package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Player;

@Local
public interface PlayerRepositoryLocal extends IDispatcherRepository<Player, Long> {

}
