import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(int numPawns, Boolean isBlack) {
        super(numPawns, isBlack);
    }
    
    public BoardState runTurn(BoardState currentState){
        Scanner reader = new Scanner(System.in);
        
        Collection<Integer> pawnOptions = currentState.renderPawnOptions(this);
        System.out.println("Which pawn do you want to move?: ");
        int pawnIdx = reader.nextInt();
        while(!pawnOptions.contains(new Integer(pawnIdx-1))){
            System.out.println("Please enter a number representing a pawn on the board:");
            pawnIdx = reader.nextInt();
        }
        Pawn selectedPawn = this.getPawnList().get(pawnIdx-1);
        
        List<Integer> moveOptions = currentState.renderMoveOptions(this, selectedPawn);
        System.out.println("Which move do you want to make?:");
        int moveIdx = reader.nextInt();
        while(moveIdx < 1 || moveIdx > moveOptions.size()){
            System.out.println("Please enter a number representing an available move:");
            moveIdx = reader.nextInt();
        }
        
        BoardState newState = new BoardState(currentState, selectedPawn, moveOptions.get(moveIdx-1));
        return newState;
    }

}
