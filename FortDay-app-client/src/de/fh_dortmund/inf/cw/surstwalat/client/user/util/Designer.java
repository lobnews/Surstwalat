package de.fh_dortmund.inf.cw.surstwalat.client.user.util;

import java.util.LinkedList;

/**
 * Designer
 *
 * @author Stephan Klimek
 */
public class Designer {

    private static final String BOXHTML_BEGIN
            = "<html><head><style>"
            + "html, body { "
            //            + "width: 235px;"
            + "width: 100%;"
            + "}"
            + "</style></head><body style=\""
            + "padding: 10px;"
            + "margin: 0 0 10px 0;"
            + "border-style: solid;"
            + "border-width: 1px;";
    private static final String BOXHTML_MIDDLE = "\">";
    private static final String BOXHTML_END = "</body></html>";

    /**
     * Makes a list of error messages to red error message box
     *
     * @param messageList
     * @return
     */
    public static String errorBox(LinkedList<String> messageList) {
        return errorBox(" - " + String.join("<br/> - ", messageList));
    }

    /**
     * Makes a string of error message/s to red error message box
     *
     * @param message
     * @return
     */
    public static String errorBox(String message) {
        return BOXHTML_BEGIN
                + "border-color: red;"
                + "color: red;"
                + BOXHTML_MIDDLE
                + message
                + BOXHTML_END;
    }

    /**
     * Makes a list of success messages to green success message box
     *
     * @param messageList
     * @return
     */
    public static String successBox(LinkedList<String> messageList) {
        return successBox(" - " + String.join("<br/> - ", messageList));
    }

    /**
     * Makes a string of success message/s to green success message box
     *
     * @param message
     * @return
     */
    public static String successBox(String message) {
        return BOXHTML_BEGIN
                + "border-color: green;"
                + "color: green;"
                + BOXHTML_MIDDLE
                + message
                + BOXHTML_END;
    }
}
