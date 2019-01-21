package de.fh_dortmund.inf.cw.surstwalat.common.model;

import javax.persistence.Entity;
/**
 * 
 * @author Johannes Heiderich
 *
 */
@Entity
public class RollActionResult extends ActionResult{
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
