package TigerIsland;

public class Hexagon {
    // Members
    private int level;
    private Terrain terrain;
    private int tileHashCode;
    private PieceStatusHexagon piecesStatus;
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
    public PieceStatusHexagon getPiecesStatus() {
        return piecesStatus;
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
        piecesStatus = move.getOccupyStatus();
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
        piecesStatus = PieceStatusHexagon.EMPTY;
    }
    private Hexagon(Hexagon copiedHexagon) {
        canBeNuked = copiedHexagon.getCanBeNuked();
        level = copiedHexagon.getLevel();
        terrain = copiedHexagon.getTerrain();
        piecesStatus = copiedHexagon.getPiecesStatus();
        tileHashCode = copiedHexagon.getTileHashCode();
        occupationColor = copiedHexagon.getOccupationColor();
    }

    // Methods
    public boolean isVolcano() {
        return terrain == Terrain.VOLCANO;
    }
    public boolean containsPieces() {
        return piecesStatus != PieceStatusHexagon.EMPTY;
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
        piecesStatus = PieceStatusHexagon.EMPTY;
        occupationColor = null;
    }
}
