package de.fh_dortmund.inf.cw.surstwalat.client.user.view;

import de.fh_dortmund.inf.cw.surstwalat.client.MainFrame;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Designer;
import de.fh_dortmund.inf.cw.surstwalat.client.user.UserManagementHandler;
import de.fh_dortmund.inf.cw.surstwalat.client.user.util.Validator;
import de.fh_dortmund.inf.cw.surstwalat.client.util.FontRepository;
import de.fh_dortmund.inf.cw.surstwalat.client.util.TextRepository;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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
public class LoginPanel extends JPanel {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = 1563209170882467417L;

    private JLabel lb_infoBox;
    private JLabel lb_username;
    private JTextField tf_username;
    private JLabel lb_password;
    private JPasswordField pf_password;
    private JButton bt_login;
    private JButton bt_registry;
    private JButton bt_close;

    private final UserManagementHandler userManager;
    private final Image backgroundImage;

    /**
     * Default Constructor
     */
    public LoginPanel() {
        backgroundImage = new ImageIcon(getClass().getResource("/resources/backgrounds/background-login.png")).getImage();
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
        lb_infoBox = new JLabel();
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        this.add(lb_infoBox, gridBag);
        
        // Username
        lb_username = new JLabel(textRepository.get("username"));
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
        lb_password = new JLabel(textRepository.get("password"));
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
        bt_login = new JButton(textRepository.get("login"));
        bt_login.addActionListener((ActionEvent e) -> {
            login();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_login, gridBag);

        // Registry button
        bt_registry = new JButton(textRepository.get("signin"));
        bt_registry.addActionListener((ActionEvent e) -> {
            openRegisterPanel();
        });
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 3;
        this.add(bt_registry, gridBag);

        // Close button
        bt_close = new JButton(textRepository.get("exit"));
        bt_close.addActionListener((ActionEvent e) -> {
            exit();
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
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");
        String name = tf_username.getText();
        String password = String.valueOf(pf_password.getText());

        // Validation check
        if (!checkLoginInput(name, password)) {
            return;
        }

        // Login call
        try {
            userManager.login(name, password);

            // Success
            Logger.getLogger(LoginPanel.class.getName()).log(Level.INFO, textRepository.get("login_success_short"));
            MainFrame.getInstance().setFrame(new StartHubPanel(), false);
        } catch (AccountNotFoundException e) {
            Logger.getLogger(LoginPanel.class.getName()).log(Level.INFO, textRepository.get("accountNotFoundException_ex"));
            lb_infoBox.setText(Designer.errorBox(textRepository.get("accountNotFoundException")));

        } catch (LoginFailedException e) {
            Logger.getLogger(LoginPanel.class.getName()).log(Level.INFO, textRepository.get("loginFailedException_ex"));
            lb_infoBox.setText(Designer.errorBox(textRepository.get("loginFailedException")));

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
     * Input validation checking for loggin in
     *
     * @param name
     * @param password
     * @return
     */
    private boolean checkLoginInput(String name, String password) {
        Map<String, String> textRepository = TextRepository.getInstance().getTextRepository("messages");
        LinkedList<String> errorMsgList = new LinkedList<>();

        // Check name
        if (!Validator.isEmptyString(name)) {
            errorMsgList.add(textRepository.get("username_empty"));
        }

        // Check password
        if (!Validator.isEmptyString(password)) {
            errorMsgList.add(textRepository.get("password_empty"));

        }

        // Error dialog
        if (errorMsgList.size() > 0) {
            Logger.getLogger(
                    RegistryPanel.class.getName()).log(Level.INFO,
                    textRepository.get("input_error_short"),
                    errorMsgList.toString());

            lb_infoBox.setText(Designer.errorBox(errorMsgList));
            errorMsgList.clear();
            return false;
        }
        return true;
    }

    /**
     * Open register panel
     */
    private void openRegisterPanel() {
        MainFrame.getInstance().setFrame(new RegistryPanel(), false);
    }

    /**
     * Exit program
     */
    private void exit() {
        System.exit(0);
    }
}
