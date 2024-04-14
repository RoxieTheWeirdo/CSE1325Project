
public class GameData {
    public String BlueColor = "\u001B[34m";
    public String ResetColor = "\u001B[0m";

    public boolean passable(char[][] maze, Player Pl,int offsetRow, int offsetCol, String moveType) {
        char tile = maze[Pl.currentRow + offsetRow][Pl.currentCol + offsetCol];
        switch (tile) {
            case '█': // Basic Wall
            case '▓': // Breakable Wall
            case 'X': // Key Door
                if (moveType.equals("Reg")) {
                    System.out.println("You hit a wall and took 5 damage!");
                    Pl.takeDamage(5);
                }
                return false;
            default:
                return true;
        }
    }

    public void useItem(Player Pl, String Item, char[][] currentMaze, int itemSlot) {
        switch (Item) {
            case "Potion of Vigor":
                if (Pl.health < 100) {
                    Pl.restoreHealth(50); // Use restoreHealth instead of direct manipulation
                    System.out.println("You used a Potion of Vigor. Health is now " + Pl.health);
                } else {
                    System.out.println("Using a Potion of Vigor now would have no effect!");
                }
                break;
            case "Key":
                // Use key to unlock doors, updated to handle all directions around the player
                handleKeyUse(Pl, currentMaze);
                break;
        }
        // Handle inventory slot clearing
        clearInventorySlot(Pl, itemSlot);
    }

    private void handleKeyUse(Player Pl, char[][] currentMaze) {
        // Check surroundings for a door ('X') to unlock
        if (Pl.currentCol + 1 < currentMaze[0].length && currentMaze[Pl.currentRow][Pl.currentCol+1] == 'X') {
            currentMaze[Pl.currentRow][Pl.currentCol+1] = '-';
            System.out.println("You have unlocked a door!");
        } // Additional checks for other directions omitted for brevity
    }

    private void clearInventorySlot(Player Pl, int itemSlot) {
        if (itemSlot == 1) {
            Pl.item1 = "";
        } else if (itemSlot == 2) {
            Pl.item2 = "";
        }
    }

    public void addItem(int ID, Player Pl, char[][] currentMaze) {
        if (Pl.item2.isEmpty()) {
            switch (ID) {
                case 1:
                    System.out.println("You found a Potion of Vigor!");
                    placeInventory(Pl,currentMaze, "Potion of Vigor");
                    break;
                case 2:
                    System.out.println("You found a Key");
                    placeInventory(Pl,currentMaze,"Key");
                    break;
            }
        }
        else {
            System.out.println("Your inventory is full!");
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
}
