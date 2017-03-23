package TigerIsland;

public class Hexagon {
    private int level;
    private Terrain terrain;
    public int tileHashCode;
    private int population;
    private HexagonOccupationStatus occupationStatus;
    private Color occupationColor;

    public Hexagon() {
        level = 0;
        terrain = Terrain.EMPTY;
    }
    private void incrementLevel(){
        level++;
    }

    public int getLevel(){
        return level;
    }

    public int getPopulation(){
        return this.population;
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
    }

    public Color getOccupationColor() { return occupationColor; }

    //TODO: Should below function call eliminatePieces() ?
    public void changeTerrainTypeThoughExplosion(Terrain new_terrain){
        terrain = new_terrain;
        incrementLevel();
    }

    public boolean isVolcanoHex(){
        if(this.terrain == Terrain.VOLCANO)
            return true;
        else
            return false;
    }

    public boolean containsUnKillablePiece() {
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
