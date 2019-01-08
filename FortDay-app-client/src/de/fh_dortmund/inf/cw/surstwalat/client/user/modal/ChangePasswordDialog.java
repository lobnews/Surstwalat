package de.fh_dortmund.inf.cw.surstwalat.client.user.modal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.border.LineBorder;

/**
 * @author Stephan Klimek
 *
 */
public class ChangePasswordDialog extends JDialog {

    /**
     * Generated serial version id
     */
    private static final long serialVersionUID = -9083280899052966509L;

    private JLabel lb_password;
    private JPasswordField pf_password;
    private JLabel lb_password_repeat;
    private JPasswordField pf_password_repeat;
    private JButton bt_update;
    private JButton bt_cancel;

    /**
     * Default Constructor
     */
    public ChangePasswordDialog(Component parent) {
        initComponent(parent);
    }

    /**
     * Initiates ui components
     */
    private void initComponent(Component parent) {
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.fill = GridBagConstraints.HORIZONTAL;

        int gridRow = 0;

        lb_password = new JLabel("Password: ");
        gridBag.gridx = 0;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 1;
        gridPanel.add(lb_password, gridBag);

        pf_password = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        gridPanel.add(pf_password, gridBag);
        gridPanel.setBorder(new LineBorder(Color.GRAY));

        lb_password_repeat = new JLabel("Passwort wiederholen: ");
        gridBag.gridx = 0;
        gridBag.gridy = ++gridRow;
        gridBag.gridwidth = 1;
        gridPanel.add(lb_password_repeat, gridBag);

        pf_password_repeat = new JPasswordField(20);
        gridBag.gridx = 1;
        gridBag.gridy = gridRow;
        gridBag.gridwidth = 2;
        gridPanel.add(pf_password_repeat, gridBag);
        gridPanel.setBorder(new LineBorder(Color.GRAY));

        bt_update = new JButton("Speichern");

        bt_update.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Login check

                JOptionPane.showMessageDialog(ChangePasswordDialog.this,
                        "Das Passwort wurde erfolgreich geändert", "Passwort geändert",
                        JOptionPane.INFORMATION_MESSAGE);
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
        bp.add(bt_update);
        bp.add(bt_cancel);

        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
}
