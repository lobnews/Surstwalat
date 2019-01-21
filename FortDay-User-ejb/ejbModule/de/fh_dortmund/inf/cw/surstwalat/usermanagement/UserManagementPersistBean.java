package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.UserManagementLocal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Stephan Klimek
 */
//@Startup
@Singleton
public class UserManagementPersistBean implements UserManagementLocal {

    @PersistenceContext(unitName = "FortDayDB")
    private EntityManager entityManager;

//    @PostConstruct
//    public void init() {
//        System.out.println("UserManagement: started");
//    }
    /**
     * Registed new account
     *
     * @param account
     */
    @Override
    public void register(Account account) {
        try {
            hashAccount(account);
            entityManager.persist(account);
        } catch (NullPointerException ex) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementPersistBean.register(): NoResultException");
        } catch (IllegalStateException e) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementPersistBean.register(): IllegalStateException");
        }
    }

    /**
     * Log user in
     *
     * @param account
     */
    @Override
    public void login(Account account) {
        try {
            hashAccount(account);
            Account dbAccount = getAccountByName(account.getName());
            if (dbAccount.getPassword().equals(generateHash(account.getPassword()))) {
                System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementPersistBean.login() success");
            }
        } catch (NoResultException e) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementPersistBean.login(): NoResultException");
        } catch (NullPointerException e) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementPersistBean.login(): NullPointerException");
        } catch (IllegalStateException e) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementPersistBean.login(): IllegalStateException");
        } catch (Exception ex) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementPersistBean.login(): Exception");
        }
    }

    /**
     * Change user password
     *
     * @param account
     */
    @Override
    public void changePassword(Account account) {
        entityManager.merge(account);
    }

    /**
     * Update user email address
     *
     * @param account
     */
    @Override
    public void updateEmailAddress(Account account) {
        entityManager.merge(account);
    }

    /**
     * Remove account
     *
     * @param account
     */
    @Override
    public void deleteAccount(Account account) {
        entityManager.remove(account);
    }

    /**
     * Get account data by account id
     *
     * @param id account id
     * @return account
     * @throws NoResultException
     */
    public Account getAccountById(int id) throws NoResultException {
        TypedQuery<Account> accountQuery = entityManager.createNamedQuery("Account.getById", Account.class);
        accountQuery.setParameter("id", id);
        return accountQuery.getSingleResult();
    }

    /**
     * Get account data by account name
     *
     * @param name
     * @return account
     * @throws java.lang.Exception
     */
    public Account getAccountByName(String name) throws Exception {
        TypedQuery<Account> accountQuery = entityManager.createNamedQuery("Account.getByName", Account.class);
        accountQuery.setParameter("name", name);
        return accountQuery.getSingleResult();
    }

    /**
     * Hash plaintext
     *
     * @param plaintext
     * @return hashed text
     */
    public String generateHash(String plaintext) {
        String hash;
        try {
            MessageDigest encoder = MessageDigest.getInstance("SHA-1");
            hash = String.format("%040x", new BigInteger(1, encoder.digest(plaintext.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            hash = null;
        }
        return hash;
    }

    /**
     * Hash account password
     *
     * @param account account without hashed password
     * @return account with hashed password
     * @throws NullPointerException if account param ist null
     */
    private Account hashAccount(Account account) throws NullPointerException {
        account.setPassword(generateHash(account.getPassword()));
        return account;
    }
}
