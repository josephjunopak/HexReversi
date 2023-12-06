package reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;

import reversi.provider.model.Disc;
import reversi.provider.model.DiscType;
import reversi.provider.model.ReadonlyReversiModel;

/**
 * Strategy to avoid cells next to corners.
 */
public class AvoidNearCorner implements MoveStrategy {
  private final MoveStrategy backup = new FirstOpening();


  @Override
  public Disc chooseDisc(ReadonlyReversiModel model, DiscType player) {
    List<Disc> corners = isNextToCorners(model);
    for (List<Disc> rows : model.getBoard()) {
      for (Disc d : rows) {
        if (!corners.contains(d) && model.validMove(d)) {
          return d;
        }
      }
    }
    return backup.chooseDisc(model, player);
  }

  /**
   * Makes list of all discs next to corners.
   *
   * @param model model corners are found in
   * @return list of all discs next to corners.
   */
  List<Disc> isNextToCorners(ReadonlyReversiModel model) {
    List<Disc> ans = new ArrayList<>();
    int split = (model.getBoardSize() - 1) / 2;
    for (List<Disc> rows : model.getBoard()) {
      for (Disc d : rows) {
        if ((d.getQ() == 0 && d.getR() == 1
                && d.getZ() == split - 1)
                || (d.getQ() == -1 && d.getR() == 1
                && d.getZ() == split)
                || (d.getQ() == 0 && d.getR() == 1
                && d.getZ() == split - 1)) {
          ans.add(d);
        } else if ((d.getQ() == split && d.getR() == 1
                && d.getZ() == -1)
                || (d.getQ() == split - 1 && d.getR() == 0
                && d.getZ() == 1)
                || (d.getQ() == split - 1 && d.getR() == 1
                && d.getZ() == 0)) {
          ans.add(d);
        } else if ((d.getQ() == -1 * split
                && d.getR() == model.getBoardSize() - 2
                && d.getZ() == 1)
                || (d.getQ() == -1 * (split - 1)
                && d.getR() == model.getBoardSize() - 1
                && d.getZ() == -1)
                || (d.getQ() == -1 * (split - 1)
                && d.getR() == model.getBoardSize() - 2
                && d.getZ() == 0)) {
          ans.add(d);
        } else if ((d.getQ() == -1 * (split - 1) && d.getR() == split - 1
                && d.getZ() == split)
                || (d.getQ() == -1 * (split - 1) && d.getR() == split
                && d.getZ() == split - 1)
                || (d.getQ() == -1 * split && d.getR() == split + 1
                && d.getZ() == split - 1)) {
          ans.add(d);
        } else if ((d.getQ() == split && d.getR() == split - 1
                && d.getZ() == -1 * (split - 1))
                || (d.getQ() == split - 1 && d.getR() == split
                && d.getZ() == -1 * (split - 1))
                || (d.getQ() == split - 1 && d.getR() == split + 1
                && d.getZ() == -1 * (split))) {
          ans.add(d);
        } else if ((d.getQ() == 0 && d.getR() == model.getBoardSize() - 2
                && d.getZ() == -1 * (split - 1))
                || (d.getQ() == -1 && d.getR() == model.getBoardSize() - 1
                && d.getZ() == -1 * (split - 1))
                || (d.getQ() == 1 && d.getR() == model.getBoardSize() - 2
                && d.getZ() == -1 * split)) {
          ans.add(d);
        }
      }
    }
    return ans;
  }

}
