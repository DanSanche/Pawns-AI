import java.util.Iterator;
import java.util.List;

public class MaxNode extends GameNode {

    public MaxNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
    }

    public int findBestOption(int depth){
        int maxScorePossible = this.rootState.boardSize -1;
        List<BoardState> successorOptions = this.findSuccessorStates();
        if(!successorOptions.isEmpty()){
            //we have options. Find the max of them
            Iterator<BoardState> it = successorOptions.iterator();
            int maxVal = 0;
            while(it.hasNext() && maxVal<maxScorePossible){
                BoardState nextState = it.next();
                MinNode nextNode = new MinNode(nextState, !this.isBlack);
                int nextCost = nextNode.findBestOption(depth-1);
                if(nextCost > maxVal){
                    maxVal = nextCost;
                    this.bestOption = nextState;
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
            this.bestOption = this.rootState;
            return nextNode.findBestOption(depth-1);
        }
    }
}
