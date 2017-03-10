import java.util.ArrayList;

public class Player {

    private int score;
    private int meepleCount;
    private int totoroCount;
    private Color color;

    Player(Color color) {
        score = 0;
        meepleCount = 20;
        totoroCount = 3;
        this.color = color;
    }

    public int getScore() {
        return score;
    }
    public int getMeeplesCount() { return meepleCount; }
    public int getTotoroCount() { return totoroCount; }

    public void setScore(int newScore) {
        this.score = newScore;
    }
    public void setAutoLoseScore() {
        this.score = -1;
    }

    // TODO: This will be used in a 'expandFromHexagonToTerrain' method.
        // That method should check that the Player has enough Meeples to perform the entire expansion.
    public void placeMeepleOnHexagon(Hexagon hexagon) {
        if (placementIsValidOnHexagon(hexagon)) {
            int level = hexagon.getLevel();
            for (int i = 0; i < level; i++) {
                hexagon.addPiece(new Piece(PieceType.MEEPLE, color));
                meepleCount--;
                score += level;
            }
        }
    }
    public boolean placementIsValidOnHexagon(Hexagon hexagon) {
        boolean placementIsValid = true;
        if (hexagon.getTerrain() == Terrain.VOLCANO) {
            placementIsValid = false;
        }
        if (hexagon.getLevel() == 0) {
            placementIsValid = false;
        }
        return placementIsValid;
    }
    // TODO: Check to see that the Totoro is being placed in a valid way.
    public void placeTotoroOnHexagon(Hexagon hexagon) {
        hexagon.addPiece(new Piece(PieceType.TOTORO, color));
        totoroCount--;
        score += 200;
    }
}
