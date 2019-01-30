package de.fh_dortmund.inf.cw.surstwalat.client;

import de.fh_dortmund.inf.cw.surstwalat.client.event.EventHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.event.EventListener;
import de.fh_dortmund.inf.cw.surstwalat.client.event.EventManager;
import de.fh_dortmund.inf.cw.surstwalat.client.event.EventPriority;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.AssignActivePlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.AssignPlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.EliminatePlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.GameStartedEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.PlayerRollEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.SetTokenHealthEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.StartRoundEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.TokenCreatedEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.UpdateZoneEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.game.view.MainPanel;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.user.view.LoginPanel;
import de.fh_dortmund.inf.cw.surstwalat.client.user.view.RegistryPanel;
import de.fh_dortmund.inf.cw.surstwalat.client.util.Pawn;
import de.fh_dortmund.inf.cw.surstwalat.client.util.PawnColor;
import de.fh_dortmund.inf.cw.surstwalat.client.util.TextRepository;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Lars Borisek
 */
public class MainFrame extends javax.swing.JFrame implements EventListener {

    private static MainFrame INSTANCE;

    private final EventManager eventManager;
    private final UserManagementHandler userManager;
    private final Map<String, String> textRepository;

    private Account account;
    private int playerId;
    private int gameId;

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
        eventManager = new EventManager();
        eventManager.registerListener(this);
        userManager = new UserManagementHandler();
        textRepository = TextRepository.getInstance().getTextRepository("messages");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel1 = new de.fh_dortmund.inf.cw.surstwalat.client.user.view.LoginPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FortDay-Client");
        setMinimumSize(new java.awt.Dimension(100, 100));
        getContentPane().add(loginPanel1, java.awt.BorderLayout.CENTER);
        this.setMinimumSize(loginPanel1.getMinimumSize());

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
            getInstance().setFrame(new MainPanel(), false);
        });
    }

    public void setFrame(JPanel newFrame) {
        setFrame(newFrame, true);
    }

    public void setFrame(JPanel newFrame, boolean pack) {
        getContentPane().removeAll();
        getContentPane().add(newFrame, java.awt.BorderLayout.CENTER);
        this.setMinimumSize(newFrame.getMinimumSize());
        if (pack) {
            pack();
        } else {
            revalidate();
        }
        repaint();
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDisplayEvent(StartRoundEvent e) {
        if (e.getDisplayMessage() == null || e.getDisplayMessage().isEmpty()) {
            return;
        }
        showMessage(e.getDisplayMessage());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDisplayEvent(AssignActivePlayerEvent e) {
        if (e.getDisplayMessage() == null || e.getDisplayMessage().isEmpty()) {
            return;
        }
        showMessage(e.getDisplayMessage());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDisplayEvent(EliminatePlayerEvent e) {
        if (e.getDisplayMessage() == null || e.getDisplayMessage().isEmpty()) {
            return;
        }
        showMessage(e.getDisplayMessage());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDisplayEvent(GameStartedEvent e) {
        if (e.getDisplayMessage() == null || e.getDisplayMessage().isEmpty()) {
            return;
        }
        showMessage(e.getDisplayMessage());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDisplayEvent(PlayerRollEvent e) {
        if (e.getDisplayMessage() == null || e.getDisplayMessage().isEmpty()) {
            return;
        }
        showMessage(e.getDisplayMessage());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDisplayEvent(UpdateZoneEvent e) {
        if (e.getDisplayMessage() == null || e.getDisplayMessage().isEmpty()) {
            return;
        }
        showMessage(e.getDisplayMessage());
    }

    @EventHandler
    public void onTokenHealth(SetTokenHealthEvent e) {
        Pawn p = Pawn.getInstance(e.getTokenID());
        if (p == null) {
            return;
        }
        p.setHealth(e.getHealth());
    }

    @EventHandler
    public void onTokenCreated(TokenCreatedEvent e) {
        for (int i : e.getTokens()) {
            new Pawn(PawnColor.getByPlayerNR(e.getPlayerNR()), 25, e.getPlayerID(), i);
        }
    }

    @EventHandler
    public void onDisplayEvent(AssignPlayerEvent e) {
        try {
            gameId = e.getGameID();
            playerId = e.getPlayerID();
            account = userManager.getAccountById(e.getUserID());
        } catch (AccountNotFoundException ex) {
            Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, textRepository.get("accountNotFoundException_ex"), ex);
        } catch (GeneralServiceException ex) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, textRepository.get("generalServiceException_ex"), ex);
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.fh_dortmund.inf.cw.surstwalat.client.user.view.LoginPanel loginPanel1;
    // End of variables declaration//GEN-END:variables
}
