import java.util.Random;

public class CarsonTron2000 extends C4Bot{
    CarsonTron2000(int playerID, int enemyID,int playable, int empty, int[][] board){
        super(playerID,enemyID,playable,empty,board);
    }
    public int update(){
        Random random = new Random();
        return random.nextInt(0,board[0].length);
    }
}
