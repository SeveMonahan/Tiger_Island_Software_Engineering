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

    public void setScoreAfterPiecePlacement(Piece piece, Hexagon hexagon) {score += piece.getPointsAfterPlacement(hexagon);}
    public void setScore(int newScore) { this.score = newScore; }
    public void setAutoLoseScore(){ this.score = -1; }

    public boolean attemptPieceMove(ConstructionMoveTransmission constructionMove, Board board){
        switch(constructionMove.getBuildOption()){
            case BUILDTIGER:
                return placeTotoroOnHexagon(constructionMove.getCoordinate(), board);
            case BUILDTOTORO:
                return placeTotoroOnHexagon(constructionMove.getCoordinate(), board);
            case EXPANDSETTLEMENT:
                // TODO impliment this.
                return false;
            case FOUNDSETTLEMENT:
                return placeMeepleOnHexagon(constructionMove.getCoordinate(), board);
            case UNABLETOBUILD:
                return true;
        }
        return false;
    }

    // TODO rewrite this to return a boolean from attemptToPlacePiece so we know if it actually happened
    public boolean placeMeepleOnHexagon(Coordinate coordinate, Board board) {
        Piece newPiece = new Meeple(this.color);
        return attemptToPlacePiece(newPiece, coordinate, board);
    }

    // TODO rewrite this to return a boolean from attemptToPlacePiece so we know if it actually happened
    public boolean placeTotoroOnHexagon(Coordinate coordinate, Board board) {
        TotoroConstructionMove totoroMove = new TotoroConstructionMove(coordinate);

        int neededTotoros = totoroMove.isValidPlace(this.color, board);

        if(neededTotoros < totoroCount){
           totoroCount -= neededTotoros;
           int scoreIncrease = totoroMove.makeValidMoveAndReturnPointsGained(this.color, board);
           score += scoreIncrease;
           return true;
        }

        return false;

    }

    // TODO rewrite this to return a boolean from attemptToPlacePiece so we know if it actually happened
    public boolean placeTigerOnHexagon(Coordinate coordinate, Board board) {
        Piece newPiece = new Tiger(this.color);
        return attemptToPlacePiece(newPiece, coordinate, board);
    }

    public boolean attemptToPlacePiece(Piece piece, Coordinate coordinate, Board board) {
        boolean placementValid = piece.isPlacementValid(coordinate, board);
        Hexagon hexagon = board.getHexagon(coordinate);
        if(placementValid) {
            hexagon.setOccupationStatusTakingPieceClass(piece);
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
        } else if (piece instanceof Tiger ){
            tigerCount -= piecesPlaced;
        }

    }

    public boolean placeSettlement(Coordinate coordinate, Board board) {
        Piece meeple = new Meeple(this.color);
        Hexagon hexagon = board.getHexagon(coordinate);
        if (hexagon.getLevel() == 1) {
            if (attemptToPlacePiece(meeple, coordinate, board)) {
                return true;
            }
        }
        return false;
    }
}