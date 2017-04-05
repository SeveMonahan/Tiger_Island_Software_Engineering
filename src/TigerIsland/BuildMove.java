package TigerIsland;


public class BuildMove {

    private final BuildOption buildOption;
    private final Coordinate coordinate;
    private final Terrain terrain;

    public BuildOption getBuildOption() { return buildOption; }
    public Coordinate getCoordinate() { return coordinate; }
    public Terrain getTerrain() {return terrain;}

    public BuildMove(BuildOption buildOption, Coordinate coordinate) {
        this.buildOption = buildOption;
        this.coordinate = coordinate;
        this.terrain = null;
    }

    //The constructor for when the move is an expansion and a terrain must be provided
    public BuildMove(BuildOption buildOption, Coordinate coordinate, Terrain terrain) {
        this.buildOption = buildOption;
        this.coordinate = coordinate;
        this.terrain = terrain;
    }

}
