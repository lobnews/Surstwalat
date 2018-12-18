/**
 *
 */
package de.fh_dortmund.inf.cw.surstwalat.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	private JLabel lb_username;
	private JTextField tf_username;
	private JLabel lb_password;
	private JPasswordField pf_password;
	private JButton bt_login;
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
		this.setLayout(gridBagLayout);
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.insets = new Insets(5, 5, 5, 5);

		lb_username = new JLabel("Username: ");
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		this.add(lb_username, gridBag);

		tf_username = new JTextField(20);
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.gridwidth = 2;
		this.add(tf_username, gridBag);

		lb_password = new JLabel("Password: ");
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		this.add(lb_password, gridBag);

		pf_password = new JPasswordField(20);
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.gridwidth = 2;
		this.add(pf_password, gridBag);
		this.setBorder(new LineBorder(Color.GRAY));

		bt_login = new JButton("Login");
		bt_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Login check

				JOptionPane.showMessageDialog(LoginPanel.this,
				        "Hallo " + getUsername() + "! You have successfully logged in.", "Login",
				        JOptionPane.INFORMATION_MESSAGE);
				succeeded = true;
			}
		});
		gridBag.gridx = 0;
		gridBag.gridy = 2;
		gridBag.gridwidth = 3;
		this.add(bt_login, gridBag);
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
