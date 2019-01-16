package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;

@Local
public interface PlaygroundRepositoryLocal extends ILocationManagement<Playground, Long>
{

    public Playground getByGameId(int gameID);
}
