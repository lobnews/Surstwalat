/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem;

/**
 *
 * @author Lars
 */
public class HealthInteractEvent extends InteractionEvent{
    
    private final HealthItem health;

    public HealthInteractEvent(HealthItem health) {
        super(InteractionType.HEALTH);
        this.health = health;
    }

    public HealthItem getHealth() {
        return health;
    }
    
}
