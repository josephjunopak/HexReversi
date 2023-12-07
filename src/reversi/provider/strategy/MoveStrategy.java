package reversi.provider.strategy;

import reversi.provider.model.Disc;
import reversi.provider.model.DiscType;
import reversi.provider.model.ReadonlyReversiModel;

/**
 * Interface for strategies in a Reversi game.
 */
public interface MoveStrategy {

  /**
   * A Strategy interface for choosing where to play next for the given player.
   */
  Disc chooseDisc(ReadonlyReversiModel model, DiscType player);


}
