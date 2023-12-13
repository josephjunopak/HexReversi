# HexReversi

## Overview
This codebase focuses on creating a model, view, controller (MVC) style-coded game of Reversi.
The model interface, Reversi, allows for almost all applications
of Reversi including different shapes and sizes for the board. In this version,
we have implemented a game of HexReversi using the interface.



## Quick Start
Run HexReversi.jar by inputting into the console:

Player vs Player:
```
$ java -jar HexReversi.jar human human
```

Player vs Computer:
```
$ java -jar HexReversi.jar human capture_max
```

## Changes

- The ability to create a copy of a board:
  - In terms of purely implementing the model, we did not see a need to copy the board.
  We simply created a method that returns a copy of the current state of the board.
- What is the current score for either player?
  - Since we did not know how the score was going to be calculated, we did not
  implement a score method. We implemented the function by simply going over each
  cell and counting how many cells contain a piece for the player.
- Can the player move at a specific coordinate
  - We had this function previously as private but switched it to public knowing that we would have 
to use it in the controller. It was also added moved to the read-only interface for the model.

- Added a continueGame function to HexReversi to support creating a custom game
  - doing this required creating a new helper function to validate the shape of the board.
- Moved the Player Enum to a separate file instead of keeping it within the game.
- Created a Coord value-class that tracks the 2d coordinates within the hex-grid.
  - The 2 changes above required some refactoring of the dependant files.
- Added access modifiers to HexReversi fields.
- Added a Mock implementation of the readonly interface to test the CaptureMax Strategy
- Added a Strategy package containing function objects that can find the "best move" according to a
strategy.
  - Only the CaptureMax strategy has been added, which prioritizes moves which flip the most amount
of pieces. It also defaults to top-left cells when there are ties.
- Added a stub GUI that can display the model but only supports selecting cells and has no
interactions with the model yet.
- Added JUnit testing for mockModel and maxCapture strategy.
- Added Features interfaces for both the model and controller to abstract higher-level usage
- Renamed Players Enum to PlayerPiece Enum (Enum is delegated to black and white pieces in Reversi)
- Created new Players interface in the controller package to handle Player actions
  - Mainly used for the MachinePlayer, Human player doesn't do anything since inputs are taken from
    the view.
- Added two new methods to the Reversi interface: addFeatures() and addPlayer()
  - addFeatures(): adds a model feature-listener to the model, which are notified whenever the turn
    changes in the model.
  - addPlayer() adds a player to the model, and is assigned a piece automatically.
- Added a method to the ReadonlyReversi interface: getPiece(), which returns the piece assigned to
  the given player.
- Added two new features interfaces, which allow for the controller to be notified whenever certain
  events occur in the view or model.
  - ModelFeatures: 
    - yourTurn(): notifies the controller whenever the turn changes in the model
  - PlayerActions: (this is used by both the players and view interfaces)
    - passMove(): notifies the controller whenever a player wants to pass their turn.
    - playMove(): notifies the controller whenever a player wants to make a move.
- Created Factory class for Players that allows for the creation of either:
  - Human Player
  - Computer Player w/ Strategy = CaptureMax (Capture the maximum number of pieces possible)
- Main method now accepts CLI arguments. The first input binds the player to the black piece 
 the second input (separated by a space) binds the player to the white piece.
 the black player always goes first. Example inputs should be:
    - "Human Human"
    - "Human Capture_Max"
- Removed the debug print statements from the view.
