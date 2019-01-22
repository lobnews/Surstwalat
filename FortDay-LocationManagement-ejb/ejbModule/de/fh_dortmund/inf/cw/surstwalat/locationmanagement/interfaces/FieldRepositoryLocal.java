package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.PlayField;

public interface FieldRepositoryLocal extends ILocationManagement<PlayField, Integer>
{
    public PlayField findFieldByItemId(int itemId);
}
