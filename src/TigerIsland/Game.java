package TigerIsland;

public class Game {
    private Player player_1;
    private Player player_2;
    private Player playerWhoseTurn;
    private boolean gameIsOver;

    public Game() {
        player_1 = new Player(Color.WHITE);
        player_2 = new Player(Color.BLACK);
        playerWhoseTurn = player_1;
        gameIsOver = false;
    }

    public Player getPlayerOne(){
        return player_1;
    }

    public Player getPlayerTwo(){
        return player_2;
    }

    public boolean isMyTurn(Player me) {
        return me == playerWhoseTurn;
    }

    public void changeTurn() {
        if (playerWhoseTurn == player_1)
            playerWhoseTurn = player_2;
        else
            playerWhoseTurn = player_1;
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
            if (playerScoreIsLessThanOpponent(player_1, player_2)) {
                winner = player_2;
            } else if (playerScoreIsLessThanOpponent(player_2, player_1)) {
                winner = player_1;
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