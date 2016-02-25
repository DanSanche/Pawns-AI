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
        
        List<Integer> positionList = currentState.pawnPositionsForPlayer(this);
        Iterator<Integer> it = positionList.iterator();
        while(it.hasNext()){
            Integer position = it.next();
            Pawn nextPawn = currentState.pawnAtPosition(position);

            List<Integer> optionsList = currentState.nextOptionsForPawn(nextPawn, position.intValue());
            Iterator<Integer> optionsIt = optionsList.iterator();
            while(optionsIt.hasNext()){
                Integer thisOption = optionsIt.next();
                BoardState newState = new BoardState(currentState, nextPawn, thisOption);
                resultsList.add(newState);
            }
        }
        return resultsList;
    }
}
