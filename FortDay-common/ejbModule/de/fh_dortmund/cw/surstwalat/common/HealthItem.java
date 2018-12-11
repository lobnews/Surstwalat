package de.fh_dortmund.cw.surstwalat.common;

import javax.persistence.Entity;

@Entity
public class HealthItem extends Item {
	private static final long serialVersionUID = 1L;
	
	public enum Level{
		Stufe_1, Stufe_2, Stufe_3
	};
	
	private Level level;
	
	/**
	 * Menge der Heilung bei Anwenung
	 */
	private int amount;
	
	public HealthItem(Level level) {
		this.level = level;
		switch (level) {
			case Stufe_1:
				amount = 20;
				break;
			case Stufe_2:
				amount = 50;
				break;
			case Stufe_3:
				amount = 100;
				break;
		}
	}

	public Level getLevel() {
		return level;
	}

	public int getAmount() {
		return amount;
	}
}
