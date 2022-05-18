package rpsclassroom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//v0.2.3 update: added class comments, a tutorial message, and the drop function, and cleaned up some code

//v0.2.4 update: map *expansion*, the ability to mess with delay between messages in the settings, map import from XML, and command changes

//v0.2.5 update: more commands, more map, keycards, and now you can actually see your inventory.

//v0.3.1 update: made private every. single. variable. 

//v0.3.2 update: GUI!! (also you can only have one item now)

//v0.4.1: Created the MOB class, overhauled how Keys work, removed the Map class

//v0.4.2: Added 3 MOBS to fight, large GUI update

//v0.4.3 Added EVEN MORE MOBS and Rock Paper Scissors is now playable!

//IN ORDER TO SET IMAGE CORRECLTY DO THE REST OF THE STRING INPUT ON THE CALL FOR THE FILE then + number which will concatenate it correctly

//TODO Finish making stuff observable, add an image changer

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// initializes the map and tells the Players and MOBs to start running

public class RPSClassroom {	
	
	private ArrayList<Room> roomList = new ArrayList<Room>();
	
	private RPSClassroom () {
		mapDataReader();
	}
	
	public static void main(String[] args) {
		RPSClassroom RPSClassroom = new RPSClassroom();
				
		MOB brunders = new MOB("Brunders Skroob", RPSClassroom.roomList.get(39)); //not meant to be accessible
//		Runnable m2 = new MOB("Kent Devereaux", RPSClassroom.roomList.get(1));
//		MOB them = new MOB("Them", RPSClassroom.roomList.get(6));
		Runnable m4 = new MOB("Q", RPSClassroom.roomList.get(20));
		Runnable m5 = new MOB("Cas", RPSClassroom.roomList.get(19));
		MOB tom = new MOB("Tom", RPSClassroom.roomList.get(18));
		Runnable m7 = new MOB("RA Nathan", RPSClassroom.roomList.get(23));
		Runnable m8 = new MOB("RA Edwardo", RPSClassroom.roomList.get(14));
		Runnable m9 = new MOB("RA Brittney", RPSClassroom.roomList.get(35));
		
		Object[] options = { "1 Player Mode", "2 Player Mode" };
		int mode = JOptionPane.showOptionDialog(null, "How many players?", "Choose mode", 
			JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (mode == 1) {
			Player p1 = new Player();
			Player p2 = new Player();
			p1.play("Player 1", RPSClassroom.roomList.get(0), 2);
			p2.play("Player 2", RPSClassroom.roomList.get(0), 2);
		} else {
			Player p1 = new Player();
			p1.play("You", RPSClassroom.roomList.get(0), 1);
		}
		
		ExecutorService service = Executors.newFixedThreadPool(8);
//		service.execute(m1);
//		service.execute(m2);
		service.execute(m4);
		service.execute(m5);
//		service.execute(m6);
		service.execute(m7);
		service.execute(m8);
		service.execute(m9);
	}
	
	public void mapDataReader() {
        try {
	        File fXmlFile = new File("mapData.xml");
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(fXmlFile);
	        doc.getDocumentElement().normalize();
	        NodeList nList = doc.getElementsByTagName("room");
	        
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            Element eElement = (Element) nNode;
	            Room room = new Room(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("desc").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
	            roomList.add(room);    
	        }
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            Element eElement = (Element) nNode;
	            try {
	            	roomList.get(temp).exits().add(roomList.get(Integer.parseInt(eElement.getElementsByTagName("up").item(0).getTextContent())));
	            	} catch (Exception e) {
	            		roomList.get(temp).exits().add(null);
	            }
	            try {
	            	roomList.get(temp).exits().add(roomList.get(Integer.parseInt(eElement.getElementsByTagName("left").item(0).getTextContent())));
	            	} catch (Exception e) {
	            		roomList.get(temp).exits().add(null);
	            }
	            try {
		            roomList.get(temp).exits().add(roomList.get(Integer.parseInt(eElement.getElementsByTagName("right").item(0).getTextContent())));
	            	} catch (Exception e) {
	            		roomList.get(temp).exits().add(null);
	            }
	            try {
		            roomList.get(temp).exits().add(roomList.get(Integer.parseInt(eElement.getElementsByTagName("down").item(0).getTextContent())));   
	            	} catch (Exception e) {
	            		roomList.get(temp).exits().add(null);
	            } 
	        }	        
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        Item pSelzOnecard = new Item("P-Selz OneCard", "Gives access to P-Selz.", true);
        roomList.get(0).setItem(pSelzOnecard);
        roomList.get(1).setItemReq(pSelzOnecard);
        
        Item firesideOnecard = new Item("Fireside OneCard", "Gives access to Fireside.", true);
        roomList.get(14).setItem(firesideOnecard);
        roomList.get(2).setItemReq(firesideOnecard);
        
        Item trusteesOnecard = new Item("Trustees OneCard", "Gives access to Trustees.", true);
        roomList.get(2).setItem(trusteesOnecard);
        roomList.get(3).setItemReq(trusteesOnecard);
        
//        Item = new Item("", "");
//        roomList.get().item = firesideOnecard;
//        roomList.get().itemRequirement = firesideOnecard;
    }
	
}