package de.marcovogt.avrremote;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.CardLayout;

public class MainWindow {

	private JFrame frame;
	private CardLayout cl;
	
	private Main main;
	private Info info;
	private Settings settings;
	private NoConnection noConnection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		Config.readConfigFile();
		
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/de/marcovogt/avrremote/icon.png")));
		frame.setResizable(false);
		frame.setTitle("AVR Remote");
		frame.setBounds(100, 100, 226, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		cl = new CardLayout(0, 0);
		frame.getContentPane().setLayout(cl);
		
		info = new Info(this);
		frame.getContentPane().add(info, "info");
		
		settings = new Settings(this);
		settings.update();
		frame.getContentPane().add(settings, "settings");
		
		noConnection = new NoConnection(this);
		frame.getContentPane().add(noConnection, "noConnection");
		
		main = new Main(this);
		frame.getContentPane().add(main, "main");
		
		if(Config.getAVR().equals("")) {
			cl.show(frame.getContentPane(), "settings");
		} else {
			if(Controller.isConnected()) {
				cl.show(frame.getContentPane(), "main");
			} else {
				cl.show(frame.getContentPane(), "noConnection");
			}
		}
	}
	
	public void showMain() {
		main.updateFavoriteSourceLabel();
		main.updateSliderMaximumVolume();
		cl.show(frame.getContentPane(), "main");
	}
	
	public void showInfo() {
		cl.show(frame.getContentPane(), "info");
	}
	
	public void showSettings() {
		cl.show(frame.getContentPane(), "settings");
		settings.update();
	}

	public void showNoConnection() {
		cl.show(frame.getContentPane(), "noConnection");
		noConnection.update();
	}
	
}
