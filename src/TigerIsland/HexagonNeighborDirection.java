package src.TigerIsland;

public enum HexagonNeighborDirection {
    UPPERLEFT,
    UPPERRIGHT,
    LEFT,
    RIGHT,
    LOWERLEFT,
    LOWERRIGHT;

    public HexagonNeighborDirection getNextClockwise(){
        switch(this) {
            case UPPERLEFT:
                return UPPERRIGHT;
            case UPPERRIGHT:
                return RIGHT;
            case RIGHT:
                return LOWERRIGHT;
            case LOWERRIGHT:
                return LOWERLEFT;
            case LOWERLEFT:
                return LEFT;
            case LEFT:
                return UPPERLEFT;
        }

        // Error result
        return HexagonNeighborDirection.LEFT;
    }

    public HexagonNeighborDirection intToDirection(int x) {
        switch (x) {
            case 1:
                return UPPERLEFT;
            case 2:
                return UPPERRIGHT;
            case 3:
                return RIGHT;
            case 4:
                return LOWERRIGHT;
            case 5:
                return LOWERLEFT;
            case 6:
                return LEFT;
        }

        return HexagonNeighborDirection.LEFT;
    }

    public int directionToInt(HexagonNeighborDirection dir) {
        switch (dir) {
            case UPPERLEFT:
                return 1;
            case UPPERRIGHT:
                return 2;
            case RIGHT:
                return 3;
            case LOWERRIGHT:
                return 4;
            case LOWERLEFT:
                return 5;
            case LEFT:
                return 6;
        }
        return 1;
    }
}
