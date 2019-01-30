/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

import de.fh_dortmund.inf.cw.surstwalat.client.event.Event;

/**
 *
 * @author Lars
 */
public class InteractionEvent extends Event {
    
    public static enum InteractionType {
        DICE,
        HEALTH,
        PAWN;
    }
    
    private final InteractionType actionType;

    public InteractionEvent(InteractionType actionType) {
        this.actionType = actionType;
    }

    public InteractionType getActionType() {
        return actionType;
    }
}
