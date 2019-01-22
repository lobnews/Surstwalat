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

    public void addItemToPlayground(int gameId, int itemId)
    {
        Playground p = playgroundRepository.getByGameId(gameId);
        int field = (int)(Math.random() * p.getFields().size());
        while (p.getField(field).getItem() != null)
        {
            field = (int)(Math.random() * p.getFields().size());
        }
        Item i = itemRepository.findById(itemId);

        p.getField(field).setItem(i);

        playgroundRepository.save(p);

    }

    public void createPlayground(int gameId, int fieldSize)
    {
        Playground playground = new Playground();
        Game game = gameRepository.findById(gameId);
        playground.setGameId(game);

        List<Field> fields = new ArrayList<>();

        for (int i = 0; i < fieldSize; i++)
        {
            Field f = new Field();
            fields.add(f);
        }

        playground.setFields(fields);

        playgroundRepository.save(playground);
    }

    @Override
    public void updateZone(int gameId, int zoneBegin, int zoneSize)
    {
        Playground playground = playgroundRepository.getByGameId(gameId);
        playground.getFields().forEach(f -> {
            f.setToxic(false);
        });
        for (int i = zoneBegin; i < zoneBegin + zoneSize; i++)
        {
            playground.getField(i).setToxic(true);
        }

        playgroundRepository.save(playground);
        checkForTokensInToxic(playground);

    }

    private void checkForTokensInToxic(Playground playground)
    {
        List<Token> tokens = new ArrayList<Token>();
        playground.getFields().forEach(f -> {
            if (f.isToxic() || f.getToken() != null)
            {
                tokens.add(f.getToken());
            }
        });

        outgoingEvents.triggerCharactersInToxicMessage(playground.getGame().getId(), tokens);

    }

    public void moveToken(int gameId, int tokenId, int count)
    {
        Playground playground = playgroundRepository.getByGameId(gameId);
        Token token;
        for (int i = 0; i < playground.getFields().size(); i++)
        {
            if (playground.getField(i).getToken() != null && playground.getField(i).getToken().getId() == tokenId)
            {
                token = playground.getFields().get(i).getToken();
                if (checkForCollisionWithPlayer(playground, token, i, count))
                {
                    return;
                }

                checkForCollisionWithItem(playground, token, i, count);

                playground.getField(i + count).setToken(token);
                playground.getField(i).setToken(null);

                playgroundRepository.save(playground);

            }
        }
    }

    private boolean checkForCollisionWithPlayer(Playground playground, Token token, int pos, int count)
    {
        if (playground.getField(pos + count).getToken() != null)
        {
            Token enemy = playground.getField(pos + count).getToken();
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

    private boolean checkForCollisionWithItem(Playground playground, Token token, int pos, int count)
    {
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

    public void addTokenToPlayground(int gameId, int playerId, int tokenNumber)
    {
        Playground playground = playgroundRepository.getByGameId(gameId);

        int field = (int)(Math.random() * playground.getFields().size());

        while (playground.getField(field).getToken() != null)
        {
            field = (int)(Math.random() * playground.getFields().size());
        }
        Token token = tokenRepository.findByPlayerIdAndTokenNr(playerId, tokenNumber);

        playground.getField(field).setToken(token);

        playgroundRepository.save(playground);
        outgoingEvents.triggerPlayerOnFieldMessage(gameId, playerId, tokenNumber, field);

    }

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
