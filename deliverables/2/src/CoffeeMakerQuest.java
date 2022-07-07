public interface CoffeeMakerQuest {
	public static CoffeeMakerQuest createInstance() {
		if(Config.getBuggyCoffeeMakerQuest()) {
			return new CoffeeMakerQuestBuggy();
		}
		else {
			return new CoffeeMakerQuestImpl();
		}
	}
	
	// Public interface of CoffeeMakerQuest
	public boolean isGameOver();
	public void setPlayer(Player player);
	public boolean addFirstRoom(Room room);
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor);
	public Room getCurrentRoom();
	public boolean setCurrentRoom(Room room);
	public String getInstructionsString();
	public String processCommand(String cmd);
}