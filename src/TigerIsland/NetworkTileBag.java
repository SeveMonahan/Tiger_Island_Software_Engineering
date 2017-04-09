package TigerIsland;

import java.util.LinkedList;

public class NetworkTileBag implements TileBag {

    private LinkedList<Tile> bag;
    private int numberOfTilesInBag;
    private PostMan tileBagPostMan;
    private String gameID;

    public NetworkTileBag(PostMan postMan, String gameID) {
        bag = new LinkedList<Tile>();
        this.tileBagPostMan = postMan;
        this.gameID = gameID;
    }

    @Override
    public Tile drawTile() {
        Tile tile = tileBagPostMan.accessTileMailBox(gameID);
        while(tile == null){
            try {
                tileBagPostMan.wait();
                tile = tileBagPostMan.accessTileMailBox(gameID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        numberOfTilesInBag--;
        return this.bag.pop();
    }

    public LinkedList<Tile> getAllTilesInBag() {
        return this.bag;
    }
    @Override
    public int getNumberOfTilesInBag() {
        return numberOfTilesInBag;
    }
}
