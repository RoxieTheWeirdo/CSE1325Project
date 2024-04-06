public class Player {
    double health;
    double stamina;
    int currentRow;
    int currentCol;
    int LVL;
    String item1;
    String item2;
    public void setBaseStats() {            //default stats loaded at beginning of program
        health = 100;
        stamina = 100;
        currentRow = 0;
        currentCol = 0;
        LVL = 1;
        item1 ="";
        item2 ="";
    }
    public void basePosition(Player Pl, int LVL) {
        if (LVL == 1) {
            Pl.currentRow = 14;
            Pl.currentCol = 1;
        }
        else if (LVL == 2) {
            Pl.currentRow = 0;
            Pl.currentCol = 0;
        }
    }
    //Function must be called each time player takes damage
    public void Death(killer) {    //Checks if player is dead, killer is cause of death
        if(health == 0) {
        System.out.println("\t\tYou have died");
            switch(killer) {
            case wall:
            System.out.println("You have hit the wall too many times");
            break;
            case rollingBall:
            System.out.println("You were crushed by a large rolling ball");
            break;
            case fallingCeilling:
            System.out.println("You were flattened by a falling ceiling");
            break;
            case minotaur:
            System.out.println("You have been mauled by the Minotaur");
            break;
            }
        Scanner continues = new Scanner(System.in);
        System.out.println("\tType ok to continue");
        String okInput = continues.nextLine();
        if(okInput.equals("ok"))
        reset();
        }      
    }
    public void resetPlayer() { //player reset to starting point after death
        System.out.println("\n\nYou awaken and see that you have returned to the same spot where you fell into the maze");
        p1.setBaseStats(p1, p1.LVL);
        game();
        }

}
