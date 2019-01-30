/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.healthmanagement.beans.interfaces;

import java.util.List;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;

/**
 *
 * @author Lars
 */
public interface HealthManagement {
    
    public void damageToken(int gameId, int characterId, int damage);
    public void createTokens(int playerId, int gameId, int playerNr);
    
    public Token getToken(int tokenId);
    public int getTokenCount(int playerId);
    
    public void killToken(int gameId, int tokenId);
    
    public void bulkDamage(int gameId, List<Integer> tokenIds, int damage);
    
}
