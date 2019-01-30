package de.fh_dortmund.cw.surstwalat.ejb.ki;

import java.util.List;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.ki.beans.interfaces.KiLocal;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * @author Marcel Scholz
 *
 */
public class Ki implements KiLocal {
	
	private UserSession session;
	
	private OutgoingEventHelperBean sender;
	
	private int gameid;
	
	private int player_no;
	
	private List<Item> inventory;
	
	public Ki(UserSession session, OutgoingEventHelperBean sender, int gameid, int player_no) {
		this.session = session;
		this.sender = sender;
		this.gameid = gameid;
		this.player_no = player_no;
		init();
	}

	public void init() {
		System.out.println("@@@FortDayKi started");
	}
	
	@Override
	public void makeTurn() {
		sender.sendInventoryRequest(gameid, player_no);
		
		Item item;
		
		if(!inventory.isEmpty())
		{
			item = inventory.get(0);
			sender.sendUseItem(gameid, player_no, item);
		}
		else
		{
			sender.sendPlayerRoll(gameid,player_no);
		}
	}

	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public int getPlayerid() {
		return player_no;
	}

	public void setPlayerid(int playerid) {
		this.player_no = playerid;
	}

	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}
}