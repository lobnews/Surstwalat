package de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.common.model.DamageZone;
import de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans.interfaces.GlobalEventManagementLocal;

/**
 *
 * @author Rebekka Michel
 */

@Stateless
public class GlobalEventManagementBean implements GlobalEventManagementLocal{

	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	@PersistenceContext(unitName = "FortDayDB")
	private EntityManager em;

	@Override
	public void updateZone(int gameId, int roundNo) {
                Game game = getGameByGameID(gameId);
		DamageZone zone = getZoneByGame(game);
		if(roundNo == 0)
		{
			int fieldsize = getPlaygroundByGameID(gameId).getFields().size();
			int randomStartingField = (int)(Math.random() * fieldsize);
			int nextZoneBegin =	randomStartingField;
			int nextZoneSize = 3;

			zone.setCurrentZoneBegin(0);
			zone.setCurrentZoneSize(0);
			zone.setDamage(0);
			zone.setGame(game);
			zone.setNextZoneBegin(nextZoneBegin);
			zone.setNextZoneSize(nextZoneSize);

			em.persist(zone);
		}
		else if(roundNo%3 == 0)
		{
			int damage = zone.getDamage() + 1;
			int currentZoneBegin = zone.getNextZoneBegin();
			int currentZoneSize = zone.getNextZoneSize();
			int nextZoneSize = currentZoneSize + damage;
			int nextZoneBegin = currentZoneBegin + (int)(Math.random() * currentZoneSize) + 1;

			zone.setCurrentZoneBegin(currentZoneBegin);
			zone.setCurrentZoneSize(currentZoneSize);
			zone.setDamage(damage);
			zone.setNextZoneBegin(nextZoneBegin);
			zone.setNextZoneSize(nextZoneSize);

			em.merge(zone);
		}

		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.UPDATE_ZONE);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			message.setIntProperty(PropertyType.CURRENT_ZONE_BEGIN, zone.getCurrentZoneBegin());
			message.setIntProperty(PropertyType.CURRENT_ZONE_SIZE, zone.getCurrentZoneSize());
			message.setIntProperty(PropertyType.NEXT_ZONE_BEGIN, zone.getNextZoneBegin());
			message.setIntProperty(PropertyType.NEXT_ZONE_SIZE, zone.getNextZoneSize());
			message.setIntProperty(PropertyType.DAMAGE, zone.getDamage());
			message.setStringProperty(PropertyType.DISPLAY_MESSAGE, "Die Gift-Zone wurde aktualisiert!");
			jmsContext.createProducer().send(eventTopic, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void triggerAirdrop(int gameId) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_AIRDROP);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			jmsContext.createProducer().send(eventTopic, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void triggerStartingItems(int gameId) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_STARTING_ITEMS);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			jmsContext.createProducer().send(eventTopic, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void triggerDamage(int gameId, List<Token> token) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
                        Game game = getGameByGameID(gameId);
			int damage = getZoneByGame(game).getDamage();
			for (Token hitToken : token) {
				message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_DAMAGE);
				message.setIntProperty(PropertyType.GAME_ID, gameId);
				message.setIntProperty(PropertyType.CHARACTER_ID, hitToken.getId());
				message.setIntProperty(PropertyType.DAMAGE, damage);
				jmsContext.createProducer().send(eventTopic, message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private Game getGameByGameID(int gameId) {
		TypedQuery<Game> query = em.createNamedQuery("Game.getById", Game.class);
		query.setParameter("id", gameId);
		return query.getSingleResult();
	}

	private Playground getPlaygroundByGameID(int gameId) {
		TypedQuery<Playground> query = em.createNamedQuery("Playground.getByGameId", Playground.class);
		query.setParameter("id", gameId);
		return query.getSingleResult();
	}

	private DamageZone getZoneByGame(Game game) {
		TypedQuery<DamageZone> query = em.createNamedQuery("Zone.getByGame", DamageZone.class);
		query.setParameter("game", game);
		return query.getSingleResult();
	}
}
