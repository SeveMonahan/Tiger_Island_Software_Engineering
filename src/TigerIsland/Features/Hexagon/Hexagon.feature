Feature: Hexagon

#Hexagon scenario test

Scenario: Level up Hexagon
   Given I have initialized a Hexagon and added a level to it
   When I query the Hexagon's Level
   Then I receive 1 as my result