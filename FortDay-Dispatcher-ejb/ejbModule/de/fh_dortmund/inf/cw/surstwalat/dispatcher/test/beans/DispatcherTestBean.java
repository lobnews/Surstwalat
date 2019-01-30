package de.fh_dortmund.inf.cw.surstwalat.dispatcher.test.beans;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;

/**
 * Session Bean implementation class DispatcherTestBean
 * @author Johannes Heiderich
 */
@Singleton
@Startup
public class DispatcherTestBean {

	public int gameId;

	@PersistenceContext(unitName = "FortDayDB")
	protected EntityManager entityManager;
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	
    
    @PostConstruct
	public void runTests() {
    	if(this.gameId == 0)
    		this.gameId = testGameStarted();    
    }
    
    public int testGameStarted() {
    	Game game = new Game();
    	game.setAiPlayerCount(2);
    	for (int i = 0; i < 2; i++) {
    		Account a = new Account();
    		a.setName("Test_" + i);
    		a.setEmail("test");
    		a.setPassword("p");
    		game.getHumanUsersInGame().add(a);
    		System.out.println("DISPATCHER (Test): Create Account with id " + game.getHumanUsersInGame().get(i).getId());
		}
    	game.setGameStarted(true);
    	game = entityManager.merge(game);
    	triggerGameStartedEvent(game);
    	return game.getId();
    }
    

    
    private void triggerGameStartedEvent(Game game) {
    	System.out.println("DISPATCHER (Test): Send GAME_STARTED message with GameId " + game.getId());
		try {
			ObjectMessage message = jmsContext.createObjectMessage();
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.GAME_STARTED);
			message.setIntProperty(PropertyType.GAME_ID, game.getId());
			message.setStringProperty(PropertyType.DISPLAY_MESSAGE, "Spiel startet");
			message.setIntProperty(PropertyType.GAME_FIELDSIZE, 50);
			jmsContext.createProducer().send(eventTopic, message);
		} catch( JMSException e) {}
	}	
}
