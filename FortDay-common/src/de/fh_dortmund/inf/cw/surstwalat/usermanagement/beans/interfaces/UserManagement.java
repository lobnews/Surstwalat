/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;

/**
 * UserManagement interface
 *
 * @author Stephan Klimek
 */
public interface UserManagement {

    public Account login(Account account) throws AccountNotFoundException, LoginFailedException, GeneralServiceException;

    public void changePassword(Account account) throws GeneralServiceException;

    public void register(Account account) throws AccountAlreadyExistException, GeneralServiceException;

    public void updateEmailAddress(Account account) throws GeneralServiceException;

    public void deleteAccount(Account account) throws GeneralServiceException;
}
