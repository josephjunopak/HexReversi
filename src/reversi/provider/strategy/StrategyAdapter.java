package reversi.provider.strategy;

import reversi.model.Coord;
import reversi.model.PlayerPiece;
import reversi.model.ReadonlyReversi;
import reversi.provider.model.Disc;
import reversi.provider.model.DiscConversion;
import reversi.provider.model.ReadonlyReversiModel;
import reversi.strategy.ReversiStrategy;

public class StrategyAdapter implements ReversiStrategy {
  ReadonlyReversiModel model;
  MoveStrategy providerStrategy;
  public StrategyAdapter(ReadonlyReversiModel model, MoveStrategy providerStrategy) {
    this.providerStrategy = providerStrategy;
    this.model = model;
  }

  @Override
  public Coord chooseMove(ReadonlyReversi model, PlayerPiece forWhom) {
    Disc discMove = providerStrategy.chooseDisc(this.model, DiscConversion.toDiscType(forWhom));
    return DiscConversion.toCoord(discMove, model.getBoardHeight());
  }
}
