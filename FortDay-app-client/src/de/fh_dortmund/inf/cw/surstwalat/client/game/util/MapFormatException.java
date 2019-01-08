/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.game.util;

/**
 *
 * @author Lars
 */
public class MapFormatException extends Exception {

    /**
     * Creates a new instance of <code>MapFormatException</code> without detail
     * message.
     */
    public MapFormatException() {
    }

    /**
     * Constructs an instance of <code>MapFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MapFormatException(String msg) {
        super(msg);
    }
}
