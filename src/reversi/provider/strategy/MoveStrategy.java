package reversi.provider.strategy;

import reversi.provider.model.Disc;
import reversi.provider.model.DiscType;
import reversi.model.ReadonlyReversi;

/**
 * Interface for strategies in a Reversi game.
 */
public interface MoveStrategy {

  /**
   * A Strategy interface for choosing where to play next for the given player.
   */
  Disc chooseDisc(ReadonlyReversi model, DiscType player);


}
