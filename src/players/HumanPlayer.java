package players;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import models.BoardState;
import models.Pawn;

public class HumanPlayer extends Player {

    private Scanner reader;

    /**
     * creates a new human player
     * @param isBlack represents whether the player is controlling the black team
     */
    public HumanPlayer(Boolean isBlack) {
        super(isBlack);
        reader = new Scanner(System.in);
    }
    
    /**
     * Runs a turn for the player
     * Since it's a human player, this function will ask the player which pawn it wants to move
     * @param currentState - the current state of the game
     * @return an object representing the new game state
     */
    public BoardState runTurn(BoardState currentState){ 
        Collection<Integer> pawnOptions = currentState.renderPawnOptions(this);
        System.out.println("Which pawn do you want to move?: ");
        int pawnIdx = reader.nextInt();
        Pawn selectedPawn = null;
        List<Integer> moveOptions = null;
        while(selectedPawn==null){
            if(!pawnOptions.contains(new Integer(pawnIdx-1))){
                System.out.println("Please enter a number representing a pawn on the board:");
                pawnIdx = reader.nextInt();
            } else {
                selectedPawn = this.getPawnList().get(pawnIdx-1);
                moveOptions = currentState.nextOptionsForPawn(selectedPawn);
                if(moveOptions.size()==0){
                    selectedPawn = null;
                    pawnOptions = currentState.renderPawnOptions(this);
                    System.out.println("No moves available for that pawn. Select another:");
                    pawnIdx = reader.nextInt();
                }
            }
        }
        
        int moveIdx = -1;
        if(moveOptions.size() == 1){
            moveIdx = 1;
        } else {
            currentState.renderMoveOptions(this, selectedPawn);
            System.out.println("Which move do you want to make?:");
            moveIdx = reader.nextInt();
            while(moveIdx < 1 || moveIdx > moveOptions.size()){
                System.out.println("Please enter a number representing an available move:");
                moveIdx = reader.nextInt();
            }
        }
        
        BoardState newState = new BoardState(currentState, selectedPawn, moveOptions.get(moveIdx-1));
        return newState;
    }

}
