package reversi.provider.model;

import java.util.List;

import reversi.provider.controller.ModelStatusListener;


/**
 * Readonly interface of a Reversi game.
 */
public interface ReadonlyReversiModel {

  /**
   * Signals if a game is over.
   *
   * @return true if is, false otherwise.
   */
  boolean isGameOver();

  /**
   * Returns player 1.
   *
   * @return player 1.
   */
  Player getPlayer1();

  /**
   * Returns player 2.
   *
   * @return player 2.
   */
  Player getPlayer2();

  /**
   * Returns the board's current state.
   *
   * @return the current board state
   */
  List<List<Disc>> getBoard();

  /**
   * Returns the color of the current player.
   *
   * @return the current player's disc type
   */
  DiscType getCurrentTurn();

  /**
   * Returns the winner of the game.
   *
   * @return the player with the most discs on the board
   */
  Player getWinner();

  /**
   * Returns player one's score.
   *
   * @return player one's score.
   */
  int getPlayer1Score();

  /**
   * Returns player two's score.
   *
   * @return player two's score.
   */
  int getPlayer2Score();

  /**
   * Checks if clicked disc is a valid move.
   *
   * @param clicked disc that was clicked
   * @return true if valid, false otherwise
   */
  boolean validMove(Disc clicked);

  /**
   * Returns the size of the board.
   *
   * @return size of the board
   */
  int getBoardSize();

  /**
   * Finds the clicked disc in the board.
   *
   * @return the clicked disc
   */
  Disc findCLicked();

  /**
   * List of all valid moves a player can make based on their color.
   *
   * @param player the disc type.
   * @return List of all valid moves
   */
  List<Disc> allMovesLeft(DiscType player);

  /**
   * Returns score of a player based on given disc type.
   *
   * @param player the disc type.
   * @return score of a player based on given disc type.
   */
  int getPlayerScore(DiscType player);

  /**
   * Makes a list of all discs to flip based on axis.
   *
   * @param discs   the discs within one axis (q, r, or z).
   * @param clicked the spot clicked.
   * @return lists of discs to flip.
   */
  List<Disc> discsToFlip(List<Disc> discs, Disc clicked);

  /**
   * Method that flips the discs color.
   *
   * @param clicked the disc clicked.
   * @return the flipped discs.
   */
  List<Disc> discsToFlipAll(Disc clicked);

  /**
   * Returns the disc type of the next player to add to game.
   *
   * @return disc type of the next player to add to game.
   */
  DiscType playerToAddType();

  /**
   * Gets the model status listeners.
   *
   * @return the model status listeners.
   */
  List<ModelStatusListener> getListeners();
}


