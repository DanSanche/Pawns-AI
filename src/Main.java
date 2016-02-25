
public class Main {

    public static void main(String [] args){
        int boardSize = 5;
        //initialize game
        Player firstPlayer = new HumanPlayer(boardSize, false);
        Player secondPlayer = new HumanPlayer(boardSize, true);
        BoardState currentState = new BoardState(boardSize, firstPlayer, secondPlayer);
        
        //run the game loop
        Boolean gameComplete = false;
        while(!gameComplete){
            firstPlayer.runTurn(currentState);
            if(!gameComplete){
                secondPlayer.runTurn(currentState);
            }
        }
    }

}
