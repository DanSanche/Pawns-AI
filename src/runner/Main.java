package runner;
import java.util.Scanner;

import models.BoardState;
import players.HumanPlayer;
import players.Player;
import players.RobotPlayer;

public class Main {

    private static Scanner reader;

    /**
     * Main function sets up the program, and runs the game
     * Gives each player a turn until a termination state is reached
     */
    public static void main(String [] args){
        long startTime = new java.util.Date().getTime();
        Boolean printTree = false;
        int turns = 0;
        int depth = 5;
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
            whitePlayer = new HumanPlayer(false);
        } else {
            whitePlayer = new RobotPlayer(false, depth, printTree);
        }
        
        do {
            System.out.println("Is the black player a human? (y/n)");
            inputString = reader.next();
        } while(!isValidInput(inputString));
        if(inputString.equals("yes") || inputString.equals("y") || inputString.equals("Y")){
            blackPlayer = new HumanPlayer(true);
        } else {
            blackPlayer = new RobotPlayer(true, depth, printTree);
        }
        
        BoardState currentState = new BoardState(whitePlayer, blackPlayer);
        
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
        long endTime = new java.util.Date().getTime();
        long timeDiff = (endTime - startTime);
        System.out.println(timeDiff/1000.0 + " seconds");
    }
    
    private static Boolean isValidInput(String input){
        return (input.equals("yes") || input.equals("y") || input.equals("Y") || 
                input.equals("no") || input.equals("n") || input.equals("N")); 
    }

}
