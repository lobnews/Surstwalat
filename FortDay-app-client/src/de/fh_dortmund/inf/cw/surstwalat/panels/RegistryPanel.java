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
public class RegistryPanel extends JPanel {

	/**
	 * Generated serial version id
	 */
	private static final long serialVersionUID = 1563209170882467417L;

	private JLabel lb_username;
	private JTextField tf_username;
	private JLabel lb_email;
	private JTextField tf_email;
	private JLabel lb_password;
	private JPasswordField pf_password;
	private JLabel lb_password_repeat;
	private JPasswordField pf_password_repeat;
	private JButton bt_registry;

	/**
	 * Default Constructor
	 */
	public RegistryPanel() {
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

		lb_username = new JLabel("Benutzername: ");
		gridBag.gridx = 0;
		gridBag.gridy = gridRow;
		gridBag.gridwidth = 1;
		this.add(lb_username, gridBag);

		tf_username = new JTextField(20);
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

		lb_password = new JLabel("Password: ");
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

		lb_password_repeat = new JLabel("Passwort wiederholen: ");
		gridBag.gridx = 0;
		gridBag.gridy = ++gridRow;
		gridBag.gridwidth = 1;
		this.add(lb_password_repeat, gridBag);

		pf_password_repeat = new JPasswordField(20);
		gridBag.gridx = 1;
		gridBag.gridy = gridRow;
		gridBag.gridwidth = 2;
		this.add(pf_password_repeat, gridBag);
		setBorder(new LineBorder(Color.GRAY));

		bt_registry = new JButton("Registrieren");
		bt_registry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(RegistryPanel.this,
				        "Hallo " + getUsername() + "! You have successfully registry you.", "registry",
				        JOptionPane.INFORMATION_MESSAGE);
			}
		});
		gridBag.gridx = 0;
		gridBag.gridy = ++gridRow;
		gridBag.gridwidth = 3;
		this.add(bt_registry, gridBag);
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
}
