package de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans.interfaces;

/**
 * 
 * @author Rebekka Michel
 */

public interface GlobalEventManagement {
	public void updateZone(int gameId, int damage);
	public void triggerAirdrop(int gameId);
	public void triggerStartingItems(int gameId);
	public void triggerDamage(int gameId, int playerNo, int characterId, int damage);
}
