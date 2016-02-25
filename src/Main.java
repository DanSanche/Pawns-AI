
public class Main {

    public static void main(String [] args){
        int boardSize = 5;
        //initialize game
        Player whitePlayer = new HumanPlayer(boardSize, false);
        Player blackPlayer = new HumanPlayer(boardSize, true);
        BoardState currentState = new BoardState(boardSize, whitePlayer, blackPlayer);
        
        //run the game loop
        Boolean gameComplete = false;
        while(!gameComplete){
            Boolean whiteCanMove = currentState.playerCanMove(whitePlayer);
            if(whiteCanMove){
                currentState = whitePlayer.runTurn(currentState);
            }
            Boolean blackCanMove = currentState.playerCanMove(blackPlayer);
            if(blackCanMove){
                currentState = blackPlayer.runTurn(currentState);
            }
            gameComplete = !(whiteCanMove || blackCanMove);
        }
        System.out.println("Game Complete");
    }

}
