import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class MinNode extends GameNode {

    public MinNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
    }
    
    public int findBestOption(int depth,  int alpha, int beta){
        List<BoardState> successorOptions = this.findSuccessorStates();
        if(!successorOptions.isEmpty()){
            Iterator<BoardState> it = successorOptions.iterator();
            while(it.hasNext() && alpha < beta){
                int nextCost;
                BoardState nextState = it.next();
                GameNode nextNode;
                if(depth <= 0){
                    //terminal node. use base cost
                    nextNode = new TerminalNode(nextState, !this.isBlack);
                } else {
                    //more levels. Make another max node
                    nextNode = new MaxNode(nextState, !this.isBlack);
                }
                nextCost = nextNode.findBestOption(depth, alpha, beta);
                if(nextCost < beta){
                    beta = nextCost;
                }
            }
            return beta;
        } else {
            GameNode nextNode;
            GameCompletion state = this.rootState.gameCompletionState();
            if(state == GameCompletion.Game_Ongoing){
                //we are stuck, but our opponent isn't. The game isn't over. Let them make a move
                nextNode = new MaxNode(this.rootState, !this.isBlack);
            } else {
                //the game is complete. Create a terminal node to calculate our costs
                nextNode = new TerminalNode(this.rootState, !this.isBlack);
            }
            return nextNode.findBestOption(depth-1, alpha, beta);
        }
    }
    
}
