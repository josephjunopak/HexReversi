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