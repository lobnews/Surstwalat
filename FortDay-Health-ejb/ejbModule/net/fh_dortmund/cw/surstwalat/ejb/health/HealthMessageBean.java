package net.fh_dortmund.cw.surstwalat.ejb.health;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message-Driven Bean implementation class for: HealthMessageBean
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "java:global/jms/HealthMessageTopic")
public class HealthMessageBean implements MessageListener {
	
    public void onMessage(Message message) {
        
    }

}
