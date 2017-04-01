package TigerIsland;

public class Hexagon {
    // Members
    private int level;
    private Terrain terrain;
    private int tileHashCode;
    private int population;
    private HexagonOccupationStatus occupationStatus;
    private Color occupationColor;
    private boolean canBeNuked;

    // Getters
    public int getLevel(){
        return level;
    }
    public Terrain getTerrain(){
        return terrain;
    }
    public int getTileHashCode() { return tileHashCode; }
    public HexagonOccupationStatus getOccupationStatus() {
        return occupationStatus;
    }
    public Color getOccupationColor() { return occupationColor; }
    public boolean getCanBeNuked() { return canBeNuked; }

    // Setters
    public void setOccupationStatus(Piece piece) {
        occupationStatus = piece.getOccupyStatus();
        population = piece.populationRequirements(this);
        occupationColor = piece.getPieceColor();
        this.canBeNuked = piece.canBeKilled();
    }
    public void setTileHashCode(int tileHashCode) {
        this.tileHashCode = tileHashCode;
    }

    // Constructors
    public Hexagon() {
        canBeNuked = true;
        level = 0;
        terrain = Terrain.EMPTY;
        occupationStatus = HexagonOccupationStatus.EMPTY;
    }

    // Methods
    public boolean isVolcano() {
        if(terrain == Terrain.VOLCANO)
            return true;
        else
            return false;
    }
    public boolean isOccupied() {
        if (occupationStatus != HexagonOccupationStatus.EMPTY) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isEmpty() {
        return !isOccupied();
    }

    private void incrementLevel(){
        level++;
    }
    //TODO: Should below function call eliminatePieces() ?
    public void changeTerrainTypeThoughExplosion(Terrain new_terrain){
        terrain = new_terrain;
        incrementLevel();
        eliminatePieces();
    }
    private void eliminatePieces() {
        occupationStatus = HexagonOccupationStatus.EMPTY;
        population = 0;
        occupationColor = null;
    }
}
