package de.fh_dortmund.inf.cw.surstwalat.dispatcher;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;

/**
 * Message-Driven Bean implementation class for: EventHandlerBean
 * @author Johannes Heiderich
 */
@MessageDriven(
		activationConfig = { 
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class EventHandlerBean implements MessageListener {
	

    public void onMessage(Message message) {
    	if(message instanceof ObjectMessage) {
    		try {
    			ObjectMessage objectMessage = (ObjectMessage) message;
				MessageType messageType = MessageType.values()[message.getIntProperty(PropertyType.MessageType.toString())];
				switch(messageType) {
					default:
						break;
				}
				
			} catch (JMSException e) {
				e.printStackTrace();
			}
    	}
    }

}
