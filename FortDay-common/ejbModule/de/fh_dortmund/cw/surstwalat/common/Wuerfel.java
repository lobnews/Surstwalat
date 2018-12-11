package de.fh_dortmund.cw.surstwalat.common;

import javax.persistence.Entity;

@Entity
public class Wuerfel extends Item {
	private static final long serialVersionUID = 1L;
	
	private String label;
	
	private int[] zahlen;
	
	public Wuerfel() {
		
	}
	
	public Wuerfel(int[] zahlen, String label) {
		this.zahlen = zahlen;
		this.label = label;
		
	}
	
	public int[] getZahlen() {
		return zahlen;
	}
	
	public void setZahlen(int[] zahlen) {
		this.zahlen = zahlen;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
