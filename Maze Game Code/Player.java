public class Player {
    double health;
    double stamina;
    int currentRow;
    int currentCol;
    int LVL;
    String item1;
    String item2;
    String killer;
    public void setBaseStats() {            //default stats loaded at beginning of program
        health = 100;
        stamina = 100;
        currentRow = 0;
        currentCol = 0;
        LVL = 2;
        item1 ="";
        item2 ="";
        killer ="";
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
    public void takeDamage(double damage) {
        health -= damage;
        if (health <= 0) {
            System.out.println("Player has died.");
            // Additional death handling logic here, if needed
        } else {
            System.out.println("Player takes " + damage + " damage, health now: " + health);
        }
    }
    public boolean isAlive() {
        return health > 0;
    }
    public void useStamina(double amount) {
        if (stamina >= amount) {
            stamina -= amount;
            System.out.println("Used " + amount + " stamina, remaining stamina: " + stamina);
        } else {
            System.out.println("Not enough stamina to perform this action!");
        }
    }

    public void restoreStamina(double amount) {
        stamina += amount;
        if (stamina > 100) stamina = 100;
        System.out.println("Stamina restored to: " + stamina);
    }

    public void restoreHealth(double amount) {
        health += amount;
        if (health > 100) health = 100;
        System.out.println("Player health restored by " + amount + ", total health: " + health);
    }
}
