/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.healthmanagement.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;

/**
 *
 * @author Lars
 */
public interface HealthManagement {
    
    public void damageToken(int gameId, int characterId, int damage);
    public void createTokens(int playerId, int gameId);
    
    public Token getToken(int tokenId);
    public int getTokenCount(int playerId);
    
}
