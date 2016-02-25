import java.util.Collection;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(int numPawns, Boolean isBlack) {
        super(numPawns, isBlack);
    }
    
    public void runTurn(BoardState currentState){
        Scanner reader = new Scanner(System.in);
        
        Collection<Integer> validOptions = currentState.renderPawnOptions(this);;
        System.out.println("Which pawn do you want to move?: ");
        int pawnIdx = reader.nextInt();
        while(!validOptions.contains(new Integer(pawnIdx-1))){
            System.out.println("Please enter a number representing a pawn on the board:");
            pawnIdx = reader.nextInt();
        }
    }

}
