/**
 *
 */
package de.fh_dortmund.inf.cw.surstwalat.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
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
public class LoginDialog extends JDialog {

	/**
	 * Generated serial version id
	 */
	private static final long serialVersionUID = 1563209170882467417L;

	private JLabel lb_username;
	private JTextField tf_username;
	private JLabel lb_password;
	private JPasswordField pf_password;
	private JButton bt_login;
	private JButton bt_cancel;
	private boolean succeeded;

	/**
	 * Default Constructor
	 */
	public LoginDialog(Frame parent) {
		initComponent(parent);
	}

	/**
	 * Initiates ui components
	 */
	private void initComponent(Frame parent) {
		JPanel gridPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.HORIZONTAL;

		lb_username = new JLabel("Username: ");
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		gridPanel.add(lb_username, gridBag);

		tf_username = new JTextField(20);
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.gridwidth = 2;
		gridPanel.add(tf_username, gridBag);

		lb_password = new JLabel("Password: ");
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		gridPanel.add(lb_password, gridBag);

		pf_password = new JPasswordField(20);
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.gridwidth = 2;
		gridPanel.add(pf_password, gridBag);
		gridPanel.setBorder(new LineBorder(Color.GRAY));

		bt_login = new JButton("Login");

		bt_login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Login check

				JOptionPane.showMessageDialog(LoginDialog.this,
				        "Hallo " + getUsername() + "! You have successfully logged in.", "Login",
				        JOptionPane.INFORMATION_MESSAGE);
				succeeded = true;
				dispose();
			}
		});

		bt_cancel = new JButton("Cancel");
		bt_cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JPanel bp = new JPanel();
		bp.add(bt_login);
		bp.add(bt_cancel);

		getContentPane().add(gridPanel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}

	public String getUsername() {
		return tf_username.getText().trim();
	}

	public String getPassword() {
		return new String(pf_password.getPassword());
	}

	public boolean isSucceeded() {
		return succeeded;
	}

}
