Feature: Settlement

#Settlement scenario test
  Scenario: Settlement Size is 1
    Given I have initialized the players
    And I have initialized a board
    When I placed a tile without restrictions at 98, 100 with terrain ROCK, JUNGLE and direction LOWERLEFT
    When Player 1 places a meeple at 97,99
    Then the settlement at 97,99 should be 1 for player 1

  Scenario: Settlement Size is 2
    Given I have initialized the players
    And I have initialized a board
    When I placed a tile without restrictions at 100, 100 with terrain ROCK, JUNGLE and direction LEFT
    When Player 1 places a meeple at 99,100
    And Player 1 places a meeple at 99,101
    Then the settlement at 99,100 should be 2 for player 1

  Scenario: Settlement Size of 1 stops tile placement
    Given I have initialized a board
    And placed two tiles adjacent to each other with two touching hexagons across tiles
    And the tiles have adjacent volcanoes
    And There is a meeple on a non-volcano hexagon which is adjacent to both volcanos
    And There are no other meeples on the board
    When I attempt to place a tile on a volcano so it overlaps onto the other tile
    And The tile would overwrite the meeple
    Then The operation should fail