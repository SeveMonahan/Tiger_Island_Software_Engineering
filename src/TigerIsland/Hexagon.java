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

    private Hexagon(Hexagon copied) {
        canBeNuked = copied.getCanBeNuked();
        level = copied.getLevel();
        terrain = copied.getTerrain();
        occupationStatus = copied.getOccupationStatus();
        tileHashCode = copied.getTileHashCode();
        occupationColor = copied.getOccupationColor();
    }

    // Methods
    public boolean isVolcano() {
        return terrain == Terrain.VOLCANO;
    }

    public boolean containsPieces() {
        return occupationStatus != HexagonOccupationStatus.EMPTY;
    }

    private void incrementLevel(){
        level++;
    }

    public void changeTerrainTypeThoughExplosion(Terrain new_terrain){
        terrain = new_terrain;
        incrementLevel();
        eliminatePieces();
    }

    private void eliminatePieces() {
        occupationStatus = HexagonOccupationStatus.EMPTY;
        occupationColor = null;
    }
}
