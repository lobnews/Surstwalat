package de.fh_dortmund.inf.cw.surstwalat.client.user.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Designer;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Validator;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fh_dortmund.inf.cw.surstwalat.client.user.modal.ChangePasswordDialog;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Stephan Klimek
 *
 */
public class ProfilEditorPanel extends JPanel {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = 1563209170882467417L;

    private JLabel lb_infoBox;
    private JLabel lb_username;
    private JTextField tf_username;
    private JLabel lb_email;
    private JTextField tf_email;
    private JButton bt_updateProfil;
    private JButton bt_changePassword;
    private JButton bt_deleteProfil;
    private JButton bt_back;

    private final UserManagementHandler userManager;

    /**
     * Default Constructor
     */
    public ProfilEditorPanel() {
        initComponent();

        userManager = UserManagementHandler.getInstance();
    }

    /**
     * Initialize ui components
     */
    private void initComponent() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setMinimumSize(new Dimension(600, 400));

        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 5, 5, 5);

        int gridRow = 0;

        // Error label
        lb_infoBox = new JLabel();
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(lb_infoBox, gridBag);

        // Username
        lb_username = new JLabel("Benutzername: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
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

        // Edit profil button
        bt_updateProfil = new JButton("Speichern");
        bt_updateProfil.addActionListener((ActionEvent e) -> {
            updateEmailAddress();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_updateProfil, gridBag);

        // Change password button
        bt_changePassword = new JButton("Passwort ändern");
        bt_changePassword.addActionListener((ActionEvent e) -> {
            openChangePasswordDialog();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_changePassword, gridBag);

        // Delete profil button
        bt_deleteProfil = new JButton("Löschen");
        bt_deleteProfil.addActionListener((ActionEvent e) -> {
            deleteAccount();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_deleteProfil, gridBag);

        // Back button
        bt_back = new JButton("Zurück");
        bt_back.addActionListener((ActionEvent e) -> {
            back();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_back, gridBag);
    }

    /**
     * Update email address
     */
    private void updateEmailAddress() {
        String email = tf_email.getText();

        // Validation check
        if (!checkEmailAddress(email)) {
            return;
        }

        // update call
        try {
            userManager.updateEmailAddress(email);
            lb_infoBox.setText(Designer.successBox("E-Mail-Adresse wurde erfolgreich geändert."));
        } catch (GeneralServiceException e) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, "GeneralServiceException", e);
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Server wurde nicht gefunden!", "Systemfehler!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Input validation checking for change password
     *
     * @param inputMap
     * @return
     */
    private boolean checkEmailAddress(String email) {
        LinkedList<String> errorMsgList = new LinkedList<>();

        // Check email address
        if (!Validator.checkEmailAddress(email)) {
            errorMsgList.add("Die Angabe ist keine korrekte E-Mail-Adresse.");
        }

        // Error dialog
        if (errorMsgList.size() > 0) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, "Eingabefehler!", errorMsgList.toString());
            lb_infoBox.setText(Designer.errorBox(errorMsgList));
            errorMsgList.clear();
            return false;
        }
        return true;
    }

    /**
     * Open modal change password dialog
     */
    private void openChangePasswordDialog() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(this);
        changePasswordDialog.setVisible(true);
    }

    /**
     * Delete account
     */
    private void deleteAccount() {
        int dialogResult = JOptionPane.showConfirmDialog(
                MainFrame.getInstance(),
                "Achtung: Konto wirklich löschen?",
                "Warnung!",
                JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                userManager.deleteAccount();

                // Success dialog
                JOptionPane.showMessageDialog(
                        this,
                        "Das Konto wurde erfolgreich gelöscht.",
                        "Erfolgreich!",
                        JOptionPane.INFORMATION_MESSAGE);
                close();
            } catch (GeneralServiceException e) {
                Logger.getLogger(ProfilEditorPanel.class.getName()).log(Level.SEVERE, "GeneralServiceException", e);
            }
        }
    }

    /**
     * Get a site back
     */
    private void back() {
        MainFrame.getInstance().setFrame(new StartHubPanel(), false);
    }

    /**
     * Exit program
     */
    private void close() {
        System.exit(0);
    }
}
