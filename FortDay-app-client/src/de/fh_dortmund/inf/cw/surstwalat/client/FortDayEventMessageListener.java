/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client;

import de.fh_dortmund.inf.cw.surstwalat.client.event.Event;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.AssignActivePlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.AssignPlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.StartRoundEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 *
 * @author Lars
 */
public class FortDayEventMessageListener implements MessageListener {
    
    private static FortDayEventMessageListener INSTANCE;
    
    private final JMSContext jmsContext;
    private final Topic fortDayEventTopic;
    
    public synchronized static FortDayEventMessageListener getInstance(Context ctx) {
        if(INSTANCE == null) {
            JMSContext jmsContext;
            Topic fortDayEventTopic;
            
            try {
                ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("java:comp/DefaultJMSConnectionFactory");
                jmsContext = connectionFactory.createContext();

                fortDayEventTopic = (Topic) ctx.lookup("java:global/jms/FortDayEventTopic");
                INSTANCE = new FortDayEventMessageListener(jmsContext, fortDayEventTopic);
                jmsContext.createConsumer(fortDayEventTopic).setMessageListener(INSTANCE);
            } catch (NamingException ex) {
                Logger.getLogger(UserManagementHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return INSTANCE;
    }

    public FortDayEventMessageListener(JMSContext jmsContext, Topic fortDayEventTopic) {
        this.jmsContext = jmsContext;
        this.fortDayEventTopic = fortDayEventTopic;
    }

    @Override
    public void onMessage(Message message) {
        try {
            if(!(message instanceof ObjectMessage)) {
                return;
            }
            
            ObjectMessage objectMessage = (ObjectMessage) message;
            System.out.printf("Read MessageType %d\n", objectMessage.getIntProperty(PropertyType.MESSAGE_TYPE));
            Event e;
            switch(objectMessage.getIntProperty(PropertyType.MESSAGE_TYPE)) {
                case MessageType.ASSIGN_PLAYER:
                    e = new AssignPlayerEvent(objectMessage);
                    break;
                case MessageType.START_ROUND:
                    e = new StartRoundEvent(objectMessage);
                    break;
                case MessageType.ASSIGN_ACTIVE_PLAYER:
                    e = new AssignActivePlayerEvent(objectMessage);
                    break;
                default:
                    return;
            }
            MainFrame.getInstance().getEventManager().fireEvent(e);
        } catch (JMSException ex) {
            Logger.getLogger(FortDayEventMessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
