/**
 *
 */
package de.fh_dortmund.inf.cw.surstwalat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fh_dortmund.inf.cw.surstwalat.dialogs.ChangePasswordDialog;

/**
 * @author Stephan Klimek
 *
 */
public class ProfilEditorPanel extends JPanel {

	/**
	 * Generated serial version id
	 */
	private static final long serialVersionUID = 1563209170882467417L;

	private JLabel lb_username;
	private JTextField tf_username;
	private JLabel lb_email;
	private JTextField tf_email;
	private JButton bt_updateProfil;
	private JButton bt_changePassword;

	/**
	 * Default Constructor
	 */
	public ProfilEditorPanel() {
		initComponent();
	}

	/**
	 * Initiates ui components
	 */
	private void initComponent() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.insets = new Insets(5, 5, 5, 5);

		int gridRow = 0;

		lb_username = new JLabel("Benutzername");
		gridBag.gridx = 0;
		gridBag.gridy = gridRow;
		gridBag.gridwidth = 1;
		this.add(lb_username, gridBag);

		tf_username = new JTextField(20);
		tf_username.setEnabled(false);
		gridBag.gridx = 1;
		gridBag.gridy = gridRow;
		gridBag.gridwidth = 2;
		this.add(tf_username, gridBag);

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

		bt_changePassword = new JButton("Passwort ändern");
		ProfilEditorPanel panel = this;
		bt_changePassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(panel);
				changePasswordDialog.setVisible(true);
			}
		});
		gridBag.gridx = 0;
		gridBag.gridy = ++gridRow;
		gridBag.gridwidth = 3;
		this.add(bt_changePassword, gridBag);

		bt_updateProfil = new JButton("Speichern");
		bt_updateProfil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		gridBag.gridx = 0;
		gridBag.gridy = ++gridRow;
		gridBag.gridwidth = 3;
		this.add(bt_updateProfil, gridBag);
	}

	/**
	 * Get email adress
	 *
	 * @return email
	 */
	public String getEMail() {
		return tf_email.getText().trim();
	}
}
