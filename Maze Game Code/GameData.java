public class GameData {
    public boolean passable(char[][] maze, Player Pl,int offsetRow, int offsetCol) {
        if (maze[Pl.currentRow + offsetRow][Pl.currentCol + offsetCol] == '█') {        //if a block should be able to go through
            System.out.println("Invalid Position! (You'd take damage normally)");       //can modify more blocks later (breakable, ball, etc)
            return false;
        }
        return true;
    }
}
//also lets the user be able to go past things they should be able to (like items on ground)