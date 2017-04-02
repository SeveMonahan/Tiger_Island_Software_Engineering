package TigerIsland;

public class Game {
    private Player player1;
    private Player player2;
    private Player playerWhoseTurn;
    private boolean gameIsOver;

    public Game() {
        player1 = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        playerWhoseTurn = player1;
        gameIsOver = false;
    }

    public Player getPlayerOne(){
        return player1;
    }

    public Player getPlayerTwo(){
        return player2;
    }

    public boolean isMyTurn(Player me) {
        return me == playerWhoseTurn;
    }

    public void changeTurn() {
        if (playerWhoseTurn == player1)
            playerWhoseTurn = player2;
        else
            playerWhoseTurn = player1;
    }

    private boolean playerScoreIsLessThanOpponent(Player player1, Player opponent) {
        return player1.getScore() < opponent.getScore();
    }

    public void setGameIsOver(){
        gameIsOver = true;
    }

    public GameOutcome getGameOutcome(Player me) {
        Player winner;

        if(gameIsOver){
            if (playerScoreIsLessThanOpponent(player1, player2)) {
                winner = player2;
            } else if (playerScoreIsLessThanOpponent(player2, player1)) {
                winner = player1;
            } else { // Tie game
                return GameOutcome.TIE;
            }

            if(winner == me){
                return GameOutcome.WIN;
            }else{
                return GameOutcome.LOSS;
            }
        }

        return GameOutcome.UNDETERMINED;
    }
}