package reversi.provider.model;

import reversi.provider.controller.IController;

/**
 * Interface that represents the model for a player in the game of Reversi.
 */
public interface ProviderPlayer {

  /**
   * Adds a controller to a player.
   *
   * @param c controller to add.
   */
  void addController(IController c);

  /**
   * Starts the players move.
   */
  void play();

  /**
   * Returns player's disc type.
   *
   * @return player's disc type.
   */
  DiscType getDiscPiece();
}
