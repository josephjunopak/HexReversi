package reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;

import reversi.provider.model.Disc;
import reversi.provider.model.DiscType;
import reversi.provider.model.ReadonlyReversiModel;

/**
 * Strategy to choose the move that captures the most pieces.
 */
public class GetMostPieces implements MoveStrategy {

  @Override
  public Disc chooseDisc(ReadonlyReversiModel model, DiscType player) {
    Disc ans = model.getBoard().get(0).get(0);
    boolean tie = false;
    List<Disc> tieList = new ArrayList<>();
    for (Disc d : model.allMovesLeft(player)) {
      if (model.discsToFlipAll(ans).size() <
              model.discsToFlipAll(d).size()) {
        ans = d;
        if (!tieList.isEmpty() && model.discsToFlipAll(tieList.get(0)).size()
                < model.discsToFlipAll(d).size()) {
          tieList.clear();
          tie = false;
        }
      } else if (model.discsToFlipAll(ans).size() == model.discsToFlipAll(d).size()
              && !ans.equals(d)) {
        tie = true;
        tieList.add(d);
      }
    }
    if (!tie) {
      return ans;
    } else {
      return findUpperLeft(tieList);
    }
  }

  /**
   * Find the most upper-left move option.
   *
   * @param discs the list of moves to choose from
   * @return the most upper-left disc
   */
  Disc findUpperLeft(List<Disc> discs) {
    Disc ans = discs.get(0);
    for (Disc d : discs) {
      if (ans.getR() > d.getR()) {
        ans = d;
      } else if (ans.getR() == d.getR() && ans.getQ() < d.getQ()) {
        ans = d;
      } else if (ans.getR() == d.getR() && ans.getQ() == d.getQ()
              && ans.getZ() > d.getZ()) {
        ans = d;
      }
    }
    return ans;
  }


}

