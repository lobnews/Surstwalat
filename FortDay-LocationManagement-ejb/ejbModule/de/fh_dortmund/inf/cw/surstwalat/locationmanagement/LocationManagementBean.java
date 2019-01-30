package de.fh_dortmund.inf.cw.surstwalat.locationmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Field;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces.LocationManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.EventHelperLocal;

// The Location Management class
@Stateless
public class LocationManagementBean implements LocationManagementLocal
{

    @EJB
    private EventHelperLocal outgoingEvents;

    @PersistenceContext(unitName = "FortDayDB")
    protected EntityManager entityManager;

    // Adding an Item to the Playground
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addItemToPlayground(int gameId, int itemId)
    {
        // get the Playground from database
        TypedQuery<Playground> query = entityManager.createNamedQuery("Playground.getByGameId", Playground.class);
        query.setParameter("gameId", gameId);
        Playground p = query.getSingleResult();

        entityManager.lock(p, LockModeType.PESSIMISTIC_READ);

        // get random field
        int field = (int)(Math.random() * p.getFields().size());

        // while no empty field found, get new random field
        while (p.getField(field).getItem() != null )
        {
            field = (int)(Math.random() * p.getFields().size());
        }

        // get the item
        Item i = entityManager.find(Item.class, itemId);

        // set the item
        p.getField(field).setItem(i);

        entityManager.merge(p);

    }

    // create the Playground
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createPlayground(int gameId, int fieldSize)
    {
        Playground playground = new Playground();

        // get the game
        Game game = entityManager.find(Game.class, gameId);

        playground.setGame(game);

        List<Field> fields = new ArrayList<>();

        // Generate the Fields
        for (int i = 0; i < fieldSize; i++)
        {
            Field f = new Field();
            f.setPlayground(playground);
            fields.add(f);
        }
        playground.setFields(fields);

        entityManager.merge(playground);
    }

    // Updsate the Zone
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateZone(int gameId, int zoneBegin, int zoneSize)
    {
        // get the playground
        TypedQuery<Playground> query = entityManager.createNamedQuery("Playground.getByGameId", Playground.class);
        query.setParameter("gameId", gameId);
        Playground playground = query.getSingleResult();
        entityManager.lock(playground, LockModeType.PESSIMISTIC_READ);

        // clear all toxic
        for (Field f : playground.getFields())
        {
            f.setToxic(false);
        }

        // generate new zone
        for (int i = zoneBegin; i < zoneBegin + zoneSize; i++)
        {
            playground.getField(i % playground.getFields().size()).setToxic(true);
        }

        checkForTokensInToxic(playground);

    }

    // Check if Tokens are in Toxic
    private void checkForTokensInToxic(Playground playground)
    {
        List<Token> tokens = new ArrayList<Token>();
        // check, which token is in toxic
        for (Field f : playground.getFields())
        {
            if (f.isToxic() && f.getToken() != null)
            {
                tokens.add(f.getToken());
            }
        }
        if (tokens.size() > 0)
        {
            outgoingEvents.triggerCharactersInToxicMessage(playground.getGame().getId(), tokens);
        }
        else
        {
            outgoingEvents.triggerNoCollisionMessage(playground.getGame().getId());

        }

    }

    // move the token
    public void moveToken(int gameId, int tokenId, int count)
    {
        // get the playground
        TypedQuery<Playground> query = entityManager.createNamedQuery("Playground.getByGameId", Playground.class);
        query.setParameter("gameId", gameId);
        Playground playground = query.getSingleResult();
        entityManager.lock(playground, LockModeType.PESSIMISTIC_READ);

        Token token;
        // find the token
        for (int i = 0; i < playground.getFields().size(); i++)
        {
            if (playground.getField(i).getToken() != null && playground.getField(i).getToken().getId() == tokenId)
            {
                // get the token
                token = playground.getFields().get(i).getToken();
                if (checkForCollisionWithPlayer(playground, token, i, count))
                {
                    return;
                }

                checkForCollisionWithItem(playground, token, i, count);

                // move the token
                playground.getField(i + count).setToken(token);
                playground.getField(i).setToken(null);
            }
        }
    }

    // check for collision, return true, if own token was hit
    private boolean checkForCollisionWithPlayer(Playground playground, Token token, int pos, int count)
    {
        // check for collision
        if (playground.getField(pos + count).getToken() != null)
        {
            Token enemy = playground.getField(pos + count).getToken();
            // check if own token
            if (token.getPlayerId() == enemy.getPlayerId())
            {
                outgoingEvents
                    .triggerCollisionWithOwnCharacterMessage(
                        playground.getGame().getId(),
                        token.getPlayerId(),
                        token.getNr());
                return true;

            }
            else
            {
                outgoingEvents
                    .triggerCollisionWithPlayerMessage(
                        playground.getGame().getId(),
                        token.getPlayerId(),
                        token.getNr(),
                        enemy.getPlayerId(),
                        enemy.getNr());
                return false;
            }
        }

        return false;
    }

    // check if item was hit.
    private boolean checkForCollisionWithItem(Playground playground, Token token, int pos, int count)
    {
        // check if item is on field
        if (playground.getField(pos + count).getItem() != null)
        {
            outgoingEvents
                .triggerCollisionWithItemMessage(
                    playground.getGame().getId(),
                    token.getPlayerId(),
                    token.getNr(),
                    playground.getField(pos + count).getItem().getId());

            return true;
        }
        return false;
    }

    // add a token to the playground
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addTokenToPlayground(int gameId, int playerId, List<Integer> tokenIds)
    {
        TypedQuery<Playground> query = entityManager.createNamedQuery("Playground.getByGameId", Playground.class);
        query.setParameter("gameId", gameId);
        Playground playground = query.getSingleResult();
        entityManager.lock(playground, LockModeType.PESSIMISTIC_READ);

        for (Integer tokenId : tokenIds)
        {
            // randomly set the field for the token

            int field = (int)(Math.random() * playground.getFields().size());
            while (playground.getField(field).getToken() != null)
            {
                field = (int)(Math.random() * playground.getFields().size());
            }

            // Set tokens and trigger event
            Token token = entityManager.find(Token.class, tokenId, LockModeType.PESSIMISTIC_READ);
            Field tokenField = playground.getField(field);
            token.setField(tokenField);
            tokenField.setToken(entityManager.merge(token));
            outgoingEvents.triggerPlayerOnFieldMessage(gameId, playerId, token.getId(), field);
        }

        // check if all tokens were set for the game and trigger the message
        Game game = entityManager.find(Game.class, gameId);
        int tokens = game.getAiPlayerCount() + game.getHumanUsersInGame().size();
        tokens = tokens * 4;

        int counter = 0;
        for (Field f : playground.getFields())
        {
            if (f.getToken() != null)
                counter++;
        }

        if (counter == tokens)
        {
            outgoingEvents.triggerNoCollisionMessage(gameId);
        }

    }

    // remove an item from playground
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeItemFromPlayground(int gameId, int itemId)
    {
        if (itemId != -1)
        {
            TypedQuery<Field> query = entityManager.createNamedQuery("Field.getByItemId", Field.class);
            query.setParameter("itemId", itemId);
            Field field = query.getSingleResult();
            field.setItem(null);
        }
        outgoingEvents.triggerNoCollisionMessage(gameId);

    }

}
