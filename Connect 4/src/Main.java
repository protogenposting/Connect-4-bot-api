import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    // the grid types are the different objects that can be on a specific grid space
    public static GridType[] gridTypes = {
            new GridType("Empty", " ", false),
            new GridType("PlayableSpace", "X", false),
            new GridType("Spunchbop", "S", true),
            new GridType("Patrice", "P", true),
    };
    public static int gridXSize = 3;

    public static int gridYSize = 3;

    public static void main(String[] args) {
        // the board stores all the objects
        int[][] board = new int[gridYSize][gridXSize];
        // current player is the current grid type that we will place
        int currentPlayer = 0;
        // funny scanner :3
        Scanner console = new Scanner(System.in);

        boolean playerHasWon = false;

        // main game loop :D
        while(!playerHasWon) {
            //if the current grid type we're using is not a playable one, move up until we hit one that is.
            while(!gridTypes[currentPlayer].isPlayable)
            {
                currentPlayer++;
                if(currentPlayer>=gridTypes.length)
                {
                    currentPlayer=0;
                }
            }

            boolean hasEmpty = false;
            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board[y].length; x++) {
                    if(board[y][x]==getGridTypeID(" "))
                    {
                        hasEmpty=true;
                        break;
                    }
                }
            }
            if(!hasEmpty)
            {
                playerHasWon=true;
                System.out.println("WHAT??? A DRAW???");
                continue;
            }

            get_valid_places(board);

            print_board(board);

            System.out.println(gridTypes[currentPlayer].name+"'s turn... gimme an X value!");

            //get the x input
            int xInput = Integer.parseInt(console.nextLine());

            // set y input to 0, since we don't really need that
            int yInput = 0;

            //is invalid is used to break the program if it is an invalid input
            boolean isInvalid=false;

            //if we're out of bounds, reset the turn
            if(xInput<0||xInput>=gridXSize)
            {
                System.out.println("INVALID LOCATION DO IT AGAIN SLAG");
                continue;
            }

            // If the current grid type isn't an "X" (a playable tile), push down until it is.
            // If we go all the way to the bottom then it'll be invalid.
            while(board[yInput][xInput]!=getGridTypeID("X"))
            {
                yInput++;
                if(yInput>=gridYSize)
                {
                    yInput--;
                    isInvalid=true;
                    break;
                }
            }

            //if the current grid piece is an "X", we did good! If not, we're invalid!
            if(board[yInput][xInput]==getGridTypeID("X"))
            {
                board[yInput][xInput]=currentPlayer;
            }
            else {
                isInvalid=true;
            }

            //reset if invalid
            if(isInvalid)
            {
                System.out.println("INVALID LOCATION DO IT AGAIN SLAG");
                continue;
            }

            //DON'T SHOW THIS TO DANI THEY WILL DIE
            //This checks for winning tiles.
            for(int playerChecked = 0; playerChecked<gridTypes.length;playerChecked++) {
                // if the current grid type is not playable CONTINUE
                if(!gridTypes[playerChecked].isPlayable)
                {
                    continue;
                }
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
                                while(board[checkY][checkX]==playerChecked)
                                {
                                    foundInThisLine++;
                                    checkX+=directionX;
                                    checkY+=directionY;
                                    if(checkX<0||checkX>gridXSize-2||checkY<0||checkY>gridYSize-2)
                                    {
                                        break;
                                    }
                                }
                                //If we found 4 in a row, we got a winner!
                                if(foundInThisLine>=4)
                                {
                                    playerHasWon = true;
                                    System.out.println(gridTypes[playerChecked].name+" has won!");
                                    print_board(board);
                                }
                            }
                        }
                    }
                }
            }

            //Move up the player!
            currentPlayer++;
            if(currentPlayer>=gridTypes.length)
            {
                currentPlayer=0;
            }
        }
    }

    /**
     * This can be used to get a grid tile ID from just the character.
     * For example, "X" would lead to a playable space.
     * @param tile The tile character to check for.
     * @return int
     */
    public static int getGridTypeID(String tile){
        for(int i=0;i<gridTypes.length;i++)
        {
            if(tile.equals(gridTypes[i].tile))
            {
                return i;
            }
        }
        return -1;
    }
    /**
     * Prints the board in the console.
     * @param board The board to print.
     */
    public static void print_board(int[][] board) {
        //Add some space at the start
        System.out.print("   ");
        //Print x locations
        for(int x = 0;x<gridXSize;x++)
        {
            System.out.print(x+" ");
        }
        //Go to next line
        System.out.println();
        for (int y = 0; y < board.length; y++) {
            //Print the Y locations
            System.out.print(y+"| ");
            for (int x = 0; x < board[y].length; x++) {
                //Print the current tile.
                System.out.print(gridTypes[board[y][x]].tile + " ");
            }
            System.out.println();
        }
    }
    /**
     * Sets all valid places that can be placed on to playable spaces.
     * @param board The board to set valid places on.
     */
    public static void get_valid_places(int[][] board) {
        //Reset all playable tiles to empty tiles
        for (int y = board.length - 1; y >= 0; y--) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == getGridTypeID("X")) {
                    board[y][x] = getGridTypeID(" ");
                }
            }
        }
        //Set valid empty tiles to playables
        for(int x = 0;x<board[0].length;x++)
        {
            for (int y = board.length - 1; y >= 0; y--) {
                if (board[y][x] == getGridTypeID(" ")) {
                    board[y][x] = getGridTypeID("X");
                    break;
                }
            }
        }
    }
}