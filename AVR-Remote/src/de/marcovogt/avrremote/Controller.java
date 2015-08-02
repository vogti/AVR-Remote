package de.marcovogt.avrremote;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Controller {
	
	public static boolean isConnected() {
		try {
			new URL("http://" + Config.getAVR() + "/goform/formMainZone_MainZoneXmlStatus.xml").openStream();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static void setVolume(int vol) {
		sendRequest("http://" + Config.getAVR() + "/goform/formiPhoneAppDirect.xml?MV" + vol);
	}
	
	public static void volumeUp() {
		sendRequest("http://" + Config.getAVR() + "/goform/formiPhoneAppDirect.xml?MVUP");
	}

	public static void volumeDown() {
		sendRequest("http://" + Config.getAVR() + "/goform/formiPhoneAppDirect.xml?MVDOWN");
	}
	
	public static void setMute(boolean state) {
		if(state) {
			sendRequest("http://" + Config.getAVR() + "/goform/formiPhoneAppMute.xml?1+MuteOn");
		} else {
			sendRequest("http://" + Config.getAVR() + "/goform/formiPhoneAppMute.xml?1+MuteOff");
		}
	}
	
	public static void setPower(boolean state) {
		if(state) {
			sendRequest("http://" + Config.getAVR() + "/goform/formiPhoneAppPower.xml?1+PowerOn");
		} else {
			sendRequest("http://" + Config.getAVR() + "/goform/formiPhoneAppPower.xml?1+PowerStandby");
		}
	}
	
	public static void setFavoriteSource() {
		sendRequest("http://" + Config.getAVR() + "/goform/formiPhoneAppDirect.xml?SI" + Config.getFavoriteSource());
	}
	
	public static ArrayList<Input> getInputList() {
		Document res = parseRequest("http://" + Config.getAVR() + "/goform/formMainZone_MainZoneXmlStatus.xml");
		Element docEle = res.getDocumentElement();
		
		ArrayList<Input> inputList = new ArrayList<Input>();
		
		NodeList inputs = docEle.getElementsByTagName("InputFuncList").item(0).getChildNodes();
		if (inputs != null && inputs.getLength() > 0) {
			for (int i = 0; i < inputs.getLength(); i++) {
				if (inputs.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element input = (Element) inputs.item(i);
					Input in = new Input();
					in.name = input.getFirstChild().getNodeValue();
					inputList.add(in);
				}
			}
		}
		
		inputs = docEle.getElementsByTagName("RenameSource").item(0).getChildNodes();
		if (inputs != null && inputs.getLength() > 0) {
			for (int i = 0; i < inputs.getLength(); i++) {
				if (inputs.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element input = (Element) inputs.item(i).getFirstChild();
					Input in = inputList.get(i/2);
					in.customName = input.getFirstChild().getNodeValue();
				}
			}
		}
		
		return inputList;
	}
	            
	public static class Input {
		public String name;
		public String customName;
		
		@Override
		public String toString() {
			return customName;
		}
	}
	
	public static String getSelectedInput() {
		Document res = parseRequest("http://" + Config.getAVR() + "/goform/formMainZone_MainZoneXmlStatus.xml");
		Element docEle = res.getDocumentElement();
		return docEle.getElementsByTagName("InputFuncSelect").item(0).getFirstChild().getFirstChild().getNodeValue();
	}
	
	public static Status getStatus() {
		Document res = parseRequest("http://" + Config.getAVR() + "/goform/formMainZone_MainZoneXmlStatus.xml");
		Element docEle = res.getDocumentElement();
		
		Status status = new Status();
		
		// Power
		if(docEle.getElementsByTagName("Power").item(0).getFirstChild().getFirstChild().getNodeValue().equals("ON")) {
			status.power = true;
		} else {
			status.power = false;
		}
		
		// Mute
		if(docEle.getElementsByTagName("Mute").item(0).getFirstChild().getFirstChild().getNodeValue().equals("on")) {
			status.mute = true;
		} else {
			status.mute = false;
		}
		
		// Favorite Source
		if(docEle.getElementsByTagName("InputFuncSelect").item(0).getFirstChild().getFirstChild().getNodeValue().equals(Config.getFavoriteSource())) {
			status.favoriteSourceSelected = true;
		} else {
			status.favoriteSourceSelected = false;
		}
		
		// Volume
		String str = docEle.getElementsByTagName("MasterVolume").item(0).getFirstChild().getFirstChild().getNodeValue();
		Integer val;
		if(str.equals("--")) {
			val = 80;
		} else {
			val = (int) Double.parseDouble(str);
		}
		status.volume = 80-Math.abs(val);
		
		return status;
	}
	
	public static Document parseRequest(String urlStr) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
       	try (
				InputStream res = new URL(urlStr).openStream()
		) {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse(res);
			
			return dom;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void sendRequest(String urlStr) {
       	try (
				InputStream res = new URL(urlStr).openStream()
		) {
       		// send request
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static class Status {
		public boolean power;
		public boolean mute;
		public boolean favoriteSourceSelected;
		public int volume;
	}

}
