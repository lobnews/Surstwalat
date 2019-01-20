package de.fh_dortmund.inf.cw.surstwalat.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HealthItem extends Item {
	private static final long serialVersionUID = 1L;
	
	public enum Level{
		Stufe_1, Stufe_2, Stufe_3
	};
	
	@Column
	private Level level;
	
	public HealthItem() {
		this(null);
	}
	
	public HealthItem(Level level) {
		this.level = level;
		
	}

	public Level getLevel() {
		return level;
	}

	public int getAmount() {
		switch (level) {
			case Stufe_1:
				return 20;
			case Stufe_2:
				return 50;
			case Stufe_3:
				return 100;
			default:
				return 0;
		}
	}
}
