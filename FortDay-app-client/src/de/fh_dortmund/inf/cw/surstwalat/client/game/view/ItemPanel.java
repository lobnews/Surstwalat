/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.game.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.DiceInteractEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.HealthInteractEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.InteractionEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.util.ItemType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Lars
 */
public class ItemPanel extends javax.swing.JPanel {
    
    private Item item;
    
    /**
     * Creates new form ItemPanel
     */
    public ItemPanel() {
        initComponents();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void paint(Graphics g) {
        String tooltip = "";
        ItemType type = ItemType.getByItem(item);
        BufferedImage newBackground = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        if (item != null) {
            Graphics2D g2D = (Graphics2D) newBackground.getGraphics();
            g2D.drawImage(type.getItemImage(), 0, 0, newBackground.getWidth(), newBackground.getHeight(), 0, 0, type.getItemImage().getWidth(), type.getItemImage().getHeight(), null);
            if(type == ItemType.DICE) {
                Dice d = (Dice) item;
                tooltip = d.getLabel();
            } else if(type == ItemType.HEALTH) {
                HealthItem h = (HealthItem) item;
                tooltip = h.getLevel().toString();
            }
        } else {
            Graphics2D g2D = (Graphics2D) newBackground.getGraphics();
            g2D.setColor(new Color(0, 0, 0, 0));
            g2D.fillRect(0, 0, newBackground.getWidth(), newBackground.getHeight());
        }
        itemLabel.setIcon(new ImageIcon(newBackground));
        itemLabel.setToolTipText(tooltip);
        super.paint(g);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        itemLabel = new javax.swing.JLabel();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());
        add(itemLabel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        
        if(item != null) {
            InteractionEvent e;
            switch(ItemType.getByItem(item)) {
                case DICE: e = new DiceInteractEvent((Dice) item); break;
                case HEALTH: e = new HealthInteractEvent((HealthItem) item); break;
                default: return;
            }
            MainFrame.getInstance().getEventManager().fireEvent(e);
        }
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel itemLabel;
    // End of variables declaration//GEN-END:variables
}
