package de.marcovogt.avrremote;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NoConnection extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel heading;
	private JTextPane txtpnText;
	private JButton btnSettings;
	
	public NoConnection(MainWindow window) {
		setBounds(100, 100, 220, 385);
		setLayout(null);
		
		heading = new JLabel("AVR Remote");
		heading.setBounds(10, 11, 200, 37);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(heading);
		
		txtpnText = new JTextPane();
		txtpnText.setText("No connection to AVR!");
		txtpnText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnText.setEditable(false);
		txtpnText.setBackground(UIManager.getColor("ScrollBar.foreground"));
		txtpnText.setBounds(10, 59, 200, 267);
		add(txtpnText);
		
		btnSettings = new JButton("Settings");
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.showSettings();
			}
		});
		btnSettings.setBounds(10, 337, 200, 37);
		add(btnSettings);
		
	}
	
	public void update() {
		
	}
}
