package de.fh_dortmund.inf.cw.surstwalat.client;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.surstwalat.panels.LoginPanel;
import de.fh_dortmund.inf.cw.surstwalat.panels.ProfilEditorPanel;
import de.fh_dortmund.inf.cw.surstwalat.panels.RegistryPanel;

public class Main {
	
//	private static Main instance;
//
//	private Context ctx;
//	private UserManagmentRemote chatRemote;
//	private Topic chatMessageTopic;
//	private Queue chatMessageQueue;
//	private JMSContext jmsContext;
	
	public static void main(String[] args) {

		final JFrame frame = new JFrame("Prototype Login");
		
		JPanel loginPanel = new LoginPanel();
		JPanel registryPanel = new RegistryPanel();
		JPanel profilEditorPanel = new ProfilEditorPanel();
		frame.add(profilEditorPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setLayout(new FlowLayout());
		frame.setLocationRelativeTo(null);
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