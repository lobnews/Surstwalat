package de.fh_dortmund.inf.cw.surstwalat.lobbymanagement.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.LobbyManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.LobbyManagementRemote;

/**
 * Implementation of the given interfaces (Local/Remote). Implements the logic for the Game-Lobbies (until Game-start)
 * @author Niklas Sprenger
 *
 */
@Stateless
public class LobbyManagementBean implements LobbyManagementLocal, LobbyManagementRemote{
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	
	
}
