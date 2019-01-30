/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.lobby;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.event.EventHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.event.EventListener;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.UserJoinGameEvent;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Lars
 */
public class LobbyDetailPanel extends javax.swing.JPanel {

    /**
     * Creates new form LobbyDetailPanel
     */
    public LobbyDetailPanel() {
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
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("FortDay - Game Lobby");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(jLabel1, java.awt.BorderLayout.NORTH);

        jButton1.setText("Spiel starten");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, java.awt.BorderLayout.SOUTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(100, 100));

        jTable1.setModel(new LobbyPlayerModel());
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Spielerliste");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel2, java.awt.BorderLayout.NORTH);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        MainFrame.getInstance().startGame();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private class LobbyPlayerModel implements TableModel, EventListener {
        
        private List<Account> accounts;
        
        public LobbyPlayerModel() {
            accounts = MainFrame.getInstance().getUserManager().getUserInLobby();
            MainFrame.getInstance().getEventManager().registerListener(this);
        }
        
        @Override
        public int getRowCount() {
            return accounts.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch(columnIndex) {
                case 0: return "Name";
                case 1: return "E-Mail";
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Account a = accounts.get(rowIndex);
            switch(columnIndex) {
                case 0: return a.getName();
                case 1: return a.getEmail();
                default: return "";
            }
            
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

        @Override
        public void addTableModelListener(TableModelListener l) {}

        @Override
        public void removeTableModelListener(TableModelListener l) {}
        
        @EventHandler
        public void onPlayerJoin(UserJoinGameEvent e) {
            accounts = MainFrame.getInstance().getUserManager().getUserInLobby();
            LobbyDetailPanel.this.revalidate();
        }
    }
}
