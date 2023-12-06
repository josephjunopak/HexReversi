package reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;

import reversi.provider.model.Disc;
import reversi.provider.model.DiscType;
import reversi.provider.model.ReadonlyReversiModel;

/**
 * Strategy that aims for corners pieces in the game.
 */
public class GoForCorners implements MoveStrategy {
  private final MoveStrategy backup = new FirstOpening();

  @Override
  public Disc chooseDisc(ReadonlyReversiModel model, DiscType player) {
    List<Disc> corners = isCorner(model);
    for (Disc d : corners) {
      if (model.validMove(d)) {
        return d;
      }
    }
    return backup.chooseDisc(model, player);
  }

  /**
   * Makes a list of all the corners in given reversi board.
   *
   * @param model the model it is based on.
   * @return list of all the corners in given reversi board.
   */
  public List<Disc> isCorner(ReadonlyReversiModel model) {
    List<Disc> ans = new ArrayList<>();
    int split = (model.getBoardSize() - 1) / 2;
    for (List<Disc> rows : model.getBoard()) {
      for (Disc d : rows) {
        if (d.getQ() == 0
                && d.getR() == 0 && d.getZ() == split) {
          ans.add(d);
        } else if (d.getQ() == split
                && d.getR() == 0 && d.getZ() == 0) {
          ans.add(d);
        } else if (d.getQ() == -1 * split
                && d.getR() == model.getBoardSize() - 1
                && d.getZ() == 0) {
          ans.add(d);
        } else if (d.getQ() == -1 * split && d.getR() == split
                && d.getZ() == split) {
          ans.add(d);
        } else if (d.getQ() == split && d.getR() == split
                && d.getZ() == -1 * split) {
          ans.add(d);
        } else if (d.getQ() == 0
                && d.getR() == model.getBoardSize() - 1
                && d.getZ() == -1 * split) {
          ans.add(d);
        }
      }
    }
    return ans;
  }

}
