import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MaxNode extends GameNode {
    
    private BoardState bestState;

    public MaxNode(BoardState rootState, Boolean playerIsBlack, int depth, int alpha, int beta) {
        super(rootState, playerIsBlack, depth, alpha, beta);
        this.printPrefix = "+";
    }
    
    public BoardState finalState(){
        return bestState;
    }

    protected int findBestOption(int depth,  int alpha, int beta){
        this.childStates = new LinkedList<GameNode>();
        int result;
        List<BoardState> successorOptions = this.findSuccessorStates();
        if(this.isBlack){
            Collections.sort(successorOptions, BoardState.Comparators.BLACK_DESCENDING);
        } else {
            Collections.sort(successorOptions, BoardState.Comparators.WHITE_DESCENDING);
        }
        
        if(!successorOptions.isEmpty()){
            //we have options. Find the max of them
            Iterator<BoardState> it = successorOptions.iterator();
            while(it.hasNext() && alpha<beta){
                BoardState nextState = it.next();
                MinNode nextNode = new MinNode(nextState, this.isBlack, depth-1, alpha, beta);
                int nextCost = nextNode.nodeValue;
                if(nextCost > alpha){
                    alpha = nextCost;
                    bestState = nextState;
                    this.childStates.add(nextNode);
                }
            }
            result = alpha;
        } else {
            GameNode nextNode;
            GameCompletion state = this.rootState.gameCompletionState();
            if(state == GameCompletion.Game_Ongoing){
                //we are stuck, but our opponent isn't. The game isn't over. Let them make a move
                nextNode = new MinNode(this.rootState, this.isBlack, depth-1, alpha, beta);
            } else {
                //the game is complete. Create a terminal node to calculate our costs
                nextNode = new TerminalNode(this.rootState, this.isBlack, depth-1, alpha, beta);
            }
            this.childStates.add(nextNode);
            result = nextNode.nodeValue;
        }
        return result;
    }
}
