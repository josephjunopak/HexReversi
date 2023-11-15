# HexReversi

## Overview
This codebase focuses on creating a model, view, controller (MVC) style-coded game of Reversi.
The model interface, Reversi, allows for almost all applications
of Reversi including different shapes and sizes for the board. In this version,
we have implemented a game of HexReversi using the interface.



## Quick Start
Here is a short example of how one might start using this codebase and try testing/playing
a game of Reversi

~~~~
@Test
public void endNonTrivialGame() {
Reversi model = new HexReversi();
model.startGame(3);
model.makeMove(1, 0);
model.makeMove(1, 3);
model.makeMove(3, 3);
model.makeMove(4, 1);
model.makeMove(0, 1);
model.makeMove(3, 0);
Assert.assertTrue(model.isGameOver());
}
~~~~
For further explanation, we first create a new
instance of the implementation of HexReversi
using
`Reversi model = new HexReversi();`

Then we start the game by initializing the size of the board
`model.startGame(3);`
In our case, the size of the board is a hexagon where
each side has 3 cells

`model.makeMove(1, 0);`
Can be read as putting a black piece onto the most-left cell 
located at the second row (index starts at 0).
Since black starts first, a X will be placed onto that cell..

The next move:
`model.makeMove(1, 3);`
Can be read as putting a white piece onto the 3rd cell from the left
located at the second row.
Since the player alternates, a 0 will be placed onto the cell.

We continue to put pieces down until there are no more spots left
on the board.

Finally, 
`Assert.assertTrue(model.isGameOver());`
checks if the game is over (which it is)

To display the board, the view can be used as follows:
~~~~
TextView view = new ReversiTextualView(model);
System.out.println(view.toString);
~~~~

Here we initialize the view by passing in the model that
we want to view to a new instance of ReversiTextualView.

Then we simply print it out to the command line.

## Source Organization

- *HexReversi/src/reversi.model*: This directory contains
the interface and implementation(s) of the model
    - *Reversi.java:* The model interface which allows for
    different shapes and sizes for the implementation of Reversi
    - *HexReversi.java:* Hex-shaped implementation of Reversi

- HexReversi/view/: This directory contains the interface and
implementation(s) of the view
    - *TextView.java:* The marker interface for a textual implementation
    - *ReversiTextualView.java:* Implementation of the textual view
     that simply displays the current state of the board of Reversi.

## Changes for Part 2

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