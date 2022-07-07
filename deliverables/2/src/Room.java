public class Room {
	private String furnishing;
	private String adjective;
	private Item item;
	private String northDoor;
	private String southDoor;
	
	// Class constructor to populate the class variables
	public Room(String furnishing, String adjective, Item item) {
		this.furnishing = furnishing;
		this.adjective = adjective;
		this.item = item;
		this.northDoor = null;
		this.southDoor = null;
	}
	
	public void setNorthDoor(String door) {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		northDoor = door;
	}
	
	public void setSouthDoor(String door) {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		southDoor = door;
	}
	
	// Return the furnishing in the room
	public String getFurnishing() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		return furnishing;
	}

	// Return the adjective of the room
	public String getAdjective() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		return adjective;
	}

	// Return the item in the room
	public Item getItem() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		return item;
	}
	
	// Print the description for the current room (includes adjective, furnishing, and door description)
	public String getDescription() {
		if (Config.getBuggyRoom()) {
			throw new UnsupportedOperationException("Injected bug for Room class");
		}
		String desc = "You see a " + this.adjective + " room.\nIt has a " + this.furnishing + ".\n";
		if(northDoor != null) {
			desc += "A " + this.northDoor + " door leads North.\n";
		}
		if(southDoor != null) {
			desc += "A " + this.southDoor + " door leads South.\n";
		}
		return desc;
	}
}
