

import org.junit.Test;
import org.junit.Assert;

import reversi.model.Coord;
import reversi.model.HexReversi;
import reversi.model.Player;
import reversi.model.Reversi;
import reversi.view.ReversiTextualView;
import reversi.view.TextView;

/**
 * Test class for testing the functionality of the HexReversi model.
 */
public class HexReversiTest {
  /**
   * Checks that model sets up board correctly
   * when the game starts.
   */
  @Test
  public void testStartGameGrid() {
    Reversi model = new HexReversi();
    model.startGame(4);
    Assert.assertEquals(7, model.getBoardHeight());
    for (int row = 0; row < model.getBoardHeight(); row++) {
      if (row < 4) {
        Assert.assertEquals(4 + row, model.getRowWidth(row));
      }
      else {
        Assert.assertEquals(10 - row, model.getRowWidth(row));
      }
    }
  }

  /**
   * Tests to see if the view properly outputs
   * the model.
   */
  @Test
  public void testTextualRendering() {
    Reversi model = new HexReversi();
    model.startGame(4);
    ReversiTextualView view = new ReversiTextualView(model);

    Assert.assertTrue(view.toString().contains(
            "   _ _ _ _" + System.lineSeparator() +  "  _ _ _ _ _" + System.lineSeparator()
                    + " _ _ X 0 _ _" + System.lineSeparator()
                    + "_ _ 0 _ X _ _" + System.lineSeparator()
                    + " _ _ X 0 _ _" + System.lineSeparator()
                    + "  _ _ _ _ _" + System.lineSeparator()
                    + "   _ _ _ _"));
  }

  /**
   * Ensures that startGame will throw
   * an exception if the game is already started.
   */
  @Test
  public void testStartGameAfterAlreadyStarted() {
    Reversi model = new HexReversi();
    model.startGame(4);

    Assert.assertThrows(IllegalStateException.class,
        () -> model.startGame(4));
  }

  /**
   * Ensures that startGame will throw
   * an exception given board size is 0.
   */
  @Test
  public void testStartGameWithZeroBoardSize() {
    Reversi model = new HexReversi();
    Assert.assertThrows(IllegalArgumentException.class,
        () -> model.startGame(0));
  }

  /**
   * Ensures that startGame will throw
   * an exception given board size is negative.
   */
  @Test
  public void testStartGameWithNegativeBoardSize() {
    Reversi model = new HexReversi();

    Assert.assertThrows(IllegalArgumentException.class,
        () -> model.startGame(-1));
  }

  /**
   * Ensures that startGame will throw
   * an exception given board size is 1.
   */
  @Test
  public void testStartGameWithBoardSizeOne() {
    Reversi model = new HexReversi();

    Assert.assertThrows(IllegalArgumentException.class,
        () -> model.startGame(1));
  }

  /**
   * Tests that the first player move will be black
   * at the start of the game.
   */
  @Test
  public void testGetCurrentPlayerAtStart() {
    Reversi model = new HexReversi();
    model.startGame(4);

    Assert.assertEquals(model.getCurrentPlayer(), Player.BLACK);
  }

  @Test
  public void testMakeMoveInvalidMove() {
    Reversi model = new HexReversi();
    model.startGame(6);
    model.makeMove(Coord.coordAt(3, 4));
    Assert.assertThrows(IllegalStateException.class,
        () -> model.makeMove(Coord.coordAt(3, 3)));
  }

  @Test
  public void testMakeMoveInvalidMovesPlayers() {
    Reversi model = new HexReversi();
    model.startGame(6);

    model.makeMove(Coord.coordAt(3, 4));
    model.makeMove(Coord.coordAt(2, 4));
    model.makeMove(Coord.coordAt(4, 3));
    model.makeMove(Coord.coordAt(6, 3));
    Assert.assertTrue(model.canPlayerMove(Player.BLACK));
  }

  @Test
  public void testBasicGameOver() {
    Reversi model = new HexReversi();
    model.startGame(2);
    Assert.assertTrue("A size 2 game starts with no legal moves.", model.isGameOver());
  }

  @Test
  public void endNonTrivialGame() {
    Reversi model = new HexReversi();
    TextView view = new ReversiTextualView(model);
    model.startGame(3);
    model.makeMove(Coord.coordAt(1, 0));
    model.makeMove(Coord.coordAt(1, 3));
    model.makeMove(Coord.coordAt(3, 3));
    model.makeMove(Coord.coordAt(4, 1));
    model.makeMove(Coord.coordAt(0, 1));
    model.makeMove(Coord.coordAt(3, 0));
    Assert.assertTrue(model.isGameOver());
    String gameEndState = "  _ X _" + System.lineSeparator()
            + " X X X 0" + System.lineSeparator()
            + "_ X _ X _" + System.lineSeparator()
            + " 0 0 0 X" + System.lineSeparator()
            + "  _ 0 _";
    Assert.assertTrue(view.toString().contains(gameEndState));
  }

  @Test
  public void testBlackMovesFirst() {
    Reversi model = new HexReversi();
    model.startGame(6);
    Assert.assertEquals(Player.BLACK, model.getCurrentPlayer());
  }

  @Test
  public void testInvalidMoveOnExistingPiece() {
    Reversi model = new HexReversi();
    model.startGame(3);
    Assert.assertThrows(IllegalStateException.class,
        () -> model.makeMove(Coord.coordAt(1, 1)));
    Assert.assertEquals(Player.BLACK, model.getCurrentPlayer());
  }

  @Test
  public void testInvalidMoveOutOfBounds() {
    Reversi model = new HexReversi();
    model.startGame(3);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> model.makeMove(Coord.coordAt(0, 3)));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> model.makeMove(Coord.coordAt(-1, 3)));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> model.makeMove(Coord.coordAt(4, 3)));
  }

  @Test
  public void testInvalidMoveNoPathToSameColor() {
    Reversi model = new HexReversi();
    model.startGame(3);
    Assert.assertThrows(IllegalStateException.class,
        () -> model.makeMove(Coord.coordAt(0, 0)));
    Assert.assertThrows(IllegalStateException.class,
        () -> model.makeMove(Coord.coordAt(2, 2)));
    Assert.assertThrows(IllegalStateException.class,
        () -> model.makeMove(Coord.coordAt(2, 0)));
  }

  @Test
  public void testFlipMultipleLines() {
    Reversi model = new HexReversi();
    model.startGame(4);
    TextView view = new ReversiTextualView(model);
    model.makeMove(Coord.coordAt(1, 2));
    model.makeMove(Coord.coordAt(0, 2));
    model.makeMove(Coord.coordAt(5, 2));
    model.makeMove(Coord.coordAt(6, 2));
    model.makeMove(Coord.coordAt(2, 1));
    model.makeMove(Coord.coordAt(4, 4));
    String gameStateSample = " _ X X 0 _ _" + System.lineSeparator()
            + "_ _ 0 _ 0 _ _" + System.lineSeparator()
            + " _ _ 0 0 0 _";
    Assert.assertTrue(view.toString().contains(gameStateSample));
  }

  @Test
  public void testPassTurnEndsGame() {
    Reversi model = new HexReversi();
    model.startGame(4);
    model.passTurn();
    Assert.assertEquals(model.getCurrentPlayer(), Player.WHITE);
    model.passTurn();
    Assert.assertEquals(model.getCurrentPlayer(), Player.BLACK);
    Assert.assertTrue(model.isGameOver());
  }
}
