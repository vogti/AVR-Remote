package de.marcovogt.avrremote;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Info extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel heading;
	private JLabel lblIcon;
	private JTextPane txtpnCredit;
	private JTextPane txtpnVersion;
	private JButton btnBack;
	
	public Info(MainWindow window) {
		setBounds(100, 100, 220, 385);
		setLayout(null);
		
		heading = new JLabel("AVR Remote");
		heading.setBounds(10, 11, 200, 37);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(heading);
		
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(Info.class.getResource("/de/marcovogt/avrremote/icon-small.png")));
		lblIcon.setBounds(10, 59, 85, 84);
		add(lblIcon);
		
		txtpnVersion = new JTextPane();
		txtpnVersion.setText("Version: \r\n1.5\r\n\r\nDate: 11.08.2015");
		txtpnVersion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnVersion.setEditable(false);
		txtpnVersion.setBackground(UIManager.getColor("ScrollBar.foreground"));
		txtpnVersion.setBounds(105, 59, 105, 96);
		add(txtpnVersion);
		
		txtpnCredit = new JTextPane();
		txtpnCredit.setEditable(false);
		txtpnCredit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnCredit.setText("This Software was developed by\r\n\r\nMarco Vogt\r\nmarcovogt.de\r\n\r\nmail@marcovogt.de");
		txtpnCredit.setBackground(UIManager.getColor("ScrollBar.foreground"));
		txtpnCredit.setBounds(10, 178, 200, 153);
		add(txtpnCredit);
		
		// Center Text
		StyledDocument doc = txtpnCredit.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.showMain();
			}
		});
		btnBack.setBounds(10, 337, 200, 37);
		add(btnBack);
		
	}
}
