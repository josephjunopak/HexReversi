package reversi.controller;

import reversi.model.PlayerPiece;
import reversi.view.PlayerActions;

/**
 * This interface defines the behaviors of a player in Reversi.
 */
public interface Player {

  /**
   * Requests the player to make a move in the game or pass.
   */
  void requestMove();

  /**
   * This listener receives notifications about player actions.
   *
   * @param features Actions of the player
   */
  void addListener(PlayerActions features);

  /**
   * Returns the player piece that this player is assigned to.
   *
   * @return BLACK or WHITE
   */
  PlayerPiece getPiece();

  /**
   * Initializes the players piece at the start of the game.
   */
  void initializePiece();

  /**
   * String representation of the player based on the piece.
   *
   * @return BLACk or WHITE or an empty string if the player piece is neither.
   */
  String toString();
}
