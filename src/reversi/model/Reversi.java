package reversi.model;

/**
 * Represents the primary model interface for playing a game of reversi.
 */
public interface Reversi extends ReadonlyReversi {

  /**
   * Starts the game with a set board size.
   * 
   * @param boardSize The length of an edge of the board to set to.
   * @throws IllegalArgumentException if the board size is less than 1
   * @throws IllegalStateException if the game has already started
   */
  void startGame(int boardSize) throws IllegalArgumentException, IllegalStateException;

  /**
   * Moves a piece to the requested spot based on the row and column for the current player.
   *
   * @param row The top-down oriented row where the 0th row is the 1st row.
   * @param col The left-right oriented col where the 0th col is the left-most cell
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  void makeMove(int row, int col) throws IllegalArgumentException, IllegalStateException;

  /**
   * Passes the current players turn to the other player.
   *
   * @throws IllegalStateException if the game hasn't started yet
   */
  void passTurn() throws IllegalStateException;
}
