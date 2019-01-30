package de.fh_dortmund.inf.cw.surstwalat.client.user;

import de.fh_dortmund.inf.cw.surstwalat.client.FortDayEventMessageListener;
import de.fh_dortmund.inf.cw.surstwalat.client.user.view.RegistryPanel;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.WrongPasswordException;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSessionRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Handler for UserManagement
 *
 * @author Stephan Klimek
 */
public class UserManagementHandler implements MessageListener{

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
            initJMSConnection();
        } catch (NamingException e) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void initJMSConnection() {
        FortDayEventMessageListener.getInstance(ctx);
    }

    /**
     * Get instance UserManagementHandler
     *
     * @return instance of UserManagementHandler
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
     * @return new instance of UserManagementHandler
     */
    public static UserManagementHandler getNewInstance() {
        return new UserManagementHandler();
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
     * @param curPassword current password
     * @param newPassword new password
     * @throws WrongPasswordException if old password is wrong
     * @throws GeneralServiceException if there is a general service exception
     */
    public void changePassword(String curPassword, String newPassword)
            throws WrongPasswordException, GeneralServiceException {
        userSessionRemote.changePassword(curPassword, newPassword);
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

    /**
     * Get account email
     *
     * @return email address
     */
    public String getEMailAddress() {
        return userSessionRemote.getEMailAddress();
    }

    /**
     * Get account name
     *
     * @return name
     */
    public String getAccountName() {
        return userSessionRemote.getAccountName();
    }

    @Override
    public void onMessage(Message message) {
        
    }
}
