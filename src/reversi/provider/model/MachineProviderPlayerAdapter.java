package reversi.provider.model;

import reversi.controller.MachinePlayer;
import reversi.model.ReadonlyReversi;
import reversi.provider.controller.IController;
import reversi.provider.strategy.MoveStrategy;
import reversi.strategy.ReversiStrategy;

public class MachineProviderPlayerAdapter extends MachinePlayer implements ProviderPlayer {
  /**
   * Constructs a machine player given a read-only version of the model and a game strategy
   * for the computer to utilize in order to make its moves.
   *
   * @param model    Read-only model
   * @param strategy Game strategy
   */
  public MachineProviderPlayerAdapter(ReadonlyReversi model, ReversiStrategy strategy) {
    super(model, strategy);
  }

  @Override
  public void addController(IController c) {

  }

  @Override
  public void play() {
    requestMove();
  }

  @Override
  public DiscType getDiscPiece() {
    return DiscConversion.toDiscType(getPiece());
  }
}
