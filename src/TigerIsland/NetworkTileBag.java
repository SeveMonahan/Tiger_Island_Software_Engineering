package TigerIsland;

public class NetworkTileBag implements TileBag {
    private int numberOfTilesInBag;
    private String gid;

    @Override
    public int getNumberOfTilesInBag() {
        return numberOfTilesInBag;
    }

    // Constructors
    public NetworkTileBag(String gid) {
        this.gid = gid;
        numberOfTilesInBag = 48;
    }

    // Methods
    @Override
    public Tile drawTile() {
        System.out.println(this.gid + " attempts to draw a tile.");
        numberOfTilesInBag--;
        // Note: This is a consequence of me not knowing how to make these functions polymorphic.
        return new Tile(Terrain.EMPTY, Terrain.EMPTY);
    }
}
