package reversi.provider.strategy;

import reversi.provider.model.Disc;
import reversi.provider.model.DiscType;
import reversi.provider.model.ReadonlyReversiModel;

/**
 * Strategy that finds the first available opening.
 */
public class FirstOpening implements MoveStrategy {

  @Override
  public Disc chooseDisc(ReadonlyReversiModel model, DiscType player) {
    if (!model.allMovesLeft(player).isEmpty()) {
      return model.allMovesLeft(player).get(0);
    } else {
      throw new IllegalStateException("No valid moves");
    }
  }
}
