package de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans.interfaces.GlobalEventManagementLocal;

/**
 * 
 * @author Rebekka Michel
 */
@MessageDriven(
		activationConfig = { 
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class GlobalEventManagementMessageReceiver implements MessageListener{

	@EJB 
	private GlobalEventManagementLocal globalEventManagement;
	
	@Override
	public void onMessage(Message message) {
    	ObjectMessage o = (ObjectMessage) message;
    	int messageType = -1;
    	try {
			messageType = o.getIntProperty(PropertyType.MESSAGE_TYPE);
		} catch (JMSException e) {
			//nothing to do, message type is still -1, switch-case will catch nothing
			System.out.println("Unable to get Message_Type from message at GlobalEventManagement.");
			e.printStackTrace();
		}
    	switch(messageType) {
    		case MessageType.PLAYER_ROLL: playerRoll(message);break;
    		case MessageType.START_ROUND: updateZone(message);break;
    		case MessageType.GAME_STARTED: triggerStartingItems(message);break;
    		// TODO Message, dass Figur in Gift steht bearbeiten
    	}
	}

    public void playerRoll (Message message) {
    	try {
    		if(message.getBody(Integer.class) == 6)
    		{
    			int gameId = message.getIntProperty(PropertyType.GAME_ID);
    			
    			globalEventManagement.triggerAirdrop(gameId);    			
    		}
    	}catch(JMSException e) {
    		e.printStackTrace();
    	}
    }
    
    public void updateZone(Message message) {
    	try {
			int gameId = message.getIntProperty(PropertyType.GAME_ID);
			int roundNo = message.getBody(Integer.class);
			
			int damage = (int) Math.floor(roundNo/3);
			
			//TODO Methode/Methodenaufruf bearbeiten
			globalEventManagement.updateZone(gameId, 0, 0, 0, 0, damage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
    
    public void triggerStartingItems(Message message) {
		try {
			int gameId = message.getIntProperty(PropertyType.GAME_ID);
			globalEventManagement.triggerStartingItems(gameId);
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
    
    public void triggerDamage(Message message) {
    	//TODO Auslesen der Nachricht, dass eine Spielfigur außerhalb der sicheren Zone steht, Aufruf der triggerDamage Methode im GlobalEventManagement
    }
}
