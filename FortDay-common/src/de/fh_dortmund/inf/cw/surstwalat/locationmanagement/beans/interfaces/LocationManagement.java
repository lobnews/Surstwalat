package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces;

import java.util.List;

public interface LocationManagement
{

    public void createPlayground(int gameId, int fieldSize);
    public void addItemToPlayground(int gameId, int itemId);
    public void updateZone(int gameId, int zoneBegin, int zoneEnd);
    public void addTokenToPlayground(int gameId, int playerId, List<Integer> tokenIds);
    public void moveToken(int gameId, int tokenId, int count);
    public void removeItemFromPlayground(int gameId, int itemId);
}
