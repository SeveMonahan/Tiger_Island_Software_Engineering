This is the Group E Tiger Island Project for Software Engineering, Spring 2017 at University of Florida.

Group Members:

Jason Cochran

Seve Monahan

Marc Mendoza

Evan Renz

Cameron Durr

Justin Woo

We believe we have all features from the game completed, any missing features should be classified as bugs. Our network connection capabilities are still slightly buggy and ill-tested, so we might have some crashes or similar problems there.

Basic Architecture:

The main loop of the game-server system is in NetworkClient.  This quickly passes to PostMan, which interprets the messages from the server using the helper class Parser and various communication objects, then calls Match class instances to simulate the game. 

The Match class initializes a Referee object, which each simulate a game. Referee classes holds two PlayerController objects and keeps track of the current GameState. GameState and is derived class are non-mutable representations of the game state (a "snapshot" of the different game variables that allows us to continue playing the game from that point). PlayerController objects are an interface which is used by the AI and to mutate the game according the Server commands. The main function is newGameState, and function which takes in a GameState and returns a new one, defining what move the PlayerController wants to make. Referee also has an OutputPlayerActions interface to pass information on game moves to the Server.

PlayerControllers create new GameStates by calling a function on the old one and passing in an object, such as Tile, TileMove or ConstructionMove. When calling these "child functions" we verify that the move is valid, and return a null pointer if they are not. The AI knows not to pass a null pointer back to Referee, while the server's moves are assumed to be valid.

Our main game logic is in the classes TileMove, TileMoveChecker, Board, Hexagon, Player, ConstructionMove, Settlement, etc. These are all organized in GameState and its derived classes.

We have tried several AIs over the course of this project. The most recent version is “SmartAIController”. We also have a “DumbController” (basic place a new settlement AI) and “GenuisAIController” (a failed AI which took too long to move and did not win significantly move than “DumbController”, which we later used for testing).
