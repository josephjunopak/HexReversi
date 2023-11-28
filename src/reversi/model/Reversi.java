package reversi.model;

import reversi.controller.Player;

/**
 * Represents the primary model interface for playing a game of reversi. The game support 2 players
 * and a two-dimensional grid.
 */
public interface Reversi extends ReadonlyReversi {

  /**
   * Starts a reversi game.
   *
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

  /**
   * Adds a feature listener to the model, which will be notified when the game turn changes.
   *
   * @param features the listener which will be notified once the game turn changes.
   */
  void addFeatures(ModelFeatures features);

  /**
   * Adds a player to the game, and assigns the player a piece. This method also ensures that there
   * are a maximum of 2 players per game.
   * @param player  The player which participates in this game.
   * @throws IllegalStateException if the game already has 2 players.
   */
  void addPlayer(Player player) throws IllegalStateException;
}
