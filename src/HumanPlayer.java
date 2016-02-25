import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(int numPawns, Boolean isBlack) {
        super(numPawns, isBlack);
    }
    
    public void runTurn(BoardState currentState){
        Scanner reader = new Scanner(System.in);
        
        currentState.renderPawnOptions(this);;
        
        System.out.println("Which pawn do you want to move?: ");
        int n = reader.nextInt();
    }

}
