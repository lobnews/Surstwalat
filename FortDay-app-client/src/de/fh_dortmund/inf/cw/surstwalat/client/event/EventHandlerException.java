/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event;

import java.lang.reflect.Method;

/**
 *
 * @author Lars
 */
public class EventHandlerException extends RuntimeException {
    
    public EventHandlerException(Method m) {
        super(buildMsg(m));
    }
    
    private static String buildMsg(Method m) {
        String msg = "The following Method is not an EventHandler: " + m;
        
        return msg;
    }

}
