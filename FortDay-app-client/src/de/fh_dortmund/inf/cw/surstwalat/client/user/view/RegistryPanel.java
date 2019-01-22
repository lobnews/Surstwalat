package de.fh_dortmund.inf.cw.surstwalat.client.user.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Designer;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Validator;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
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
public class RegistryPanel extends JPanel {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = 1563209170882467417L;

    private JLabel lb_errorMsg;
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

    private final UserManagementHandler userManager;

    /**
     * Default Constructor
     */
    public RegistryPanel() {
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
        lb_errorMsg = new JLabel();
        lb_errorMsg.setForeground(Color.RED);
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        add(lb_errorMsg, gridBag);

        // Username
        lb_username = new JLabel("Benutzername: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        add(lb_username, gridBag);

        tf_username = new JTextField(20);
        tf_username.requestFocus();
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        add(tf_username, gridBag);

        // Email
        lb_email = new JLabel("E-Mail Adresse: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        add(lb_email, gridBag);

        tf_email = new JTextField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        add(tf_email, gridBag);

        // Password
        lb_password = new JLabel("Password: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        add(lb_password, gridBag);

        pf_password = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        add(pf_password, gridBag);
        setBorder(new LineBorder(Color.GRAY));

        // Password repeat
        lb_password_repeat = new JLabel("Passwort wiederholen: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        add(lb_password_repeat, gridBag);

        pf_password_repeat = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        add(pf_password_repeat, gridBag);
        setBorder(new LineBorder(Color.GRAY));

        // Registry Button
        bt_registry = new JButton("Registrieren");
        bt_registry.addActionListener((ActionEvent e) -> {
            register();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        add(bt_registry, gridBag);

        // Back Button
        bt_abort = new JButton("Abbrechen");
        bt_abort.addActionListener((ActionEvent e) -> {
            back();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        add(bt_abort, gridBag);
    }

    /**
     * Registration
     */
    private void register() {
        String accoutName = tf_username.getText();
        String password = String.valueOf(pf_password.getPassword());
        String password_repeat = String.valueOf(pf_password_repeat.getPassword());
        String email = tf_email.getText();

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("name", accoutName);
        inputMap.put("password", password);
        inputMap.put("password_repeat", password_repeat);
        inputMap.put("email", email);

        // Validation check
        if (!checkRegistration(inputMap)) {
            return;
        }

        // Registration call
        try {
            userManager.register(accoutName, email, password);

            // Success dialog
            JOptionPane.showMessageDialog(
                    RegistryPanel.this,
                    "Hallo, " + accoutName + ". Du hast dich erfolgreich bei FortDay registriert.",
                    "Registrierung erfolgreich!",
                    JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, "Registration success");
            back();
        } catch (AccountAlreadyExistException e) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, "AccountAlreadyExistException");
            lb_errorMsg.setText(Designer.errorBox("Der angegebene Benutzername ist bereits vorhanden."));
        } catch (GeneralServiceException e) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, "GeneralServiceException", e);
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Server wurde nicht gefunden!", "Systemfehler!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Input validation
     *
     * @param inputMap
     * @return
     */
    private boolean checkRegistration(Map<String, String> inputMap) {
        LinkedList<String> errorMsgList = new LinkedList<>();

        // Input validation checking
        if (!Validator.checkStringLength(inputMap.get("name"))) {
            errorMsgList.add("Der Benutzername muss zwischen " + Validator.MINIMALINPUTLENGTH + " und " + Validator.MAXIMALINPUTLENGTH + " Zeichen lang sein.");
        }
        if (!Validator.checkStringLength(inputMap.get("password"))) {
            errorMsgList.add("Das Passwort muss zwischen " + Validator.MINIMALINPUTLENGTH + " und " + Validator.MAXIMALINPUTLENGTH + " Zeichen lang sein.");
        } else if (!inputMap.get("password").equals(inputMap.get("password_repeat"))) {
            errorMsgList.add("Die Passwörter stimmen nicht über ein.");
        }
        if (!Validator.checkEmailAddress(inputMap.get("email"))) {
            errorMsgList.add("Die E-Mail-Adresse ist nicht korrekt.");
        }

        // Error dialog
        if (errorMsgList.size() > 0) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, "Input error!", errorMsgList.toString());
            lb_errorMsg.setText(Designer.errorBox(errorMsgList));
            errorMsgList.clear();
            return false;
        }
        return true;
    }

    /**
     * Get a site back
     */
    private void back() {
        MainFrame.getInstance().setFrame(new LoginPanel(), false);
    }
}
