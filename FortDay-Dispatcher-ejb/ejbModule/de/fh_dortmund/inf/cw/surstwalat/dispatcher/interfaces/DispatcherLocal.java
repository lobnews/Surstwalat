package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;


@Local
public interface DispatcherLocal {
	void assignPlayer(int gameId, int playerId);
}
