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
                System.out.println("\n\n\nWHITE'S TURN\n----------------");
                currentState = whitePlayer.runTurn(currentState);
            }
            Boolean blackCanMove = currentState.playerCanMove(blackPlayer);
            if(blackCanMove){
                System.out.println("\n\n\nBLACK'S TURN\n----------------");
                currentState = blackPlayer.runTurn(currentState);
            }
            gameComplete = !(whiteCanMove || blackCanMove);
        }
        System.out.println("\n\n\n----------------");
        currentState.renderState();
        System.out.println("\n Game Complete");
        Player winner = currentState.findWinner(blackPlayer, whitePlayer);
        if(winner == null){
            System.out.println("Tie Game");
        } else {
            System.out.println(winner.toString() + " Wins!");
        }
    }

}
