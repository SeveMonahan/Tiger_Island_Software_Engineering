#Feature: TileMove

#TileMove scenario test
#Scenario: Place first tile
#   Given I have initialized a board
#   When I place a tile
#   Then The operation is successful
#   And I receive the placed tiles notification
#   And The hexagon level of every hexagon which has been overwritten by a tile is 1
#
#Scenario: Place tile on Level 0 Hexagons
#    Given I have initialized a board and placed an initial tile
#    When I attempt to place a tile with the volcano touching the volcano on the board
#    Then The operation is successful
#	And I can query the hexagon properties of any hexagon which has had a tile placed on it
#	And The hexagon level of every hexagon which has been overwritten by a tile is 1
#
#Scenario: Fail to place tile due to no adjacency
#    Given I have initialized a board and placed an initial tile
#    When I attempt to place a tile two hexagons away from the initial hexagon
#    Then The operation fails
#	And The board is not altered
#
#Scenario: Overwrite Level 1 Hexagons
#    Given I have initialized a board
#	And placed two tiles adjacent to each other with two touching hexagons across tiles
#	And the tiles have adjacent volcanoes
#    When I attempt to place a tile on a volcano so it overlaps onto the other tile
#    Then The operation should be successful
#	And The level of each hexagon which was overwritten should be 1
#	And The terrain type of each hexagon which was overwritten should be that of the corresponding tile hexagon
#	And The properties of the other hexagons should be unchanged
#
#Scenario: Fail to overwrite Level 1 Hexagons due to overhang
#    Given I have initialized a board
#	And I have placed two tiles adjacent to each other with two touching hexagons across tiles
#	And the tiles have adjacent volcanoes
#    When I attempt to place a tile so that one hexagon is on a volcano
#	And one hexagon is on a second tile's board hexagon
#	And one hexagon is on a level 0 board hexagon
#    Then The placement should fail
#
#Scenario: Fail to overwrite Level 1 Hexagons due to volcano mismatch
#    Given I have initialized a board
#	And I have placed two tiles adjacent to each other with two touching hexagons across tiles
#	And the tiles DO NOT have adjacent volcanoes
#    When I attempt to place a tile so that the tile volcano hexagon is not on the board volcano hexagon
#	And the other two hexagons are on a second tile's board hexagons
#    Then The placement should fail
#
#Scenario: Fail to overwrite Level 1 Hexagons due to exact tile overlap
#    Given I have initialized a board
#	And I have placed a tile on it
#    When I attempt to place a tile with each hexagon corresponding to the on-board Level 1 hexagons
#	And the tile volcano matches the volcano hexagon on the board
#    Then the placement should fail

