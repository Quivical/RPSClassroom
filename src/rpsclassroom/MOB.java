package rpsclassroom;

import java.util.Observable;

public class MOB extends Player implements Runnable {
	private int gamestate = 0;
	
	public MOB(String name, Room start) {	
		this.name = name;
		start.checkIn(this);
		currentRoom = start;
	}
	
	public void run() {
		chill(5);
		while (gamestate != -1) {
			chill((Math.random() * 5 + 10));
			if (gamestate == 0) {
				move((int) (Math.random() * 4 + 1));
			}
		}
	}
	
	public boolean move(int dir) {
		Room nextRoom = currentRoom.exits().get(dir);
		if (nextRoom != null) {
			currentRoom.checkOut(this);
			nextRoom.checkIn(this);
			currentRoom = nextRoom;
			return true;
		} else {
			return move((int) (Math.random() * 4 + 1));
		}
	}
	
	public void setEngaged(boolean b) {
		if (b) {
			gamestate = 1;
		} else {
			gamestate = 0;
		}
	}
	
	public void update(Observable o, Object arg) {
		if (arg != null) {
			gamestate = -1;
		}
	}
	
	public int getInput() {
		chill(1);
		return ((int) (Math.random() * 3 + 1));
	}

}
