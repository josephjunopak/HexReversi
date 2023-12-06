package reversi.provider.controller;

import reversi.provider.model.Disc;

/**
 * Player - view listener interface.
 */
public interface PlayerListener {

  /**
   * Flips the disc.
   *
   * @param d the disc to flip.
   */
  void flip(Disc d);

  /**
   * Passes the current turn.
   */
  void pass();

}
