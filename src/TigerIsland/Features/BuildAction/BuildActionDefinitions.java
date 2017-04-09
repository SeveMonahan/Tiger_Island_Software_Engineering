package BuildAction;

import TigerIsland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;


public class BuildActionDefinitions {
    Board board = null;
    Hexagon targetHexagon = null;
    Player player = null;
    private boolean isValid;

    @Given("^A board with a tile placed on it$")
    public void initBoard() {
        board = new Board();
        board.placeStartingTile();
    }

    @Given("^I have initialized a player in BuildActionDefinitions$")
    public void initializePlayer() {
        player = new Player(Color.BLACK);
    }

    @When("^I attempt to build a settlement on a non-volcano tile$")
    public void attemptToBuildSettlementNonVolcano() {
        Coordinate coordinate = new Coordinate(100,100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        isValid = player.foundSettlement(coordinate, board);
        Assert.assertEquals(true, isValid);

        targetHexagon = board.getHexagonAt(coordinate);
    }

    @Then("^A meeple is placed on that hexagon$")
    public void meeplePlacedOnHexagon() {
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagon.getPiecesStatus());
    }

    @When("^I attempt to build a settlement on a volcano tile$")
    public void attemptToBuildSettlementVolcano() {
        Coordinate coordinate = new Coordinate(101, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);

        player = new Player(Color.BLACK);

        isValid = player.foundSettlement(coordinate, board);
        Assert.assertEquals(false, isValid);
    }

    @Given("^each non-volcano tile has a meeple on it$")
    public void populateAllAvailableLocations() {
        Coordinate coordinate = new Coordinate(100,100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);

        player.foundSettlement(coordinate, board);

        player.foundSettlement(new Coordinate(100, 100), board);
    }

    @Given("^A board with a tile placed on it, and all hexagons set to Level 2$")
    public void initBoardWithLevel2Tiles() {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        board = boardWithTile;
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));
    }

    @When("^I attempt to build a settlement on a level 2 non-volcano tile$")
    public void attemptToStartLevel2Settlement() {
        player = new Player(Color.WHITE);

        player.foundSettlement(new Coordinate(100, 100), board);
    }

    @Then("^The tile place fails$")
    public void tilePlaceFails() {
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
    }

    @Then("^The tile place fails, but there is still a meeple in the target Hexagon$")
    public void tilePlaceFailsButMeepleInTargetHexagon() {
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagon.getPiecesStatus());
    }

    @When("^I attempt to build a settlement on an occupied non-volcano tile$")
    public void attemptToPlaceOnOccupiedHex() {
        player = new Player(Color.WHITE);
        player.foundSettlement(new Coordinate(100,100), board);
    }

/*

Scenario: Build new settlement fails because there are already meeples on the hexagon
    // And each non-volcano tile has a meeple on it
    // When I attempt to build a settlement on a non-volcano tile
    // Then The operation fails

Scenario: Build new settlement fails because there are already another player's meeples on hexagon
    Given A board with a tile placed on it
    And each non-volcano tile has a meeple from a different player on it
    When I attempt to build a settlement on a non-volcano tile
    Then The operation fails

Scenario: Develop settlement fails because no valid adjacent hexagon selected
    Given A board with a volcano, beach, and rock hexagons, and a meeple on the rock hexagon
    When I attempt to develop settlement choosing the meeple settlement and "Jungle"
    Then The operation fails

Scenario: Develop settlement places meeples in exactly one territory
    Given A board with a volcano, beach, and rock hexagons, and a meeple on the rock hexagon
    And the hexagons are all level 0
    When I attempt to develop settlement choosing the meeple settlement and "Rock"
    Then A meeple is placed on the rock hexagon
    And exactly one meeple is depleted from the player's supply

Scenario: Develop settlement places meeples in two adjacent territories
    Given A board with a volcano, beach, and rock hexagons on a tile
    And Volcano, jungle, and rock on an adjacent tile.
    And The adjacent tile is placed such the rock hexagon touches the old rock & beach hexagons
    And the jungle hexagon touches the beach hexagon
    And there is a meeple on the beach hexagon
    When I attempt to develop settlement choosing the meeple settlement and "rock"
    Then A meeple is placed on each rock hexagon
    And exactly two meeple are depleted from the player's supply

Scenario: Develop settlement places meeples in two territories, one using flood fill
    Given A board with a volcano, beach, and rock hexagons on a tile
    And Volcano, jungle, and rock on an adjacent tile.
    And The adjacent tile is placed such the rock hexagon touches the old rock hexagon
    And there are no other adjacencies between tiles
    And there is a meeple on the beach hexagon
    When I attempt to develop settlement choosing the meeple settlement and "rock"
    Then A meeple is placed on each rock hexagon
    And exactly two meeple are depleted from the player's supply

Scenario: Develop settlement fails due to insufficient meeples
    Given A board with a volcano, beach, and rock hexagons on a tile
    And Volcano, jungle, and rock on an adjacent tile.
    And The adjacent tile is placed such the rock hexagon touches the old rock & beach hexagons
    And the jungle hexagon touches the beach hexagon
    And there is a meeple on the beach hexagon
    And the player has exactly one meeple left
    When I attempt to develop settlement choosing the meeple settlement and "rock"
    Then The operation fails

Scenario: Build Totoro fails because there is not a big enough settlement
    Given A board with a tile placed on it
    When I attempt to place a Totoro on a non-volcano hexagon
    Then The operation fails

Scenario: Attempt to place Totoro on volcano hexagon
    Given A board with a tile placed on it
    When I attempt to place a Totoro on a volcano hexagon
    Then The operation fails

Scenario: Build Totoro fails because there is not a big enough settlement adjacent
    Given A board with 8 non-volcano hexagons connected in a straight line
    And The first five from some index have meeples from the same player's
    When I attempt to place a Totoro on the last hexagon from that index
    Then The operation fails

Scenario: Build Totoro fails because there is already a Totoro in an adjacent settlement
    Given A board with 6 non-volcano hexagons connected in a straight line
    And The first five from some index have meeples from the same player
    And The next hexagon has a totoro from the same player
    When The player attempts to add another totoro adjacent to this settlement
    Then The operation fails

Scenario: Build Totoro is successful
    Given A board with 8 non-volcano hexagons connected in a straight line
    And The first five from some index have meeples from the same player's
    When I attempt to place a Totoro on the sixth hexagon from that index
    Then The sixth Hexagon now has a Totoro on it
    And the player now has one less Totoro
    And the player has 200 more points

Scenario: One player uses all of their pieces
    Given A player has 1 Meeples and 0 Totoros
    When That player places their last meeple
    Then The game is immediately over and the scores are compared

Scenario: One player uses all of their pieces
    Given A player has 0 Meeples and 1 Totoros
    And There is a board with 6 connection non-volcano spaces on it
    And 5 of those have the player’s meeples on them
    When That player places their last Totoro
    Then The game is immediately over and the scores are compared
    Then The operation should fail
    */
}