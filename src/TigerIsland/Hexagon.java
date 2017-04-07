package TigerIsland;

public class Hexagon {
    // Members
    private int level;
    private Terrain terrain;
    private int tileHashCode;
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
    int getTileHashCode() { return tileHashCode; }
    public HexagonOccupationStatus getOccupationStatus() {
        return occupationStatus;
    }
    public Color getOccupationColor() { return occupationColor; }
    boolean getCanBeNuked() { return canBeNuked; }

    public static Hexagon cloneHexagon(Hexagon copiedHexagon) {
        if (copiedHexagon == null){
            return null;
        }
        return new Hexagon(copiedHexagon);
    }

    // Setters
    public void setOccupationStatus(Color color, ConstructionMoveInternal move) {
        occupationStatus = move.getOccupyStatus();
        occupationColor = color;
        this.canBeNuked = move.canBeKilled();
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
    private Hexagon(Hexagon copiedHexagon) {
        canBeNuked = copiedHexagon.getCanBeNuked();
        level = copiedHexagon.getLevel();
        terrain = copiedHexagon.getTerrain();
        occupationStatus = copiedHexagon.getOccupationStatus();
        tileHashCode = copiedHexagon.getTileHashCode();
        occupationColor = copiedHexagon.getOccupationColor();
    }

    // Methods
    public boolean isVolcano() {
        return terrain == Terrain.VOLCANO;
    }
    public boolean isOccupied() {
        return occupationStatus != HexagonOccupationStatus.EMPTY;
    }

    public void changeTerrainTypeThoughExplosion(Terrain new_terrain){
        terrain = new_terrain;
        incrementLevel();
        eliminatePieces();
    }
    private void incrementLevel(){
        level++;
    }
    private void eliminatePieces() {
        occupationStatus = HexagonOccupationStatus.EMPTY;
        occupationColor = null;
    }
}
