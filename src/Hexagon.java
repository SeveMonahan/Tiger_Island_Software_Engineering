import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Hexagon {
    private int level;
    private Terrain terrain;
    public int tileHashCode;
    private int population;
    private ArrayList<Piece> pieces;
    private HexagonOccupationStatus occupationStatus;

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
        pieces.add(piece);
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

    //TODO need to replace below function with something more polymorphic... e.g. canPieceBeKilled
    //TODO you can use the canThisBeKilled() function in Piece to check this
    boolean containsTotoro() {
        for(Piece piece : getPieces()){
           if(piece.getOccupyStatus() == HexagonOccupationStatus.Totoro){
               return true;
           }
        }
        return false;
    }

    // TODO this function assumes that the piece elimination is valid... need to check we aren't killing Totoro
    public void eliminatePieces() {
        occupationStatus = HexagonOccupationStatus.empty;
        population = 0;
    }



}
