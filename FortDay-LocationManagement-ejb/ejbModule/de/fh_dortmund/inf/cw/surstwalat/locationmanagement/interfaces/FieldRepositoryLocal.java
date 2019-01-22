package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Field;

public interface FieldRepositoryLocal extends ILocationManagement<Field, Integer>
{
    public Field findFieldByItemId(int itemId);
}
