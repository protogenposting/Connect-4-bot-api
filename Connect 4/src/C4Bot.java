import java.util.Random;
import java.util.random.RandomGenerator;

public class C4Bot {
    String name = "BAKERTRON 2000";
    int playerID;
    int enemyID;
    int playable;
    int empty;
    public int[][] board;
    C4Bot(int playerID, int enemyID, int playable, int empty, int[][] board){
        this.playerID = playerID;
        this.enemyID = enemyID;
        this.playable = playable;
        this.empty = empty;
        this.board = board;
    }
    int xValue=0;
    public int update(){
        Random random = new Random();
        return random.nextInt(0,board[0].length);
        /*xValue++;
        if(xValue>=board.length)
        {
            xValue=0;
        }
        return xValue;*/
    }
}
