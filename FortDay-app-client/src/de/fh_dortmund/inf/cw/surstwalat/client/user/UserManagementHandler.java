package de.fh_dortmund.inf.cw.surstwalat.client.user;

import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSessionRemote;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Handler for UserManagement
 *
 * @author Stephan Klimek
 */
public class UserManagementHandler implements MessageListener, ExceptionListener {

    private static UserManagementHandler instance;

    private Context ctx;
    private UserSessionRemote userSessionRemote;

    /**
     * Handler constructor for UserManagement
     */
    public UserManagementHandler() {
        // LookUp to UserSessionRemote
        try {
            ctx = new InitialContext();
            String lookUpString
                    = "java:global/FortDay-ear/FortDay-UserSession-ejb/UserSessionBean!de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSessionRemote";
            userSessionRemote = (UserSessionRemote) ctx.lookup(lookUpString);
        } catch (NamingException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Get instance UserManagementHandler
     *
     * @return
     */
    public static UserManagementHandler getInstance() {
        if (instance == null) {
            instance = new UserManagementHandler();
        }
        return instance;
    }

    /**
     * Get new instance UserManagementHandler
     *
     * @return
     */
    public static UserManagementHandler getNewInstance() {
        return new UserManagementHandler();
    }

    /**
     * On Message
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * On Exception
     *
     * @param exception
     */
    @Override
    public void onException(JMSException exception) {
        System.err.println(exception.getMessage());
    }

    /**
     * Register
     *
     * @param accountName
     * @param email
     * @param password
     * @throws Exception
     */
    public void register(String accountName, String email, String password) throws Exception {
        userSessionRemote.register(accountName, email, password);
    }

    /**
     * Delete account
     */
    public void deleteAccount() {
        userSessionRemote.deleteAccount();
    }

    /**
     * Login user
     *
     * @param username
     * @param password
     * @throws java.lang.Exception
     */
    public void login(String username, String password) throws Exception {
        userSessionRemote.login(username, password);
    }

    /**
     * Change password
     *
     * @param oldPassword
     * @param newPassword
     */
    public void changePassword(String oldPassword, String newPassword) {
        userSessionRemote.changePassword(oldPassword, newPassword);
    }

    /**
     * Update email address
     *
     * @param email
     */
    public void updateEmailAddress(String email) {
        userSessionRemote.updateEmailAddress(email);
    }
}
