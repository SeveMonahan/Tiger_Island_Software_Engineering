public class Game {
    public Player player1;
    public Player player2;
    private Player playerWhoseTurn;
    protected Player winner;

    Game() {
        player1 = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        playerWhoseTurn = player1;
    }

    public Player getTurn(){
        return playerWhoseTurn;
    }

    public void changeTurn(){
        if(playerWhoseTurn == player1)
            playerWhoseTurn = player2;
        else
           playerWhoseTurn = player1;
    }
    public void endGameConditions() {
        if( player1.getScore() == -1 || player2.getScore() > player1.getScore() ) {
            winner = player2;
        } else if( player2.getScore() == -1 || player1.getScore() > player2.getScore() ) {
            winner = player1;
        } else { // Tie game
            winner = null;
        }
    }

}
