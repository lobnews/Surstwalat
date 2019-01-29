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
            + "html {"
            + "margin: 0 0 10px 0;"
            + "}"
            + "html, body {"
            + "width: 100%;"
            + "}"
            + "</style></head><body style=\""
            + "background-color: rgb(250, 250, 250);"
            + "opacity: 0.8;"
            + "padding: 10px;"
            + "border-style: solid;"
            + "border-width: 1px;";
    private static final String BOXHTML_MIDDLE = "\">";
    private static final String BOXHTML_END = "</body></html>";

    /**
     * Makes a list of error messages to red error message box
     *
     * @param messageList list of error messages
     * @return box with error messages
     */
    public static String errorBox(LinkedList<String> messageList) {
        return errorBox(" - " + String.join("<br/> - ", messageList));
    }

    /**
     * Makes a string of error message/s to red error message box
     *
     * @param message error message
     * @return box with error message
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
     * @param messageList list of success messages
     * @return box with success messages
     */
    public static String successBox(LinkedList<String> messageList) {
        return successBox(" - " + String.join("<br/> - ", messageList));
    }

    /**
     * Makes a string of success message/s to green success message box
     *
     * @param message list of success messages
     * @return box with success messages
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
