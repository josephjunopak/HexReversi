package reversi.provider.strategy;

import cs3500.reversi.model.Disc;
import cs3500.reversi.model.DiscType;
import cs3500.reversi.model.ReadonlyReversiModel;

/**
 * Interface for strategies in a Reversi game.
 */
public interface MoveStrategy {

  /**
   * A Strategy interface for choosing where to play next for the given player.
   */
  Disc chooseDisc(ReadonlyReversiModel model, DiscType player);


}
