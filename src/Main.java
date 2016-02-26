import java.util.Scanner;

public class Main {

    private static Scanner reader;

    public static void main(String [] args){
        int boardSize = 5;
        int depth = 1;
        reader = new Scanner(System.in);
        Player whitePlayer;
        Player blackPlayer;
        String inputString;
        //initialize game
        do {
            System.out.println("Is the white player a human? (y/n)");
            inputString = reader.next();
        } while(!isValidInput(inputString));

        if(inputString.equals("yes") || inputString.equals("y") || inputString.equals("Y")){
            whitePlayer = new HumanPlayer(boardSize, false);
        } else {
            whitePlayer = new RobotPlayer(boardSize, false, depth);
        }
        
        do {
            System.out.println("Is the black player a human? (y/n)");
            inputString = reader.next();
        } while(!isValidInput(inputString));
        if(inputString.equals("yes") || inputString.equals("y") || inputString.equals("Y")){
            blackPlayer = new HumanPlayer(boardSize, true);
        } else {
            blackPlayer = new RobotPlayer(boardSize, true, depth);
        }
        
        BoardState currentState = new BoardState(boardSize, whitePlayer, blackPlayer);
        
        //run the game loop
        Boolean gameComplete = false;
        while(!gameComplete){
            Boolean whiteCanMove = currentState.playerCanMove(whitePlayer);
            if(whiteCanMove){
                System.out.println("\n\n\nWHITE'S TURN\n----------------");
                currentState = whitePlayer.runTurn(currentState);
            } else {
                System.out.println("White is stuck");
            }
            Boolean blackCanMove = currentState.playerCanMove(blackPlayer);
            if(blackCanMove){
                System.out.println("\n\n\nBLACK'S TURN\n----------------");
                currentState = blackPlayer.runTurn(currentState);
            } else {
                System.out.println("Black is stuck");
            }
            gameComplete = !(whiteCanMove || blackCanMove);
        }
        //print out winner information
        System.out.println("\n\n\n----------------");
        currentState.renderState();
        System.out.println("\nPwned!!");
    }
    
    private static Boolean isValidInput(String input){
        return (input.equals("yes") || input.equals("y") || input.equals("Y") || 
                input.equals("no") || input.equals("n") || input.equals("N")); 
    }

}
