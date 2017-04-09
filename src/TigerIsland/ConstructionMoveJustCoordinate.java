package TigerIsland;

abstract class ConstructionMoveJustCoordinate implements ConstructionMoveInternal {
    Coordinate coordinate;

    ConstructionMoveJustCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    String get_coordinate_marshalling(){
        int coordinateNumbers[] = coordinate.ConvertToCube();
        String result = "";

        for(int i = 0; i < coordinateNumbers.length; i++){
            result += " " + coordinateNumbers[i];
        }

        return result;
    }

    protected abstract String getMoveTypeName();

    @Override
    public String marshallMove() {
        return this.getMoveTypeName() + get_coordinate_marshalling();
    }
}
