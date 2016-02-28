import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameNode {
    
    protected BoardState rootState;
    protected Boolean isBlack;
    protected List<GameNode> childStates;
    protected int nodeValue;
    protected String printPrefix = "";

    public GameNode(BoardState rootState, Boolean playerIsBlack, int depth, int alpha, int beta) {
        this.rootState = rootState;
        this.isBlack = playerIsBlack;
        this.nodeValue = this.findBestOption(depth, alpha, beta);
    }
    
    public int getNodeValue(){
        return this.nodeValue;
    }
    
    protected int findBestOption(int depth, int alpha, int beta){
        return 0;
    }
    
    protected List<BoardState> findEnemySuccessorStates(){
        return findSuccessorStatesHelper(!this.isBlack);
    }
    
    protected List<BoardState> findSuccessorStates(){
        return findSuccessorStatesHelper(this.isBlack);
    }
    
    private List<BoardState>findSuccessorStatesHelper(Boolean forBlackTeam){
        List<BoardState> resultsList = new LinkedList<BoardState>();
        
        if(this.rootState.gameCompletionState() != GameCompletion.Game_Ongoing){
            return resultsList;
        }
        
        List<Integer> positionList = this.rootState.pawnPositionsForPlayer(forBlackTeam);
        Iterator<Integer> it = positionList.iterator();
        while(it.hasNext()){
            Integer position = it.next();
            Pawn nextPawn = this.rootState.pawnPositions.get(position);

            List<Integer> optionsList = this.rootState.nextOptionsForPawn(nextPawn, position.intValue());
            Iterator<Integer> optionsIt = optionsList.iterator();
            while(optionsIt.hasNext()){
                Integer thisOption = optionsIt.next();
                BoardState newState = new BoardState(this.rootState, nextPawn, thisOption);
                newState.setComparisonPlayer(this.isBlack);
                resultsList.add(newState);
            }
        }
        Collections.sort(resultsList);
        return resultsList;
    }
    
    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + this.printPrefix + this.nodeValue);
        for (int i = 0; i < this.childStates.size() - 1; i++) {
            this.childStates.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (this.childStates.size() > 0) {
            this.childStates.get(this.childStates.size() - 1).print(prefix + (isTail ?"    " : "│   "), true);
        }
    }

   
}