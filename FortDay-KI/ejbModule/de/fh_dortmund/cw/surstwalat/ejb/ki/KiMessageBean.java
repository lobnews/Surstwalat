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
	private OutgoingEventHelperBean sender;
	
    private ArrayList<Ki> kis;

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
            case MessageType.ASSIGN_PLAYER:
                createKi(message);
                break;
            case MessageType.ASSIGN_ACTIVE_PLAYER:
                makeTurn(message);
                break;
            case MessageType.PLAYER_INVENTAR:
                refreshInventory(message);
                break;
        }
    }

    private void refreshInventory(Message message) {
        try {
            int player_no = message.getIntProperty(PropertyType.PLAYER_NO);
            ObjectMessage objectMessage = (ObjectMessage) message;
            List<Item> inventory = objectMessage.getBody(List.class);
            kis.get(player_no).setInventory(inventory);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    private void makeTurn(Message message) {
        try {
        	ObjectMessage objectMessage = (ObjectMessage) message;
            int player_no = objectMessage.getBody(Integer.class);
        	
        	kis.get(player_no).makeTurn();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void createKi(Message message) {
    	try {
    		
    		int userid = message.getIntProperty(PropertyType.USER_ID);
    		
    		if(userid == -1)
    		{
    			int gameid = message.getIntProperty(PropertyType.GAME_ID);
        		ObjectMessage objectMessage = (ObjectMessage) message;
        		int player_no = objectMessage.getBody(Integer.class);
        		
    			Ki ki = new Ki(session, sender, gameid, player_no);
    			kis.add(player_no, ki);
    		}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
