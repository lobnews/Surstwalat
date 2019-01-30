package de.fh_dortmund.inf.cw.surstwalat.client.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

/**
 * Custom font loader
 *
 * @author Stephan Klimek
 */
public class FontKeeper {

    private final static String FONT = "Impact";
    
    public final static Font BUTTON = new Font(FONT, 0, 16);
    public final static Font BIG_BUTTON = new Font(FONT, 0, 38);
    public final static Font LABEL = new Font(FONT, 0, 16);
    
    /**
     * Load fonts
     */
    @Deprecated
    public static void loadFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("/resources/fonts/closeandopen.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            System.err.println(e.getMessage());
        }
    }
}
