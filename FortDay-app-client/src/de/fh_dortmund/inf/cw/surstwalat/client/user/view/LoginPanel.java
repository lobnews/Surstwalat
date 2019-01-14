package de.fh_dortmund.inf.cw.surstwalat.client.user.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.user.Designer;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.user.Validator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
public class LoginPanel extends JPanel {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = 1563209170882467417L;

    private JLabel lb_errorMsg;
    private JLabel lb_username;
    private JTextField tf_username;
    private JLabel lb_password;
    private JPasswordField pf_password;
    private JButton bt_login;
    private JButton bt_hackin;
    private JButton bt_registry;
    private JButton bt_close;

    private final UserManagementHandler userManager;

    /**
     * Default Constructor
     */
    public LoginPanel() {
        initComponent();

        userManager = UserManagementHandler.getInstance();
    }

    /**
     * initialize ui components
     */
    private void initComponent() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setPreferredSize(new Dimension(600, 400));

        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 5, 5, 5);

        int gridRow = 0;

        // Error label
        lb_errorMsg = new JLabel();
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(lb_errorMsg, gridBag);

        // Username
        lb_username = new JLabel("Username: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        this.add(lb_username, gridBag);

        tf_username = new JTextField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(tf_username, gridBag);

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

        // Login button
        bt_login = new JButton("Login");
        bt_login.addActionListener((ActionEvent e) -> {
            login();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_login, gridBag);

        // Fake login button
        bt_hackin = new JButton("Hackin");
        bt_hackin.addActionListener((ActionEvent e) -> {
            hackin();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_hackin, gridBag);

        // Registry button
        bt_registry = new JButton("Registrieren");
        bt_registry.addActionListener((ActionEvent e) -> {
            openRegisterPanel();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_registry, gridBag);

        // Close button
        bt_close = new JButton("Beenden");
        bt_close.addActionListener((ActionEvent e) -> {
            close();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_close, gridBag);
    }

    /**
     * Login
     */
    private void login() {
        String name = tf_username.getText();
        String password = String.valueOf(pf_password.getText());

        // Validation check
        if (!checkLoginInput(name, password)) {
            return;
        }

        // Registration call
        try {
            userManager.login();
        } catch (Exception ex) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Server wurde nicht gefunden!", "Systemfehler!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Success
        MainFrame.getInstance().setFrame(new StarterPanel());
    }

    /**
     * Hackin (fake login)
     */
    private void hackin() {
        MainFrame.getInstance().setFrame(new StarterPanel());
    }

    /**
     * Input validation checking for loggin in
     *
     * @param name
     * @param password
     * @return
     */
    private boolean checkLoginInput(String name, String password) {
        LinkedList<String> errorMsgList = new LinkedList<>();

        // Check name
        if (!Validator.isEmptyString(name)) {
            errorMsgList.add("Kein Benutzername angegeben.");
        }

        // Check password
        if (!Validator.isEmptyString(password)) {
            errorMsgList.add("Kein Passwort angegeben.");
        }

        // Error dialog
        if (errorMsgList.size() > 0) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.FINER, null, errorMsgList.toString());
            lb_errorMsg.setText(Designer.errorBox(errorMsgList));
            errorMsgList.clear();
            return false;
        }
        return true;
    }

    /**
     * Open register panel
     */
    private void openRegisterPanel() {
        MainFrame.getInstance().setFrame(new RegistryPanel());
    }

    /**
     * Exit program
     */
    private void close() {
        System.exit(0);
    }
}
