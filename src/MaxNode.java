import java.util.Iterator;
import java.util.List;

public class MaxNode extends GameNode {

    public MaxNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
    }

    public int findBestOption(int depth){
        List<BoardState> successorOptions = this.findSuccessorStates();
        Iterator<BoardState> it = successorOptions.iterator();
        int maxVal = 0;
        while(it.hasNext()){
            BoardState nextState = it.next();
            MinNode nextNode = new MinNode(nextState, !this.isBlack);
            int nextCost = nextNode.findBestOption(depth-1);
            if(nextCost > maxVal){
                maxVal = nextCost;
                this.bestOption = nextState;
            }
        }
        return maxVal;
    }
}
