package de.fh_dortmund.inf.cw.surstwalat.client.user.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Designer;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Validator;
import de.fh_dortmund.inf.cw.surstwalat.client.util.FontKeeper;
import de.fh_dortmund.inf.cw.surstwalat.client.util.TextRepository;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

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
    private final Image backgroundImage;

    /**
     * Default Constructor
     */
    public RegistryPanel() {
        backgroundImage = new ImageIcon(getClass().getResource("/resources/backgrounds/background.png")).getImage();
        initComponent();

        userManager = UserManagementHandler.getInstance();
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
        lb_errorMsg = new JLabel();
        lb_errorMsg.setForeground(Color.RED);
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        add(lb_errorMsg, gridBag);

        // Username
        lb_username = new JLabel(textRepository.get("username"));
        lb_username.setFont(FontKeeper.LABEL);
        lb_username.setForeground(Color.WHITE);
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
        lb_email = new JLabel(textRepository.get("email"));
        lb_email.setFont(FontKeeper.LABEL);
        lb_email.setForeground(Color.WHITE);
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
        lb_password = new JLabel(textRepository.get("password"));
        lb_password.setFont(FontKeeper.LABEL);
        lb_password.setForeground(Color.WHITE);
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
        lb_password_repeat = new JLabel(textRepository.get("password_repeat"));
        lb_password_repeat.setFont(FontKeeper.LABEL);
        lb_password_repeat.setForeground(Color.WHITE);
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
        bt_registry = new JButton(textRepository.get("signin"));
        bt_registry.setFont(FontKeeper.BUTTON);
        bt_registry.setBackground(Color.WHITE);
        bt_registry.setForeground(Color.DARK_GRAY);
        bt_registry.addActionListener((ActionEvent e) -> {
            register();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        add(bt_registry, gridBag);

        // Back Button
        bt_abort = new JButton(textRepository.get("cancel"));
        bt_abort.setFont(FontKeeper.BUTTON);
        bt_abort.setBackground(Color.WHITE);
        bt_abort.setForeground(Color.DARK_GRAY);
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
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");

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
                    String.format(textRepository.get("signin_success"), accoutName),
                    textRepository.get("signin_success_short"),
                    JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, textRepository.get("signin_success_short"));
            back();
        } catch (AccountAlreadyExistException e) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, textRepository.get("accountAlreadyExistException_ex"));
            lb_errorMsg.setText(Designer.errorBox(textRepository.get("accountAlreadyExistException")));
        } catch (GeneralServiceException e) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.SEVERE, textRepository.get("generalServiceException_ex"), e);
            JOptionPane.showMessageDialog(
                    MainFrame.getInstance(),
                    textRepository.get("generalServiceException"),
                    textRepository.get("generalServiceException_short"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Input validation
     *
     * @param inputMap
     * @return
     */
    private boolean checkRegistration(Map<String, String> inputMap) {
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");

        LinkedList<String> errorMsgList = new LinkedList<>();

        // Input validation checking
        if (!Validator.checkStringLength(inputMap.get("name"))) {
            errorMsgList.add(String.format(textRepository.get("username_length"), Validator.MINIMALINPUTLENGTH, Validator.MAXIMALINPUTLENGTH));
        }
        if (!Validator.checkStringLength(inputMap.get("password"))) {
            errorMsgList.add(String.format(textRepository.get("password_length"), Validator.MINIMALINPUTLENGTH, Validator.MAXIMALINPUTLENGTH));
        } else if (!inputMap.get("password").equals(inputMap.get("password_repeat"))) {
            errorMsgList.add(textRepository.get("passwords_do_not_match"));
        }
        if (!Validator.checkEmailAddress(inputMap.get("email"))) {
            errorMsgList.add(textRepository.get("email_not_valid"));
        }

        // Error dialog
        if (errorMsgList.size() > 0) {
            Logger.getLogger(RegistryPanel.class.getName()).log(Level.INFO, textRepository.get("input_error_short"), errorMsgList.toString());
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
