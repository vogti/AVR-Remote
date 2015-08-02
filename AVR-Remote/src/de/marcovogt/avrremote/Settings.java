package de.marcovogt.avrremote;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Settings extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel heading;
	private JTextField avr;
	private JTextField maximumVolume;
	private JTextField favoriteSource;
	private JTextField favoriteSourceLabel;
	
	private JButton btnSave;
	private JButton btnCancel;
	
	public Settings(MainWindow window) {
		setBounds(100, 100, 220, 385);
		setLayout(null);
		
		heading = new JLabel("AVR Remote");
		heading.setBounds(10, 11, 200, 37);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(heading);
		
		JLabel lblAVR = new JLabel("AVR:");
		lblAVR.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAVR.setBounds(10, 59, 200, 25);
		add(lblAVR);
		
		avr = new JTextField();
		avr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				btnSave.setEnabled(isValidAVR());
			}
		});
		avr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		avr.setBounds(10, 84, 200, 31);
		add(avr);
		avr.setColumns(10);
		
		JLabel lblMaximumVolume = new JLabel("Maximum Volume:");
		lblMaximumVolume.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaximumVolume.setBounds(10, 126, 200, 25);
		add(lblMaximumVolume);
		
		maximumVolume = new JTextField();
		maximumVolume.setFont(new Font("Tahoma", Font.PLAIN, 14));
		maximumVolume.setBounds(10, 152, 200, 31);
		add(maximumVolume);
		maximumVolume.setColumns(10);
		
		JLabel lblFavoriteSource = new JLabel("Favorite Source:");
		lblFavoriteSource.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFavoriteSource.setBounds(10, 194, 200, 25);
		add(lblFavoriteSource);
		
		favoriteSource = new JTextField();
		favoriteSource.setFont(new Font("Tahoma", Font.PLAIN, 14));
		favoriteSource.setBounds(10, 219, 200, 31);
		add(favoriteSource);
		favoriteSource.setColumns(10);
		
		JLabel lblFavoriteSourceLabel = new JLabel("Favorite Source Label:");
		lblFavoriteSourceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFavoriteSourceLabel.setBounds(10, 261, 200, 25);
		add(lblFavoriteSourceLabel);
		
		favoriteSourceLabel = new JTextField();
		favoriteSourceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		favoriteSourceLabel.setBounds(10, 286, 200, 31);
		add(favoriteSourceLabel);
		favoriteSourceLabel.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.setAVR(avr.getText());
				Config.setFavoriteSource(favoriteSource.getText());
				Config.setFavoriteSourceLabel(favoriteSourceLabel.getText());
				
				try {
					Config.setMaximumVolume(Integer.parseInt(maximumVolume.getText()));
				} catch(NumberFormatException ex) {
					// Not a valid Number. Do nothing.
				}
				if(Controller.isConnected()) {
					window.showMain();
				} else {
					window.showNoConnection();
				}
			}
		});
		btnSave.setBounds(112, 337, 98, 37);
		add(btnSave);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.showMain();
			}
		});
		btnCancel.setBounds(10, 337, 98, 37);
		add(btnCancel);
		
	}
	
	public void update() {
		avr.setText(Config.getAVR());
		maximumVolume.setText(Config.getMaximumVolume() + "");
		favoriteSource.setText(Config.getFavoriteSource());
		favoriteSourceLabel.setText(Config.getFavoriteSourceLabel());
		
		btnSave.setEnabled(isValidAVR());
		btnCancel.setEnabled(isValidAVR());
	}
	
	private boolean isValidAVR() {
		if(avr.getText().equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
}
