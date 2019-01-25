package de.fh_dortmund.cw.surstwalat.ejb.ki;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.fh_dortmund.cw.surstwalat.ejb.ki.OutgoingEventHelperBean;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.ki.beans.interfaces.KiRemote;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession;

@Stateless
public class KiBean implements KiRemote {
	
	@EJB
	private UserSession session;
	
	@EJB
	private OutgoingEventHelperBean sender;
	
	private int gameid;
	
	private int player_no;
	
	private List<Item> inventory;
	
	@PostConstruct
	public void init() {
		System.out.println("@@@FortDayKi started");
	}

	@Override
	public void makeTurn() {
		sender.sendInventoryRequest(gameid, player_no);
		this.setInventory(getInventory());
		
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
