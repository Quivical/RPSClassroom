package rpsclassroom;

import java.util.ArrayList;
import java.util.Observable;

//holds all of the room data

public class Room extends Observable {
	
	private String name;
	private String description;
	private int id;
	private Item item;
	private ArrayList<Room> exits;
	private Item itemRequirement;
	private ArrayList<Player> charactersInside = new ArrayList<Player>();
	private int gamestate = 0;
	
	public Room(String name, String description, int id) {
		this.name = name;
		this.description = description;
		this.id = id;
		exits = new ArrayList<Room>();
		exits.add(this);
	}
	
	public synchronized void checkIn(Player p) {
		charactersInside.add(p);
		setChanged();
		notifyObservers();
		addObserver(p);
	}
	
	public synchronized void checkOut(Player p) {
		charactersInside.remove(p);
		deleteObserver(p);
		setChanged();
		notifyObservers();
	}
	
	public Item item() {
		return item;
	}
	
	public void clear() {
		item = null;
	}
	
	public ArrayList<Room> exits() {
		return exits;
	}
	
	public String name() {
		return name;
	}
	
	public String description() {
		return description;
	}
	
	public Item itemRequirement() {
		return itemRequirement;
	}
	
	public void setItem(Item it) {
		if (it.isKey() && it == itemRequirement) {
			itemRequirement = null;
		} else {
			item = it;
		}
	}
	
	public void setItemReq(Item it) {
		itemRequirement = it;
	}
	
	public void shutdown() {
		gamestate = -1;
		setChanged();
		notifyObservers(-1);
		for (Room room : exits) {
			if (room != null && room.gamestate != -1) {
				room.shutdown();
			}
		}
	}
	
	public synchronized ArrayList<Player> getOtherPlayers(Player self) {
		ArrayList<Player> others = new ArrayList<Player>();
		for (Player p : charactersInside) {
			others.add(p);
		}
		if (self == null) {
			return others;
		}
		others.remove(self);
		return others;
	}
	
	public boolean atCap() {
		if (charactersInside.size() < 4) {
			return false;
		} else {
			return true;
		}
	}
	
	public int id() {
		return id;
	}
}
