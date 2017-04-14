package TigerIsland;

import java.util.LinkedList;

public class NetworkTileBag implements TileBag {
    // Members
    private LinkedList<Tile> bag;
    private int numberOfTilesInBag;
    private PostMan tileBagPostMan;
    private String gameID;

    // Constructors
    public NetworkTileBag(PostMan postMan, String gameID) {
        bag = new LinkedList<Tile>();
        this.tileBagPostMan = postMan;
        this.gameID = gameID;
        numberOfTilesInBag = 48;
    }

    // Methods
    // Do we still need these "Override" keywords? Or can we delete them? -Cameron
    @Override
    public Tile drawTile() {
        System.out.println(this.gameID + " attempts to draw a tile.");
        Tile tile = tileBagPostMan.accessTileMailBox(gameID);

        // TODO: Delete this block.
        while(tile == null){
            try {
                synchronized (tileBagPostMan) {
                    System.out.println(this.gameID + " doesn't have a tile in accessTileMailBox and is waiting");
                    tileBagPostMan.wait();
                    System.out.println(this.gameID + " has been notified by tileBagPostMan there are new tiles availible.");
                    tile = tileBagPostMan.accessTileMailBox(gameID);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assert(tile != null);

        numberOfTilesInBag--;
        return tile;
    }

    public LinkedList<Tile> getAllTilesInBag() {
        return this.bag;
    }
    @Override
    public int getNumberOfTilesInBag() {
        return numberOfTilesInBag;
    }
}
