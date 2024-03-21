import java.io.BufferedReader;      //for titletext.txt
import java.io.FileReader;          //titletext.txt
import java.util.Scanner;           //The scanner, the majestic scanner
public class Main {
    public static Player Pl = new Player();     //stats found in Player.java
    public static GameData Gm = new GameData();     //Use of functions in GameData.java
    public static char[][] maze = null;
    public static void loadMaze() {
        switch (Pl.currentLvl()) {
            case 1:
                maze = new char[][] {           //level 1
                        {'█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '-', '█',},
                        {'█', '-', '-', '-', '-', '█', '█', '-', '█', '-', '█', '█', '-', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '-', '-', '█', '-', '-', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '-', '-', '█', '█', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '-', '-', '-', '-', '-', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '█', '-', '█', '█', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '-', '█', '-', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '-', '-', '█', '-', '█', '-', '█', '-', '-', '-', '-', '█',},
                        {'█', '-', '-', '-', '█', '-', '█', '-', '█', '-', '█', '█', '-', '█',},
                        {'█', '█', '█', '█', '█', '-', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '-', '-', '-', '-', '█', '-', '-', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '-', '█', '-', '█', '-', '-', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '-', '█', '█', '█', '█', '█', '█',},
                        {'█', '-', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█',}

                };
                break;
            case 2:
                maze = new char[][] {       //test level (Change Pl.LVL to 2 if you want to use this)
                        {'.', '.', '.', '█'},
                        {'█', '.', '.', '.'},
                        {'█', '█', '.', '.'},
                        {'█', '█', '█', '.'}
                };
                break;
        }
        for (int i = 0; i < maze.length; i++) {     //prints the selected maze
            for (int j = 0; j < maze[0].length; j++) {
                if (i == Pl.currentRow && j == Pl.currentCol) {
                    System.out.print("P");
                }
                else {
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
    }
    public static void validPosition(char choice) {
        switch (choice) {
            case 'A':           //A (Left)
                if (Pl.currentCol - 1 >= 0 && Gm.passable(maze, Pl,0,-1)) {
                    Pl.currentCol -= 1;
                }
                break;
            case 'W':           //W (Up)
                if (Pl.currentRow - 1 >= 0 && Gm.passable(maze, Pl,-1,0)) {
                    Pl.currentRow -= 1;
                }
                break;
            case 'S':           //S (Down)
                if (Pl.currentRow + 1 < maze.length && Gm.passable(maze, Pl,1,0)) {
                    Pl.currentRow += 1;
                }
                break;
            case 'D':           //D (Right)
                if (Pl.currentCol + 1 < maze[0].length && Gm.passable(maze, Pl,0,1)) {
                    Pl.currentCol += 1;
                }
                break;
        }
    }
    public static void game() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            loadMaze();         //loads maze
            System.out.println("Health: " + Pl.health + "%\t\tStamina: " + Pl.stamina + "%");        //Displays HP and Stamina
            //System.out.println("Coords: ("+ Pl.currentRow + "),(" + Pl.currentCol + ")");
            System.out.println("Enter your choice, or press A,W,S,D to move");
            System.out.println("[1] Mass Move");
            System.out.println("[2] Items");
            System.out.println("[3] Options (Temp Exit)");
            String c = sc.nextLine().toUpperCase();
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
                    System.out.println("Items!");           //placeholder
                    break;
                case '3':           //placeholder
                    return;
            }
        }
    }
    public static void main(String[] args) {
        String fileName = "titletext.txt";
        Pl.setBaseStats();
        while (true) {
            Scanner sc = new Scanner(System.in);
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
