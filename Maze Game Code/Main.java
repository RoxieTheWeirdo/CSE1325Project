import java.io.BufferedReader;      //for titletext.txt
import java.io.FileReader;          //titletext.txt
import java.util.ArrayList;
import java.util.Scanner;           //The scanner, the majestic scanner
import java.util.Arrays;
import java.util.List;
public class Main {
    public static Player Pl = new Player();     //stats found in Player.java
    public static GameData Gm = new GameData();     //Use of functions in GameData.java
    public static GameData.Bomb B = Gm.getBomb();
    public static char[][] currentMaze = null;
    public static char[][] baseMaze = null;
    public static Scanner sc = new Scanner(System.in);
    public static List<GameData.Thief> T;
    static int[] fallingCeilling = new int[3]; //holds quordinates for the trap
    public static void loadBaseMaze() {
        switch (Pl.LVL) {
            case 1:
                baseMaze = new char[][]{           //level 1
                        {'█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '-', '█',},
                        {'█', '-', '-', '-', '-', '█', '█', '-', '█', '-', '█', '█', 'X', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '-', '-', '█', '-', '-', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '-', '-', '█', '█', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '-', '-', '-', '-', '-', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '█', '-', '█', '█', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '█', '-', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '-', '-', '█', '-', '█', '-', '█', '-', '-', '-', '-', '█',},
                        {'█', '-', '-', '-', '█', '-', '█', '-', '█', '-', '█', '█', '-', '█',},
                        {'█', '█', '█', '█', '█', '-', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', 'V', '-', '-', '-', '█', 'K', '-', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '-', '█', '█', '█', '█', '█', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█',}
                };
                break;
            case 2:
                baseMaze = new char[][]{       //test level (Change Pl.LVL to 2 if you want to use this)
                        {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', 'X', '-', '-', '-', '-', },
                        {'-', '-', '▓', '-', '-', '-', '-', '█', '█', '█', '-', '-', '-', '-', '-', '█', '-', '-', '-', '-', },
                        {'-', '▓', '-', '▓', 'F', '-', '-', 'B', 'B', '█', '-', '█', '-', '-', '-', '█', '▓', '-', '-', '-', },
                        {'-', '-', '-', '-', '-', '-', '-', 'B', 'B', '█', '█', '█', 'X', '▓', '-', '█', '▓', '-', '-', '-', },
                        {'-', '-', '▓', '-', '-', '-', '-', 'B', 'B', '-', '-', '-', '-', '-', '▓', '█', '▓', '-', '-', '-', },
                        {'-', '▓', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '▓', '-', '█', '▓', '▓', '▓', 'X', },
                        {'-', '-', '▓', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '█', '-', '-', '-', '-', },
                        {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '█', '-', '-', '-', '-', },
                        {'V', '-', '-', '█', '█', '█', '-', '-', '-', 'K', 'K', '-', '-', '-', '-', '█', '-', '-', '-', '-', },
                        {'V', '-', '-', '█', '-', '-', '-', '-', '-', 'K', 'K', '-', '-', '-', '-', '█', '-', '-', '-', '-', },
                        {'V', '-', '-', '█', '-', '-', '-', '-', '-', '-', 'K', '-', '-', '-', '-', '-', '-', '-', '-', '-', },
                        {'V', '-', '-', '█', '-', '-', '-', '-', '█', '█', '█', '█', '█', '-', '▓', '-', '-', '-', '-', '-', },
                        {'V', '-', '-', '█', '-', '-', '-', '-', '-', 'I', '-', '-', '█', '-', '-', '▓', '-', '-', '-', '-', },
                        {'V', '-', '-', '-', '▓', '-', '▓', '-', '-', 'I', '-', '█', '█', '-', '▓', '-', '-', '-', '-', '-', },
                        {'V', '-', '-', '-', '-', '▓', '-', '-', '-', 'I', '█', '█', '█', '-', '-', '-', '-', '-', '-', '-', },
                        {'X', '-', '-', '-', '-', '-', '-', '-', '-', '█', '█', '█', '█', '-', '-', '-', '-', '-', '-', 'X', }
                };
                spawnThief('N', 4, 4, "");
                spawnThief('N', 12, 8, "");       //also an option to make them spawn WITH an item
                break;
        }
        currentMaze = Arrays.copyOf(baseMaze,baseMaze.length);
    }
    public static void spawnThief(char direction, int col, int row, String storedItem) {
        if (T == null) {
            T = new ArrayList<>();
        }
        GameData.Thief newThief = new GameData.Thief();
        newThief.direction = direction;
        newThief.ThiefCol = col;
        newThief.ThiefRow = row;
        newThief.storedItem = storedItem;
        T.add(newThief);
    }
    public static void loadCurrentMaze() {
        for (int i = 0; i < currentMaze.length; i++) {     //prints the selected maze
            for (int j = 0; j < currentMaze[0].length; j++) {
                boolean thiefSpawn = false;
                if (i == Pl.currentRow && j == Pl.currentCol) {
                    System.out.print("P");
                    continue;
                }
                if (i == B.currentRow && j == B.currentCol) {
                    if (B.duration >= 2) {
                        System.out.print(Gm.RedColor + "B" + Gm.ResetColor);
                    }
                    else if (B.duration == 1) {
                        System.out.print(Gm.DarkRedColor + "B" + Gm.ResetColor);
                    }
                    continue;
                }
                if (T != null) {
                    for (GameData.Thief thief : T) {
                        if (i == thief.ThiefRow && j == thief.ThiefCol && !thiefSpawn) {
                            if (!thief.storedItem.isEmpty()) {
                                System.out.print(Gm.BlueColor + "T" + Gm.ResetColor);
                            } else {
                                System.out.print("T");
                            }
                            thiefSpawn = true;
                        }
                    }
                }
                if(currentMaze[i][j] == 'F') {
					currentMaze[i][j] = '-';
					fallingCeilling[0] = i;
					fallingCeilling[1] = j;
                }
                if (!thiefSpawn) {
                    System.out.print(currentMaze[i][j]);
                }
            }
            System.out.println();
        }
    }
    public static void validPosition(char choice) {
        switch (choice) {
            case 'A':           //A (Left)
                if (Pl.currentCol - 1 >= 0 && Gm.passable(currentMaze, Pl,0,-1,"Reg")) {
                    Pl.currentCol -= 1;
                    Pl.restoreStamina(Pl.staminaRegen);
                }
                break;
            case 'W':           //W (Up)
                if (Pl.currentRow - 1 >= 0 && Gm.passable(currentMaze, Pl,-1,0,"Reg")) {
                    Pl.currentRow -= 1;
                    Pl.restoreStamina(Pl.staminaRegen);
                }
                break;
            case 'S':           //S (Down)
                if (Pl.currentRow + 1 < currentMaze.length && Gm.passable(currentMaze, Pl,1,0,"Reg")) {
                    Pl.currentRow += 1;
                    Pl.restoreStamina(Pl.staminaRegen);
                }
                break;
            case 'D':           //D (Right)
                if (Pl.currentCol + 1 < currentMaze[0].length && Gm.passable(currentMaze, Pl,0,1,"Reg")) {
                    Pl.currentCol += 1;
                    Pl.restoreStamina(Pl.staminaRegen);
                }
                break;
        }
    }
    public static void Items() {
        while (true) {
            int drop = 0;
            int back = 1;
            System.out.println("Items:");
            if (!Pl.item1.isEmpty()) {
                System.out.println("[1] " + Pl.item1);
                drop++;
                back++;
            }
            if (!Pl.item2.isEmpty()) {
                System.out.println("[2] " + Pl.item2);
                drop++;
                back++;
            }
            if (!Pl.item1.isEmpty()) {
                drop++;
                System.out.println("[" + drop + "] Drop Item");
                back++;
            }
            System.out.println("[" + back + "] Back");
            int choice = validInput(sc);
            if (!Pl.item1.isEmpty() && choice == 1) {
                Gm.useItem(Pl, Pl.item1, currentMaze, 1, T);
                break;
            }
            else if (!Pl.item2.isEmpty() && choice == 2) {
                Gm.useItem(Pl, Pl.item2, currentMaze, 2, T);
                break;
            }
            else if (choice == drop && drop != 0) {
                if (currentMaze[Pl.currentRow][Pl.currentCol] == '-') {
                    dropItem();
                }
                else {
                    System.out.println("You can't drop an item here! Please move to an open space to drop an item!");
                }
                break;
            }
            else if (choice == back) {
                break;
            }
            else {
                System.out.println("Please enter a different number.");
            }
        }
    }
    public static void dropItem() {
        while (true) {
            System.out.println("Which item will you drop?");
            int back = 1;
            if (!Pl.item1.isEmpty()) {
                System.out.println("[1] " + Pl.item1);
                back++;
            }
            if (!Pl.item2.isEmpty()) {
                System.out.println("[2] " + Pl.item2);
                back++;
            }
            System.out.println("[" + back + "] Back");
            int choice = validInput(sc);
            if (!Pl.item1.isEmpty() && choice == 1) {
                Gm.dropItem(Pl, Pl.item1, currentMaze);
                Pl.item1 = "";
                if (!Pl.item2.isEmpty()) {
                    Pl.item1 = Pl.item2;
                    Pl.item2 = "";
                }
                break;
            }
            else if (!Pl.item2.isEmpty() && choice == 2) {
                Gm.dropItem(Pl, Pl.item2, currentMaze);
                Pl.item2 = "";
                break;
            }
            else if (choice == back) {
                break;
            }
        }
    }
    public static void massMove() {
        while (true) {
            sc.nextLine();
            System.out.println("Enter a list of movements in the directions you want to go (AWSD), or type B to go back: ");
            String mass = sc.nextLine().toUpperCase();
            char[] movements = mass.toCharArray();
            int moveCounter;
            try {
                moveCounter = movements.length;
                if (movements[0] == 'B') {
                    break;
                } else if (Gm.validMovement(movements)) {
                    B.duration -= 1;
                    if (T != null) {
                        Gm.thiefMovement(T, currentMaze);
                        Gm.thiefPickup(T, currentMaze);
                    }
                    for (char c : movements) {
                        switch (c) {
                            case 'A':           //A (Left)
                                if (Pl.currentCol - 1 >= 0 && Gm.passable(currentMaze, Pl, 0, -1, "Mass")) {
                                    Pl.currentCol -= 1;
                                } else {
                                    Pl.takeDamage(moveCounter*5);
                                    System.out.println("You have taken serious damage from mass moving!");
                                    Pl.useStamina(moveCounter);
                                    return;
                                }
                                break;
                            case 'W':           //W (Up)
                                if (Pl.currentRow - 1 >= 0 && Gm.passable(currentMaze, Pl, -1, 0, "Mass")) {
                                    Pl.currentRow -= 1;
                                } else {
                                    Pl.takeDamage(moveCounter*5);
                                    System.out.println("You have taken serious damage from mass moving!");
                                    Pl.useStamina(moveCounter);
                                    return;
                                }
                                break;
                            case 'S':           //S (Down)
                                if (Pl.currentRow + 1 < currentMaze.length && Gm.passable(currentMaze, Pl, 1, 0, "Mass")) {
                                    Pl.currentRow += 1;
                                } else {
                                    Pl.takeDamage(moveCounter*5);
                                    System.out.println("You have taken serious damage from mass moving!");
                                    Pl.useStamina(moveCounter);
                                    return;

                                }
                                break;
                            case 'D':           //D (Right)
                                if (Pl.currentCol + 1 < currentMaze[0].length && Gm.passable(currentMaze, Pl, 0, 1, "Mass")) {
                                    Pl.currentCol += 1;
                                } else {
                                    Pl.takeDamage(moveCounter*5);
                                    System.out.println("You have taken serious damage from mass moving!");
                                    Pl.useStamina(moveCounter);
                                    return;
                                }
                                break;
                        }
                    }
                    Pl.useStamina(moveCounter);
                    break;
                } else {
                    System.out.println("Please only enter 'A', 'W', 'S', or 'D' in the movement list!\nExample: \"AAWWD\" (left, left, up, up, right)");
                }
            }
            catch (Exception e) {
                System.out.println("Please only enter 'A', 'W', 'S', or 'D' in the movement list!\nExample: \"AAWWD\" (left, left, up, up, right)");
            }
        }
    }
    public static void game() {
        B.duration = -1;
        if (T != null) {
                T.clear();          //clears thieves from past levels/reload levels
        }
        loadBaseMaze();
        while (Pl.health > 0) {
            if (currentMaze[Pl.currentRow][Pl.currentCol] == 'E') {
                break;
            }
            if (B.duration == 0) {
                System.out.println(Gm.DarkRedColor + "A nearby Bomb has detonated!" + Gm.ResetColor);
                Gm.detonateBomb(currentMaze, Pl);
                B.duration -= 1;
                B.currentCol = -1;
                B.currentRow = -1;
                if (Pl.health <= 0) {
                    Pl.health = 0;
                    break;
                }
            }
            if (T != null) {
                for (GameData.Thief thief : T) {
                    if (Pl.currentRow == thief.ThiefRow && Pl.currentCol == thief.ThiefCol) {
                        System.out.println("The nearby thief has slashed you with a dagger!");
                        Pl.takeDamage(3);
                        if (Pl.health <= 0) {
                            Pl.health = 0;
                            break;
                        }
                    }
                }
            }
            loadCurrentMaze(); //loads maze
            if((Pl.currentCol == fallingCeilling[0]) && (Pl.currentRow == fallingCeilling[1])) //check for trap at current location
            Gm.FallingCeiling(Pl);
			
		    if(Pl.health <= 0)
			Death(0,"fallingCeilling");
            
            int overlapItemID = 0;
            if (B.duration > 0) {
                if (B.currentRow == Pl.currentRow && B.currentCol == Pl.currentCol) {
                    System.out.println(Gm.DarkRedColor + "The bomb you are standing on will detonate in " + B.duration + " move" + (B.duration > 1 ? "s" : "") + "!" + Gm.ResetColor);

                }
                else {
                    System.out.println(Gm.RedColor + "A nearby Bomb will detonate in " + B.duration + " move" + (B.duration > 1 ? "s" : "") + "!" + Gm.ResetColor);

                }
            }
            System.out.println("Health: " + Pl.health + "%\t\tStamina: " + Pl.stamina + "%");        //Displays HP and Stamina
            /*System.out.println("Coords: ("+ Pl.currentRow + "),(" + Pl.currentCol + ")");
            System.out.println("Bomb Coords: ("+ B.currentRow + "),(" + B.currentCol + ")");
            int thiefCount = 1;
            for (GameData.Thief thief : T) {
                System.out.println("-------------");
                System.out.println("Thief " + thiefCount + " Coords: (" + thief.ThiefRow + "),(" + thief.ThiefCol + ")");
                System.out.println("Thief " + thiefCount + " Facing Direction: " + thief.direction);
                thiefCount ++;
            }*/
            System.out.println("Enter your choice, or press A,W,S,D to move");
            switch (currentMaze[Pl.currentRow][Pl.currentCol]) {
                case 'V':
                    System.out.println(Gm.BlueColor + "You found a Potion of Vigor! Enter 4 to pick it up!" + Gm.ResetColor);
                    overlapItemID = 1;
                    break;
                case 'K':
                    System.out.println(Gm.BlueColor + "You found a Key! Enter 4 to pick it up!" + Gm.ResetColor);
                    overlapItemID = 2;
                    break;
                case 'B':
                    System.out.println(Gm.BlueColor + "You found a Bomb! Enter 4 to pick it up!" + Gm.ResetColor);
                    overlapItemID = 3;
                    break;
                case 'I':
                    System.out.println(Gm.BlueColor + "You found a Pickaxe! Enter 4 to pick it up!" + Gm.ResetColor);
                    overlapItemID = 4;
                    break;
            }
            System.out.println("[1] Mass Move");
            System.out.println("[2] Items");
            System.out.println("[3] Options");
            if (overlapItemID > 0) {
                System.out.println(Gm.BlueColor + "[4] Pick up Item" + Gm.ResetColor);
            }
            String c = sc.next().toUpperCase();
            if (c.isEmpty()) {
                System.out.println("Please enter a character or number!");
                continue;
            }
            char choice = c.charAt(0);
            switch (choice) {
                case 'A':
                case 'W':
                case 'S':
                case 'D':
                    GameData.clearScreen();
                    B.duration -= 1;
                    validPosition(choice);
                    if (T != null) {
                        Gm.thiefMovement(T, currentMaze);
                        Gm.thiefPickup(T, currentMaze);
                    }
                    break;
                case '1':
                    GameData.clearScreen();
                    massMove();
                    break;
                case '2':
                    GameData.clearScreen();
                    Items();
                    break;
                case '3':           //placeholder
                    if (options() == 1) {
                        return;
                    }
                    break;
                case '4':
                    if (overlapItemID > 0) {
                        Gm.addItem(overlapItemID, Pl, currentMaze);
                    }
                    else {
                        System.out.println("Please enter a different character!");
                    }
                    break;
                default:
                    System.out.println("Please enter a different character!");
                    break;
            }
        }
        if (Pl.health <= 0) {
            Death(0, "wall");
        }
    }
    public static int options() {
        while (true) {
            GameData.clearScreen();
            System.out.println("Options: ");
            System.out.println("[1] Reset Level");
            System.out.println("[2] Quit Game");
            System.out.println("[3] Back");
            int choi = validInput(sc);
            switch (choi) {
                case 1:
                    System.out.println("Reseting Game..");
                    int LVL = Pl.LVL;
                    Pl.setBaseStats();
                    Pl.LVL = LVL;
                    GameData.clearScreen();
                    if (T != null) {
                        T.clear();          //clears thieves from past levels/reload levels
                    }
                    Pl.basePosition(Pl, Pl.LVL);
                    loadBaseMaze();
                    return 0;
                case 2:
                    GameData.clearScreen();
                    System.out.println("Back to Main Menu...");
                    return 1;
                case 3:
                    GameData.clearScreen();
                    return 0;
                default:
                    GameData.clearScreen();
                    System.out.println("Please enter a different number.");
            }
        }
    }

    public static void resetPlayer() { //player reset to starting point after death
        System.out.println("\n\nYou awaken and see that you have returned to the same spot where you fell into the maze");
        Gm.ActiveCeilingTrap = true;
        int TempLVL = Pl.LVL;
        Pl.setBaseStats();
        Pl.LVL = TempLVL;
        Pl.basePosition(Pl, Pl.LVL);
        game();
    }

    //Function must be called each time player takes damage
    public static void Death(int health, String killer) {    //Checks if player is dead, killer is cause of death
        if(health == 0) {
            if (T != null) {
                T.clear();
            }
            System.out.println("\t\tYou have died");
            switch(killer) {
                case "wallmass":
                    System.out.println("You ran into the wall!");
                    break;
                case "wallreg":
                    System.out.println("You have hit the wall!");
                    break;
                case "rollingBall":
                    System.out.println("You were crushed by a large rolling ball");
                    break;
                case "fallingCeilling":
                    System.out.println("You were flattened by a falling ceiling");
                    break;
                case "minotaur":
                    System.out.println("You have been mauled by the Minotaur");
                    break;
            }
            while (true) {
                sc.nextLine();
                System.out.println("\tType ok to continue");
                String okInput = sc.nextLine();
                if(okInput.equals("ok"))
                    break;
            }
            resetPlayer();

        }
    }

    public static void main(String[] args) {
        String fileName = "titletext.txt";
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {        //prints line by line
                    System.out.println(line);
                }
                reader.close();
            } catch (Exception e) {     //if titletext.txt is not there in directory
                System.out.println("Failed to load titletext.txt, Please ensure title.txt is located in the same directory!");
            }
            Pl.setBaseStats();
            System.out.println("Main Menu");
            System.out.println("Enter your choice (Type the corresponding number, then press enter)");
            System.out.println("[1] Start Game");
            System.out.println("[2] Load Game");
            System.out.println("[3] Settings");
            System.out.println("[4] Quit");
            int choi = validInput(sc);
            switch (choi) {
                case 1:
                    GameData.clearScreen();
                    System.out.println("Starting Game..");
                    Pl.basePosition(Pl, Pl.LVL);
                    game();
                    break;
                case 2:
                    System.out.println("Loading Save File..");      //just an idea, could use a file as a save file then load a specific state from here
                    break;
                case 3:
                    System.out.println("Loading Settings..");       //settings (got a few ideas)
                    break;
                case 4:                             //quit
                    System.exit(0);
            }
        }
    }
    public static int validInput(Scanner sc) {          //so the user doesn't kamikaze at the first input, might use later for items
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a number (Not including decimals)!");
            sc.next();          //consumes invalid input
        }
        return sc.nextInt();
    }
}
