import java.util.ArrayList;
import java.util.Random;

public class CarsonTron2000 extends C4Bot{
    int turn = 0;
    CarsonTron2000(int playerID, int enemyID,int playable, int empty, int[][] board){
        super(playerID,enemyID,playable,empty,board);
    }
    public int update(){
        int spaceToPlace = 0;
        if(turn==0)
        {
            for(int i=1;i<board[0].length;i++)
            {
                if(board[board.length-1][i]==playable)
                {
                    spaceToPlace=i;
                    break;
                }
            }
        }
        else if(turn==1)
        {
            for(int i=board[0].length-2;i>=0;i--)
            {
                if(board[board.length-1][i]==playable)
                {
                    spaceToPlace=i;
                    break;
                }
            }
        }
        else
        {
            spaceToPlace=find_best_space();
            if(board[0][spaceToPlace]!=playable&&board[0][spaceToPlace]!=empty)
            {
                Random random = new Random();
                return random.nextInt(0,board[0].length);
            }
        }
        turn++;
        return spaceToPlace;
    }
    public int find_best_space(){
        int highestSpace = 0;
        ArrayList<int[]> foundSpaces = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                for (int directionX = -1; directionX < 2; directionX++) {
                    for (int directionY = -1; directionY < 2; directionY++) {
                        //The x to check.
                        int checkX = x;
                        //The y to check.
                        int checkY = y;
                        //The amount of correct tiles found in this line.
                        int foundInThisLine = 0;
                        //If we wouldn't move, then continue.
                        if (directionY == 0 && directionX == 0) {
                            continue;
                        }
                        // While our current player is at the check location, keep looking for more
                        while (board[checkY][checkX] == playerID || board[checkY][checkX] == playable) {
                            foundInThisLine++;
                            checkX += directionX;
                            checkY += directionY;
                            if (checkX < 0 || checkX > board.length - 1 || checkY < 0 || checkY > board.length - 1) {
                                break;
                            }
                            if(board[checkY][checkX] == playable)
                            {
                                int[] returnValue = {checkY,checkX,foundInThisLine};
                                foundSpaces.add(returnValue);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(foundSpaces.size()==0)
        {
            Random random = new Random();
            return random.nextInt(0,board[0].length);
        }
        int biggestSpace = 0;
        for(int i=0;i<foundSpaces.size();i++)
        {
            if(foundSpaces.get(i)[2]<foundSpaces.get(biggestSpace)[2])
            {
                biggestSpace=i;
            }
        }
        return foundSpaces.get(biggestSpace)[2];
    }
}
