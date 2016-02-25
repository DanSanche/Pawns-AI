import java.util.Iterator;
import java.util.List;

public class MinNode extends GameNode {

    public MinNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
    }
    
    public int findBestOption(int depth){
        List<BoardState> successorOptions = this.findSuccessorStates();
        Iterator<BoardState> it = successorOptions.iterator();
        int minVal = Integer.MAX_VALUE;
        while(it.hasNext()){
            int nextCost;
            BoardState nextState = it.next();
            GameNode nextNode;
            if(depth == 0){
              //terminal node. use base cost
                nextNode = new TerminalNode(nextState, !this.isBlack);
            } else {
                //more levels. Make another max node
                nextNode = new MaxNode(nextState, !this.isBlack);
            }
            nextCost = nextNode.findBestOption(depth);
            if(nextCost < minVal){
                minVal = nextCost;
                this.bestOption = nextState;
            }
        }
        return minVal;
    }
}
