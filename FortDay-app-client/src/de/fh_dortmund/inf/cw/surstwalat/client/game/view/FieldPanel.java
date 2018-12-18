/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.game.view;
    
import de.fh_dortmund.inf.cw.surstwalat.client.util.Pawn;
import de.fh_dortmund.inf.cw.surstwalat.client.util.PawnColor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Lars
 */
public class FieldPanel extends javax.swing.JPanel {

    public static final int MIN_WIDTH = 25;
    public static final int MIN_HEIGHT = 25;
    public static final double HEALTH_HEIGHT_FACTOR = 0.1;
    
    private final int x;
    private final int y;
    private final int value;
    
    private final Pawn pawn;
    
    /**
     * Creates new form FieldPanel
     */
    public FieldPanel(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        if(value > 0) {
            Random r = new Random();
            if(r.nextBoolean()) {
                pawn = new Pawn(PawnColor.values()[r.nextInt(PawnColor.values().length)], r.nextInt(10) + 1);
                pawn.setHealth(r.nextInt((int) pawn.getMaxHealth()));
            } else {
                pawn = null;
            }
        } else {
            pawn = null;
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        backgroundLayer = new javax.swing.JPanel();
        backgroundLabel = new javax.swing.JLabel();
        foregroundLayer = new javax.swing.JPanel();
        foregroundLabel = new javax.swing.JLabel();

        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(50, 50));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        backgroundLayer.setOpaque(false);
        backgroundLayer.setLayout(new java.awt.BorderLayout());
        backgroundLayer.add(backgroundLabel, java.awt.BorderLayout.CENTER);

        foregroundLayer.setOpaque(false);
        foregroundLayer.setLayout(new java.awt.BorderLayout());
        foregroundLayer.add(foregroundLabel, java.awt.BorderLayout.CENTER);

        jLayeredPane1.setLayer(backgroundLayer, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(foregroundLayer, javax.swing.JLayeredPane.PALETTE_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(backgroundLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(foregroundLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(backgroundLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(foregroundLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        add(jLayeredPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if(value != 0) {
            System.out.println(String.format("clicked X: %3d Y: %3d Value: %2d", x, y, value));
        }
    }//GEN-LAST:event_formMouseClicked
    
    public boolean isClickable() {
        return value != 0;
    }
    
    public int getValue() {
        return value;
    }

    @Override
    public void paint(Graphics g) {
        backgroundLayer.setSize(jLayeredPane1.getSize());
        foregroundLayer.setSize(jLayeredPane1.getSize());
        if(pawn != null) {
            BufferedImage newBackground = new BufferedImage(backgroundLayer.getWidth(), backgroundLayer.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) newBackground.getGraphics();
            g2D.drawImage(pawn.getColor().getPawnImage(), 0, 0, newBackground.getWidth(), newBackground.getHeight(), 0, 0, pawn.getColor().getPawnImage().getWidth(), pawn.getColor().getPawnImage().getHeight(), null);
            backgroundLabel.setIcon(new ImageIcon(newBackground));
            backgroundLayer.repaint();
            newBackground = new BufferedImage(foregroundLayer.getWidth(), foregroundLayer.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            g2D = (Graphics2D) newBackground.getGraphics();
            g2D.drawImage(Pawn.HEALTH_EMPTY, 0, 0, newBackground.getWidth(), (int) (newBackground.getHeight() * HEALTH_HEIGHT_FACTOR), 0, 0, Pawn.HEALTH_EMPTY.getWidth(), Pawn.HEALTH_EMPTY.getHeight(), null);
            g2D.drawImage(Pawn.HEALTH_FULL, 0, 0, (int) (newBackground.getWidth() * (pawn.getHealth() / pawn.getMaxHealth())), (int) (newBackground.getHeight() * HEALTH_HEIGHT_FACTOR), 0, 0, Pawn.HEALTH_FULL.getWidth(), Pawn.HEALTH_FULL.getHeight(), null);
            foregroundLabel.setIcon(new ImageIcon(newBackground));
            foregroundLayer.repaint();
        }
        super.paint(g);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JPanel backgroundLayer;
    private javax.swing.JLabel foregroundLabel;
    private javax.swing.JPanel foregroundLayer;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
