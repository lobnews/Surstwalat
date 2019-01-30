package de.fh_dortmund.cw.surstwalat.ejb.ki;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession;

/**
 * @author Marcel Scholz
 *
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")}, mappedName = "java:global/jms/FortDayEventTopic")
public class KiMessageBean implements MessageListener {
	
	@EJB
	private KiBean kibean;
	
    @Override
    public void onMessage(Message message) {
        ObjectMessage o = (ObjectMessage) message;
        int messageType = -1;
        try {
            messageType = o.getIntProperty(PropertyType.MESSAGE_TYPE);
        } catch (JMSException e) {
            //nothing to do, message type is still -1, switch-case will catch nothing
            System.out.println("Unable to get Message_Type from message at Ki.");
            e.printStackTrace();
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

    private void makeTurn(Message message) {
        try {
        	int player_id = message.getIntProperty(PropertyType.PLAYER_ID);
        	int gameid = message.getIntProperty(PropertyType.GAME_ID);
        	kibean.makeTurn(player_id, gameid);
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void moveToken(Message message) {
        try {
        	int player_id = message.getIntProperty(PropertyType.PLAYER_ID);
        	int gameid = message.getIntProperty(PropertyType.GAME_ID);
        	kibean.moveToken(player_id);
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
