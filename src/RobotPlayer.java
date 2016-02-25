import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RobotPlayer extends Player {
    
    protected int depth;
    
    public RobotPlayer(int numPawns, Boolean isBlack, int depth) {
        super(numPawns, isBlack);
        this.depth = depth;
    }

    public BoardState runTurn(BoardState currentState){
        List<BoardState> options = this.findAllValidMoves(currentState);
        
        int highestIdx = 0;
        int maxScore = -1;
        for(int i=0; i<options.size(); i++){
            BoardState thisState = options.get(i);
            int thisScore = thisState.evaluateValueForPlayer(this);
            if(thisScore > maxScore){
                highestIdx = i;
                maxScore = thisScore;
            }
        }
        System.out.println("highest score: " + maxScore);
        return options.get(highestIdx);
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
