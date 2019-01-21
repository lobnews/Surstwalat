package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.UserManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.auth.login.FailedLoginException;

/**
 *
 * @author Stephan Klimek
 */
@Stateless
public class UserManagementPersistBean implements UserManagementLocal {

    @PersistenceContext(unitName = "FortDayDB")
    private EntityManager entityManager;

    /**
     * Registed new account
     *
     * @param account input account
     * @throws AccountAlreadyExistException if the account already exists.
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void register(Account account) throws AccountAlreadyExistException, GeneralServiceException {
        hashAccount(account);
        try {
            entityManager.persist(account);
        } catch (EntityExistsException e) {
            throw new AccountAlreadyExistException();
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Login user
     *
     * @param account input account
     * @return logged in user account
     * @throws NoResultException if account not exist
     * @throws FailedLoginException if input datas not match the account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public Account login(Account account) throws NoResultException, FailedLoginException, GeneralServiceException {
        hashAccount(account);
        Account dbAccount = getAccountByName(account.getName());
        if (dbAccount.getPassword().equals(account.getPassword())) {
            return dbAccount;
        } else {
            throw new FailedLoginException();
        }
    }

    /**
     * Update account password
     *
     * @param account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void changePassword(Account account) throws GeneralServiceException {
        try {
            entityManager.merge(account);
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Update account email address
     *
     * @param account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void updateEmailAddress(Account account) throws GeneralServiceException {
        try {
            entityManager.merge(account);
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Remove account
     *
     * @param account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void deleteAccount(Account account) throws GeneralServiceException {
        try {
            entityManager.remove(account);
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Get account data by account id
     *
     * @param id account id
     * @return account
     * @throws NoResultException if there is no result
     * @throws GeneralServiceException if there is a general service exception
     */
    public Account getAccountById(int id) throws NoResultException, GeneralServiceException {
        TypedQuery<Account> accountQuery = entityManager.createNamedQuery("Account.getById", Account.class);
        accountQuery.setParameter("id", id);
        try {
            return accountQuery.getSingleResult();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Get account data by account name
     *
     * @param name account name
     * @return account from db
     * @throws NoResultException if there is no result
     * @throws GeneralServiceException if there is a general service exception
     */
    public Account getAccountByName(String name) throws NoResultException, GeneralServiceException {
        TypedQuery<Account> accountQuery = entityManager.createNamedQuery("Account.getByName", Account.class);
        accountQuery.setParameter("name", name);
        try {
            return accountQuery.getSingleResult();
        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
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
    private Account hashAccount(Account account) {
        account.setPassword(generateHash(account.getPassword()));
        return account;
    }
}
