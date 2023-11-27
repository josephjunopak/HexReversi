package reversi.model;

import java.util.List;

import reversi.controller.Player;

/**
 * The read-only interface for reversi that only contains the observational public functions for
 * the Reversi game.
 */
public interface ReadonlyReversi {
  /**
   * Gives the current player turn.
   * @return The color representing the current player's turn.
   * @throws IllegalStateException if the game hasn't started
   */
  PlayerPiece getCurrentPlayer() throws IllegalStateException;

  /**
   * Returns the player occupying the cell or if the cell is empty.
   * @param coord The coordinates containing information of the row and col.
   * @return The player in the cell or if the cell is empty at the given coordinates.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  PlayerPiece getPlayerAtCell(Coord coord) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns whether the cell at the given row and column is empty or not.
   *
   * @param coord The coordinates containing information of the row and col.
   * @return true if the cell at the given coordinates is empty or false if it is occupied.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  boolean isCellEmpty(Coord coord) throws IllegalArgumentException, IllegalStateException;

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
  boolean canPlayerMove(PlayerPiece player) throws IllegalStateException;

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
   * Returns the score of the given player.
   * @param player  The player whose score is being requested.
   * @return  The number of pieces the given player has on the board.
   * @throws IllegalArgumentException If the given player is empty or null.
   * @throws IllegalStateException  If the game hasn't started.
   */
  int getPlayerScore(PlayerPiece player) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns a copy of the current game board of Reversi.
   */
  List<List<PlayerPiece>> copyBoard();

  /**
   * Returns whether the given spot is a legal move for the given player. A legal move
   * is defined as a move that must flip at least one opponent's piece.
   *
   * @param player the player that wants to place the piece
   * @param coord Location on the grid to check
   * @return True if the given coord is a legal move for the given player
   */
  boolean isMoveLegal(PlayerPiece player, Coord coord);

  PlayerPiece getPiece(Player player);
}
