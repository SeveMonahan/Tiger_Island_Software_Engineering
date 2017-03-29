Feature: Board

#Board scenario test
Scenario: Query Hexagon Neighbors
   Given a new board
   When I query a Hexagon's neighbors
   Then I receive that Hexagon's neighbors as a result
