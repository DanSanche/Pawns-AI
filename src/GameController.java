
public class GameController {
    
    private Team firstTeam;
    private Team secondTeam;
    private Board gameBoard;

    public GameController(int boardSize) {
        setupFreshGame(boardSize);
    }

    
    public void setupFreshGame(int boardSize){
        this.firstTeam = new Team(boardSize);
        this.secondTeam = new Team(boardSize);
        this.gameBoard = new Board(boardSize, this.firstTeam, this.secondTeam);
    }
    
    public void render(){
        System.out.print(this.gameBoard.toString());
    }
}
