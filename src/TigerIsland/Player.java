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

    public int getScore() { return score; }
    public int getMeeplesCount() { return meepleCount; }
    public int getTotoroCount() { return totoroCount; }
    public int getTigerCount() { return tigerCount; }
    public Color getColor() { return color; }

    public void setScoreAfterPiecePlacement(Piece piece, Hexagon hexagon) {score += piece.getPointsAfterPlacement(hexagon);}
    public void setScore(int newScore) { this.score = newScore; }
    public void setAutoLoseScore(){ this.score = -1; }

    // TODO rewrite this to return a boolean from attemptToPlacePiece so we know if it actually happened
    public void placeMeepleOnHexagon(Hexagon hexagon) {
        Piece newPiece = new Meeple(this.color);
        attemptToPlacePiece(newPiece, hexagon);
    }

    // TODO rewrite this to return a boolean from attemptToPlacePiece so we know if it actually happened
    public void placeTotoroOnHexagon(Hexagon hexagon) {
        Piece newPiece = new Totoro(this.color);
        attemptToPlacePiece(newPiece, hexagon);
    }
    // TODO rewrite this to return a boolean from attemptToPlacePiece so we know if it actually happened
    public void placeTigerOnHexagon(Hexagon hexagon) {
        Piece newPiece = new Tiger(this.color);
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
        } else if (piece instanceof Tiger ){
            tigerCount -= piecesPlaced;
        }

    }

    public boolean placeSettlement(Hexagon hexagon) {
        Piece meeple = new Meeple(this.color);
        if( hexagon.getLevel() == 1 ) {
            if( attemptToPlacePiece(meeple, hexagon) ) {
                return true;
            }
        }
        return false;
    }

}