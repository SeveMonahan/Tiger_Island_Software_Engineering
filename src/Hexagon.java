import java.util.ArrayList;

class Hexagon {
    private int level;
    private Terrain terrain;
    public int tileHashCode;
    private int population;
    private ArrayList<Piece> pieces;

    Hexagon() {
        level = 0;
        terrain = Terrain.EMPTY;
        pieces = new ArrayList<Piece>();
    }

    int getLevel(){
        return level;
    }
    Terrain getTerrain(){
        return terrain;
    }
    ArrayList<Piece> getPieces() { return pieces; }

    private void incrementLevel(){
        level++;
    }

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

    void increasePopulation(int byPopulationOf){
        population += byPopulationOf;
    }

    void addPiece(Piece piece) {
        this.pieces.add(piece);
    }
    void eliminatePieces() { this.pieces = new ArrayList<Piece>(); }

    boolean containsTotoro() {
        boolean containsTotoro = false;
        if (this.getPieces().size() != 0) {
            if (this.getPieces().get(0).getPieceType() == PieceType.TOTORO) {
                containsTotoro = true;
            }
        }
        return containsTotoro;
    }

    int getPopulation(){
        return this.population;
    }
}
