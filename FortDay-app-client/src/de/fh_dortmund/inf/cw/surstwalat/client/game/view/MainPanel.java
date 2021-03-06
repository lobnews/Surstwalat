/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.game.view;

import de.fh_dortmund.inf.cw.surstwalat.client.game.util.Map;
import de.fh_dortmund.inf.cw.surstwalat.client.game.util.MapFormatException;
import de.fh_dortmund.inf.cw.surstwalat.client.game.util.MapLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Lars
 */
public class MainPanel extends javax.swing.JPanel {

    private static final String mapsFile = "maps.yml";
    private MapModel mapModel;

    /**
     * Creates new form MainPanel
     */
    public MainPanel() {
        System.out.println("test");
        initComponents();
        mapModel.addObserver(gamePanel1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mapModel = new MapModel(mapsFile);
        jComboBox1 = new javax.swing.JComboBox<>();
        gamePanel1 = new de.fh_dortmund.inf.cw.surstwalat.client.game.view.GamePanel();

        setMinimumSize(new java.awt.Dimension(200, 200));
        setLayout(new java.awt.BorderLayout());

        jComboBox1.setModel(mapModel);
        jComboBox1.setSelectedIndex(1);
        add(jComboBox1, java.awt.BorderLayout.NORTH);

        gamePanel1.setMinimumSize(new java.awt.Dimension(200, 200));
        add(gamePanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.fh_dortmund.inf.cw.surstwalat.client.game.view.GamePanel gamePanel1;
    private javax.swing.JComboBox<String> jComboBox1;
    // End of variables declaration//GEN-END:variables

    private class MapModel extends Observable implements ComboBoxModel<String> {

        private static final String CONFIG_PATH = "resources/mapdata/";
        private final List<Map> maps;
        private int selected;

        public MapModel(String fileName) {
            MapLoader loader = new MapLoader(CONFIG_PATH + fileName);
            List<Map> mapsCache = new LinkedList<>();
            try {
                loader.loadAll();
                mapsCache = loader.getMaps();
            } catch (MapFormatException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                maps = mapsCache;
            }
        }

        @Override
        public void setSelectedItem(Object anItem) {
//            System.out.println("Set Item " + anItem);
            int i = 0;
            for (Map map : maps) {
                if (anItem.equals(map.getName())) {
//                    System.out.println("Found item at " + i);
                    selected = i;
//                    System.out.println(String.format("Found %d observers.", countObservers()));
                    setChanged();
                    notifyObservers(map);
                    return;
                }
                i++;
            }
        }

        @Override
        public Object getSelectedItem() {
            return maps.get(selected).getName();
        }

        @Override
        public int getSize() {
            return maps.size();
        }

        @Override
        public String getElementAt(int index) {
            return maps.get(index).getName();
        }

        @Override
        public void addListDataListener(ListDataListener l) {
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
        }

    }
}
