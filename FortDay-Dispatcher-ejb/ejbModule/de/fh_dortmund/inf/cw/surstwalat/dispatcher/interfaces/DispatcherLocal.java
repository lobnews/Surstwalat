package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;


@Local
public interface DispatcherLocal {
	void addGame(int gameId, int kiCount, int...userIds);
	void addPlayer(int gameId, int playerNo);
}
