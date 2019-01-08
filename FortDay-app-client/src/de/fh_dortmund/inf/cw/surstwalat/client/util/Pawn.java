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
public class Pawn {
    
    private static final String RESOURCE_FOLDER = "resources/pawns/";
    private static final String HEALTH_FULL_NAME = "Healthbar_full.png";
    private static final String HEALTH_EMPTY_NAME = "Healthbar_empty.png";
    public static final BufferedImage HEALTH_FULL;
    public static final BufferedImage HEALTH_EMPTY;
    
    static {
        BufferedImage imageCache = null;
        try {
            imageCache = ImageIO.read(Pawn.class.getClassLoader().getResourceAsStream(RESOURCE_FOLDER + HEALTH_FULL_NAME));
        } catch (IOException ex) {
            Logger.getLogger(Pawn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            HEALTH_FULL = imageCache;
        }
        imageCache = null;
        try {
            imageCache = ImageIO.read(Pawn.class.getClassLoader().getResourceAsStream(RESOURCE_FOLDER + HEALTH_EMPTY_NAME));
        } catch (IOException ex) {
            Logger.getLogger(Pawn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            HEALTH_EMPTY = imageCache;
        }
    }
    
    private PawnColor color;
    private double health;
    private double maxHealth;

    public Pawn(PawnColor color, double maxHealth) {
        this.color = color;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }
    
}
