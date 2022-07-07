import java.util.Scanner;

public class Game {	
	public static void main(String[] args) {
		CoffeeMakerQuest cmq = CoffeeMakerQuest.createInstance();
		
		// Create the rooms with their appropriate items
		Room room1 = new Room("Quaint sofa", "Small", Item.CREAM);
		Room room2 = new Room("Sad record player", "Funny", Item.NONE);
		Room room3 = new Room("Tight pizza", "Refinanced", Item.COFFEE);
		Room room4 = new Room("Flat energy drink", "Dumb", Item.NONE);
		Room room5 = new Room("Beautiful bag of money", "Bloodthirsty", Item.NONE);
		Room room6 = new Room("Perfect air hockey table", "Rough", Item.SUGAR);
		
		cmq.addFirstRoom(room1);
		cmq.addRoomAtNorth(room2, "Magenta", "Massive");
		cmq.addRoomAtNorth(room3, "Beige", "Smart");
		cmq.addRoomAtNorth(room4, "Dead", "Slim");
		cmq.addRoomAtNorth(room5, "Vivacious", "Sandy");
		cmq.addRoomAtNorth(room6, "Purple", "Minimalist");
					
		System.out.println("Coffee Maker Quest 1.0\n");
		cmq.setPlayer(new Player());
		cmq.setCurrentRoom(room1);
		
		Scanner scanner = new Scanner(System.in);
		
		// Main game loop
		while(cmq.isGameOver() == false) {
			System.out.println(cmq.getCurrentRoom().getDescription());
			System.out.println(cmq.getInstructionsString());
			
			String cmd = scanner.nextLine();
			String response = cmq.processCommand(cmd);
			System.out.println(response);
		}
		scanner.close();
	}
}
