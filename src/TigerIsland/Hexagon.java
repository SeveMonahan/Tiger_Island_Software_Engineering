package TigerIsland;

public class Hexagon {
    private int level;
    private Terrain terrain;
    public int tileHashCode;
    private int population;
    private HexagonOccupationStatus occupationStatus;
    private Color occupationColor;
    private boolean canBeNuked;

    public Hexagon() {
        canBeNuked = true;
        level = 0;
        terrain = Terrain.EMPTY;
        occupationStatus = HexagonOccupationStatus.empty;
    }
    private void incrementLevel(){
        level++;
    }

    public int getLevel(){
        return level;
    }

    public Terrain getTerrain(){
        return terrain;
    }

    public HexagonOccupationStatus getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(Piece piece) {
        occupationStatus = piece.getOccupyStatus();
        population = piece.populationRequirements(this);
        occupationColor = piece.getPieceColor();
        this.canBeNuked = piece.canBeKilled();
    }

    public Color getOccupationColor() { return occupationColor; }

    //TODO: Should below function call eliminatePieces() ?
    public void changeTerrainTypeThoughExplosion(Terrain new_terrain){
        terrain = new_terrain;
        incrementLevel();
        eliminatePieces();
    }

    public boolean isVolcanoHex(){
        if(this.terrain == Terrain.VOLCANO)
            return true;
        else
            return false;
    }

    public boolean containsUnKillablePiece() {
        return !this.canBeNuked;
    }

    private void eliminatePieces() {
        occupationStatus = HexagonOccupationStatus.empty;
        population = 0;
        occupationColor = null;
    }
}
