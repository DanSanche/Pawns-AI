import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    
    private ArrayList<ArrayList<Pawn>> spacesArray;

    public Board(int boardSize, Player firstTeam, Player secondTeam) {
        //create board spaces
        this.spacesArray = new ArrayList<ArrayList<Pawn>>(boardSize);
        for(int i=0; i<boardSize; i++){
            ArrayList<Pawn> thisRow = new ArrayList<Pawn>(boardSize);
            this.spacesArray.add(i, thisRow);
        }
        
        //set up pawns in initial positions
        List<Pawn> firstList = firstTeam.getPawnList();
        Iterator<Pawn> it = firstList.iterator();
        ArrayList<Pawn> firstRow = spacesArray.get(0);
        while(it.hasNext()){
            Pawn thisPawn = it.next();
            firstRow.add(thisPawn);
        }
        
        List<Pawn> secondList = secondTeam.getPawnList();
        it = secondList.iterator();
        ArrayList<Pawn> lastRow = spacesArray.get(boardSize-1);
        while(it.hasNext()){
            Pawn thisPawn = it.next();
            lastRow.add(thisPawn);
        }
        
    }

    //returns a string representation of the board
    public String toString(){
        String boardString = "";
        for(int i=0; i<spacesArray.size(); i++){
            for(int j=0; j<spacesArray.size(); j++){
                Pawn thisPawn = peekPawnAtPosition(i, j);
                String pawnString = ".";
                if (thisPawn != null){
                    pawnString = thisPawn.toString();
                }
                boardString = boardString + pawnString + " ";
            }
            boardString = boardString + "\n";
        }
        return boardString;
    }
    
    //returns a reference to the pawn at a position, or null if there is none
    public Pawn peekPawnAtPosition(int row, int col){
        try{
            return this.spacesArray.get(row).get(col);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }
    
    
}
