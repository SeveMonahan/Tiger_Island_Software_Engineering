public class Player {

    private int score;
    private int meepleCount;
    private int totoroCount;
    private boolean turn;
    private Color color;

    Player(Color color) {
        score = 0;
        meepleCount = 20;
        totoroCount = 3;
        this.color = color;
        this.turn = true;
    }
    Player(Color color, boolean turn) {
        score = 0;
        meepleCount = 20;
        totoroCount = 3;
        this.color = color;
        this.turn = turn;
    }

    public int getScore() { return score; }
    public int getMeeplesCount() { return meepleCount; }
    public int getTotoroCount() { return totoroCount; }
    public boolean getTurn(){return turn;}
    public Color getColor() { return color; }

    public void setScoreAfterPiecePlacement(Piece piece, Hexagon hexagon) {score += piece.getPointsAfterPlacement(hexagon);}
    public void setScore(int newScore) { this.score = newScore; }
    public void setAutoLoseScore() { this.score = -1; }
    public void setTurn(boolean turn) { this.turn = turn; }

    public boolean hexIsVolcano(Hexagon hexagon) { return (hexagon.getTerrain() == Terrain.VOLCANO); }

    public boolean hexDoesntExist(Hexagon hexagon){
        return hexagon.getLevel() == 0;
    }

    // TODO finish moving this to Piece
    public boolean isPiecePlacementValid(Piece piece, Hexagon hexagon) {
        if( !hexagon.isVolcanoHex() ) {
            return true;
        } else {
            return false;
        }
    }

    // TODO rewrite this to return a boolean from attemptToPlacePiece so we know if it actually happened
    public void placeMeepleOnHexagon(Hexagon hexagon) {
        Piece newPiece = new Meeple(this.color);
        attemptToPlacePiece(newPiece, hexagon);
    }

    public void placeTotoroOnHexagon(Hexagon hexagon) {
        Piece newPiece = new Totoro(this.color);
        attemptToPlacePiece(newPiece, hexagon);
    }

    public boolean attemptToPlacePiece(Piece piece, Hexagon hexagon) {
        boolean placementValid = piece.isPlacementValid(hexagon);
        if(placementValid) {
            hexagon.setOccupationStatus(piece);
            deductPlacedPieces(piece, hexagon);
            setScoreAfterPiecePlacement(piece, hexagon);
        }
        return placementValid;
    }

    // TODO Make this properly polymorphic
    public void deductPlacedPieces(Piece piece, Hexagon hexagon) {
        int piecesPlaced = piece.populationRequirements(hexagon);
        if(piece instanceof Meeple) {
            meepleCount -= piecesPlaced;
        } else if (piece instanceof Totoro) {
            totoroCount -= piecesPlaced;
        }
    }

}
