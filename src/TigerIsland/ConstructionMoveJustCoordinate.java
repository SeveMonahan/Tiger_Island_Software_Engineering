package TigerIsland;

abstract class ConstructionMoveJustCoordinate implements ConstructionMoveInternal {
    Coordinate coordinate;

    ConstructionMoveJustCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
    }
}
