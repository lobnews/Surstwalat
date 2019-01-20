package de.fh_dortmund.cw.surstwalat.ejb.ki;

import java.util.List;

import javax.annotation.PostConstruct;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;

public class KiBean {
	@PostConstruct
	public void init() {
		System.out.println("@@@FortDayKi started");
	}

	public void makeTurn(int userid) {
		
	}
	
	private List<Item> getInventory()
	{
		return null;
	}
}
