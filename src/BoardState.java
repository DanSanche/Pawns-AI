import java.util.List;
import java.util.Set;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class BoardState {
    
    private Hashtable<Pawn, Integer> pawnPositions;
    
    private int boardSize;

    public BoardState(int boardSize, Player firstTeam, Player secondTeam) {
        //create board spaces
        this.pawnPositions = new Hashtable<Pawn, Integer>();

        this.boardSize = boardSize;
        
        //set up pawns in initial positions
        List<Pawn> firstList = firstTeam.getPawnList();
        for(int i=0; i<boardSize; i++){
            Pawn thisPawn = firstList.get(i);
            pawnPositions.put(thisPawn, new Integer(i));
        }
        
        List<Pawn> secondList = secondTeam.getPawnList();
        for(int i=0; i<boardSize; i++){
            Pawn thisPawn = secondList.get(i);
            int pawnPos = (boardSize * (boardSize-1)) + i;
            pawnPositions.put(thisPawn, new Integer(pawnPos));
        }
    }
    
    public Collection<Integer> renderPawnOptions(Player selectedPlayer){
        String boardString = "";
        Collection<Integer> validOptions = new LinkedList<Integer>();
        
        ArrayList<String> tileStrings = new ArrayList<String>(this.boardSize * this.boardSize);
        for(int i=0; i<this.boardSize*this.boardSize; i++){
            tileStrings.add(i, ".");
        }
        
        List<Pawn> playerPawns = selectedPlayer.getPawnList();
        for(int i=0; i<playerPawns.size(); i++){
            Pawn thisPawn = playerPawns.get(i);
            Integer pawnPos = this.pawnPositions.get(thisPawn);
            if(pawnPos != null){
                tileStrings.set(pawnPos, Integer.toString(i+1));
                validOptions.add(new Integer(i));
            }
        }
        
        Set<Pawn> allPawns = this.pawnPositions.keySet();
        Iterator<Pawn> it = allPawns.iterator();
        while(it.hasNext()){
            Pawn thisPawn = it.next();
            if(!thisPawn.isOwnedBy(selectedPlayer)){
                Integer pawnPos = this.pawnPositions.get(thisPawn);
                tileStrings.set(pawnPos, thisPawn.toString());   
            }
        }
        
        for(int i=0; i<tileStrings.size(); i++){
            if(i%(this.boardSize) == 0 && i!=0){
                boardString = boardString + "\n";
            }
            String tileValue = tileStrings.get(i);
            boardString = boardString + " " + tileValue + " ";
        }
        System.out.println(boardString);
        return validOptions;
    }  
    
}
