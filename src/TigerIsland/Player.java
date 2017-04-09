package TigerIsland;

public class Player {
    // Members
    private int score;
    private int meepleCount;
    private int totoroCount;
    private int tigerCount;
    private Color color;

    // Getters
    public int getScore() { return score; }
    public int getMeeplesCount() { return meepleCount; }
    public int getTotoroCount() { return totoroCount; }
    public int getTigerCount() { return tigerCount; }
    public Color getColor() { return color; }

    public static Player clonePlayer(Player player) {
        return new Player(player);
    }

    // Setters
    public void setScore(int newScore) { this.score = newScore; }

    // Constructors
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

    // Methods
    public void addScore(int points) {
        this.score += points;
    }
    public void setAutoLoseScore() { this.score = -1; }

    public void subtractTotoro() {
        totoroCount--;
    }
    public void subtractTiger() {
        tigerCount--;
    }
    public void subtractMeeples(int num) {
        meepleCount -= num;
    }

    public boolean buildTotoroSanctuary(Coordinate coordinate, Board board) {
        TotoroConstructionMove totoroMove = new TotoroConstructionMove(coordinate);

        if(totoroMove.canPerformMove(this, board)){
           totoroMove.makePreverifiedMove(this, board);
           return true;
        }
        return false;
    }
    public boolean buildTigerPlayground(Coordinate coordinate, Board board) {
        TigerConstructionMove tigerMove = new TigerConstructionMove(coordinate);

        if(tigerMove.canPerformMove(this, board)) {
            tigerMove.makePreverifiedMove(this, board);
            return true;
        }
        return false;
    }
    public boolean foundSettlement(Coordinate coordinate, Board board) {
        FoundSettlementConstructionMove foundMove = new FoundSettlementConstructionMove(coordinate);

        if(foundMove.canPerformMove(this, board)) {
            foundMove.makePreverifiedMove(this, board);
            return true;
        }
        return false;
    }
    public boolean expandSettlement(Coordinate coordinate, Board board, Terrain terrain) {
        ExpandSettlementConstructionMove expandMove = new ExpandSettlementConstructionMove(coordinate, terrain);

        if(expandMove.canPerformMove(this, board)) {
            expandMove.makePreverifiedMove(this, board);
            return true;
        }
        return false;
    }

    boolean triggeredGameEnd() {
        int piecesDepleted = 0;

        if(totoroCount == 0)
            piecesDepleted++;
        if(tigerCount == 0)
            piecesDepleted++;
        if(meepleCount == 0)
            piecesDepleted++;

        return piecesDepleted > 2;
    }
}