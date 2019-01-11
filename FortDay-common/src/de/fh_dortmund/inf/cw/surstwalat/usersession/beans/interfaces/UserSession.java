package de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces;

/**
 * @author Daniel Buschmann
 *
 */
public interface UserSession{

	public void login(String username, String password) throws Exception;
	
	public void logout();
	
	public void register(String username, String password, String email);
	
	public void disconnect();
	
	public void timeout();
}
