

import org.junit.Test;
import org.junit.Assert;

import reversi.model.HexReversi;
import reversi.model.Reversi;
import view.ReversiTextualView;

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
                    +"_ _ 0 _ X _ _" + System.lineSeparator()
                    +" _ _ X 0 _ _" + System.lineSeparator()
                    +"  _ _ _ _ _" + System.lineSeparator()
                    +"   _ _ _ _"));
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

    Assert.assertEquals(model.getCurrentPlayer(), Reversi.Player.BLACK);
  }

  @Test
  public void testMakeMoveInvalidMove() {
    Reversi model = new HexReversi();
    model.startGame(6);
    model.makeMove(3, 4);
    Assert.assertThrows(IllegalStateException.class,
        () -> model.makeMove(3, 3));
  }

  @Test
  public void testMakeMoveInvalidMovesPlayers() {
    Reversi model = new HexReversi();
    model.startGame(6);

    model.makeMove(3, 4);
    model.makeMove(2, 4);
    model.makeMove(4, 3);
    model.makeMove(6, 3);
    Assert.assertTrue(model.canPlayerMove(Reversi.Player.BLACK));
  }

  @Test
  public void testBasicGameOver() {
    Reversi model = new HexReversi();
    model.startGame(2);
    Assert.assertTrue("A size 2 game starts with no legal moves.", model.isGameOver());
  }

}
