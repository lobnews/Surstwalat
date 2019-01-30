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
import javax.persistence.NoResultException;
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
public class GlobalEventManagementBean implements GlobalEventManagementLocal {

	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	@PersistenceContext(unitName = "FortDayDB")
	private EntityManager em;
        
        
        /**
         * Updates the damage zone:
         * increases the damage and size of the zone and updates its position
         * sets the values position and size of the next zone
         * 
         * @param gameId
         * @param roundNo 
         */
	@Override
	public void updateZone(int gameId, int roundNo) {
		Game game = em.find(Game.class, gameId);
		DamageZone zone = getZoneByGame(game);
		if (zone == null) {
			zone = new DamageZone();
			int fieldsize = getPlaygroundByGameID(gameId).getFields().size();
			int randomStartingField = (int) (Math.random() * fieldsize);
			int nextZoneBegin = randomStartingField;
			int nextZoneSize = 3;

			zone.setCurrentZoneBegin(0);
			zone.setCurrentZoneSize(0);
			zone.setDamage(0);
			zone.setGame(game);
			zone.setNextZoneBegin(nextZoneBegin);
			zone.setNextZoneSize(nextZoneSize);

			em.merge(zone);
		} else if (roundNo > 0 && roundNo % 3 == 0) {
			int damage = zone.getDamage() + 1;
			int currentZoneBegin = zone.getNextZoneBegin();
			int currentZoneSize = zone.getNextZoneSize();
			int nextZoneSize = currentZoneSize + damage;
			int nextZoneBegin = currentZoneBegin + (int) (Math.random() * currentZoneSize) + 1;

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
			System.out.println("[GLOBALEVENTMANAGEMENT] Zone updated: start: " + zone.getCurrentZoneBegin() + " size: " + zone.getCurrentZoneSize());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
        
        /**
         * Sends the message that an airdrop should happen
         * Gets called, if a player rolled a 6
         * 
         * @param gameId 
         */
	@Override
	public void triggerAirdrop(int gameId) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_AIRDROP);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			jmsContext.createProducer().send(eventTopic, message);
			System.out.println("[GLOBALEVENTMANAGEMENT] Airdrop triggered");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
        
        /**
         * Sends the message that the starting items for a game should be placed
         * 
         * @param gameId 
         */
	@Override
	public void triggerStartingItems(int gameId) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_STARTING_ITEMS);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			jmsContext.createProducer().send(eventTopic, message);
			System.out.println("[GLOBALEVENTMANAGEMENT] Startingitems triggered");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

        /**
         * Sends the message which tokens have to suffer damage from the zone
         * 
         * @param gameId
         * @param token 
         */
	@Override
	public void triggerDamage(int gameId, List<Token> token) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			Game game = em.find(Game.class, gameId);
			int damage = getZoneByGame(game).getDamage();
			for (Token hitToken : token) {
				message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_DAMAGE);
				message.setIntProperty(PropertyType.GAME_ID, gameId);
				message.setIntProperty(PropertyType.TOKEN_ID, hitToken.getId());
				message.setIntProperty(PropertyType.DAMAGE, damage);
				jmsContext.createProducer().send(eventTopic, message);
				System.out.println("[GLOBALEVENTMANAGEMENT] Token " + hitToken.getId() + " gets " + damage + " from zone");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

        /**
         * Gets the Playground of the given game by using a NamedQuery
         * 
         * @param gameId
         * @return 
         */
	private Playground getPlaygroundByGameID(int gameId) {
		TypedQuery<Playground> query = em.createNamedQuery("Playground.getByGameId", Playground.class);
		query.setParameter("gameId", gameId);
		return query.getSingleResult();
	}

        /**
         * Gets the DamageZone of the given game by using a NamedQuery
         * 
         * @param game
         * @return 
         */
	private DamageZone getZoneByGame(Game game) {
		try {
			TypedQuery<DamageZone> query = em.createNamedQuery("DamageZone.getByGame", DamageZone.class);
			query.setParameter("game", game);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
