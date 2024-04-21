public class Player {
    double health;
    double stamina;
    double staminaRegen;
    int currentRow;
    int currentCol;
    int LVL;
    String item1;
    String item2;
    String killer;
    public void setBaseStats() {            //default stats loaded at beginning of program
        health = 100;
        stamina = 100;
        staminaRegen = 3;
        currentRow = 0;
        currentCol = 0;
        LVL = 1;
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
            System.out.println("\n\nPlayer takes " + damage + " damage, health now: " + health);
            // Additional death handling logic here, if needed
        } else {
            System.out.println("\n\nPlayer takes " + damage + " damage, health now: " + health);
        }
    }
    public boolean isAlive() {
        return health > 0;
    }
    public void useStamina(double amount) {
        double count = amount;
        if (stamina >= Math.pow(amount, 2)) {
            stamina -= Math.pow(amount, 2);	
            System.out.println("\nUsed " + Math.pow(amount, 2) + " stamina, remaining stamina: " + stamina);
	}
        else if (stamina <= 0){
		stamina -= Math.pow(amount, 2);	
		takeDamage(amount*2);
            	System.out.println("Used " + Math.pow(amount, 2) + " stamina, remaining stamina: " + stamina);
		System.out.println("Stamina is too low, you are taking damage from exhaustion");			
		
	}else{
		while((stamina - Math.pow(count, 2))<=0){ //creates a counter for the amount of spaces the player traveled without stamina
			if (count == 1)	
				break;
			count--;
				
		}
			
		count = amount - count;	
		count--;
		stamina -= Math.pow(amount, 2);
            	takeDamage(count*2);			
           	System.out.println("Used " + Math.pow(amount, 2) + " stamina, remaining stamina: " + stamina);
           	System.out.println("Stamina is too low, you are taking damage from exhaustion");
        }
    }

    public void restoreStamina(double amount) {
        if(stamina < 100){
		stamina += amount;
        if (stamina > 100) stamina = 100;
        	System.out.println(amount + " Stamina restored");
		}
	}

    public void restoreHealth(double amount) {
        health += amount;
        if (health > 100) health = 100;
        System.out.println("Player health restored by " + amount + ", total health: " + health);
    }
}
