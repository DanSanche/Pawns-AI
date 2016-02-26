import java.util.Iterator;

public class TerminalNode extends GameNode {

    public TerminalNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
        this.bestOption = rootState;
    }
    
    public int findBestOption(int depth){
        //if the game is over, we can assign a max or a min value depending on the result
        GameCompletion state = this.rootState.gameCompletionState();
        if(state == GameCompletion.Black_More_Pawns || state == GameCompletion.Black_Reached_End){
            if(this.isBlack){
                return this.rootState.boardSize;
            } else {
                return 0;
            }
        } else if(state == GameCompletion.White_More_Pawns || state == GameCompletion.White_Reached_End){
            if(!this.isBlack){
                return this.rootState.boardSize;
            } else {
                return 0;
            }
        } else if (state == GameCompletion.Stalemate){
            return 0;
        } else {
            //if no winner was decided, return the utility value of the state
            return this.findUtilityValue();
        }
    }
    
    public int findUtilityValue(){
        int maxLevel = 0;
        Iterator<Integer> it = this.rootState.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer pos = it.next();
            Pawn nextPawn = this.rootState.pawnPositions.get(pos);
            if(nextPawn.isBlackTeam() == isBlack){
                int thisLevel = pos / this.rootState.boardSize;
                //black pawns start at bottom and move to top. Invert this level
                if(nextPawn.isBlackTeam()){
                    thisLevel = Math.abs(this.rootState.boardSize - (thisLevel+1));
                }
                if(thisLevel > maxLevel){
                    maxLevel = thisLevel;
                }
            }
        }
        System.out.println(maxLevel);
        return maxLevel;
    }

}
