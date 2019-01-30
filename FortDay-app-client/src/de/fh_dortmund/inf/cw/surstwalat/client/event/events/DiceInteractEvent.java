/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;

/**
 *
 * @author Lars
 */
public class DiceInteractEvent extends InteractionEvent {
    
    private final Dice d;
    
    public DiceInteractEvent(Dice d) {
        super(InteractionType.DICE);
        this.d = d;
    }

    public Dice getD() {
        return d;
    }
    
}
