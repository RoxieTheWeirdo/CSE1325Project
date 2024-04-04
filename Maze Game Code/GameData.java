public class GameData {
        public String BlueColor = "\u001B[34m";
        //public String RedColor = "\u001B[31m";
        public String ResetColor = "\u001B[0m";
    public boolean passable(char[][] maze, Player Pl,int offsetRow, int offsetCol) {
        if (maze[Pl.currentRow + offsetRow][Pl.currentCol + offsetCol] == '█') {        //Basic Wall
            System.out.println("Invalid Position! (You'd take damage normally)");       //can modify more blocks later (breakable, ball, etc)
            return false;
        }
        else if (maze[Pl.currentRow + offsetRow][Pl.currentCol + offsetCol] == '▓') {   //Breakable Wall
            System.out.println("Invalid Position! (You'd take damage normally)");
            return false;
        }
        else if (maze[Pl.currentRow + offsetRow][Pl.currentCol + offsetCol] == 'X') {   //Key Door
            System.out.println("Invalid Position! (You'd take damage normally)");
            return false;
        }
        return true;
    }
    public void useItem(Player Pl, String Item, char[][] currentMaze, int itemSlot) {
        switch (Item) {
            case "Potion of Vigor":
                if (Pl.health < 100) {
                    Pl.health += 50;
                    if (Pl.health > 100) {
                        Pl.health = 100;
                    }
                }
                else {
                    System.out.println("Using a Potion of Vigor now would have no effect!");
                    return;
                }
                break;
            case "Key":
                if (Pl.currentCol + 1 < currentMaze[0].length && currentMaze[Pl.currentRow][Pl.currentCol+1] == 'X') {
                    currentMaze[Pl.currentRow][Pl.currentCol+1] = '-';
                    System.out.println("You have unlocked a door!");
                }
                else if (Pl.currentRow + 1 < currentMaze[0].length && currentMaze[Pl.currentRow+1][Pl.currentCol] == 'X') {
                    currentMaze[Pl.currentRow+1][Pl.currentCol] = '-';
                    System.out.println("You have unlocked a door!");
                }
                else if (Pl.currentRow - 1 >= 0 && currentMaze[Pl.currentRow][Pl.currentCol-1] == 'X') {
                    currentMaze[Pl.currentRow][Pl.currentCol-1] = '-';
                    System.out.println("You have unlocked a door!");
                }
                else if (Pl.currentCol - 1 >= 0 && currentMaze[Pl.currentRow-1][Pl.currentCol] == 'X') {
                    currentMaze[Pl.currentRow-1][Pl.currentCol] = '-';
                    System.out.println("You have unlocked a door!");
                }
                else {
                    System.out.println("There is no place nearby for the key to go into!");
                    return;
                }
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
    public void dropItem(Player Pl, String Name, char[][] currentMaze) {
        switch (Name) {
            case "Potion of Vigor":
                currentMaze[Pl.currentRow][Pl.currentCol] = 'V';
                System.out.println("You dropped a Potion of Vigor!");
                break;
            case "Key":
                currentMaze[Pl.currentRow][Pl.currentCol] = 'K';
                System.out.println("You dropped a Key!");
                break;
            default:
                System.out.println("If this message appears, then something in the code is missing (Came from dropItem() in GameData.java)!");
                break;
        }
    }
    public void placeInventory(Player Pl, char[][] currentMaze, String name) {
        if (Pl.item1.isEmpty()) {
            Pl.item1 = name;
        }
        else {
            Pl.item2 = name;
        }
        currentMaze[Pl.currentRow][Pl.currentCol] = '-';
    }
}
