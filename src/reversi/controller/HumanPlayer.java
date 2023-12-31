package reversi.controller;

import java.util.ArrayList;
import java.util.List;

import reversi.model.PlayerPiece;
import reversi.model.ReadonlyReversi;
import reversi.view.PlayerActions;

/**
 * Represents a human player in Reversi.
 */
public class HumanPlayer implements Player {
  private PlayerPiece piece;

  private ReadonlyReversi model;

  private final List<PlayerActions> featuresListeners;

  /**
   * Constructs a human player given a read-only version of the model.
   * @param model Read-only model
   */
  public HumanPlayer(ReadonlyReversi model) {
    this.model = model;
    this.featuresListeners = new ArrayList<>();
  }

  @Override
  public void requestMove() {
    // Human player should interact with the view to make a move
  }

  @Override
  public void addListener(PlayerActions features) {
    this.featuresListeners.add(features);
  }

  @Override
  public PlayerPiece getPiece() {
    return this.piece;
  }

  @Override
  public void initializePiece() {
    this.piece = this.model.getPiece(this);
  }

  @Override
  public String toString() {
    return (this.piece == PlayerPiece.WHITE) ? "White" : "Black";
  }
}
