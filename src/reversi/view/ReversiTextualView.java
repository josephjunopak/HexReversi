package reversi.view;

import reversi.model.Coord;
import reversi.model.PlayerPiece;
import reversi.model.Reversi;

/**
 * A class that helps display a HexReversi game via text.
 */
public class ReversiTextualView implements TextView {
  private final Reversi model;

  public ReversiTextualView(Reversi model) {
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder text = new StringBuilder();
    //int lengthOfMiddleRow = this.model.getBoardHeight();
    String oneSpace = " ";
    String underscore = "_";
    String blackPlayer = "X";
    String whitePlayer = "0";

    //Draw whole grid first
    for (int row = 0; row < this.model.getBoardHeight(); row++) {
      text.append(oneSpace.repeat(model.getBoardHeight()
              - this.model.getRowWidth(row)));
      for (int col = 0; col < this.model.getRowWidth(row); col++) {
        PlayerPiece player = this.model.getPlayerAtCell(Coord.coordAt(row, col));

        if (player == PlayerPiece.WHITE) {
          text.append(whitePlayer);
        } else if (player == PlayerPiece.BLACK) {
          text.append(blackPlayer);
        } else {
          text.append(underscore);
        }

        text.append(oneSpace);
      }
      text.deleteCharAt(text.length() - 1); //Remove whitespace at end
      text.append(System.lineSeparator());
    }

    return text.toString();
  }

}
