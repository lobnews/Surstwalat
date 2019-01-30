/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

/**
 *
 * @author Lars
 */
public class PawnInteractEvent extends InteractionEvent {
    
    private final int tokenID;

    public PawnInteractEvent(int tokenID) {
        super(InteractionType.PAWN);
        this.tokenID = tokenID;
    }

    public int getTokenID() {
        return tokenID;
    }
    
}
