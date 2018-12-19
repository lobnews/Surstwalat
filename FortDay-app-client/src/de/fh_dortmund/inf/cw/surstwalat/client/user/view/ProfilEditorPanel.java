package de.fh_dortmund.inf.cw.surstwalat.client.user.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fh_dortmund.inf.cw.surstwalat.client.user.modal.ChangePasswordDialog;

/**
 * @author Stephan Klimek
 *
 */
public class ProfilEditorPanel extends JPanel {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = 1563209170882467417L;

    private JLabel lb_username;
    private JTextField tf_username;
    private JLabel lb_email;
    private JTextField tf_email;
    private JButton bt_updateProfil;
    private JButton bt_back;
    private JButton bt_changePassword;

    /**
     * Default Constructor
     */
    public ProfilEditorPanel() {
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
        lb_username = new JLabel("Benutzername: ");
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 1;
        this.add(lb_username, gridBag);

        tf_username = new JTextField(20);
        tf_username.setEnabled(false);
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

        // Change password button
        bt_changePassword = new JButton("Passwort ändern");
        ProfilEditorPanel panel = this;
        bt_changePassword.addActionListener((ActionEvent e) -> {
            ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(panel);
            changePasswordDialog.setVisible(true);
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_changePassword, gridBag);

        // Edit profil button
        bt_updateProfil = new JButton("Speichern");
        bt_updateProfil.addActionListener((ActionEvent e) -> {
            // TODO update function
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_updateProfil, gridBag);

        // Back button
        bt_back = new JButton("Zurück");
        bt_back.addActionListener((ActionEvent e) -> {
            MainFrame.getInstance().setFrame(new StarterPanel());
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_back, gridBag);
    }

    /**
     * Get email adress
     *
     * @return email
     */
    public String getEMail() {
        return tf_email.getText().trim();
    }
}
