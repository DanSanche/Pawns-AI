
public class GameController {
    
    private Player firstPlayer;
    private Player secondPlayer;
    private BoardState gameBoard;

    public GameController(int boardSize) {
        setupFreshGame(boardSize);
    }

    
    public void setupFreshGame(int boardSize){
        this.firstPlayer = new HumanPlayer(boardSize, false);
        this.secondPlayer = new HumanPlayer(boardSize, true);
        this.gameBoard = new BoardState(boardSize, this.firstPlayer, this.secondPlayer);
    }
    
    public void startGame(){
        Boolean gameComplete = false;
        while(!gameComplete){
            firstPlayer.runTurn(this.gameBoard);
            if(!gameComplete){
                secondPlayer.runTurn(this.gameBoard);
            }
        }
    }
}
