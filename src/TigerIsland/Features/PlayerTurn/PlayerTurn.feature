Feature: PlayerTurn

#PlayerTurn scenario test

Scenario: Player 1 starts the game
    Given A game has started and both players have been initialized
    When I ask who’s turn it is
    Then Player 1 is returned as the result

Scenario: Player cannot take two tiles placement actions
    Given A player has just take a tile placement action
    When The player attempts to take another tile placement action
    Then the operation fails

Scenario: Player cannot take two build actions
    Given The player has just take a build action
    When The player attempts to take another build action
    Then The operation fails

Scenario: Player cannot take build action first
    Given The player has never taken an action
    When The player attempts to take another build action
    Then The operation fails

Scenario: Player cannot take two turns in a row
    Given The player has just taken a tile placement action and a build action
    And no other players have taken an action since
    When The player attempts a tile placement action
    Then The operation fails

Scenario: Player cannot interrupt a turn
    Given The player has just taken a tile placement action
    When Another player attempts a tile placement action
    Then The operation fails

Scenario: One player cannot perform a valid build action
    Given A player has 0 meeples
    When that player takes a tile placement action
    Then That player should lose and the other player should win immediately