package de.fh_dortmund.inf.cw.surstwalat.dispatcher.test.beans;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Action;
import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.GameRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.PlayerRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.test.interfaces.DispatcherTestLocal;

/**
 * Message-Driven Bean implementation class for: DispatcherTestEventHandlerBean
 * @author Johannes Heiderich
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"), }, mappedName = "java:global/jms/FortDayEventTopic")
public class DispatcherTestEventHandlerBean implements MessageListener {

	@EJB
	private DispatcherTestLocal test;
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	@EJB
	private GameRepositoryLocal gameRepository;
	@EJB
	private PlayerRepositoryLocal playerRepository;

	public void onMessage(Message message) {
		try {
			ObjectMessage m = (ObjectMessage) message;
			int gameId = m.getIntProperty(PropertyType.GAME_ID);
			if (gameId == test.getGameId()) {
				int type = m.getIntProperty(PropertyType.MESSAGE_TYPE);

				switch (type) {
				case MessageType.ASSIGN_PLAYER:
					
					
					if (m.getBody(Integer.class).equals(4)) {
						//testNoCollision(gameId);
					}
					break;
				case MessageType.START_ROUND:
					//testNoCollision(gameId);
					break;
				case MessageType.ASSIGN_ACTIVE_PLAYER:
					//testPlayerAction(m.getIntProperty(PropertyType.PLAYER_ID));
					break;
				case MessageType.PLAYER_ROLL:
					//testNoCollision(gameId);
					break;
				case MessageType.ELIMINATE_PLAYER:
					//testPlayerDeath(m.getIntProperty(PropertyType.PLAYER_ID));
					break;
				default:
					break;
				}
			}

		} catch (Exception e) {

		}
	}
	
	private void testCreateToken(int gameId) {
		
	}
	
    private void testNoCollision(int gameId) {
    	Game g = gameRepository.findById(gameId);
    	triggerNoCollisionEvent(g);
    }
    
    private void testPlayerAction(int playerId) {
    	Player p = playerRepository.findById(playerId);
    	Action a = new Action();
    	a.setActionType(ActionType.ROLL);
    	a.setPlayer(p);
    	Dice dice = new Dice();
    	dice.setNumbers(new int[] {1,2,3,4,5,6});
    	a.setItem(dice);
    	triggerPlayerActionEvent(a);
    }
    
    private void testPlayerDeath(int playerId) {
    	Player p = playerRepository.findById(playerId);
    	triggerPlayerDeathEvent(p);
    }
	
    
    private void triggerNoCollisionEvent(Game game) {
    	System.out.println("DISPATCHER (Test): Send NO_COLLISION message with GameId " + game.getId());
		ObjectMessage message = createObjectMessage(MessageType.NO_COLLISION);
		trySetIntProperty(message, PropertyType.GAME_ID, game.getId());
		trySetIntProperty(message, "TEST", game.getCurrentRound());
		sendMessage(message);
	}
    private void triggerPlayerActionEvent(Action action) {
    	System.out.println("DISPATCHER (Test): Send PLAYER_ACTION message with type " + action.getActionType().name());
		ObjectMessage message = createObjectMessage(MessageType.PLAYER_ACTION);
		trySetIntProperty(message, PropertyType.ACTION_TYPE, action.getActionType().ordinal());
		trySetIntProperty(message, PropertyType.PLAYER_ID, action.getPlayer().getId());
		trySetObject(message, action.getItem());
		sendMessage(message);
	}
    private void triggerPlayerDeathEvent(Player player) {
    	System.out.println("DISPATCHER (Test): Send PLAYER_DEATH message with GameId " + player.getGame().getId());
		ObjectMessage message = createObjectMessage(MessageType.PLAYER_DEATH);
		trySetIntProperty(message, PropertyType.GAME_ID, player.getGame().getId());
		trySetIntProperty(message, PropertyType.PLAYER_ID, player.getId());
		sendMessage(message);
	}
	
    private void sendMessage(ObjectMessage message) {
		jmsContext.createProducer().send(eventTopic, message);
	}
    
	private ObjectMessage createObjectMessage(int messageType) {
		ObjectMessage message = jmsContext.createObjectMessage();
		trySetIntProperty(message, PropertyType.MESSAGE_TYPE, messageType);
		return message;
	}
	
	private void trySetIntProperty(Message message, String propertyType, Integer value) {
		try {
			message.setIntProperty(propertyType, value);
		} catch (JMSException e) {
			System.out.println("Failed to set" + propertyType.toString() + "to " + value);
		}
	}
	
	private void trySetObject(ObjectMessage message, Serializable object) {
		try {
			message.setObject(object);
		} catch (JMSException e) {
			System.out.println("Failed to set object to" + object);
		}
	}

}
