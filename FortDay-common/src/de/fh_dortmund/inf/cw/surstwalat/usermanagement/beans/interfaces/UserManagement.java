/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;

/**
 * UserManagement interface
 *
 * @author Stephan Klimek
 */
public interface UserManagement {

    public void login(Account account);

    public void changePassword(Account account);

    public void register(Account account);

    public void updateEmailAddress(Account account);

    public void deleteAccount(Account account);
}
