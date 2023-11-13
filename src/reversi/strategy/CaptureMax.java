package reversi.strategy;
import java.util.List;

import reversi.model.Player;
import reversi.model.Coord;
import reversi.model.HexReversi;

public class CaptureMax implements ReversiStrategy {
  public Coord chooseMove(HexReversi model, Player forWhom) {
    List<List<Player>> currentBoard = model.copyBoard();
    int maxFlips = 0;
    Coord optimalMove = null;

    for (int row = 0; row < model.getBoardHeight(); row ++) {
      for (int col = 0; col < model.getRowWidth(row); col++) {
        Coord move = Coord.coordAt(row, col);
        if (model.isMoveLegal(forWhom, move)) {
          for (int dir = 0; dir < 6; dir++) {
            int depth = this.samePieceInDirection(forWhom, model.getBoardHeight(), move, dir);
            if (depth > 1) {
              this.flipPiecesInDirection(forWhom, model.getBoardHeight(), move, depth, dir);
            }
          }
        }
      }
    }



    return Coord.coordAt(0, 0);
  }

  /**
   * Gives the depth of the next piece of the same color of the player in a certain direction.
   *
   * @param player  The color of the pieces being checked for.
   * @param coord   the coordinates containing information of the row and col
   * @param dir     a number representing the direction with 0 representing directly left
   *                and subsequent numbers rotating clockwise.
   * @return  the depth of the next piece of the same color, or -1 if there is a gap or no piece of
   *          the same color.
   */
  private int samePieceInDirection(Player player, int height, Coord coord, int dir) {
    int depth = 1;
    int current_row = coord.row;
    int current_col = coord.col;
    int half_height = height / 2;
    while (depth < height) {
      switch (dir) {
        case 0: // left
          current_col--;
          break;
        case 1: // up left
          if (current_row <= half_height) {
            current_col--;
          }
          current_row--;
          break;
        case 2: // up right
          if (current_row > half_height) {
            current_col++;
          }
          current_row--;
          break;
        case 3: // right
          current_col++;
          break;
        case 4: // down right
          if (current_row < half_height) {
            current_col++;
          }
          current_row++;
          break;
        case 5: // down left
          if (current_row >= half_height) {
            current_col--;
          }
          current_row++;
          break;
        default:
          return -1;
      }
      Player player_at_cell;
      try {
        player_at_cell = this.getPlayerAtCell(Coord.coordAt(current_row, current_col));
      }
      catch (IllegalArgumentException e) {
        return -1;
      }
      if (player_at_cell == Player.EMPTY) {
        return -1;
      }
      if (player == player_at_cell) {
        return depth;
      }
      depth++;
    }
    return -1;
  }

  /**
   * Helper function for flipping pieces in a direction from the original placement position.
   *
   * @param player the player that placed the piece.
   * @param coord  the coordinates containing information of the row and col
   * @param depth  the number of pieces needed to be flipped in the direction specified
   * @param dir    the direction on the hex grid to flip pieces in, with 0 indicating directly to the
   *               left and incrementing clockwise.
   */
  private void flipPiecesInDirection(Player player, int height, Coord coord, int depth, int dir) {
    int current_row = coord.row;
    int current_col = coord.col;

    int half_height = height / 2;
    while (depth-- > 0) {
      switch (dir) {
        case 0: // left
          current_col--;
          break;
        case 1: // up left
          if (current_row <= half_height) {
            current_col--;
          }
          current_row--;
          break;
        case 2: // up right
          if (current_row > half_height) {
            current_col++;
          }
          current_row--;
          break;
        case 3: // right
          current_col++;
          break;
        case 4: // down right
          if (current_row < half_height) {
            current_col++;
          }
          current_row++;
          break;
        case 5: // down left
          if (current_row >= half_height) {
            current_col--;
          }
          current_row++;
          break;
        default:
          break;
      }
      this.cellGrid.get(current_row).set(current_col, player);
    }
  }
}
