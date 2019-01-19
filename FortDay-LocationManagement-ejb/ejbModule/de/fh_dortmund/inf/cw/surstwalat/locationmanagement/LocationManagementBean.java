package de.fh_dortmund.inf.cw.surstwalat.locationmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Field;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces.LocationManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.EventHelperLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.ItemRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.PlaygroundRepositoryLocal;

@Stateless
public class LocationManagementBean implements LocationManagementLocal
{

    @EJB
    private PlaygroundRepositoryLocal playgroundRepository;

    @EJB
    private ItemRepositoryLocal itemRepository;

    @EJB
    private EventHelperLocal outgoingEvents;

    public void addItemToPlayground(int gameId, int itemId)
    {
        Playground p = playgroundRepository.getByGameId(gameId);
        int field = (int)(Math.random() * p.getFields().size());
        while (p.getFields().get(field).getItem() != null)
        {
            field = (int)(Math.random() * p.getFields().size());
        }
        Item i = itemRepository.findById(itemId);

        p.getFields().get(field).setItem(i);

        playgroundRepository.save(p);
    }

    public void createPlayground(int gameId, int fieldSize)
    {
        Playground playground = new Playground();
        playground.setGameId(gameId);

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
    public void updateZone(int gameId, int zoneBegin, int zoneEnd)
    {
        Playground playground = playgroundRepository.getByGameId(gameId);
        playground.getFields().forEach(f -> {
            f.setToxic(false);
        });
        for (int i = zoneBegin; i < zoneEnd; i++)
        {
            playground.getFields().get(i % playground.getFields().size()).setToxic(true);
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

        outgoingEvents.triggerCharactersInToxicMessage(playground.getGameId(), tokens);

    }

    public void moveToken(int gameId, int tokenId, int count)
    {
        Playground playground = playgroundRepository.getByGameId(gameId);
        Token token;
        for (int i = 0; i < playground.getFields().size(); i++)
        {
            if (playground.getFields().get(i).getToken().getId() == tokenId)
            {
                token = playground.getFields().get(i).getToken();

            }
        }
    }

    private boolean checkForCollisionWithPlayer(Playground playground, Token token, int pos, int count)
    {
        if (playground.getFields().get(pos + count).getToken() != null)
        {
            Token enemy = playground.getFields().get(pos + count).getToken();
            if (token.getPlayer_id() == enemy.getPlayer_id())
                outgoingEvents
                    .triggerCollisionWithOwnCharacterMessage(
                        playground.getGameId(),
                        token.getPlayer_id(),
                        token.getNr());
            else
                outgoingEvents
                    .triggerCollisionWithPlayerMessage(
                        playground.getGameId(),
                        token.getPlayer_id(),
                        token.getNr(),
                        enemy.getPlayer_id(),
                        enemy.getNr());
            return true;
        }

        return false;
    }

}
