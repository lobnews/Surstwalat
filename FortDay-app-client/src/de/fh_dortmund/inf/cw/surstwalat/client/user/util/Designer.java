package de.fh_dortmund.inf.cw.surstwalat.client.user.util;

import java.util.LinkedList;

/**
 * Designer
 *
 * @author Stephan Klimek
 */
public class Designer {

    /**
     * Makes a list of error messages to red error message box
     *
     * @param msgList
     * @return
     */
    public static String errorBox(LinkedList<String> msgList) {
        return "<html><body style=\""
                + "padding: 10px;"
                + "border: 1px solid red;"
                + "margin: 0 0 10px 0;"
                + "color: red"
                + "\">"
                + String.join("<br/>", msgList)
                + "</body></html>";
    }

    /**
     * Makes a list of success messages to green success message box
     *
     * @param msgList
     * @return
     */
    public static String successBox(LinkedList<String> msgList) {
        return "<html><body style=\""
                + "padding: 10px;"
                + "border: 1px solid green;"
                + "margin: 0 0 10px 0;"
                + "color: green"
                + "\">"
                + String.join("<br/>", msgList)
                + "</body></html>";
    }
}
