import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameNode {
    
    protected BoardState rootState;
    protected Boolean isBlack;
    protected BoardState bestOption;

    public GameNode(BoardState rootState, Boolean playerIsBlack) {
        this.rootState = rootState;
        this.isBlack = playerIsBlack;
    }
    
    public int findBestOption(int depth){
        return 0;
    }
    
    protected List<BoardState> findSuccessorStates(){
        List<BoardState> resultsList = new LinkedList<BoardState>();
        
        List<Integer> positionList = this.rootState.pawnPositionsForPlayer(this.isBlack);
        Iterator<Integer> it = positionList.iterator();
        while(it.hasNext()){
            Integer position = it.next();
            Pawn nextPawn = this.rootState.pawnPositions.get(position);

            List<Integer> optionsList = this.rootState.nextOptionsForPawn(nextPawn, position.intValue());
            Iterator<Integer> optionsIt = optionsList.iterator();
            while(optionsIt.hasNext()){
                Integer thisOption = optionsIt.next();
                BoardState newState = new BoardState(this.rootState, nextPawn, thisOption);
                resultsList.add(newState);
            }
        }
        return resultsList;
    }

}