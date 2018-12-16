package de.fh_dortmund.inf.cw.surstwalat.client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.fh_dortmund.inf.cw.surstwalat.dialogs.LoginDialog;

public class Main {
	public static void main(String[] args) {

		final JFrame frame = new JFrame("Prototype Login");
		final JButton btnLogin = new JButton("login");

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginDialog loginDlg = new LoginDialog(frame);
				loginDlg.setVisible(true);

				// if logon successfully
				if (loginDlg.isSucceeded()) {
					btnLogin.setText("Hallo " + loginDlg.getUsername() + "!");
				}
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);
		frame.setLayout(new FlowLayout());
		frame.getContentPane().add(btnLogin);
		frame.setVisible(true);
	}

	/*
	 * (non-Java-doc)
	 *
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}