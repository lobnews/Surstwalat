package de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans.interfaces;

import java.util.List;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;

/**
 * GlobalEventManagement interface
 * 
 * @author Rebekka Michel
 */

public interface GlobalEventManagement {
	public void updateZone(int gameId, int roundNo);
	public void triggerAirdrop(int gameId);
	public void triggerStartingItems(int gameId);
	public void triggerDamage(int gameId, List<Token> token);
}
