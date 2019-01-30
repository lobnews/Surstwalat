/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.util;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Lars
 */
public enum ItemType {
    
    DICE("dice.png"),
    HEALTH("potion.png");
    
    private static final String RESOURCE_FOLDER = "resources/items/";
    
    private final BufferedImage itemImage;
    
    public static ItemType getByItem(Item i) {
        if(i == null) {
            return null;
        }
        if(i instanceof Dice) {
            return DICE;
        }
        if(i instanceof HealthItem) {
            return HEALTH;
        }
        return null;
    }
    
    private ItemType(String filename) {
        BufferedImage imageCache = null;
        try {
            imageCache = ImageIO.read(ItemType.class.getClassLoader().getResourceAsStream(RESOURCE_FOLDER + filename));
        } catch (IOException ex) {
            Logger.getLogger(ItemType.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            itemImage = imageCache;
        }
    }

    public BufferedImage getItemImage() {
        return itemImage;
    }
    
}
