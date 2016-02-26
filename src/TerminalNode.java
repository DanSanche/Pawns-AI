import java.util.Iterator;

public class TerminalNode extends GameNode {

    public TerminalNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
    }
    
    public int findBestOption(int depth, int alpha, int beta){
        int stateValue = 0;
        //if the game is over, we can assign a max or a min value depending on the result
        GameCompletion state = this.rootState.gameCompletionState();
        if(state == GameCompletion.Black_More_Pawns || state == GameCompletion.Black_Reached_End){
            if(this.isBlack){
                stateValue =100;
            } else {
                stateValue = 0;
            }
        } else if(state == GameCompletion.White_More_Pawns || state == GameCompletion.White_Reached_End){
            if(!this.isBlack){
                stateValue = 100;
            } else {
                stateValue = 0;
            }
        } else if (state == GameCompletion.Stalemate){
            stateValue = 0;
        } else {
            //if no winner was decided, return the utility value of the state
            stateValue = this.findUtilityValue();
        }
        return stateValue;
    }
    
    public int findUtilityValue(){
        int maxLevel = 0;
        int enemyMaxLevel = 0;
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
            } else {
                int thisLevel = pos / this.rootState.boardSize;
                //black pawns start at bottom and move to top. Invert this level
                if(nextPawn.isBlackTeam()){
                    thisLevel = Math.abs(this.rootState.boardSize - (thisLevel+1));
                }
                if(thisLevel > enemyMaxLevel){
                    enemyMaxLevel = thisLevel;
                }
            }
        }
        return maxLevel + Math.abs(this.rootState.boardSize - (enemyMaxLevel+1));
    }

}
