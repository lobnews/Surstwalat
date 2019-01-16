package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;

@Local
public interface ItemRepositoryLocal extends ILocationManagement<Item, Integer>
{

}
