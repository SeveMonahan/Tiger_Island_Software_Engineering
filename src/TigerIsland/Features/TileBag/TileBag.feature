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


