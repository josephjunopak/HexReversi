package reversi.view;


import reversi.model.Coord;

/**
 * The features listener for the view, which notifies listeners when a player has
 * either made a move or passed.
 */
public interface PlayerActions {
  /**
   * Notifies that the current player has chosen to pass their turn either due
   * to no more valid moves, or if chosen not to make any moves.
   */
  void passMove();

  /**
   * Notifies that the current player has chosen to make a move to the specified coordinate.
   *
   * @param coord The coordinates where the player has placed their piece.
   */
  void playMove(Coord coord);
}
