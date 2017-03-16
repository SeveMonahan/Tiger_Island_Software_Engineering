Feature: TileBag

#Tile bag scenario test
Scenario:
   Given I have a new tile bag
   When I draw 1 tiles
   Then I should be told there are 47 tiles
   And I should be returned a tile instance