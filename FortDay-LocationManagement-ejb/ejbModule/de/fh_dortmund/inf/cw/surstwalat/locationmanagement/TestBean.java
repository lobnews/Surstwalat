package de.fh_dortmund.inf.cw.surstwalat.locationmanagement;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces.LocationManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.GameRepositoryLocal;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.ItemRepositoryLocal;

//@Startup
@Singleton
public class TestBean
{
    @EJB
    LocationManagementLocal locationManagement;

    @EJB
    ItemRepositoryLocal itemRepository;

    @EJB
    GameRepositoryLocal gameRepository;

    @PersistenceContext(unitName = "FortDayDB")
    protected EntityManager entityManager;

    @PostConstruct
    public void init()
    {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ LocationManagement !!!  started");

        Item i = new Item();
        i = itemRepository.save(i);

        Player p = new Player();
        p = entityManager.merge(p);

        Token t = new Token();
        t.setPlayerId(1);
        t.setNr(1);
        t = entityManager.merge(t);

        Game g = new Game();

        g = gameRepository.save(g);

        locationManagement.createPlayground(g.getId(), 40);
        locationManagement.addItemToPlayground(g.getId(), i.getId());
        locationManagement.addTokenToPlayground(g.getId(), t.getPlayerId(), t.getNr());
        locationManagement.moveToken(g.getId(), t.getId(), 2);
        locationManagement.removeItemFromPlayground(g.getId(), 1);

    }

}
