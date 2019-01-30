/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Lars
 */
public enum PawnColor {
    
    BLUE("blue.png", 1),
    GREEN("green.png", 2),
    RED("red.png", 3),
    YELLOW("yellow.png", 4);
    
    private static final String RESOURCE_FOLDER = "resources/pawns/";
    
    private final BufferedImage pawnImage;
    private final int playerNR;
    
    public static PawnColor getByPlayerNR(int playerNR) {
        for(PawnColor c:values()) {
            if(c.playerNR == playerNR) {
                return c;
            }
        }
        return BLUE;
    }
    
    private PawnColor(String filename, int playerNR) {
        BufferedImage imageCache = null;
        try {
            imageCache = ImageIO.read(PawnColor.class.getClassLoader().getResourceAsStream(RESOURCE_FOLDER + filename));
        } catch (IOException ex) {
            Logger.getLogger(PawnColor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pawnImage = imageCache;
        }
        this.playerNR = playerNR;
    }

    public BufferedImage getPawnImage() {
        return pawnImage;
    }

    public int getPlayerNR() {
        return playerNR;
    }
    
}
