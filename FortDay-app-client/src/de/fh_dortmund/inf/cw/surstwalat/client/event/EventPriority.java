/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event;

/**
 *
 * @author Lars
 */
public enum EventPriority {
    
    LOWEST(0), LOW(1), NORMAL(2), HIGH(3), HIGHEST(4), MONITOR(5);
    private final int rank;
    
    private EventPriority(int rank) {
        this.rank = rank;
    }
    
    public int getRank() {
        return rank;
    }

}
