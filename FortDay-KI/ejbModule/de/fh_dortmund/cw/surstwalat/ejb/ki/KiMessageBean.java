package de.fh_dortmund.cw.surstwalat.ejb.ki;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import java.util.logging.Level;

/**
 * @author Marcel Scholz
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")}, mappedName = "java:global/jms/FortDayEventTopic")
public class KiMessageBean implements MessageListener {

    @EJB
    private KiBean kibean;

    /**
     * Incomming Messages
     * 
     * @param message incoming message
     */
    @Override
    public void onMessage(Message message) {
	ObjectMessage o = (ObjectMessage) message;
	int messageType = -1;
	try {
	    messageType = o.getIntProperty(PropertyType.MESSAGE_TYPE);
	} catch (JMSException ex) {
	    System.out.println("Unable to get Message_Type from message at Ki.");
	    java.util.logging.Logger.getLogger(KiMessageBean.class.getName()).log(Level.SEVERE, "JMSException", ex);
	}
	switch (messageType) {
	    case MessageType.ASSIGN_ACTIVE_PLAYER:
		makeTurn(message);
		break;
	    case MessageType.PLAYER_ROLL:
		moveToken(message);
		break;
	}
    }

    /**
     * Make Turn
     * 
     * @param message incomming Message
     */
    private void makeTurn(Message message) {
	try {
	    int player_id = message.getIntProperty(PropertyType.PLAYER_ID);
	    int gameid = message.getIntProperty(PropertyType.GAME_ID);
	    kibean.makeTurn(player_id, gameid);
	} catch (JMSException ex) {
	    java.util.logging.Logger.getLogger(KiMessageBean.class.getName()).log(Level.SEVERE, "JMSException", ex);
	}
    }

    /**
     * Move Token
     * 
     * @param message incomming Message
     */
    private void moveToken(Message message) {
	try {
	    int player_id = message.getIntProperty(PropertyType.PLAYER_ID);
	    int game_id = message.getIntProperty(PropertyType.GAME_ID);
	    kibean.moveToken(player_id, game_id);
	} catch (JMSException ex) {
	    java.util.logging.Logger.getLogger(KiMessageBean.class.getName()).log(Level.SEVERE, "JMSException", ex);
	}
    }
}
