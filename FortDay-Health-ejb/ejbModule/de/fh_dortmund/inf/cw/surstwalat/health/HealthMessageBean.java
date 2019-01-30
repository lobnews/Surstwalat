package de.fh_dortmund.inf.cw.surstwalat.health;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.healthmanagement.beans.interfaces.HealthManagementLocal;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


/**
 * Message-Driven Bean implementation class for: HealthMessageBean
 */
@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(
                    propertyName = "destinationType", propertyValue = "javax.jms.Topic")
        },
        mappedName = "java:global/jms/FortDayEventTopic")
public class HealthMessageBean implements MessageListener {
    
    @EJB
    private HealthManagementLocal healthManagement;
    
    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        ObjectMessage o = (ObjectMessage) message;
        int messageType;

        try {
            messageType = o.getIntProperty(PropertyType.MESSAGE_TYPE);
        } catch (JMSException e) {
            return;
            // Ignore Message
        }
        
        try {
            switch (messageType) {
                case MessageType.TRIGGER_DAMAGE:
                	System.out.println("[HEALTHMANAGEMENT] TRIGGER_DAMAGE received");
                	List<Integer> tokenIds = o.getBody(List.class);
                	healthManagement.bulkDamage(o.getIntProperty(PropertyType.GAME_ID), tokenIds, o.getIntProperty(PropertyType.DAMAGE));                 
                    break;
                case MessageType.ASSIGN_PLAYER:
                	System.out.println("[HEALTHMANAGEMENT] ASSIGN_PLAYER received");
                    healthManagement.createTokens(o.getIntProperty(PropertyType.PLAYER_ID), o.getIntProperty(PropertyType.GAME_ID));
                    break;
                case MessageType.COLLISION_WITH_PLAYER:
                	System.out.println("[HEALTHMANAGEMENT] COLLISION_WITH_PLAYER received");
                	healthManagement.killToken(o.getIntProperty(PropertyType.GAME_ID), o.getIntProperty(PropertyType.ENEMY_CHARACTER_ID));
                	break;
                	                
            }
        } catch (JMSException ex) {
            Logger.getLogger(HealthMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
