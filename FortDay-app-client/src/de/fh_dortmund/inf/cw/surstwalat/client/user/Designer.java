package de.fh_dortmund.inf.cw.surstwalat.client.user;

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
     * @param errorMsgList
     * @return
     */
    public static String errorBox(LinkedList<String> errorMsgList) {
        return "<html><body style=\""
                + "padding: 10px; "
                + "border: 1px solid red; "
                + "margin: 0 0 10px 0;"
                + "color: red"
                + "\">"
                + String.join("<br/>", errorMsgList)
                + "</body></html>";
    }
}
