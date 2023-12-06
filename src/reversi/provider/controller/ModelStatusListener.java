package reversi.provider.controller;

import reversi.provider.model.DiscType;
import reversi.provider.model.ReversiModel;

/**
 * Model status listener implementation.
 */
public interface ModelStatusListener {

  /**
   * Changes the turn.
   *
   * @param turn the turn to change to
   */
  void turnChange(DiscType turn);

  /**
   * Changes the game.
   *
   * @param m updated the model to change to.
   */
  void gameChange(ReversiModel m);

  /**
   * Plays the game.
   */
  void play();

}
