package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.ItemRepositoryLocal;

@Stateless
public class ItemRepository extends LocationManagementRepository<Item, Integer> implements ItemRepositoryLocal
{

    public ItemRepository()
    {
        super(Item.class);
    }    

}
