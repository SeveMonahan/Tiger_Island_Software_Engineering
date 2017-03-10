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

    public int getScore() {
        return score;
    }
    public int getMeeplesCount() { return meepleCount; }
    public int getTotoroCount() { return totoroCount; }
    public boolean getTurn(){return turn;}


    public void setScoreAfterPiecePlacement(Piece piece, Hexagon hexagon) {score += piece.getPointsAfterPlacement(hexagon);}
    public void setScore(int newScore) {
        this.score = newScore;
    }
    public void setAutoLoseScore() {
        this.score = -1;
    }
    public void setTurn(boolean turn){this.turn = turn;}

    public boolean hexIsVolcano(Hexagon hexagon){
        if (hexagon.getTerrain() == Terrain.VOLCANO)
            return true;
        else return false;
    }

    public boolean hexDoesntExist(Hexagon hexagon){
        if(hexagon.getLevel() == 0)
            return true;
        else return false;
    }

    public boolean placementIsValidOnHexagon(Hexagon hexagon) {
        boolean placementIsValid = true;
        if (hexIsVolcano(hexagon)) {
            placementIsValid = false;
        }
        if (hexDoesntExist(hexagon)) {
            placementIsValid = false;
        }
        return placementIsValid;
    }

    public boolean isPiecePlacementValid(Piece piece, Hexagon hexagon) {
        // TODO Check that settlement >5 and not containg Totoro (if Piece == Totoro)
        // TODO Have to check somewhere that we have sufficient pieces
        if( !hexagon.isVolcanoHex() ) {
            return true;
        } else {
            return false;
        }
    }

    public void placeMeepleOnHexagon(Hexagon hexagon) {
        Piece newPiece = new Meeple(this.color);
        attemptToPlacePiece(newPiece, hexagon);
    }

    public void placeTotoroOnHexagon(Hexagon hexagon) {
        Piece newPiece = new Totoro(this.color);
        attemptToPlacePiece(newPiece, hexagon);
    }

    // TODO finish this function to check if Totoro placement is valid
    public boolean attemptToPlacePiece(Piece piece, Hexagon hexagon) {
        boolean placementValid = isPiecePlacementValid(piece, hexagon);
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
