package reversi.model;

/**
 * The features listener for the model, which notifies listeners when the turn changes.
 */
public interface ModelFeatures {
  /**
   * Notifies listeners when the model player turn changes.
   */
  void yourTurn();
}
