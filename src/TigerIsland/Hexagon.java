package TigerIsland;

public class Hexagon {
    // Members
    private int level;
    private Terrain terrain;
    private int tileHashCode;
    private PieceStatusHexagon piecesStatus;
    private Color occupationColor;

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

    public static Hexagon cloneHexagon(Hexagon copiedHexagon) {
        if (copiedHexagon == null){
            return null;
        }
        return new Hexagon(copiedHexagon);
    }

    // Setters
    public void setOccupationStatus(Color color, PieceStatusHexagon pieceStatus) {
        this.piecesStatus = pieceStatus;
        occupationColor = color;
    }

    public void setTileHashCode(int tileHashCode) {
        this.tileHashCode = tileHashCode;
    }

    // Constructors
    public Hexagon() {
        level = 0;
        terrain = Terrain.EMPTY;
        piecesStatus = PieceStatusHexagon.EMPTY;
    }
    private Hexagon(Hexagon copiedHexagon) {
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

    boolean getCanBeNuked() { return piecesStatus != PieceStatusHexagon.TOTORO; }

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
