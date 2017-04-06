package TigerIsland;


public class ConstructionMoveTransmission {

    private final BuildOption buildOption;
    private final Coordinate coordinate;

    public BuildOption getBuildOption() { return buildOption; }
    public Coordinate getCoordinate() { return coordinate; }

    public ConstructionMoveTransmission(BuildOption buildOption, Coordinate coordinate) {
        this.buildOption = buildOption;
        this.coordinate = coordinate;
    }

}
