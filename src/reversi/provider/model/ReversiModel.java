package reversi.provider.model;

import java.util.List;

import reversi.provider.controller.ModelStatusListener;

/**
 * Represents the primary model interface for playing a game of Reversi.
 */
public interface ReversiModel extends ReadonlyReversiModel {

  /**
   * Adds a player to the game.
   *
   * @param p player to add
   */
  void addPlayer(Player p);

  /**
   * Adds ModelStatusListeners.
   *
   * @param l the ModelStatusListener
   */
  void addListeners(ModelStatusListener l);

  void notifyTurnChange();

  /**
   * Flips disc based on players move.
   *
   * @param clicked the tile clicked by the user
   */
  void flipDiscs(Disc clicked);

  /**
   * Starts the game.
   */
  void play();

  /**
   * Updates a card to be clicked.
   *
   * @param d the disc to change
   */
  void clickDisc(Disc d);

  /**
   * Allows a player to pass.
   *
   * @throws IllegalStateException if the past two moves have been passes
   */
  void pass();

  /**
   * Updates the score of the game.
   */
  void updateScores();

  /**
   * Changes board of game.
   *
   * @param newBoard the board it is changing to.
   */
  void newBoard(List<List<Disc>> newBoard);

}
