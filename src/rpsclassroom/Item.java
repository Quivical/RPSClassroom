package rpsclassroom;

//holds the info on what it does

public class Item {
	private String name;
	private String description;
	private boolean isKey = false;
	
	public Item (String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Item (String name, String description, boolean isKey) {
		this.name = name;
		this.description = description;
		this.isKey = isKey;
	}
	
	public String name() {
		return name;
	}
	
	public String description() {
		return description;
	}
	
	public boolean isKey() {
		return isKey;
	}
	
}
