package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;

/**
 * Message-Driven Bean implementation class for: PlayerCreatedEventHandlerBean
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(propertyName = "messageSelector", 
				  propertyValue = PropertyType.MESSAGE_TYPE + " = " + MessageType.START_ROUND)
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class PlayerCreatedEventHandlerBean implements MessageListener {

	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        
    }

}
