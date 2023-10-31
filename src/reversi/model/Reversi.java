package reversi.model;

/**
 * Represents the primary model interface for playing a game of reversi.
 */
public interface Reversi {
  /**
   * Enum that represents a player's piece in a game of Reversi.
   * A cell can either be occupied by a BLACK or WHITE piece
   * OR can be empty specified by EMPTY.
   */
  public enum Player {
    EMPTY,
    BLACK,
    WHITE
  }

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
   * Gives the current player turn.
   * @return The color representing the current player's turn.
   * @throws IllegalStateException if the game hasn't started
   */
  Player getCurrentPlayer() throws IllegalStateException;

  /**
   * Returns the player occupying the cell or if the cell is empty.
   * @param row The top-down oriented row where the 0th row is the 1st row.
   * @param col The left-right oriented col where the 0th col is the left-most cell
   * @return The player in the cell or if the cell is empty at the given coordinates.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  Player getPlayerAtCell(int row, int col) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns whether the cell at the given row and column is empty or not.
   *
   * @param row The top-down oriented row where the 0th row is the 1st row.
   * @param col The left-right oriented col where the 0th col is the left-most cell
   * @return true if the cell at the given coordinates is empty or false if it is occupied.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  boolean isCellEmpty(int row, int col) throws IllegalArgumentException, IllegalStateException;

  /**
   * Signals if the game is over if there are no more moves to make.
   *
   * @return true is game is over, false is not
   * @throws IllegalStateException if the game hasn't started yet
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Returns whether the current player has a legal move or not.
   *
   * @param player the player whose move availability is being checked.
   * @return true is player can move, false if player cannot move
   * @throws IllegalStateException if the game hasn't started yet
   */
  boolean canPlayerMove(Player player) throws IllegalStateException;

  /**\
   * Gives the number of rows in the board.
   *
   * @return  the number of rows in the cell grid.
   * @throws IllegalStateException if the game hasn't started yet
   */
  int getBoardHeight() throws IllegalStateException;

  /**
   * Returns the number of cells in the row specified.
   *
   * @return the number of cells in row specified
   * @throws IllegalStateException if the game hasn't started yet
   */
  int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException;

  /**
   * Passes the current players turn to the other player.
   *
   * @throws IllegalStateException if the game hasn't started yet
   */
  void passTurn() throws IllegalStateException;
}
