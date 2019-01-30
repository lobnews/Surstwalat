/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.health;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.healthmanagement.beans.interfaces.HealthManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.healthmanagement.beans.interfaces.HealthManagementRemote;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author Lars
 */
@Stateless
public class HealthManagementBean implements HealthManagementLocal, HealthManagementRemote{
    
    private static final int MAX_HEALTH = 25;
    
    @Inject
    private JMSContext jmsContext;
    @Resource(lookup = "java:global/jms/FortDayEventTopic")
    private Topic eventTopic;
    @PersistenceContext(unitName = "FortDayDB")
    private EntityManager em;

    @Override
    public void damageToken(int gameId, int tokenId, int damage) {
        Token t = getToken(tokenId);
        t.setHealth(t.getHealth() - damage);
        if(t.getHealth() <= 0) {
            //Token is dead
            em.remove(t);
            if(getTokenCount(t.getPlayerId()) > 0) {
                try {
                    ObjectMessage o = jmsContext.createObjectMessage();
                    o.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TOKEN_DEATH);
                    o.setIntProperty(PropertyType.GAME_ID, gameId);
                    o.setIntProperty(PropertyType.TOKEN_ID, tokenId);
                    jmsContext.createProducer().send(eventTopic, o);
                } catch (JMSException ex) {
                    Logger.getLogger(HealthManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    ObjectMessage o = jmsContext.createObjectMessage();
                    o.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.PLAYER_DEATH);
                    o.setIntProperty(PropertyType.PLAYER_ID, t.getPlayerId());
                    jmsContext.createProducer().send(eventTopic, o);
                } catch (JMSException ex) {
                    Logger.getLogger(HealthManagementBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return;
        } else if(t.getHealth() > t.getMaxHealth()) {
            t.setHealth(t.getMaxHealth());
        }
            
        //Health set
        try {
            ObjectMessage o = jmsContext.createObjectMessage();
            o.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.SET_TOKEN_HEALTH);
            o.setIntProperty(PropertyType.GAME_ID, gameId);
            o.setIntProperty(PropertyType.TOKEN_ID, tokenId);
            o.setIntProperty(PropertyType.HEALTH, t.getHealth());
            jmsContext.createProducer().send(eventTopic, o);
        } catch (JMSException ex) {
            Logger.getLogger(HealthManagementBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createTokens(int playerId, int gameId) {
        LinkedList<Integer> list = new LinkedList<>();
        for(int i = 1; i <= 4; i++) {
            Token t = new Token();
            t.setPlayerId(playerId);
            t.setNr(i);
            t.setMaxHealth(MAX_HEALTH);
            t.setHealth(MAX_HEALTH);
            em.persist(t);
            list.add(t.getId());
        }
        try {
            ObjectMessage o = jmsContext.createObjectMessage(list);
            o.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TOKEN_CREATED);
            o.setIntProperty(PropertyType.PLAYER_ID, playerId);
            o.setIntProperty(PropertyType.GAME_ID, gameId);
            jmsContext.createProducer().send(eventTopic, o);
        } catch (JMSException ex) {
            Logger.getLogger(HealthManagementBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Override
    public Token getToken(int tokenId) {
        TypedQuery<Token> query = em.createNamedQuery("Token.getById", Token.class);
        query.setParameter("id", tokenId);
        return query.getSingleResult();
    }

    @Override
    public int getTokenCount(int playerId) {
        TypedQuery<Integer> query = em.createNamedQuery("Token.getTokensByPlayerId", Integer.class);
        query.setParameter("playerId", playerId);
        return query.getSingleResult();
    }
    
}
