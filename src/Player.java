import java.util.ArrayList;

public class Player {

    private int score;
    private ArrayList<Piece> meeples;
    private ArrayList<Piece> totoro;

    Player() {
        meeples = new ArrayList<Piece>();
        totoro = new ArrayList<Piece>();
        for (int i = 0; i < 20; i++) {
            meeples.add(new Piece(PieceType.MEEPLE));
        }
        for (int i = 0; i < 3; i++) {
            totoro.add(new Piece(PieceType.TOTORO));
        }
        score = 0;
    }

    public int getScore() {
        return score;
    }
    public int getMeeplesCount() { return meeples.size(); }
    public int getTotoroCount() { return totoro.size(); }

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
                hexagon.addPiece(meeples.get(0));
                meeples.remove(meeples.size() - 1);
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
        hexagon.addPiece(totoro.get(0));
        totoro.remove(totoro.size() - 1);
        score += 200;
    }
}
