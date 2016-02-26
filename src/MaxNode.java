import java.util.Iterator;
import java.util.List;

public class MaxNode extends GameNode {
    
    private BoardState bestState;

    public MaxNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
    }
    
    public BoardState findFinalState(int depth){
        findBestOption(depth);
        return bestState;
    }

    public int findBestOption(int depth){
        int maxScorePossible = this.rootState.boardSize -1;
        List<BoardState> successorOptions = this.findSuccessorStates();
        if(!successorOptions.isEmpty()){
            //we have options. Find the max of them
            Iterator<BoardState> it = successorOptions.iterator();
            int maxVal = -1;
            while(it.hasNext() && maxVal<maxScorePossible){
                BoardState nextState = it.next();
                MinNode nextNode = new MinNode(nextState, !this.isBlack);
                int nextCost = nextNode.findBestOption(depth-1);
                if(nextCost > maxVal){
                    maxVal = nextCost;
                    bestState = nextState;
                }
            }
            return maxVal;
        } else {
            GameNode nextNode;
            GameCompletion state = this.rootState.gameCompletionState();
            if(state == GameCompletion.Game_Ongoing){
                //we are stuck, but our opponent isn't. The game isn't over. Let them make a move
                nextNode = new MinNode(this.rootState, !this.isBlack);
            } else {
                //the game is complete. Create a terminal node to calculate our costs
                nextNode = new TerminalNode(this.rootState, this.isBlack);
            }
            return nextNode.findBestOption(depth-1);
        }
    }
}
