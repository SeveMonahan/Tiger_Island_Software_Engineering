Feature: Meeple

#Meeple scenario test
Scenario: Meeples of initialized player
    Given I have initialized player
    When I query that player's meeples
    Then that player has 20 meeples

Scenario: Populate on Level 1 Hexagon
    Given I have an initialized board
	And I place a tile on it
	And I have an initialized player
    When I attempt to populate a Level 1 non-volcano hexagon
    Then I place 1 meeple on the hexagon
	And the player now has 1 point
	And the player has 19 meeples

Scenario: Populate on Level 2 Hexagon
    Given I have an initialized board
	And I placed at least one Level 2 non-volcanic hexagon on it
	And I have an initialized player
    When I attempt to populate a Level 2 non-volcano hexagon
    Then I place 2 meeples on the hexagon
	And the player now has 4 points
	And the player now has 18 meeples

Scenario: Populate fails due to Volcano
    Given I have an initialized board
	And I have placed a tile on it
	And I an initialized player
    When I attempt to populate a volcano hexagon
    Then the operation fails
	And the player still has 0 points
	And no meeples are placed on the hexagon
	And the player still has 20 meeples

Scenario: Attempt populate on Level 0 Hexagon
    Given I have an initialized board
	And I have an initialized player
    When I attempt to populate a hexagon
    Then the operation fails
	And the player still has 0 points
	And no meeples are placed on the hexagon
	And the player still has 20 meeples

Scenario: Meeples killed by tile placement
    Given I have initialized a board
	And placed two tiles adjacent to each other with two touching hexagons across tiles
	And the tiles have adjacent volcanoes
	And There are meeples on each non-volcano hexagon
    When I attempt to place a tile on a volcano so it overlaps onto the other tile
    Then The operation should be successful
	And The level of each hexagon which was overwritten should be 1
	And The terrain type of each hexagon which was overwritten should be that of the corresponding tile hexagon
	And All meeples on the overwritten hexagons should be removed
	And The properties of the other hexagons should be unchanged
	And Your score does not change as a result of meeples being killed




