package de.fh_dortmund.inf.cw.surstwalat.client.user.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * @author Stephan Klimek
 *
 */
public class RegistryPanel extends JPanel {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = 1563209170882467417L;

    private JLabel lb_username;
    private JTextField tf_username;
    private JLabel lb_email;
    private JTextField tf_email;
    private JLabel lb_password;
    private JPasswordField pf_password;
    private JLabel lb_password_repeat;
    private JPasswordField pf_password_repeat;
    private JButton bt_registry;
    private JButton bt_abort;

    private UserManagementHandler userManager;

    /**
     * Default Constructor
     */
    public RegistryPanel() {
        initComponent();

        userManager = UserManagementHandler.getInstance();
    }

    /**
     * Initiates ui components
     */
    private void initComponent() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setMinimumSize(new java.awt.Dimension(600, 400));

        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 5, 5, 5);

        int gridRow = 0;

        // Username
        lb_username = new JLabel("Benutzername: ");
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 1;
        this.add(lb_username, gridBag);

        tf_username = new JTextField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(tf_username, gridBag);

        // Email
        lb_email = new JLabel("E-Mail Adresse: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        this.add(lb_email, gridBag);

        tf_email = new JTextField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(tf_email, gridBag);

        // Password
        lb_password = new JLabel("Password: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        this.add(lb_password, gridBag);

        pf_password = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(pf_password, gridBag);
        setBorder(new LineBorder(Color.GRAY));

        // Password repeat
        lb_password_repeat = new JLabel("Passwort wiederholen: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        this.add(lb_password_repeat, gridBag);

        pf_password_repeat = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(pf_password_repeat, gridBag);
        setBorder(new LineBorder(Color.GRAY));

        // Registry Button
        bt_registry = new JButton("Registrieren");
        bt_registry.addActionListener((ActionEvent e) -> {
            String accoutName = tf_username.getText();
            String email = tf_email.getText();
            String password = String.valueOf(pf_password.getPassword());
            
            try {
                userManager.register(accoutName, email, password);
            } catch (Exception ex) {
                Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Server wurde nicht gefunden!", "Systemfehler", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            // TODO unterscheidung / feedback
            JOptionPane.showMessageDialog(RegistryPanel.this,
                    "Hallo " + getUsername() + "! You have successfully registry you.", "Registry success",
                    JOptionPane.INFORMATION_MESSAGE);

            MainFrame.getInstance().setFrame(new LoginPanel());
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_registry, gridBag);

        // Back Button
        bt_abort = new JButton("Abbrechen");
        bt_abort.addActionListener((ActionEvent e) -> {
            MainFrame.getInstance().setFrame(new LoginPanel());
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_abort, gridBag);
    }

    /**
     * Get Username
     *
     * @return username
     */
    public String getUsername() {
        return tf_username.getText().trim();
    }

    /**
     * Get Password
     *
     * @return password
     */
    public String getPassword() {
        return new String(pf_password.getPassword());
    }
}