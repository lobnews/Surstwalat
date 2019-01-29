package de.fh_dortmund.inf.cw.surstwalat.test;

import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.WrongPasswordException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test all user management functions
 * 
 * @author Stephan Klimek
 */
public class UserManagementTest {

    UserManagementHandler handler;

    public UserManagementTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        handler = UserManagementHandler.getInstance();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUserManagment() {
        try {
            handler.register("jUnitTest1", "jUnitTest1@mail.de", "jUnitTest1");
            handler.register("jUnitTest2", "jUnitTest2@mail.de", "jUnitTest2");
            handler.register("jUnitTest3", "jUnitTest3@mail.de", "jUnitTest3");

            // jUnitTest1
            handler.login("jUnitTest1", "jUnitTest1");
            handler.changePassword("jUnitTest1", "jUnitTest1_2");
            handler.login("jUnitTest1", "jUnitTest1_2");
            handler.deleteAccount();

            // jUnitTest2
            handler.login("jUnitTest2", "jUnitTest2");
            handler.updateEmailAddress("jUnitTest1-1@mail.de");
            handler.deleteAccount();

            // jUnitTest2
            handler.login("jUnitTest3", "jUnitTest3");
            handler.deleteAccount();
        } catch (AccountAlreadyExistException | GeneralServiceException | AccountNotFoundException | LoginFailedException | WrongPasswordException ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test(expected = AccountNotFoundException.class)
    public void testAccountNotFoundException() {
        try {
            handler.login("jUnitTest1", "jUnitTest1");
        } catch (GeneralServiceException | AccountNotFoundException | LoginFailedException ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test(expected = AccountAlreadyExistException.class)
    public void testAccountAlreadyExistException() {
        try {
            handler.register("jUnitTest1", "jUnitTest1@mail.de", "jUnitTest1");
            handler.register("jUnitTest1", "jUnitTest2@mail.de", "jUnitTest2");

            handler.login("jUnitTest1", "jUnitTest1");
            handler.deleteAccount();
        } catch (AccountAlreadyExistException | GeneralServiceException | AccountNotFoundException | LoginFailedException ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test(expected = LoginFailedException.class)
    public void testLoginFailedException() {
        try {
            handler.register("jUnitTest1", "jUnitTest1@mail.de", "jUnitTest1");

            handler.login("jUnitTest1", "wrongPWD");
            handler.login("jUnitTest1", "jUnitTest1");
            handler.deleteAccount();
        } catch (AccountAlreadyExistException | GeneralServiceException | AccountNotFoundException | LoginFailedException ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test(expected = WrongPasswordException.class)
    public void testWrongPasswordException() {
        try {
            handler.register("jUnitTest1", "jUnitTest1@mail.de", "jUnitTest1");
            handler.login("jUnitTest1", "jUnitTest1");
            
            handler.changePassword("testUnitTest2", "testUnitTest3");
            handler.deleteAccount();
        } catch (AccountAlreadyExistException | GeneralServiceException | AccountNotFoundException | LoginFailedException | WrongPasswordException ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
