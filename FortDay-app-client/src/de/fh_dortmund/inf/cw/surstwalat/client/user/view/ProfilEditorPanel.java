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
import de.fh_dortmund.inf.cw.surstwalat.client.util.FontKeeper;
import de.fh_dortmund.inf.cw.surstwalat.client.util.TextRepository;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
    private final Image backgroundImage;

    /**
     * Default Constructor
     */
    public ProfilEditorPanel() {
        userManager = UserManagementHandler.getInstance();
        backgroundImage = new ImageIcon(getClass().getResource("/resources/backgrounds/background.png")).getImage();
        initComponent();
    }

    /**
     * Paint component
     *
     * @param g graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Initialize ui components
     */
    private void initComponent() {
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("ui_controls");

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setMinimumSize(new Dimension(600, 438));

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
        lb_username = new JLabel(textRepository.get("username"));
        lb_username.setFont(FontKeeper.LABEL);     
        lb_username.setForeground(Color.WHITE);
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        this.add(lb_username, gridBag);

        tf_username = new JTextField(20);
        tf_username.setEnabled(false);
        tf_username.setText(userManager.getAccountName());
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(tf_username, gridBag);

        // Email
        lb_email = new JLabel(textRepository.get("email_new"));
        lb_email.setFont(FontKeeper.LABEL);
        lb_email.setForeground(Color.WHITE);
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        this.add(lb_email, gridBag);

        tf_email = new JTextField(20);
        tf_email.setText(userManager.getEMailAddress());
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(tf_email, gridBag);

        // Edit profil button
        bt_updateProfil = new JButton(textRepository.get("change_email"));
        bt_updateProfil.setFont(FontKeeper.BUTTON);
        bt_updateProfil.setBackground(Color.WHITE);
        bt_updateProfil.setForeground(Color.DARK_GRAY);
        bt_updateProfil.addActionListener((ActionEvent e) -> {
            updateEmailAddress();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_updateProfil, gridBag);

        // Change password button
        bt_changePassword = new JButton(textRepository.get("change_password"));
        bt_changePassword.setFont(FontKeeper.BUTTON);
        bt_changePassword.setBackground(Color.WHITE);
        bt_changePassword.setForeground(Color.DARK_GRAY);
        bt_changePassword.addActionListener((ActionEvent e) -> {
            openChangePasswordDialog();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_changePassword, gridBag);

        // Delete profil button
        bt_deleteProfil = new JButton(textRepository.get("delete"));
        bt_deleteProfil.setFont(FontKeeper.BUTTON);
        bt_deleteProfil.setBackground(Color.WHITE);
        bt_deleteProfil.setForeground(Color.DARK_GRAY);
        bt_deleteProfil.addActionListener((ActionEvent e) -> {
            deleteAccount();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_deleteProfil, gridBag);

        // Back button
        bt_back = new JButton(textRepository.get("back"));
        bt_back.setFont(FontKeeper.BUTTON);   
        bt_back.setBackground(Color.WHITE);
        bt_back.setForeground(Color.DARK_GRAY);
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
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");
        String email = tf_email.getText();

        // Validation check
        if (!checkEmailAddress(email)) {
            return;
        }

        // update call
        try {
            userManager.updateEmailAddress(email);
            tf_email.setText("");
            lb_infoBox.setText(Designer.successBox(textRepository.get("change_email_success")));
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, textRepository.get("change_email_success"));
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
    private boolean checkEmailAddress(String email) {
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");
        LinkedList<String> errorMsgList = new LinkedList<>();

        // Check email address
        if (!Validator.checkEmailAddress(email)) {
            errorMsgList.add(textRepository.get("email_not_valid"));
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
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");

        int dialogResult = JOptionPane.showConfirmDialog(
                MainFrame.getInstance(),
                textRepository.get("delete_check"),
                textRepository.get("Warning"),
                JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                userManager.deleteAccount();

                // Success dialog
                JOptionPane.showMessageDialog(
                        this,
                        textRepository.get("delete_success"),
                        textRepository.get("Success"),
                        JOptionPane.INFORMATION_MESSAGE);
                Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, textRepository.get("delete_success"));
                close();
            } catch (GeneralServiceException e) {
                Logger.getLogger(ProfilEditorPanel.class.getName()).log(Level.SEVERE, textRepository.get("generalServiceException_ex"), e);
                JOptionPane.showMessageDialog(
                        MainFrame.getInstance(),
                        textRepository.get("generalServiceException"),
                        textRepository.get("generalServiceException_short"),
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
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
