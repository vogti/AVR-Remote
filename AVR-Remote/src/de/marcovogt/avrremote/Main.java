package de.marcovogt.avrremote;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.marcovogt.avrremote.Controller.Status;

public class Main extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JToggleButton btnPower;
	private JToggleButton btnMute;
	private JToggleButton btnFavoriteSource;
	private JSlider sliderVolume;
	private JLabel lblVolume;
	private JLabel heading;
	private JLabel lblVersion;
	
	private JButton btnSettings;
	private JButton btnInfo;
	
	private boolean volumeChanged;
	private long volumeLastChangedTime;
	private boolean adjustVolume;

	private MainWindow window;
	
	private boolean connected;

	public Main(MainWindow window) {
		this.window = window;
		
		setBounds(100, 100, 220, 385);
		setLayout(null);
		
		lblVolume = new JLabel("0");
		lblVolume.setBounds(110, 173, 44, 50);
		lblVolume.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblVolume.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblVolume);

		heading = new JLabel("AVR Remote");
		heading.setBounds(10, 11, 200, 37);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(heading);

		lblVersion = new JLabel("AVR Remote v1.4");
		lblVersion.setBounds(45, 346, 130, 25);
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblVersion);

		btnMute = new JToggleButton("Mute");
		btnMute.setFocusPainted(false);
		btnMute.setBounds(10, 150, 80, 80);
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread() {
					public void run() {
						Controller.setMute(btnMute.isSelected());
					}
				}.start();
			}
		});
		add(btnMute);

		btnPower = new JToggleButton("Power");
		btnPower.setFocusPainted(false);
		btnPower.setBounds(10, 59, 80, 80);
		btnPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						Controller.setPower(btnPower.isSelected());
					}
				}.start();
			}
		});
		add(btnPower);

		sliderVolume = new JSlider();
		sliderVolume.setBounds(164, 59, 46, 259);
		sliderVolume.setMaximum(Config.getMaximumVolume());
		sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblVolume.setText(sliderVolume.getValue() + "");
				if(adjustVolume) {
					adjustVolume = false;
				} else {
					volumeChanged = true;
					volumeLastChangedTime = System.currentTimeMillis();
				}
			}
		});
		sliderVolume.setPaintTicks(true);
		sliderVolume.setFont(new Font("Tahoma", Font.PLAIN, 39));
		sliderVolume.setOrientation(SwingConstants.VERTICAL);
		add(sliderVolume);

		btnFavoriteSource = new JToggleButton("PC");
		btnFavoriteSource.setFocusPainted(false);
		btnFavoriteSource.setBounds(10, 241, 80, 80);
		btnFavoriteSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						Controller.setFavoriteSource();
					}
				}.start();
			}
		});
		add(btnFavoriteSource);
		
		btnSettings = new JButton("");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.showSettings();
			}
		});
		btnSettings.setBounds(185, 346, 25, 25);
		btnSettings.setIcon(new ImageIcon(MainWindow.class.getResource("/de/marcovogt/avrremote/settings.png")));
		btnSettings.setContentAreaFilled(false);
		btnSettings.setBorderPainted(false);
		btnSettings.setBorder(null);
		add(btnSettings);
		
		btnInfo = new JButton("");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.showInfo();
			}
		});
		btnInfo.setBounds(10, 346, 25, 25);
		btnInfo.setIcon(new ImageIcon(MainWindow.class.getResource("/de/marcovogt/avrremote/info.png")));
		btnInfo.setContentAreaFilled(false);
		btnInfo.setBorder(null);
		btnInfo.setBorderPainted(false);
		add(btnInfo);
		
		Timer timer = new Timer(1000, new Handler());
		timer.start();

	}
	
	class Handler implements ActionListener {
		  public void actionPerformed(ActionEvent e) {
			  new Thread() {
					public void run() {
						if(connected) {
							if(Controller.isConnected()) {								
								Status status = Controller.getStatus();
								btnPower.setSelected(status.power);
								btnMute.setSelected(status.mute);
								btnFavoriteSource.setSelected(status.favoriteSourceSelected);
								if(volumeChanged) {
									if(System.currentTimeMillis() - volumeLastChangedTime > 500) {
										volumeChanged = false;
										Controller.setVolume(sliderVolume.getValue());
									}
								} else {
									adjustVolume = true;
									sliderVolume.setValue(status.volume);
								}
								lblVolume.setText(sliderVolume.getValue() + "");
							} else {
								connected = false;
								window.showNoConnection();
							}
						}
						updateConnected();
					}
			  }.start();
		  }
	}
	
	public void updateFavoriteSourceLabel() {
		btnFavoriteSource.setText(Config.getFavoriteSourceLabel());
	}
	
	public void updateSliderMaximumVolume() {
		sliderVolume.setMaximum(Config.getMaximumVolume());
	}
	
	public void updateConnected() {
		boolean oldState = connected;
		connected = Controller.isConnected();
		if(!oldState && connected) {
			window.showMain();
		}
	}

}
