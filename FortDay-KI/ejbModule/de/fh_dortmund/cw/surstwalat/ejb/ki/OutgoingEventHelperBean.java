package de.fh_dortmund.cw.surstwalat.ejb.ki;

import java.io.Serializable;

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
import de.fh_dortmund.inf.cw.surstwalat.common.model.Action;
import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;

/**
 * @author Marcel Scholz
 *
 */
@Stateless
public class OutgoingEventHelperBean {

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:global/jms/FortDayEventTopic")
    private Topic eventTopic;

    /**
     *
     *
     * @param gameId    game id
     * @param player_no player no
     */
    public void sendInventoryRequest(Integer gameId, Integer player_no) {
	ObjectMessage message = createObjectMessage(gameId, MessageType.SEND_PLAYER_INVENTAR);
	trySetIntProperty(message, PropertyType.PLAYER_NO, player_no);
	sendMessage(message);
    }

    /**
     * Send item use
     *
     * @param gameid    game id
     * @param player_id player id
     * @param item      item
     */
    public void sendUseItem(int gameid, int player_id, Item item) {
	ObjectMessage msg = createObjectMessage(gameid, MessageType.PLAYER_ACTION);
	Action a = new Action();
	a.setActionType(ActionType.USE_ITEM);
	trySetIntProperty(msg, PropertyType.ACTION_TYPE, a.getActionType().ordinal());
	trySetIntProperty(msg, PropertyType.PLAYER_ID, player_id);
	trySetObject(msg, item);
	sendMessage(msg);
    }

    /**
     * Send player roll
     *
     * @param gameid    game id
     * @param player_id player id
     */
    public void sendPlayerRoll(int gameid, int player_id) {
	ObjectMessage msg = createObjectMessage(gameid, MessageType.PLAYER_ACTION);
	Action a = new Action();
	a.setActionType(ActionType.ROLL);
	trySetIntProperty(msg, PropertyType.ACTION_TYPE, a.getActionType().ordinal());
	trySetIntProperty(msg, PropertyType.PLAYER_ID, player_id);
	sendMessage(msg);
    }

    /**
     * Create object message
     *
     * @param gameId      game id
     * @param messageType messageType
     * @return object message
     */
    private ObjectMessage createObjectMessage(Integer gameId, int messageType) {
	ObjectMessage message = jmsContext.createObjectMessage();
	trySetIntProperty(message, PropertyType.MESSAGE_TYPE, messageType);
	trySetIntProperty(message, PropertyType.GAME_ID, gameId);
	return message;
    }

    /**
     * Try to set object
     *
     * @param message      incomming message
     * @param propertyType propertyType
     * @param value        int value
     */
    private void trySetIntProperty(Message message, String propertyType, Integer value) {
	try {
	    message.setIntProperty(propertyType, value);
	} catch (JMSException e) {
	    System.out.println("Failed to set" + propertyType + "to " + value);
	}
    }

    /**
     * Try to set object
     *
     * @param message      incomming message
     * @param propertyType propertyType
     * @param value        String value
     */
    private void trySetStringProperty(Message message, String propertyType, String value) {
	try {
	    message.setStringProperty(propertyType, value);
	} catch (JMSException e) {
	    System.out.println("Failed to set" + propertyType + "to " + value);
	}
    }

    /**
     * Try to set object
     *
     * @param message incomming message
     * @param object  body object
     */
    private void trySetObject(ObjectMessage message, Serializable object) {
	try {
	    message.setObject(object);
	} catch (JMSException e) {
	    System.out.println("Failed to set object to" + object);
	}
    }

    /**
     * Incomming Message
     *
     * @param message incomming message
     */
    private void sendMessage(ObjectMessage message) {
	jmsContext.createProducer().send(eventTopic, message);
    }

    /**
     * Send token move
     *
     * @param gameid  game id
     * @param tokenid token id
     * @param count   count
     */
    public void sendTokenMove(int gameid, int tokenid, int count) {
	ObjectMessage message = createObjectMessage(gameid, MessageType.MOVE_TOKEN);
	trySetIntProperty(message, PropertyType.TOKEN_ID, tokenid);
	trySetObject(message, count);
	sendMessage(message);
    }
}
