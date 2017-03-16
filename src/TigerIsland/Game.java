package TigerIsland;

public class Game {
    public Player player1;
    public Player player2;
    private Player playerWhoseTurn;
    public Player winner;

    public Game() {
        player1 = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        playerWhoseTurn = player1;
    }

    public Player getTurn() {
        return playerWhoseTurn;
    }

    public void changeTurn() {
        if (playerWhoseTurn == player1)
            playerWhoseTurn = player2;
        else
            playerWhoseTurn = player1;
    }

    public boolean lossCondition(Player player) {
        if (player.getScore() == -1)
            return true;
        else return false;
    }

    public boolean playerScoreIsLessThanOpponent(Player player1, Player opponent) {
        if (player1.getScore() < opponent.getScore())
            return true;
        else return false;
    }

    public void endGameConditions() {
        if (lossCondition(player1)|| playerScoreIsLessThanOpponent(player1,player2)) {
            winner = player2;
        } else if (lossCondition(player2) || playerScoreIsLessThanOpponent(player2,player1)) {
            winner = player1;
        } else { // Tie game
            winner = null;
        }
    }
}