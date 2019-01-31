/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client;

import de.fh_dortmund.inf.cw.surstwalat.client.event.Event;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.AssignActivePlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.AssignPlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.EliminatePlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.GameStartedEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.ItemAddToUserEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.ItemSpawnEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.PlayerInventarEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.PlayerOnFieldMessage;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.SetTokenHealthEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.StartRoundEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.TokenCreatedEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.TokenDeathEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.UpdateZoneEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.UserEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.UserJoinGameEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import java.io.Serializable;
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
    
    public ObjectMessage getMsg(Serializable o) {
        return jmsContext.createObjectMessage(o);
    }
    
    public ObjectMessage getMsg() {
        return jmsContext.createObjectMessage();
    }

    @Override
    public void onMessage(Message message) {
        try {
            if(!(message instanceof ObjectMessage)) {
                return;
            }
            
            Event e;
            switch(message.getIntProperty(PropertyType.MESSAGE_TYPE)) {
                case MessageType.ASSIGN_ACTIVE_PLAYER:
                    e = new AssignActivePlayerEvent(message);
                    break;
                case MessageType.ASSIGN_PLAYER:
                    e = new AssignPlayerEvent(message);
                    break;
                case MessageType.ELIMINATE_PLAYER:
                    e = new EliminatePlayerEvent(message);
                    break;
                case MessageType.GAME_STARTED:
                    e = new GameStartedEvent(message);
                    break;
                case MessageType.ITEM_ADD_TO_USER:
                    e = new ItemAddToUserEvent(message);
                    break;
                case MessageType.ITEM_SPAWN:
                    e = new ItemSpawnEvent(message);
                    break;
                case MessageType.PLAYER_INVENTAR:
                    e = new PlayerInventarEvent(message);
                    break;
                case MessageType.PLAYER_ON_FIELD:
                    e = new PlayerOnFieldMessage(message);
                    break;
                case MessageType.USER_JOINGAME:
                    e = new UserJoinGameEvent(message);
                    break;
                case MessageType.SET_TOKEN_HEALTH:
                    e = new SetTokenHealthEvent(message);
                    break;
                case MessageType.START_ROUND:
                    e = new StartRoundEvent(message);
                    break;
                case MessageType.TOKEN_CREATED:
                    e = new TokenCreatedEvent(message);
                    break;
                case MessageType.TOKEN_DEATH:
                    e = new TokenDeathEvent(message);
                    break;
                case MessageType.UPDATE_ZONE:
                    e = new UpdateZoneEvent(message);
                    break;
                case MessageType.USER_LOGOUT:
                case MessageType.USER_DISCONNECT:
                case MessageType.USER_TIMEOUT:
                case MessageType.USER_REGISTER:
                case MessageType.USER_LOGIN:
                case MessageType.USER_DELETE:
                    e = new UserEvent(message);
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
