package de.fh_dortmund.inf.cw.surstwalat.client.user.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
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

    private JLabel lb_username;
    private JTextField tf_username;
    private JLabel lb_password;
    private JPasswordField pf_password;
    private JButton bt_login;
    private JButton bt_registry;
    private JButton bt_close;
    private boolean succeeded;

    /**
     * Default Constructor
     */
    public LoginPanel() {
        initComponent();
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
        lb_username = new JLabel("Username: ");
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
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
            // TODO Login check            
            MainFrame.getInstance().setFrame(new StarterPanel());
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_login, gridBag);

        // Registry button
        bt_registry = new JButton("Registrieren");
        bt_registry.addActionListener((ActionEvent e) -> {         
            MainFrame.getInstance().setFrame(new RegistryPanel());
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_registry, gridBag);

        // Close button
        bt_close = new JButton("Beenden");
        bt_close.addActionListener((ActionEvent e) -> {
            MainFrame.getInstance().dispose();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_close, gridBag);
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

    /**
     * Get bool succeeded
     *
     * @return succeeded
     */
    public boolean isSucceeded() {
        return succeeded;
    }
}
