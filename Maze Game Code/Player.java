public class Player {
    public double health;
    public double stamina;
    public int currentRow;
    public int currentCol;
    public int LVL;
    public int currentLvl() {
        return LVL;
    }
    public void setBaseStats() {            //default stats loaded at beginning of program
        health = 100;
        stamina = 100;
        currentRow = 14;
        currentCol = 1;
        LVL = 1;
    }
}