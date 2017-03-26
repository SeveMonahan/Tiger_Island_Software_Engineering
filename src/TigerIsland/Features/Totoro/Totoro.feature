Feature: Totoro

#Totoro scenario test

Scenario: Totoros of initialized player
    Given I have initialized player
    When I query that player's Totoros
    Then that player has 3 Totoros

Scenario: Totoro stops tile placement
    Given I have initialized a board
	And placed two tiles adjacent to each other with two touching hexagons across tiles
	And the tiles have adjacent volcanoes
	And There is a Totoro on a non-volcano hexagon which is adjacent to both volcanos
    When I attempt to place a tile on a volcano so it overlaps onto the other tile
	And The tile would overwrite the totoro
    Then The operation should fail




