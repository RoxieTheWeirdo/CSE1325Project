import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class GameData {
    public String BlueColor = "\u001B[34m";
    public String RedColor = "\u001B[31m";
    public String DarkRedColor = "\u001B[31;2m";
    public String GreenColor = "\u001B[32m";
    public String ResetColor = "\u001B[0m";
    boolean ActiveCeilingTrap = true;

    public boolean passable(char[][] maze, Player Pl,int offsetRow, int offsetCol, String moveType) {
        char tile = maze[Pl.currentRow + offsetRow][Pl.currentCol + offsetCol];
        switch (tile) {
	    case 'M':
		Pl.takeDamage(80);
		System.out.println("You have been mauled by the Minotaur");
		return false;
            case '█': // Basic Wall
            case '▓': // Breakable Wall
            case 'X': // Key Door
                if (moveType.equals("Reg")) {
                    Pl.takeDamage(5);
                }
                if (moveType.equals("mass")) {
                    Pl.takeDamage(5);
                }
                return false;
            default:
                return true;
        }
    }

    public void useItem(Player Pl, String Item, char[][] currentMaze, int itemSlot, List<GameData.Thief> T) {
        switch (Item) {
            case "Potion of Vigor":
                if (Pl.health < 100) {
                    Pl.restoreHealth(50);
                }
                else {
                    System.out.println("Using a Potion of Vigor now would have no effect!");
                    return;
                }
                break;
            case "Key":
                if (Pl.currentCol + 1 < currentMaze[0].length && currentMaze[Pl.currentRow][Pl.currentCol + 1] == 'X') {
                    currentMaze[Pl.currentRow][Pl.currentCol + 1] = 'O';
                    System.out.println("You have unlocked a door!");
                } else if (Pl.currentRow + 1 < currentMaze.length && currentMaze[Pl.currentRow + 1][Pl.currentCol] == 'X') {
                    currentMaze[Pl.currentRow + 1][Pl.currentCol] = 'O';
                    System.out.println("You have unlocked a door!");
                } else if (Pl.currentRow - 1 >= 0 && currentMaze[Pl.currentRow - 1][Pl.currentCol] == 'X') {
                    currentMaze[Pl.currentRow - 1][Pl.currentCol] = 'O';
                    System.out.println("You have unlocked a door!");
                } else if (Pl.currentCol - 1 >= 0 && currentMaze[Pl.currentRow][Pl.currentCol - 1] == 'X') {
                    currentMaze[Pl.currentRow][Pl.currentCol - 1] = 'O';
                    System.out.println("You have unlocked a door!");
                } else {
                    System.out.println("There is no place nearby for the key to go into!");
                    return;
                }
                break;
            case "Bomb":
                if (B.currentRow == -1 && B.currentCol == -1) {
                    B.currentRow = Pl.currentRow;
                    B.currentCol = Pl.currentCol;
                    B.duration = 3;
                } else {
                    System.out.println("There is already a bomb detonating!");
                    return;
                }
                break;
            case "Pickaxe":
                Iterator<Thief> iterator = T.iterator();
                boolean success = false;
                while (iterator.hasNext()) {
                    Thief thief = iterator.next();
                    if (Pl.currentRow == thief.ThiefRow && Pl.currentCol == thief.ThiefCol) {
                        thiefDropItem(thief, currentMaze);
                        iterator.remove();
                        success = true;
                        break;
                    } else if (Pl.currentRow + 1 == thief.ThiefRow && Pl.currentCol == thief.ThiefCol ||
                            Pl.currentRow - 1 == thief.ThiefRow && Pl.currentCol == thief.ThiefCol ||
                            Pl.currentRow == thief.ThiefRow && Pl.currentCol + 1 == thief.ThiefCol ||
                            Pl.currentRow == thief.ThiefRow && Pl.currentCol - 1 == thief.ThiefCol) {
                        thiefDropItem(thief, currentMaze);
                        iterator.remove();
                        success = true;
                        break;
                    }
                }
                if (!success) {
                    System.out.println("There are no thieves to bash this pickaxe with!");
                    return;
                }
                break;
            default:
                System.out.println("You forgot to add the item's functionality or break; (Came from useItem() in GameData.java!)");
                break;
        }
        if (itemSlot == 1) {
            Pl.item1 = "";
            if (!Pl.item2.isEmpty()) {
                Pl.item1 = Pl.item2;
                Pl.item2 = "";
            }
        }
        if (itemSlot == 2) {
            Pl.item2 = "";
        }
    }


    public void addItem(int ID, Player Pl, char[][] currentMaze) {
        clearScreen();
        if (Pl.item2.isEmpty()) {
            switch (ID) {
                case 1:
                    System.out.println("You found a Potion of Vigor!");
                    placeInventory(Pl, currentMaze, "Potion of Vigor");
                    break;
                case 2:
                    System.out.println("You found a Key");
                    placeInventory(Pl, currentMaze, "Key");
                    break;
                case 3:
                    System.out.println("You found a Bomb");
                    placeInventory(Pl, currentMaze, "Bomb");
                    break;
                case 4:
                    System.out.println("You found a Pickaxe");
                    placeInventory(Pl, currentMaze, "Pickaxe");
                    break;
            }
        } else {
            System.out.println("Your inventory is full!");
        }
    }

    public void dropItem(Player Pl, String Name, char[][] currentMaze) {
        clearScreen();
        switch (Name) {
            case "Potion of Vigor":
                currentMaze[Pl.currentRow][Pl.currentCol] = 'V';
                System.out.println("You dropped a Potion of Vigor!");
                break;
            case "Key":
                currentMaze[Pl.currentRow][Pl.currentCol] = 'K';
                System.out.println("You dropped a Key!");
                break;
            case "Bomb":
                currentMaze[Pl.currentRow][Pl.currentCol] = 'B';
                System.out.println("You dropped a Bomb!");
                break;
            case "Pickaxe":
                currentMaze[Pl.currentRow][Pl.currentCol] = 'I';
                System.out.println("You dropped a Pickaxe!");
                break;
            default:
                System.out.println("If this message appears, then something in the code is missing (Came from dropItem() in GameData.java)!");
                break;
        }
    }

    public void placeInventory(Player Pl, char[][] currentMaze, String name) {
        if (Pl.item1.isEmpty()) {
            Pl.item1 = name;
        } else {
            Pl.item2 = name;
        }
        currentMaze[Pl.currentRow][Pl.currentCol] = '-';
    }

    public boolean validMovement(char[] mov) {
        for (char c : mov) {
            if (c != 'A' && c != 'W' && c != 'S' && c != 'D') {
                return false;
            }
        }
        return true;
    }

    public void detonateBomb(char[][] currentMaze, Player Pl) {
        if (currentMaze[B.currentRow][B.currentCol] == '▓') {
            currentMaze[B.currentRow][B.currentCol] = '-';
        }
        if (B.currentCol - 1 >= 0 && currentMaze[B.currentRow][B.currentCol - 1] == '▓') {
            currentMaze[B.currentRow][B.currentCol - 1] = '-';
        }
        if (B.currentRow - 1 >= 0 && currentMaze[B.currentRow - 1][B.currentCol] == '▓') {
            currentMaze[B.currentRow - 1][B.currentCol] = '-';
        }
        if (B.currentRow + 1 < currentMaze.length && currentMaze[B.currentRow + 1][B.currentCol] == '▓') {
            currentMaze[B.currentRow + 1][B.currentCol] = '-';
        }
        if (B.currentCol + 1 < currentMaze[0].length && currentMaze[B.currentRow][B.currentCol + 1] == '▓') {
            currentMaze[B.currentRow][B.currentCol + 1] = '-';
        }
	if (currentMaze[B.currentRow][B.currentCol] == 'M') {
            currentMaze[B.currentRow][B.currentCol] = 'K';
        }
        if (B.currentCol - 1 >= 0 && currentMaze[B.currentRow][B.currentCol - 1] == 'M') {
            currentMaze[B.currentRow][B.currentCol - 1] = 'K';
        }
        if (B.currentRow - 1 >= 0 && currentMaze[B.currentRow - 1][B.currentCol] == 'M') {
            currentMaze[B.currentRow - 1][B.currentCol] = 'K';
        }
        if (B.currentRow + 1 < currentMaze.length && currentMaze[B.currentRow + 1][B.currentCol] == 'M') {
            currentMaze[B.currentRow + 1][B.currentCol] = 'K';
        }
        if (B.currentCol + 1 < currentMaze[0].length && currentMaze[B.currentRow][B.currentCol + 1] == 'M') {
            currentMaze[B.currentRow][B.currentCol + 1] = 'K';
        }
        if (B.currentRow == Pl.currentRow && B.currentCol == Pl.currentCol) {
            Pl.takeDamage(75);
        }
        if (B.currentRow == Pl.currentRow - 1 && B.currentCol == Pl.currentCol) {
            Pl.takeDamage(35);
        }
        if (B.currentRow == Pl.currentRow + 1 && B.currentCol == Pl.currentCol) {
            Pl.takeDamage(35);
        }
        if (B.currentRow == Pl.currentRow && B.currentCol == Pl.currentCol - 1) {
            Pl.takeDamage(35);
        }
        if (B.currentRow == Pl.currentRow && B.currentCol == Pl.currentCol + 1) {
            Pl.takeDamage(35);
        }
    }

    private final Bomb B = new Bomb();

    public static class Bomb {
        int duration = -1;
        int currentRow = -1;
        int currentCol = -1;
    }

    public Bomb getBomb() {
        return B;
    }

    public static class Thief {
        char direction = 'W';
        String storedItem = "";
        int ThiefCol = 0;
        int ThiefRow = 0;
    }

    public void thiefMovement(List<GameData.Thief> T, char[][] currentMaze) {
        Random r = new Random();
        for (Thief thief : T) {
            char ThiefLeft;
            char ThiefRight;
            char ThiefUp;
            char ThiefDown;
            if (thief.ThiefCol - 1 >= 0) {
                ThiefLeft = currentMaze[thief.ThiefRow][thief.ThiefCol - 1];
            }
            else {
                ThiefLeft = '█';
            }
            if (thief.ThiefCol + 1 < currentMaze[0].length) {
                ThiefRight = currentMaze[thief.ThiefRow][thief.ThiefCol + 1];
            }
            else {
                ThiefRight = '█';
            }
            if (thief.ThiefRow - 1 >= 0) {
                ThiefUp = currentMaze[thief.ThiefRow - 1][thief.ThiefCol];
            }
            else {
                ThiefUp = '█';
            }
            if (thief.ThiefRow + 1 < currentMaze.length) {
                ThiefDown = currentMaze[thief.ThiefRow + 1][thief.ThiefCol];
            }
            else {
                ThiefDown = '█';
            }
            if (thief.direction == 'N') {
                ThiefDown = '█';
            } else if (thief.direction == 'E') {
                ThiefLeft = '█';
            } else if (thief.direction == 'S') {
                ThiefUp = '█';
            } else if (thief.direction == 'W') {
                ThiefRight = '█';
            }
            if (ThiefUp == '▓' || ThiefUp == 'X') {
                ThiefUp = '█';
            }
            if (ThiefDown == '▓' || ThiefDown == 'X') {
                ThiefDown = '█';
            }
            if (ThiefLeft == '▓' || ThiefLeft == 'X') {
                ThiefLeft = '█';
            }
            if (ThiefRight == '▓' || ThiefRight == 'X') {
                ThiefRight = '█';
            }
            boolean success = false;
            while (!success) {
                int choice = r.nextInt(4) + 1;
                if (ThiefUp == '█' && ThiefDown == '█' && ThiefLeft == '█' && ThiefRight == '█') {  //if they are trapped (so the loop isn't forever)
                    if (thief.direction == 'N') {
                        thief.direction = 'S';
                        ThiefDown = '-';
                    }
                    else if (thief.direction == 'S') {
                        thief.direction = 'N';
                        ThiefUp = '-';
                    }
                    else if (thief.direction == 'E') {
                        thief.direction = 'W';
                        ThiefLeft = '-';
                    }
                    else if (thief.direction == 'W') {
                        thief.direction = 'E';
                        ThiefRight = '-';
                    }
                    else {
                        System.out.println(BlueColor + ":(" + ResetColor);
                    }
                }
                switch (choice) {
                    case 1: // Move up
                        if (thief.direction != 'S' && thief.ThiefRow - 1 >= 0 && ThiefUp != '█') {
                            thief.ThiefRow -= 1;
                            thief.direction = 'N';
                            success = true;
                        }
                        break;
                    case 2: // Move down
                        if (thief.direction != 'N' && thief.ThiefRow + 1 < currentMaze.length && ThiefDown != '█') {
                            thief.ThiefRow += 1;
                            thief.direction = 'S';
                            success = true;
                        }
                        break;
                    case 3: // Move left
                        if (thief.direction != 'E' && thief.ThiefCol - 1 >= 0 && ThiefLeft != '█') {
                            thief.ThiefCol -= 1;
                            thief.direction = 'W';
                            success = true;
                        }
                        break;
                    case 4: // Move right
                        if (thief.direction != 'W' && thief.ThiefCol + 1 < currentMaze[0].length && ThiefRight != '█') {
                            thief.ThiefCol += 1;
                            thief.direction = 'E';
                            success = true;
                        }
                        break;
                }

            }
        }
    }
    public void thiefPickup(List<GameData.Thief> T, char[][] currentMaze) {
        for (Thief thief : T) {
            if (thief.storedItem.isEmpty()) {
                if (currentMaze[thief.ThiefRow][thief.ThiefCol] == 'V') {
                    thief.storedItem = "Potion of Vigor";
                    currentMaze[thief.ThiefRow][thief.ThiefCol] = '-';
                }
                else if (currentMaze[thief.ThiefRow][thief.ThiefCol] == 'K') {
                    thief.storedItem = "Key";
                    currentMaze[thief.ThiefRow][thief.ThiefCol] = '-';
                }
                else if (currentMaze[thief.ThiefRow][thief.ThiefCol] == 'B') {
                    thief.storedItem = "Bomb";
                    currentMaze[thief.ThiefRow][thief.ThiefCol] = '-';
                }
                else if (currentMaze[thief.ThiefRow][thief.ThiefCol] == 'I') {
                    thief.storedItem = "Pickaxe";
                    currentMaze[thief.ThiefRow][thief.ThiefCol] = '-';
                }
            }
        }
    }
    public void thiefDropItem(Thief T, char[][] currentMaze) {
        clearScreen();
        switch (T.storedItem) {
            case "Key":
                currentMaze[T.ThiefRow][T.ThiefCol] = 'K';
                System.out.println("The thief dropped a Key!");
                break;
            case "Potion of Vigor":
                currentMaze[T.ThiefRow][T.ThiefCol] = 'V';
                System.out.println("The thief dropped a Potion of Vigor!");
                break;
            case "Bomb":
                currentMaze[T.ThiefRow][T.ThiefCol] = 'B';
                System.out.println("The thief dropped a Bomb!");
                break;
            case "Pickaxe":
                currentMaze[T.ThiefRow][T.ThiefCol] = 'I';
                System.out.println("The thief dropped a Pickaxe!");
                break;
        }
    }

    public void FallingCeiling(Player Pl) {
        if (ActiveCeilingTrap == false) {
            return;
        }
		String correctAnswer = new String();
		System.out.println("The walls close around you and you hear a voice");
		System.out.println("\n\t\t\t\tANSWER THIS RIDDLE CORRECTLY OR BE CRUSHED FOR YOUR IGNORANCE!\n");
		Random s  = new Random();
		int rand = s.nextInt(3);
		switch(rand){
			case 0:
				System.out.println("\t\t\t\tTHE MORE THERE IS, THE LESS YOU SEE. WHAT IS IT?");
				correctAnswer = "Darkness";
				break;
			case 1:
				System.out.println("\t\t\t\tWHAT HAS KEYS BUT CAN'T OPEN LOCKS?");
				correctAnswer = "Piano";
				break;
			case 2:
				System.out.println("\t\t\t\tIM TALL WHEN IM YOUNG AND IM SHORT WHEN IM OLD. WHAT AM I?");
				correctAnswer = "Candle";
				break;
		}
		Scanner scan = new Scanner(System.in);
		String playerAnswer = scan.nextLine();
			if (playerAnswer.equalsIgnoreCase(correctAnswer)) {
			ActiveCeilingTrap = false;
            System.out.println("\n\t\t\t\tCORRECT! YOU MAY PROCEED.");
			System.out.println("The walls are raised and you are free to move");
		}else{
			System.out.println("\n\t\t\t\tWRONG ANSWER!");
			Pl.takeDamage(100);
		}
		
	}
    
    public static void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
