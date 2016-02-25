import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RobotPlayer extends Player {
    
    public RobotPlayer(int numPawns, Boolean isBlack) {
        super(numPawns, isBlack);
    }

    public BoardState runTurn(BoardState currentState){
        List<BoardState> options = this.findAllValidMoves(currentState);
        return options.get(0);
    }

    private List<BoardState> findAllValidMoves(BoardState currentState){
        List<BoardState> resultsList = new LinkedList<BoardState>();
        Iterator<Pawn> it = this.getPawnList().iterator();
        while(it.hasNext()){
            Pawn nextPawn = it.next();
            //TODO: possible speed up? It loops through pawn list every time this is called
            try{
                List<Integer> optionsList = currentState.nextOptionsForPawn(nextPawn);
                Iterator<Integer> optionsIt = optionsList.iterator();
                while(optionsIt.hasNext()){
                    Integer thisOption = optionsIt.next();
                    BoardState newState = new BoardState(currentState, nextPawn, thisOption);
                    resultsList.add(newState);
                }
            } catch(RuntimeException e){
                //this pawn is dead
            }
        }
        return resultsList;
    }
}
