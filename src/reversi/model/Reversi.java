package reversi.model;

import reversi.controller.Player;

/**
 * Represents the primary model interface for playing a game of reversi. The game support 2 players
 * and a two-dimensional grid.
 */
public interface Reversi extends ReadonlyReversi {

  /**
   * Starts the game with a set board size.
   * 
   * @param boardSize The length of an edge of the board to set to.
   * @throws IllegalArgumentException if the board size is less than 1
   * @throws IllegalStateException if the game has already started
   */
  void startGame() throws IllegalArgumentException, IllegalStateException;

  /**
   * Moves a piece to the requested spot based on the row and column for the current player.
   *
   * @param coord The coordinates containing information of the row and col.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the row or column is invalid
   */
  void makeMove(Coord coord) throws IllegalArgumentException, IllegalStateException;

  /**
   * Passes the current players turn to the other player.
   *
   * @throws IllegalStateException if the game hasn't started yet
   */
  void passTurn() throws IllegalStateException;

  void addFeatures(ModelFeatures features);

  void addPlayer(Player player);
}
