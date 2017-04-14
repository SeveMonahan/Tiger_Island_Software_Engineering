package TigerIsland;

public class NetworkTileBag implements TileBag {
    private int numberOfTilesInBag;
    private PostMan tileBagPostMan;
    private String gameID;

    public NetworkTileBag(PostMan postMan, String gameID) {
        this.tileBagPostMan = postMan;
        this.gameID = gameID;
        numberOfTilesInBag = 48;
    }

    @Override
    public Tile drawTile() {
        numberOfTilesInBag--;
        return tileBagPostMan.accessTileMailBox(gameID);
    }

    @Override
    public int getNumberOfTilesInBag() {
        return numberOfTilesInBag;
    }
}
