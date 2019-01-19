package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.events;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.EventHelperLocal;

/**
 * Session Bean implementation class EventHelperBean
 * 
 * @author Pascal Michallik
 */
@Stateless
public class OutgoingEventHelperBean implements EventHelperLocal
{

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:global/jms/FortDayEventTopic")
    private Topic eventTopic;

    @Override
    public void triggerNoCollisionMessage(Integer gameId)
    {
        ObjectMessage message = createObjectMessage(gameId, MessageType.NO_COLLISION);
        sendMessage(message);
    }

    @Override
    public void trigerPlayerOnFieldMessage(Integer gameId, Integer playerId, Integer characterId, Integer fieldId)
    {
        ObjectMessage message = createObjectMessage(gameId, MessageType.PLAYER_ON_FIELD);
        trySetIntProperty(message, PropertyType.PLAYER_NO, playerId);
        trySetIntProperty(message, PropertyType.CHARACTER_ID, characterId);
        trySetIntProperty(message, PropertyType.FIELD_ID, fieldId);
        sendMessage(message);
    }

    @Override
    public void triggerCollisionWithPlayerMessage(Integer gameId, Integer playerId, Integer characterId,
                                                  Integer enemyPlayerId, Integer enemyCharacterId)
    {
        ObjectMessage message = createObjectMessage(gameId, MessageType.COLLISION_WITH_PLAYER);
        trySetIntProperty(message, PropertyType.PLAYER_NO, playerId);
        trySetIntProperty(message, PropertyType.CHARACTER_ID, characterId);
        trySetIntProperty(message, PropertyType.ENEMY_CHARACTER_ID, enemyCharacterId);
        trySetIntProperty(message, PropertyType.ENEMY_PLAYER_ID, enemyPlayerId);
        sendMessage(message);
    }

    @Override
    public void triggerCollisionWithItemMessage(Integer gameId, Integer playerId, Integer characterId, Integer itemId)
    {
        ObjectMessage message = createObjectMessage(gameId, MessageType.COLLISION_WITH_ITEM);
        trySetIntProperty(message, PropertyType.PLAYER_NO, playerId);
        trySetIntProperty(message, PropertyType.CHARACTER_ID, characterId);
        trySetIntProperty(message, PropertyType.ITEM_ID, itemId);
        sendMessage(message);
    }

    @Override
    public void triggerCharactersInToxicMessage(Integer gameId, List<Token> characters)
    {
        ObjectMessage message = createObjectMessage(gameId, MessageType.CHARACTERS_IN_TOXIC);
        trySetObject(message, (Serializable)characters);
        sendMessage(message);
    }

    @Override
    public void triggerCollisionWithOwnCharacterMessage(Integer gameId, Integer playerId, Integer characterId)
    {
        ObjectMessage message = createObjectMessage(gameId, MessageType.COLLISION_WITH_OWN_CHARACTER);
        trySetIntProperty(message, PropertyType.PLAYER_NO, playerId);
        trySetIntProperty(message, PropertyType.CHARACTER_ID, characterId);
        sendMessage(message);
    }

    @Override
    public void triggerToxicChangedMessage(Integer gameId, Integer startZone, Integer endZone)
    {
        ObjectMessage message = createObjectMessage(gameId, MessageType.TOXIC_CHANGE);
        sendMessage(message);
    }

    private ObjectMessage createObjectMessage(Integer gameId, int messageType)
    {
        ObjectMessage message = jmsContext.createObjectMessage();
        trySetIntProperty(message, PropertyType.MESSAGE_TYPE, messageType);
        trySetIntProperty(message, PropertyType.GAME_ID, gameId);
        return message;
    }

    private void trySetIntProperty(Message message, String propertyType, Integer value)
    {
        try
        {
            message.setIntProperty(propertyType, value);
        }
        catch (JMSException e)
        {
            System.out.println("Failed to set" + propertyType.toString() + "to " + value);
        }
    }

    private void trySetObject(ObjectMessage message, Serializable object)
    {
        try
        {
            message.setObject(object);
        }
        catch (JMSException e)
        {
            System.out.println("Failed to set object to" + object);
        }
    }

    private void sendMessage(ObjectMessage message)
    {
        jmsContext.createProducer().send(eventTopic, message);
    }

}
