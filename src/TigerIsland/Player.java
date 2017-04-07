package TigerIsland;

public class Player {
    private int score;
    private int meepleCount;
    private int totoroCount;
    private int tigerCount;
    private Color color;

    public Player(Color color) {
        score = 0;
        meepleCount = 20;
        totoroCount = 3;
        tigerCount = 2;
        this.color = color;
    }

    private Player(Player player) {
        this.score = player.score;
        this.meepleCount = player.meepleCount;
        this.totoroCount = player.totoroCount;
        this.tigerCount  = player.tigerCount;
        this.color = player.color;
    }

    public static Player clonePlayer(Player player) {
        return new Player(player);
    }

    public int getScore() { return score; }
    public int getMeeplesCount() { return meepleCount; }
    public int getTotoroCount() { return totoroCount; }
    public int getTigerCount() { return tigerCount; }
    public Color getColor() { return color; }

    public void addScore(int points) {
        this.score += points;
    }
    public void setScore(int newScore) { this.score = newScore; }
    public void setAutoLoseScore(){ this.score = -1; }

    public void substractTotoro(){
        totoroCount--;
    }

    public void substractTiger(){
        tigerCount--;
    }

    public void subtractMeeples(int num){
        meepleCount = meepleCount - num;
    }

    public boolean placeTotoroOnHexagon(Coordinate coordinate, Board board) {
        TotoroConstructionMove totoroMove = new TotoroConstructionMove(coordinate);

        if(totoroMove.canPreformMove(this, board)){
           totoroMove.makePreverifiedMove(this, board);
           return true;
        }

        return false;

    }

    public boolean placeTigerOnHexagon(Coordinate coordinate, Board board) {
        TigerConstructionMove tigerMove = new TigerConstructionMove(coordinate);

        if(tigerMove.canPreformMove(this, board)){
            tigerMove.makePreverifiedMove(this, board);
            return true;
        }

        return false;
    }

    public boolean placeMeepleOnHexagon(Coordinate coordinate, Board board) {
        FoundSettlementConstructionMove foundMove = new FoundSettlementConstructionMove(coordinate);

        if(foundMove.canPreformMove(this, board)){
            foundMove.makePreverifiedMove(this, board);
            return true;
        }

        return false;
    }
}