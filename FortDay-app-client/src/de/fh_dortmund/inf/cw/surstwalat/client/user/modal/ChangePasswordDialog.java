package de.fh_dortmund.inf.cw.surstwalat.client.user.modal;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Designer;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Validator;
import de.fh_dortmund.inf.cw.surstwalat.client.user.view.RegistryPanel;
import de.fh_dortmund.inf.cw.surstwalat.client.util.FontKeeper;
import de.fh_dortmund.inf.cw.surstwalat.client.util.TextRepository;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.WrongPasswordException;
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

    private JLabel lb_infoBox;
    private JLabel lb_password_current;
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
     * @param parent parent component
     */
    public ChangePasswordDialog(Component parent) {
        initComponent(parent);

        userManager = UserManagementHandler.getInstance();
    }

    /**
     * initialize ui components
     */
    private void initComponent(Component parent) {
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("ui_controls");
        
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(new Color(45, 98, 139));
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        setPreferredSize(new Dimension(400, 250));

        int gridRow = 0;

        // Error label
        lb_infoBox = new JLabel();
        lb_infoBox.setForeground(Color.RED);
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        gridPanel.add(lb_infoBox, gridBag);

        // Password current
        lb_password_current = new JLabel(textRepository.get("password_current"));
        lb_password_current.setFont(FontKeeper.LABEL);
        lb_password_current.setForeground(Color.WHITE);
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        gridPanel.add(lb_password_current, gridBag);

        pf_password_old = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        gridPanel.add(pf_password_old, gridBag);
        gridPanel.setBorder(new LineBorder(Color.GRAY));

        // Password
        lb_password = new JLabel(textRepository.get("password"));
        lb_password.setFont(FontKeeper.LABEL);
        lb_password.setForeground(Color.WHITE);
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
        lb_password_repeat = new JLabel(textRepository.get("password_repeat"));
        lb_password_repeat.setFont(FontKeeper.LABEL);
        lb_password_repeat.setForeground(Color.WHITE);
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

        // Update button
        bt_update = new JButton(textRepository.get("save"));
        bt_update.setFont(FontKeeper.BUTTON);
        bt_update.setBackground(Color.WHITE);
        bt_update.setForeground(Color.DARK_GRAY);
        bt_update.addActionListener((ActionEvent e) -> {
            updatePassword();
        });

        // Cancel button
        bt_cancel = new JButton(textRepository.get("cancel"));
        bt_cancel.setFont(FontKeeper.BUTTON);
        bt_cancel.setBackground(Color.WHITE);
        bt_cancel.setForeground(Color.DARK_GRAY);
        bt_cancel.addActionListener((ActionEvent e) -> {
            close();
        });

        // Implement panel
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
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");
        
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

            // Success dialog
            JOptionPane.showMessageDialog(
                    this,
                    textRepository.get("change_password_success"),
                    textRepository.get("change_password_success_short"),
                    JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, textRepository.get("change_password_success_short"));
            close();
        } catch (WrongPasswordException e) {
            Logger.getLogger(ChangePasswordDialog.class.getName()).log(Level.INFO, textRepository.get("wrongPasswordException_ex"));
            lb_infoBox.setText(Designer.errorBox(textRepository.get("wrongPasswordException")));
        } catch (GeneralServiceException e) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, textRepository.get("generalServiceException_ex"), e);
            JOptionPane.showMessageDialog(
                    MainFrame.getInstance(), 
                    textRepository.get("generalServiceException"), 
                    textRepository.get("generalServiceException_short"), 
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Input validation checking for change password
     *
     * @param inputMap
     * @return
     */
    private boolean checkNewPassword(Map<String, String> inputMap) {
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");
        
        LinkedList<String> errorMsgList = new LinkedList<>();

        // Check old password
        if (!Validator.isEmptyString(inputMap.get("oldPassword"))) {
            errorMsgList.add(textRepository.get("password_empty"));
        }

        // Password check
        if (!Validator.checkStringLength(inputMap.get("newPassword"))) {
            errorMsgList.add(String.format(textRepository.get("password_length"), Validator.MINIMALINPUTLENGTH, Validator.MAXIMALINPUTLENGTH));
        } else if (!inputMap.get("newPassword").equals(inputMap.get("newPassword_repeat"))) {
            errorMsgList.add(textRepository.get("passwords_do_not_match"));
        }

        // Error dialog
        if (errorMsgList.size() > 0) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, textRepository.get("input_error_short"), errorMsgList.toString());
            lb_infoBox.setText(Designer.errorBox(errorMsgList));
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
