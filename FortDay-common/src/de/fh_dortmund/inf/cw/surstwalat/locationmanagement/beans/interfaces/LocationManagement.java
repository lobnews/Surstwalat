package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces;

public interface LocationManagement
{

    public void createPlayground(int gameId, int fieldSize);
    public void addItemToPlayground(int gameId, int itemId);
    public void updateZone(int gameId, int zoneBegin, int zoneEnd);
}
