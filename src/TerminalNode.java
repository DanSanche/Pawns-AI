import java.util.Iterator;

public class TerminalNode extends GameNode {

    public TerminalNode(BoardState rootState, Boolean playerIsBlack) {
        super(rootState, playerIsBlack);
        this.bestOption = rootState;
    }
    
    public int findBestOption(int depth){
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
