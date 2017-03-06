import org.junit.Test;

public class BoardAddingTilesTest {
    @Test
    public void ConstructorUsingTile(){
        Board TestBoard = new Board(new Tile(Terrain.JUNGLE, Terrain.ROCK));
    }
}
