package de.fh_dortmund.inf.cw.surstwalat.common.usermanagement.beans.interfaces;

/**
 * @author Stephan Klimek
 *
 */
public interface UserManagement {

    public void changePassword(String password, String newPassword) throws Exception;

    public void delete(String password) throws Exception;

    public void disconnect();

    public String getUserName();

    public void login(String userName, String password) throws Exception;

    public void logout() throws Exception;

    public void register(String userName, String password) throws Exception;
}
