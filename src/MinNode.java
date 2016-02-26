import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class MinNode extends GameNode {

    public MinNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
    }
    
    public int findBestOption(int depth){
        List<BoardState> successorOptions = this.findSuccessorStates();
        if(!successorOptions.isEmpty()){
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
            this.bestOption = this.rootState;
            return nextNode.findBestOption(depth-1);
        }
    }
    
    public void printTree(int depth, Queue<GameNode>printQueue){
        List<BoardState> successors = this.findSuccessorStates();
        Iterator<BoardState> it = successors.iterator();
        while(it.hasNext()){
            BoardState nextState = it.next();
            MaxNode nextNode = new MaxNode(nextState, !this.isBlack);
            printQueue.add(nextNode);
            nextNode.printTree(depth-1, printQueue);
        }
    }
}
