/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client;

import javax.swing.JPanel;

/**
 *
 * @author Lars
 */
public class MainFrame extends javax.swing.JFrame {

    private static MainFrame INSTANCE;

    public static MainFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainFrame();
        }
        return INSTANCE;
    }

    /**
     * Creates new form MainFrame
     */
    private MainFrame() {
        initComponents();
//        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel1 = new de.fh_dortmund.inf.cw.surstwalat.client.game.view.MainPanel();
        loginPanel1 = new de.fh_dortmund.inf.cw.surstwalat.client.user.view.LoginPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FortDay-Client");
        setMinimumSize(new java.awt.Dimension(100, 100));
        getContentPane().add(loginPanel1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(416, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            getInstance().setVisible(true);
        });
    }

    public void setFrame(JPanel newFrame) {
        getContentPane().removeAll();
        getContentPane().add(newFrame, java.awt.BorderLayout.CENTER);
        this.pack();
        this.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.fh_dortmund.inf.cw.surstwalat.client.user.view.LoginPanel loginPanel1;
    private de.fh_dortmund.inf.cw.surstwalat.client.game.view.MainPanel mainPanel1;
    // End of variables declaration//GEN-END:variables
}