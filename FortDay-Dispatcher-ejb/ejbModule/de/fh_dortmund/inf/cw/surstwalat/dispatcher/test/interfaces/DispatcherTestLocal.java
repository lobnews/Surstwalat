package de.fh_dortmund.inf.cw.surstwalat.dispatcher.test.interfaces;

import javax.ejb.Local;
/**
 * 
 * @author Johannes Heiderich
 *
 */
@Local
public interface DispatcherTestLocal {
	int getGameId();
}
