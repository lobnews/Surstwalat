package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
/**
 * 
 * @author Pascal Michallik
 *
 */
@Local
public interface EventHelperLocal {
	void triggerNoCollisionMessage(Integer gameId);
	void trigerPlayerOnFieldMessage(Integer gameId, Integer playerId, Integer characterId, Integer fieldId);
	void triggerCollisionWithPlayerMessage(Integer gameId, Integer playerId, Integer characterId, Integer enemyPlayerId, Integer enemyCharacterId);
	void triggerCollisionWithItemMessage(Integer gameId, Integer playerId, Integer characterId, Integer itemId);
	void triggerCollisionWithOwnCharacterMessage(Integer gameId, Integer playerId, Integer characterId);
	void triggerToxicChangedMessage(Integer gameId, Integer startZone, Integer endZone);
    void triggerCharactersInToxicMessage(Integer gameId, List<Token> characters);
}
