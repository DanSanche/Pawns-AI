import java.util.Iterator;
import java.util.LinkedList;

public class TerminalNode extends GameNode {

    public TerminalNode(BoardState rootState, Boolean playerIsBlack, int depth, int alpha, int beta) {
        super(rootState, playerIsBlack, depth, alpha, beta);
        this.childStates = new LinkedList<GameNode>();
    }
    
    protected int findBestOption(int depth, int alpha, int beta){
        int stateValue = 0;
        //if the game is over, we can assign a max or a min value depending on the result
        GameCompletion state = this.rootState.gameCompletionState();
        if(state == GameCompletion.Black_More_Pawns || state == GameCompletion.Black_Reached_End){
            if(this.isBlack){
                stateValue = UtilityConstants.WIN_VALUE;
            } else {
                stateValue = UtilityConstants.LOSS_VALUE;
            }
        } else if(state == GameCompletion.White_More_Pawns || state == GameCompletion.White_Reached_End){
            if(!this.isBlack){
                stateValue = UtilityConstants.WIN_VALUE;
            } else {
                stateValue = UtilityConstants.LOSS_VALUE;
            }
        } else if (state == GameCompletion.Stalemate){
            stateValue = UtilityConstants.STALEMATE_VALUE;
        } else {
            //if no winner was decided, return the utility value of the state
            stateValue = this.findUtilityValue();
        }
        return stateValue;
    }
    
    /**
     * The utility function attempts to create a score for a player from a certain layout of the board
     * Here, we use number of pawns, because it is a good way to tell who is in the lead.
     * The scale will go between 0 and 2n, where n represents the number of pawns on each team.
     * If both sides have the same number of pawns, the score is n. 
     * If the team this AI controls is winning, the score will be > n
     * 
     * @return the utiity value of the current game state
     */
    public int findUtilityValue(){
        int numPawns = 0;
        int enemyPawns = 0;
        Iterator<Integer> it = this.rootState.pawnPositions.keySet().iterator();
        while(it.hasNext()){
            Integer pos = it.next();
            Pawn nextPawn = this.rootState.pawnPositions.get(pos);
            if(nextPawn.isBlackTeam() == isBlack){
               numPawns = numPawns + 1;
            } else {
              enemyPawns = enemyPawns + 1;
            }
        }

        int diff = UtilityConstants.BOARD_SIZE + ( numPawns - enemyPawns );
        
        return diff;
    }

}
