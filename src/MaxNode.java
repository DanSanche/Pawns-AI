import java.util.Iterator;
import java.util.List;

public class MaxNode extends GameNode {
    
    private BoardState bestState;

    public MaxNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
    }
    
    public BoardState findFinalState(int depth){
        int maxValPossible = 100;
        int best = findBestOption(depth, Integer.MIN_VALUE, maxValPossible);
        System.out.println("max: " + best);
        return bestState;
    }

    public int findBestOption(int depth,  int alpha, int beta){
        int result;
        List<BoardState> successorOptions = this.findSuccessorStates();
        if(!successorOptions.isEmpty()){
            //we have options. Find the max of them
            Iterator<BoardState> it = successorOptions.iterator();
            while(it.hasNext() && alpha<beta){
                BoardState nextState = it.next();
                MinNode nextNode = new MinNode(nextState, !this.isBlack);
                int nextCost = nextNode.findBestOption(depth-1, alpha, beta);
                if(nextCost > alpha){
                    alpha = nextCost;
                    bestState = nextState;
                }
                if(this.debugOn){
                    for(int i=0; i<depth; i++){
                        System.out.print("              ");
                    }
                    System.out.println("max " +depth + ": " + nextCost);
                }
            }
            result = alpha;
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
            result = nextNode.findBestOption(depth-1, alpha, beta);
        }
        return result;
    }
}
