package reversi.provider.view;

import java.util.List;

import cs3500.reversi.model.Disc;
import cs3500.reversi.model.ReversiModel;

/**
 * A simple text-based rendering of the Reversi game.
 *    Implementation of {@link ReversiTextualView}.
 */
public class TextualView implements ReversiTextualView {
  private final ReversiModel model;

  /**
   * Constructs a textual view of the game.
   * @param model the model the game is based off of.
   */
  public TextualView(ReversiModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    String ans = "";
    for (List<Disc> rows : model.getBoard()) {
      int size = model.getBoard().size() - rows.size();
      for (int i = 0; i < size; i++) {
        ans += " ";
      }
      for (Disc d : rows) {
        ans += d.getType().toString() + " ";
      }
      ans = ans.substring(0, ans.length() - 1);
      ans += "\n";
    }
    return ans;
  }
}
