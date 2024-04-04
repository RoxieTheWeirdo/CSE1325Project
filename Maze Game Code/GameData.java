public class Player {
    double health;
    double stamina;
    int currentRow;
    int currentCol;
    int LVL;
    String item1;
    String item2;
    public void setBaseStats() {            //default stats loaded at beginning of program
        health = 20;
        stamina = 100;
        currentRow = 0;
        currentCol = 0;
        LVL = 2;
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
}
