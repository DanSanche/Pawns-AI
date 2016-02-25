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
            //if there are no other options, return the value of this state
            TerminalNode lastNode = new TerminalNode(this.rootState, this.isBlack);
            return lastNode.findBestOption(depth);
        }
    }
}
