Feature: test

#Tile bag scenario test
  Scenario: Meeples of initialized player 
    Given I have initialized player in meeple 
    When I query that player's meeples 
    Then that player has 20 meeples

  Scenario: Populate on Level 1 Hexagon 
    Given I have an initialized board 
    And I place a tile on it  in meeple
    And I have an initialized player 
    When I attempt to populate a Level 1 non-volcano hexagon 
    Then I place 1 meeple on the hexagon 
    And the player now has 1 point 
    And the player has 19 meeples  

