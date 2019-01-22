package de.fh_dortmund.inf.cw.surstwalat.client.user;

import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.WrongPasswordException;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSessionRemote;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.security.auth.login.FailedLoginException;

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
        } catch (NamingException e) {
            System.err.println(e.getMessage());
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
     * Register new account
     *
     * @param name account name
     * @param email account email
     * @param password accout password
     * @throws AccountAlreadyExistException if the account already exists.
     * @throws GeneralServiceException if there is a general service exception
     */
    public void register(String name, String email, String password)
            throws AccountAlreadyExistException, GeneralServiceException {
        userSessionRemote.register(name, password, email);
    }

    /**
     * Login user
     *
     * @param name account name
     * @param password account password
     * @throws AccountNotFoundException if account not exist
     * @throws LoginFailedException if input datas not match the account
     * @throws GeneralServiceException if there is a general service exception
     */
    public void login(String name, String password)
            throws AccountNotFoundException, LoginFailedException, GeneralServiceException {
        userSessionRemote.login(name, password);
    }

    /**
     * Update account password
     *
     * @param oldPassword
     * @param newPassword
     * @throws WrongPasswordException if old password is wrong
     * @throws GeneralServiceException if there is a general service exception
     */
    public void changePassword(String oldPassword, String newPassword)
            throws WrongPasswordException, GeneralServiceException {
        userSessionRemote.changePassword(oldPassword, newPassword);
    }

    /**
     * Update account email address
     *
     * @param email new account email
     * @throws GeneralServiceException if there is a general service exception
     */
    public void updateEmailAddress(String email) throws GeneralServiceException {
        userSessionRemote.updateEmailAddress(email);
    }

    /**
     * Delete account
     *
     * @throws GeneralServiceException if there is a general service exception
     */
    public void deleteAccount() throws GeneralServiceException {
        userSessionRemote.deleteAccount();
    }
}
