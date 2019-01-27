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
public class FontLoader {

    /**
     * Load fonts
     */
    public static void loadFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
//            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/resources/fonts/closeandopen.ttf")));

            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("/resources/fonts/closeandopen.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            System.err.println(e.getMessage());
//        } finally {
//            String[] fontnames = ge.getAvailableFontFamilyNames();
//            for (String fontname : fontnames) {
//                System.out.println(fontname);
//            }
        }

    }
}
