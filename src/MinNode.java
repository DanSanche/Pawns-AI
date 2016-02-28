import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MinNode extends GameNode {

    public MinNode(BoardState rootState, Boolean playerIsBlack, int depth, int alpha, int beta) {
        super(rootState, playerIsBlack, depth, alpha, beta);
        this.printPrefix = "-";
    }
    
    protected int findBestOption(int depth,  int alpha, int beta){
        this.childStates = new LinkedList<GameNode>();
        List<BoardState> successorOptions = this.findEnemySuccessorStates();
        if(!successorOptions.isEmpty()){
            Iterator<BoardState> it = successorOptions.iterator();
            while(it.hasNext() && alpha < beta){
                int nextCost;
                BoardState nextState = it.next();
                GameNode nextNode;
                if(depth <= 0){
                    //terminal node. use base cost
                    nextNode = new TerminalNode(nextState, this.isBlack, depth, alpha, beta);
                } else {
                    //more levels. Make another max node
                    nextNode = new MaxNode(nextState, this.isBlack, depth, alpha, beta);
                }
                this.childStates.add(nextNode);
                nextCost = nextNode.nodeValue;
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
                if(depth <= 0){
                    //no time to keep looking. How good is this sate for our enemy?
                    nextNode = new TerminalNode(this.rootState, this.isBlack, depth-1, alpha, beta);
                } else {
                    //more levels. Let the enemy make a move
                    nextNode = new MaxNode(this.rootState, this.isBlack, depth-1, alpha, beta);
                }
            } else {
                //the game is complete. Create a terminal node to calculate our costs
                nextNode = new TerminalNode(this.rootState, this.isBlack, depth-1, alpha, beta);
            }
            this.childStates.add(nextNode);
            return nextNode.nodeValue;
        }
    }
    
}
