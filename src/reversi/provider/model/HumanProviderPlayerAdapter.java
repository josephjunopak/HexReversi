package reversi.provider.model;

import reversi.controller.HumanPlayer;
import reversi.model.ReadonlyReversi;
import reversi.provider.controller.IController;
import reversi.provider.strategy.MoveStrategy;

public class HumanProviderPlayerAdapter extends HumanPlayer implements ProviderPlayer {


  /**
   * Constructs a human player given a read-only version of the model.
   *
   * @param model Read-only model
   */
  public HumanProviderPlayerAdapter(ReadonlyReversi model) {
    super(model);
  }

  @Override
  public void addController(IController c) {
    // we aren't using their controller
    throw new UnsupportedOperationException("something");
  }

  @Override
  public void play() {
    super.requestMove();
  }

  public DiscType getDiscPiece() {
    return DiscConversion.toDiscType(getPiece());
  }
}
