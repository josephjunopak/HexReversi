package reversi.controller;

import java.util.ArrayList;
import java.util.List;

import reversi.model.Coord;
import reversi.model.PlayerPiece;
import reversi.model.ReadonlyReversi;
import reversi.strategy.ReversiStrategy;
import reversi.view.PlayerActions;

/**
 * Represents a computer player in Reversi. It utilizes the given strategy
 * to choose its interactions.
 */
public class MachinePlayer implements Player {
  private PlayerPiece piece;

  private ReadonlyReversi model;

  private final List<PlayerActions> featuresListeners;
  private final ReversiStrategy strategy;

  /**
   * Constructs a machine player given a read-only version of the model and a game strategy
   * for the computer to utilize in order to make its moves.
   *
   * @param model Read-only model
   * @param strategy Game strategy
   */
  public MachinePlayer(ReadonlyReversi model, ReversiStrategy strategy) {
    this.model = model;
    this.strategy = strategy;
    this.featuresListeners = new ArrayList<>();
  }

  @Override
  public void requestMove() {
    Coord nextMove = this.strategy.chooseMove(this.model, this.piece);
    if (nextMove == null) {
      for (PlayerActions features: featuresListeners) {
        features.passMove();
      }
    }
    else {
      for (PlayerActions features : featuresListeners) {
        features.playMove(nextMove);
      }
    }
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
    return switch (this.piece) {
      case BLACK -> "Black";
      case WHITE -> "White";
      default -> "";
    };
  }
}
