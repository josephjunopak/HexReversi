package reversi.strategy;

import reversi.model.HexReversi;
import reversi.model.Player;
import reversi.model.Coord;
import reversi.model.Reversi;

public interface ReversiStrategy {
  /**
   * Chooses a move to make based on the implemented strategy. Returns null if no moves are
   * possible.
   * @param model   the model in which the move will be made.
   * @param forWhom the player in the model whose is moving.
   * @return the coordinates of the upper-left most move that satisfies the strategy, or null if
   *         the player chooses to pass.
   */
  Coord chooseMove(HexReversi model, Player forWhom);
}
