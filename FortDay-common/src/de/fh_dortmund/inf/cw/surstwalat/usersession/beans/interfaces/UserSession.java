package de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces;

/**
 * @author Daniel Buschmann
 *
 */
public interface UserSession{

	public void login();
	
	public void logout();
	
	public void register();
	
	public void disconnect();
	
	public void timeout();
}
