package de.fh_dortmund.inf.cw.surstwalat.locationmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Field;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces.LocationManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.EventHelperLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.FieldRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.GameRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.ItemRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.PlaygroundRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.TokenRepositoryLocal;


// The Location Management class
@Stateless
public class LocationManagementBean implements LocationManagementLocal
{

    
    @EJB
    private PlaygroundRepositoryLocal playgroundRepository;

    @EJB
    private ItemRepositoryLocal itemRepository;

    @EJB
    private GameRepositoryLocal gameRepository;

    @EJB
    private TokenRepositoryLocal tokenRepository;

    @EJB
    private FieldRepositoryLocal fieldRepository;

    @EJB
    private EventHelperLocal outgoingEvents;

    //Adding an Item to the Playground
    public void addItemToPlayground(int gameId, int itemId)
    {
        //get the Playground from database
        Playground p = playgroundRepository.getByGameId(gameId);
        
        //get random field
        int field = (int)(Math.random() * p.getFields().size());
        
        //while no empty field found, get new random field
        while (p.getField(field).getItem() != null)
        {
            field = (int)(Math.random() * p.getFields().size());
        }
        
        //get the item
        Item i = itemRepository.findById(itemId);

        //set the item
        p.getField(field).setItem(i);

        playgroundRepository.save(p);

    }

    
    //create the Playground
    public void createPlayground(int gameId, int fieldSize)
    {
        Playground playground = new Playground();
        
        //get the game
        Game game = gameRepository.findById(gameId);
        
        playground.setGameId(game);

        List<Field> fields = new ArrayList<>();

        //Generate the Fields
        for (int i = 0; i < fieldSize; i++)
        {
            Field f = new Field();
            fields.add(f);
        }

        playground.setFields(fields);

        playgroundRepository.save(playground);
    }

    //Updsate the Zone
    @Override
    public void updateZone(int gameId, int zoneBegin, int zoneSize)
    {
        //get the playground
        Playground playground = playgroundRepository.getByGameId(gameId);
        
        //clear all toxic
        playground.getFields().forEach(f -> {
            f.setToxic(false);
        });
        
        //generate new zone
        for (int i = zoneBegin; i < zoneBegin + zoneSize; i++)
        {
            playground.getField(i).setToxic(true);
        }

        playgroundRepository.save(playground);
        checkForTokensInToxic(playground);

    }

    //Check if Tokens are in Toxic
    private void checkForTokensInToxic(Playground playground)
    {
        List<Token> tokens = new ArrayList<Token>();
        //check, which token is in toxic
        playground.getFields().forEach(f -> {
            if (f.isToxic() || f.getToken() != null)
            {
                tokens.add(f.getToken());
            }
        });

        
        outgoingEvents.triggerCharactersInToxicMessage(playground.getGame().getId(), tokens);

    }

    //move the token
    public void moveToken(int gameId, int tokenId, int count)
    {
        //get the playground
        Playground playground = playgroundRepository.getByGameId(gameId);
        Token token;
        //find the token
        for (int i = 0; i < playground.getFields().size(); i++)
        {
            if (playground.getField(i).getToken() != null && playground.getField(i).getToken().getId() == tokenId)
            {
                //get the token
                token = playground.getFields().get(i).getToken();
                if (checkForCollisionWithPlayer(playground, token, i, count))
                {
                    return;
                }

                checkForCollisionWithItem(playground, token, i, count);

                //move the token
                playground.getField(i + count).setToken(token);
                playground.getField(i).setToken(null);

                playgroundRepository.save(playground);

            }
        }
    }

    //check for collision, return true, if own token was hit
    private boolean checkForCollisionWithPlayer(Playground playground, Token token, int pos, int count)
    {
        //check for collision
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
    

    //check if item was hit.
    private boolean checkForCollisionWithItem(Playground playground, Token token, int pos, int count)
    {
        //check if item is on field
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

    //add a token to the playground
    public void addTokenToPlayground(int gameId, int playerId, int tokenNumber)
    {
        Playground playground = playgroundRepository.getByGameId(gameId);

        Game game = gameRepository.findById(gameId);

        
        //randomly set the field for the token
        int field = (int)(Math.random() * playground.getFields().size());

        while (playground.getField(field).getToken() != null)
        {
            field = (int)(Math.random() * playground.getFields().size());
        }
        Token token = tokenRepository.findByPlayerIdAndTokenNr(playerId, tokenNumber);

        playground.getField(field).setToken(token);

        
        //check if all tokens were set for the game and trigger the message
        int tokens = game.getAiPlayerCount() + game.getHumanUsersInGame().size();
        tokens = tokens * 4;
        int counter = 0;
        for (Field f : playground.getFields())
        {
            if (f.getToken() != null)
                counter++;
        }

        playgroundRepository.save(playground);
        outgoingEvents.triggerPlayerOnFieldMessage(gameId, playerId, tokenNumber, field);

        if (counter == tokens)
        {
            outgoingEvents.triggerNoCollisionMessage(gameId);

        }

    }

    //remove an item from playground 
    public void removeItemFromPlayground(int gameId, int itemId)
    {
        if (itemId != -1)
        {
            Field field = fieldRepository.findFieldByItemId(itemId);
            field.setItem(null);
            fieldRepository.save(field);
        }

        outgoingEvents.triggerNoCollisionMessage(gameId);

    }

}
