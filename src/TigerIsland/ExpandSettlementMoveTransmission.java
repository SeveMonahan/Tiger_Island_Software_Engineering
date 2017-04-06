package TigerIsland;


public class ExpandSettlementMoveTransmission extends ConstructionMoveTransmission{
    private Terrain terrain;

    public Terrain getTerrain(){
        return terrain;
    }

    public ExpandSettlementMoveTransmission(BuildOption buildOption, Coordinate coordinate, Terrain terrain) {
        super(buildOption, coordinate);
        this.terrain = terrain;
    }

}
