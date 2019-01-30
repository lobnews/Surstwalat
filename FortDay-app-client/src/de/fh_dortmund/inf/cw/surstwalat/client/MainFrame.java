package de.fh_dortmund.inf.cw.surstwalat.client;

import de.fh_dortmund.inf.cw.surstwalat.client.event.EventHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.event.EventListener;
import de.fh_dortmund.inf.cw.surstwalat.client.event.EventManager;
import de.fh_dortmund.inf.cw.surstwalat.client.event.EventPriority;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.AssignActivePlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.AssignPlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.DiceInteractEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.EliminatePlayerEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.GameStartedEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.HealthInteractEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.InventoryChangedEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.ItemAddToUserEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.PawnInteractEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.PlayerInventarEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.PlayerRollEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.SetTokenHealthEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.StartRoundEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.TokenCreatedEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.UpdateZoneEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.event.events.UserJoinGameEvent;
import de.fh_dortmund.inf.cw.surstwalat.client.game.view.MainPanel;
import de.fh_dortmund.inf.cw.surstwalat.client.lobby.LobbyOverviewPanel;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.util.Pawn;
import de.fh_dortmund.inf.cw.surstwalat.client.util.PawnColor;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import java.util.LinkedList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Lars Borisek
 */
public class MainFrame extends JFrame implements EventListener {

    private static MainFrame INSTANCE;

    private final EventManager eventManager;
    private final UserManagementHandler userManager;

    private int playerId;
    private int gameId;
    private int number;
    private int playerNr;
    private List<Item> inventar = new LinkedList<>();
    private boolean canInteractItem = false;
    private boolean canInteractPawn = false;

    public static MainFrame getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new MainFrame();
	}
	return INSTANCE;
    }

    public void addItem(Item i) {
	inventar.add(i);
	eventManager.fireEvent(new InventoryChangedEvent());
    }

    public void removeItem(Item i) {
	inventar.remove(i);
	eventManager.fireEvent(new InventoryChangedEvent());
    }

    public List<Item> getInventar() {
	return inventar;
    }

    public void setInventar(List<Item> inventar) {
	this.inventar = inventar;
	eventManager.fireEvent(new InventoryChangedEvent());
    }

    /**
     * Creates new form MainFrame
     */
    private MainFrame() {
	eventManager = new EventManager();
	eventManager.registerListener(this);
	userManager = UserManagementHandler.getInstance();

	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent winEvt) {
		userManager.logout();
		userManager.disconnect();
		System.exit(0);
	    }
	});

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
//            getInstance().setFrame(new MainPanel(), false);
//            getInstance().addItem(new Dice(new int[]{1,2,3,4,5,6}, "Ich bin ein Text"));
        });
    }

    public void setFrame(JPanel newFrame) {
	setFrame(newFrame, true);
    }

    public void setFrame(JPanel newFrame, boolean pack) {
	setFrame(newFrame, pack, true);
    }

    public void setFrame(JPanel newFrame, boolean pack, boolean resizable) {
	getContentPane().removeAll();
	getContentPane().add(newFrame, BorderLayout.CENTER);
	setMinimumSize(newFrame.getMinimumSize());
	setMaximumSize(newFrame.getMaximumSize());
	setResizable(resizable);

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
	if (e.getPlayerID() == playerId) {
	    canInteractItem = true;
	}
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
        if(e.getGameID() != gameId) {
            return;
        }
        getInstance().setFrame(new MainPanel(), false);
        showMessage(e.getDisplayMessage());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDisplayEvent(PlayerRollEvent e) {
	if (e.getDisplayMessage() == null || e.getDisplayMessage().isEmpty()) {
	    return;
	}
	showMessage(e.getDisplayMessage());
	if (e.getGameID() == gameId && e.getPlayerNR() == playerNr) {
	    canInteractPawn = true;
	    number = e.getNumber();
	}
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
	e.getTokens().forEach((i) -> {
	    new Pawn(PawnColor.getByPlayerNR(e.getPlayerNR()), 25, e.getPlayerID(), i);
	});
    }

    @EventHandler
    public void onDisplayEvent(AssignPlayerEvent e) {
	if (userManager.compareAccountById(e.getUserID())) {
	    playerNr = e.getPlayerNR();
	    gameId = e.getGameID();
	    playerId = e.getPlayerID();
	}
    }

    @EventHandler
    public void onItemAdd(ItemAddToUserEvent e) {
	if (playerId != e.getPlayerID()) {
	    return;
	}
	addItem(e.getItem());
    }

    @EventHandler
    public void onPlayerInventar(PlayerInventarEvent e) {
	if (playerId != e.getPlayerID()) {
	    return;
	}
	setInventar(e.getItems());
    }

    @EventHandler
    public void onDiceInteract(DiceInteractEvent e) {
	if (canInteractItem) {
	    userManager.playerRolls(gameId, playerId, e.getD());
	    canInteractItem = false;
	}
    }

    @EventHandler
    public void onHealthInteract(HealthInteractEvent e) {
	if (canInteractItem) {
	    userManager.useItem(gameId, playerId, e.getHealth());
	}
    }

    @EventHandler
    public void onPawnInteract(PawnInteractEvent e) {
	if (canInteractPawn) {
	    userManager.moveToken(gameId, e.getTokenID(), number);
	    canInteractPawn = false;
	}
    }

    public void showMessage(String message) {
	JOptionPane.showMessageDialog(this, message);
    }

    public int getGameId() {
	return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    
    public void startGame() {
        userManager.startGame(gameId, 40);
    }

    public UserManagementHandler getUserManager() {
        return userManager;
    }
        
    @EventHandler
    public void onUserJoinGame(UserJoinGameEvent e) {
        if(userManager.compareAccountById(e.getUserID())) {
            gameId = e.getGameID();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.fh_dortmund.inf.cw.surstwalat.client.user.view.LoginPanel loginPanel1;
    // End of variables declaration//GEN-END:variables
}
