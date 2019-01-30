package de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans;

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
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
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
    		case MessageType.PLAYER_ROLL: System.out.println("[GLOBALEVENTMANAGEMENT] PLAYER_ROLL received"); playerRoll(message); break;
    		case MessageType.START_ROUND: System.out.println("[GLOBALEVENTMANAGEMENT] START_ROUND received"); updateZone(message); break;
    		case MessageType.GAME_STARTED: System.out.println("[GLOBALEVENTMANAGEMENT] GAME_STARTED received"); triggerStartingItems(message); break;
    		case MessageType.TOKENS_IN_TOXIC: System.out.println("[GLOBALEVENTMANAGEMENT] TOKENS_IN_TOXIC received"); triggerCharacterDamage(message); break;
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
			
			globalEventManagement.updateZone(gameId, roundNo);
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
    
    public void triggerCharacterDamage(Message message)
    {
    	try {
			int gameId = message.getIntProperty(PropertyType.GAME_ID);
			
			Object object = ((ObjectMessage) message).getObject();
			List<Token> token = (List<Token>) object;
			
			globalEventManagement.triggerDamage(gameId, token);
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
}
