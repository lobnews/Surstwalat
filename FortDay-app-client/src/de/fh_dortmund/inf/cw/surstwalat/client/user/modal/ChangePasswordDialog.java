package de.fh_dortmund.inf.cw.surstwalat.client.user.modal;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Designer;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Validator;
import de.fh_dortmund.inf.cw.surstwalat.client.user.view.RegistryPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;

/**
 * @author Stephan Klimek
 *
 */
public class ChangePasswordDialog extends JDialog {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = -9083280899052966509L;

    private JLabel lb_errorMsg;
    private JLabel lb_password_old;
    private JPasswordField pf_password_old;
    private JLabel lb_password;
    private JPasswordField pf_password;
    private JLabel lb_password_repeat;
    private JPasswordField pf_password_repeat;
    private JButton bt_update;
    private JButton bt_cancel;

    private final UserManagementHandler userManager;

    /**
     * Default Constructor
     *
     * @param parent
     */
    public ChangePasswordDialog(Component parent) {
        initComponent(parent);

        userManager = UserManagementHandler.getInstance();
    }

    /**
     * initialize ui components
     */
    private void initComponent(Component parent) {
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        setPreferredSize(new Dimension(400, 250));

        int gridRow = 0;

        // Error label
        lb_errorMsg = new JLabel();
        lb_errorMsg.setForeground(Color.RED);
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        gridPanel.add(lb_errorMsg, gridBag);

        // Password old
        lb_password_old = new JLabel("Altes Password: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        gridPanel.add(lb_password_old, gridBag);

        pf_password_old = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        gridPanel.add(pf_password_old, gridBag);
        gridPanel.setBorder(new LineBorder(Color.GRAY));

        // Password
        lb_password = new JLabel("Password: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        gridPanel.add(lb_password, gridBag);

        pf_password = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        gridPanel.add(pf_password, gridBag);
        gridPanel.setBorder(new LineBorder(Color.GRAY));

        // Password repeat
        lb_password_repeat = new JLabel("Passwort wiederholen: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        gridPanel.add(lb_password_repeat, gridBag);

        pf_password_repeat = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        gridPanel.add(pf_password_repeat, gridBag);
        gridPanel.setBorder(new LineBorder(Color.GRAY));

        bt_update = new JButton("Speichern");
        bt_update.addActionListener((ActionEvent e) -> {
            updatePassword();
        });

        bt_cancel = new JButton("Abbrechen");
        bt_cancel.addActionListener((ActionEvent e) -> {
            close();
        });

        JPanel bp = new JPanel();
        bp.add(bt_update);
        bp.add(bt_cancel);

        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    /**
     * Update password
     */
    private void updatePassword() {
        String oldPassword = String.valueOf(pf_password_old.getText());
        String newPassword = String.valueOf(pf_password.getText());
        String newPassword_repeat = String.valueOf(pf_password_repeat.getText());

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("oldPassword", oldPassword);
        inputMap.put("newPassword", newPassword);
        inputMap.put("newPassword_repeat", newPassword_repeat);

        // Validation check
        if (!checkNewPassword(inputMap)) {
            return;
        }

        // Registration call
        try {
            userManager.changePassword(oldPassword, newPassword);
        } catch (Exception ex) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Server wurde nicht gefunden!", "Systemfehler!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Success dialog
        JOptionPane.showMessageDialog(
                this,
                "Passwort wurde erfolgreich geändert.",
                "Erfolgreich!",
                JOptionPane.INFORMATION_MESSAGE);
        close();
    }

    /**
     * Input validation checking for change password
     * 
     * @param inputMap
     * @return 
     */
    private boolean checkNewPassword(Map<String, String> inputMap) {
         LinkedList<String> errorMsgList = new LinkedList<>();

        // Check old password
        if (!Validator.isEmptyString(inputMap.get("oldPassword"))) {
            errorMsgList.add("Kein gültiges Password.");
        }
        
        // Password check
        if (!Validator.checkStringLength(inputMap.get("newPassword"))) {
            errorMsgList.add("Das Passwort muss zwischen " + Validator.MINIMALINPUTLENGTH + " und " + Validator.MAXIMALINPUTLENGTH + " Zeichen lang sein.");
        } else if (!inputMap.get("newPassword").equals(inputMap.get("newPassword_repeat"))) {
            errorMsgList.add("Die Passwörter stimmen nicht über ein.");
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
     * Close modal dialog
     */
    private void close() {
        dispose();
    }
}
