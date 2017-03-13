import java.util.ArrayList;

class Hexagon {
    private int level;
    private Terrain terrain;
    public int tileHashCode;
    private int population;
    private ArrayList<Piece> pieces;
    private HexagonOccupationStatus occupationStatus;
    private Color occupationColor;

    Hexagon() {
        level = 0;
        terrain = Terrain.EMPTY;
        pieces = new ArrayList<Piece>();
    }
    private void incrementLevel(){
        level++;
    }

    int getLevel(){
        return level;
    }
    int getPopulation(){
        return this.population;
    }
    Terrain getTerrain(){
        return terrain;
    }
    ArrayList<Piece> getPieces() { return pieces; }
    public HexagonOccupationStatus getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(Piece piece) {
        occupationStatus = piece.getOccupyStatus();
        population = piece.populationRequirements(this);
        occupationColor = piece.getPieceColor();
        pieces.add(piece);
    }

    public Color getOccupationColor() { return occupationColor; }

    void changeTerrainTypeThoughExplosion(Terrain new_terrain){
        terrain = new_terrain;
        incrementLevel();
    }

    boolean isVolcanoHex(){
        if(this.terrain == Terrain.VOLCANO)
            return true;
        else
            return false;
    }

    boolean containsUnKillablePiece() {
        if( occupationStatus == HexagonOccupationStatus.Totoro ) {
            return true;
        } else {
            return false;
        }
    }

    public void eliminatePieces() {
        occupationStatus = HexagonOccupationStatus.empty;
        population = 0;
        occupationColor = null;
    }
}
