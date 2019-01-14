/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.game.view;

import de.fh_dortmund.inf.cw.surstwalat.client.game.util.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Lars
 */
public class GamePanel extends javax.swing.JPanel implements Observer {

    private Map aktMap = null;
    private BufferedImage background = null;

    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
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
        backgroundPanel = new javax.swing.JPanel();
        backgroundLabel = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(200, 200));
        setLayout(new java.awt.BorderLayout());

        jLayeredPane1.setLayout(new java.awt.BorderLayout());

        backgroundPanel.setLayout(new java.awt.BorderLayout());
        backgroundPanel.add(backgroundLabel, java.awt.BorderLayout.CENTER);

        jLayeredPane1.add(backgroundPanel, java.awt.BorderLayout.CENTER);

        gamePanel.setBackground(new Color(0, 0, 0, 0));
        gamePanel.setOpaque(false);
        gamePanel.setLayout(new java.awt.GridLayout(1, 0));
        jLayeredPane1.setLayer(gamePanel, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(gamePanel, java.awt.BorderLayout.CENTER);

        add(jLayeredPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void update(Observable o, Object arg) {
        Map map = (Map) arg;
        GridLayout layout = (GridLayout) gamePanel.getLayout();
        layout.setColumns(map.getxHeight());
        layout.setRows(map.getyHeight());
        gamePanel.setLayout(layout);
        gamePanel.removeAll();
        aktMap = map;
        for (int y = 0; y < map.getyHeight(); y++) {
            for (int x = 0; x < map.getxHeight(); x++) {
                gamePanel.add(new FieldPanel(x, y, map.getField(x, y)));
            }
        }
        gamePanel.setMinimumSize(new Dimension(FieldPanel.MIN_WIDTH * map.getxHeight(), FieldPanel.MIN_HEIGHT * map.getyHeight()));
        try {
            background = ImageIO.read(GamePanel.class.getClassLoader().getResource(map.getBackgroundMap()));
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        backgroundPanel.setSize(jLayeredPane1.getSize());
        if (background != null) {
            BufferedImage newBackground = new BufferedImage(backgroundPanel.getWidth(), backgroundPanel.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g2D = (Graphics2D) newBackground.getGraphics();
            g2D.drawImage(background, 0, 0, newBackground.getWidth(), newBackground.getHeight(), 0, 0, background.getWidth(), background.getHeight(), null);
            backgroundLabel.setIcon(new ImageIcon(newBackground));
            backgroundPanel.repaint();
        }
        gamePanel.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
