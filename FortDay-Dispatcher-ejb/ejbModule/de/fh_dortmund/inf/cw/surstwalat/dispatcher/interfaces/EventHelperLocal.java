package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;
/**
 * 
 * @author Johannes Heiderich
 *
 */
@Local
public interface EventHelperLocal {
	void triggerAssignPlayerEvent(Integer gameId, Integer userId, Integer playerNo);
	void triggerStartRoundEvent(Integer gameId, Integer roundNo);
	void triggerAssignActivePlayerEvent(Integer gameId, Integer playerNo);
	void triggerPlayerRollEvent(Integer gameId, Integer playerNo, Integer value);
	void triggerEndRoundEvent(Integer gameId, Integer roundNo);
	void triggerEliminatePlayerEvent(Integer gameId, Integer playerNo);
}
