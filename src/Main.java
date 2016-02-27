import java.util.Scanner;

public class Main {

    private static Scanner reader;

    public static void main(String [] args){
        int turns = 0;
        int boardSize = 5;
        int depth = 3;
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
            turns++;
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
        //print out winner information
        System.out.println("\n\n\n----------------");
        currentState.renderState();
        switch(currentState.gameCompletionState()){
            case White_More_Pawns:
            case White_Reached_End:
                System.out.println("\nPwned!!");
                System.out.println("White Wins!");
                break;
            case Black_More_Pawns:
            case Black_Reached_End:
                System.out.println("\nPwned!!");
                System.out.println("Black Wins!");
                break;
            case Stalemate:
                System.out.println("Stalemate");
                break;
            default:
                break;
        }
        System.out.println(turns + " turns used");
    }
    
    private static Boolean isValidInput(String input){
        return (input.equals("yes") || input.equals("y") || input.equals("Y") || 
                input.equals("no") || input.equals("n") || input.equals("N")); 
    }

}
