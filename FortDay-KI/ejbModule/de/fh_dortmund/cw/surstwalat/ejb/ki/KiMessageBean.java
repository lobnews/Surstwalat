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


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = "java:global/jms/FortDayEventTopic")
public class KiMessageBean implements MessageListener {
	
	@EJB
	private KiBean kiBean;

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
		    	switch(messageType) {
		    	case MessageType.GAME_STARTED: createKi(message);break;
		    	case MessageType.ASSIGN_ACTIVE_PLAYER: makeTurn(message);break;
		    	}
		    	
		    }

			private void makeTurn(Message message) {
				int userid = message.getIntProperty(PropertyType.USER1_ID);
				kiBean.makeTurn(userid);
			}

			private void createKi(Message message) {
				try {
		        	int userid = message.getIntProperty(PropertyType.USER1_ID);
		        	if (userid == -1) {
						
					}
		        	userid = message.getIntProperty(PropertyType.USER2_ID);
		        	if (userid == -1) {
						
					}
		        	userid = message.getIntProperty(PropertyType.USER3_ID);
		        	if (userid == -1) {
						
					}
		        	userid = message.getIntProperty(PropertyType.USER4_ID);
		        	if (userid == -1) {
						
					}
		    	}catch(Exception e) {
		    		e.printStackTrace();
		    	}
				
			}

	}
}