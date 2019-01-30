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
public class EventException extends Exception {

    /**
     * Creates a new instance of <code>EventException</code> without detail
     * message.
     */
    public EventException() {
    }

    /**
     * Constructs an instance of <code>EventException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public EventException(String msg) {
        super(msg);
    }

    public EventException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventException(Throwable cause) {
        super(cause);
    }
    
}
