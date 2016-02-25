
public class GameController {
    
    private Player firstPlayer;
    private Player secondPlayer;
    private Board gameBoard;

    public GameController(int boardSize) {
        setupFreshGame(boardSize);
    }

    
    public void setupFreshGame(int boardSize){
        this.firstPlayer = new HumanPlayer(boardSize, false);
        this.secondPlayer = new HumanPlayer(boardSize, true);
        this.gameBoard = new Board(boardSize, this.firstPlayer, this.secondPlayer);
    }
    
    public void render(){
        System.out.print(this.gameBoard.toString());
    }
    
    public void startGame(){
        Boolean gameComplete = false;
        while(!gameComplete){
            firstPlayer.runTurn();
            if(!gameComplete){
                secondPlayer.runTurn();
            }
        }
    }
}
