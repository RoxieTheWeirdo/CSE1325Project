import java.io.BufferedReader;      //for titletext.txt
import java.io.FileReader;          //titletext.txt
import java.util.Scanner;           //The scanner, the majestic scanner
import java.util.Arrays;
public class Main {
    public static Player Pl = new Player();     //stats found in Player.java
    public static GameData Gm = new GameData();     //Use of functions in GameData.java
    public static char[][] currentMaze = null;
    public static char[][] baseMaze = null;
    public static Scanner sc = new Scanner(System.in);
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
                        {'█', '-', '-', '-', '▓', '-', '█', '-', '█', '-', '-', '-', '-', '█',},
                        {'█', '-', '-', '-', '▓', '-', '█', '-', '█', '-', '█', '█', '-', '█',},
                        {'█', '█', '█', '█', '█', '-', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', 'V', '-', '-', '-', '█', 'K', '-', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '-', '█', '█', '█', '█', '█', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█',}

                };
                break;
            case 2:
                baseMaze = new char[][]{       //test level (Change Pl.LVL to 2 if you want to use this)
                        {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '█'},
                        {'-', '-', '▓', '▓', '▓', '-', '-', '-', 'V', '-', '█'},
                        {'-', '-', '▓', '▓', '▓', '-', '-', '-', 'V', '-', '█'},
                        {'█', '-', '▓', '▓', '▓', '-', '-', '-', 'V', '-', '█'},
                        {'█', '-', '-', '-', '-', '-', '-', '-', '-', '-', '█'},
                        {'█', '-', '-', '-', '-', '-', '-', '-', '-', '-', '█'},
                        {'█', '-', '-', '-', '-', 'X', '-', '-', '-', '-', '█'},
                        {'█', 'K', '-', '-', '-', '-', '-', '-', '-', '-', '█'},
                        {'█', 'K', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                        {'█', 'K', '-', '-', '-', '-', '█', 'X', '█', '-', '-'},
                        {'█', '-', '-', '-', '-', '-', '█', '-', '█', '-', '-'}
                };
                break;
        }
        currentMaze = Arrays.copyOf(baseMaze,baseMaze.length);
    }
    public static void loadCurrentMaze() {
        for (int i = 0; i < currentMaze.length; i++) {     //prints the selected maze
            for (int j = 0; j < currentMaze[0].length; j++) {
                if (i == Pl.currentRow && j == Pl.currentCol) {
                    System.out.print("P");
                } else {
                    System.out.print(currentMaze[i][j]);
                }
            }
            System.out.println();
        }
    }
    public static void validPosition(char choice) {
        switch (choice) {
            case 'A':           //A (Left)
                if (Pl.currentCol - 1 >= 0 && Gm.passable(currentMaze, Pl,0,-1)) {
                    Pl.currentCol -= 1;
                }
                break;
            case 'W':           //W (Up)
                if (Pl.currentRow - 1 >= 0 && Gm.passable(currentMaze, Pl,-1,0)) {
                    Pl.currentRow -= 1;
                }
                break;
            case 'S':           //S (Down)
                if (Pl.currentRow + 1 < currentMaze.length && Gm.passable(currentMaze, Pl,1,0)) {
                    Pl.currentRow += 1;
                }
                break;
            case 'D':           //D (Right)
                if (Pl.currentCol + 1 < currentMaze[0].length && Gm.passable(currentMaze, Pl,0,1)) {
                    Pl.currentCol += 1;
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
                Gm.useItem(Pl, Pl.item1, currentMaze, 1);
                break;
            }
            else if (!Pl.item2.isEmpty() && choice == 2) {
                Gm.useItem(Pl, Pl.item2, currentMaze, 2);
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
    public static void game() {
            loadBaseMaze();
        while (true) {
            loadCurrentMaze();         //loads maze
            int overlapItemID = 0;
            System.out.println("Health: " + Pl.health + "%\t\tStamina: " + Pl.stamina + "%");        //Displays HP and Stamina
            //System.out.println("Coords: ("+ Pl.currentRow + "),(" + Pl.currentCol + ")");

            System.out.println("Enter your choice, or press A,W,S,D to move");
            switch (currentMaze[Pl.currentRow][Pl.currentCol]) {
                case 'V':
                    System.out.println(Gm.BlueColor + "You found a Potion of Vigor! Enter 4 to pick it up!" + Gm.ResetColor);
                    overlapItemID = 1;
                    break;
                case 'K':
                    System.out.println(Gm.BlueColor + "You found a key! Enter 4 to pick it up!" + Gm.ResetColor);
                    overlapItemID = 2;
                    break;

            }
            System.out.println("[1] Mass Move");
            System.out.println("[2] Items");
            System.out.println("[3] Options (Temp Exit)");
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
                    validPosition(choice);
                    break;
                case '1':
                    System.out.println("Mass moving!");     //placeholder
                    break;
                case '2':
                    Items();           //placeholder
                    break;
                case '3':           //placeholder
                    return;
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
    }
    public static void main(String[] args) {
        String fileName = "titletext.txt";
        Pl.setBaseStats();
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
            System.out.println("Main Menu");
            System.out.println("Enter your choice (Type the corresponding number, then press enter)");
            System.out.println("[1] Start Game");
            System.out.println("[2] Load Game");
            System.out.println("[3] Settings");
            System.out.println("[4] Quit");
            int choi = validInput(sc);
            switch (choi) {
                case 1:
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
