package de.fh_dortmund.inf.cw.surstwalat.common.interfaces;

/**
 * @author Stephan Klimek
 *
 */
public interface UserManagement {

    public void changePassword(String password, String newPassword) throws Exception;

    public void delete(String password) throws Exception;

    public void disconnect();

    public String getUserName();

    public void login(String accountName, String password) throws Exception;

    public void logout() throws Exception;

    public void register(String accountName, String email, String password) throws Exception;
}
