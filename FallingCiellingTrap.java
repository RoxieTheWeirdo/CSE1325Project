//FallingCiellingTrap.java


public class FallingCeilingTrap {
    private int counter;
    private boolean isActive;
    private String riddle;
    private String answer;

    public FallingCeilingTrap(String riddle, String answer) {
        this.riddle = riddle;
        this.answer = answer;
        this.counter = 0;
        this.isActive = false;
    }

    public void activateTrap() {
        isActive = true;
        counter = 0;  // Reset counter each time trap is activated
        System.out.println(riddle);
    }

    public void attemptAnswer(String playerAnswer, Player player) {
        if (!isActive) {
            return;
        }

        if (playerAnswer.equalsIgnoreCase(answer)) {
            System.out.println("Correct answer! The ceiling stops.");
            isActive = false;
        } else {
            counter++;
            System.out.println("Wrong answer! The ceiling descends further.");
            if (counter >= 3) {
                System.out.println("The ceiling crushes down! You take damage.");
                player.takeDamage(20);
                isActive = false;
            } else {
                System.out.println("Try again!");
            }
        }
    }
}
// riddles and answers to implememnt into the actual game

//Riddle: "The more of this there is, the less you see. What is it?"
//Answer: "Darkness"

//Riddle: "What has keys but can't open locks?"
//Answer: "A piano"

//Riddle: "im tall when im young and im short when im old. what am i?""
//Answer: "A candle"
