package de.fh_dortmund.inf.cw.surstwalat.lobbymanagement.beans;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.LobbyManagementLocal;

/**
 * Cares about all messages that are coming in for the LobbyManagement. Parses them and passes
 * them to the LobbyManagementBean by calling the right methods.
 * @author Niklas Sprenger
 *
 */
@MessageDriven(
		activationConfig = { 
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class LobbyManagementMessageReceiver implements MessageListener {

	@EJB
	private LobbyManagementLocal lobbyManagement;
	
    public void onMessage(Message message) {
    	ObjectMessage o = (ObjectMessage) message;
    	int messageType = -1;
    	try {
			messageType = o.getIntProperty(PropertyType.MESSAGE_TYPE);
		} catch (JMSException e) {
			//nothing to do, message type is still -1, switch-case will catch nothing
			System.out.println("Unable to get Message_Type from message at LobbyManagement.");
			e.printStackTrace();
		}
    	switch(messageType) {
    	
    	}
    	
    }
    
    

}
