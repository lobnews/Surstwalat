package de.fh_dortmund.inf.cw.surstwalat.locationmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Field;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.events.OutgoingEventHelperBean;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces.LocationManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.ItemRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.PlaygroundRepositoryLocal;

@Stateless
public class LocationManagementBean implements LocationManagementLocal
{

    @EJB
    private PlaygroundRepositoryLocal playgroundRepository;

    @EJB
    private ItemRepositoryLocal itemRepository;

    @Inject
    private OutgoingEventHelperBean outgoingEvents;

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

    }

    private void checkForTokensInToxic(Playground playground)
    {
        Integer[][] tokens = new Integer[5][5];
        playground.getFields().forEach(f -> {
            if (f.isToxic() || f.getToken() != null)
            {
                tokens[f.getToken().getPlayer_id()][f.getToken().getNr()] = 1;

            }
        });

        outgoingEvents.triggerCharactersInToxicMessage(playground.getGameId(), tokens);

    }

}
