package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;

public interface TokenRepositoryLocal extends ILocationManagement<Token, Integer>
{
    public Token findByPlayerIdAndTokenNr(int playerId, int tokenNr);
}
