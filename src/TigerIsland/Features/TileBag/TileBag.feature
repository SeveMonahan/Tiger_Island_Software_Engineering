Feature: TileBag

#Tile bag scenario test
Scenario: tiles reduce tile bag count
   Given a new tile bag
   When a tile is drawn
   Then the bag should have 47 tiles
   And I should be returned a tile instance

Scenario: drawn tile has terrain types
   Given a new tile bag
   When a tile is drawn
   Then the tile should have terrains

Scenario: Draw a tile from a bag
   Given I have initialized a tile bag
   When I ask for a new tile
   Then I should receive a new tile
   And The tile bag should have one less tile in it
   And That occurrence of tile should now have only two occurrences in the tile bag

Scenario: Draw all tiles from a bag
   Given I have initialized a tile bag
   When I ask for 48 new tiles
   Then I should receive a list of all 16 tiles are represented exactly 3 times

Scenario: Fail to draw tile from empty bag
   Given I have a tile bag with no new tiles
   When I ask for a new tile
   Then An error message should be thrown


