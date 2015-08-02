package de.marcovogt.avrremote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	
	private static String avr = "";
	private static int maximumVolume = 80;
	private static String favoriteSource = "AUX2";
	private static String favoriteSourceLabel = "PC";
	
	private static final String CONFIG_FILE_PATH = System.getProperty("user.home") + File.separator + "avr-remote.properties";
	
	public static String getAVR() {
		return avr;
	}
	
	public static void setAVR(String pAVR) {
		avr = pAVR;
		writeConfigFile();
	}
	
	
	public static int getMaximumVolume() {
		return maximumVolume;
	}
	
	public static void setMaximumVolume(int pMaximumVolume) {
		maximumVolume = pMaximumVolume;
		writeConfigFile();
	}
	
	
	public static String getFavoriteSource() {
		return favoriteSource;
	}
	
	public static void setFavoriteSource(String pFavoriteSource) {
		favoriteSource = pFavoriteSource;
		writeConfigFile();
	}
	
	
	public static String getFavoriteSourceLabel() {
		return favoriteSourceLabel;
	}
	
	public static void setFavoriteSourceLabel(String pFavoriteSourceLabel) {
		favoriteSourceLabel = pFavoriteSourceLabel;
		writeConfigFile();
	}
	
	
	private static void writeConfigFile() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream(CONFIG_FILE_PATH);

			// set the properties value
			prop.setProperty("avr", avr);
			prop.setProperty("maximumVolume", maximumVolume + "");
			prop.setProperty("favoriteSource", favoriteSource);
			prop.setProperty("favoriteSourceLabel", favoriteSourceLabel);

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public static void readConfigFile() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			File f = new File(CONFIG_FILE_PATH);
			if(f.exists()) {
				input = new FileInputStream(f);
	
				// load a properties file
				prop.load(input);
	
				// get the property values
				avr = prop.getProperty("avr");
				maximumVolume = Integer.parseInt(prop.getProperty("maximumVolume"));
				favoriteSource = prop.getProperty("favoriteSource");
				favoriteSourceLabel = prop.getProperty("favoriteSourceLabel");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
