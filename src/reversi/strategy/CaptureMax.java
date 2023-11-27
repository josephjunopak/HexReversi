package reversi.strategy;

import reversi.model.PlayerPiece;
import reversi.model.Coord;
import reversi.model.ReadonlyReversi;

/**
 * Strategy that tries to find the optimal move to capture as many pieces on this
 * turn as possible given the player and current state of the board.
 */
public class CaptureMax implements ReversiStrategy {
  /**
   * Given the current state of the board and given player, this function will
   * return the most optimal move, which means that it will flip the most
   * number of pieces possible. In a tie, the uppermost-leftmost coord will be chosen.
   *
   * @param model   the model in which the move will be made.
   * @param forWhom the player in the model whose is moving.
   * @return Coord that will flip the most pieces
   */
  public Coord chooseMove(ReadonlyReversi model, PlayerPiece forWhom) {
    int maxFlips = -1;
    Coord optimalMove = null;

    for (int row = 0; row < model.getBoardHeight(); row ++) {
      for (int col = 0; col < model.getRowWidth(row); col++) {
        Coord move = Coord.coordAt(row, col);
        if (model.isMoveLegal(forWhom, move)) {
          int total_flips = 0;
          //Checks each direction for the number of flips that has occurred
          for (int dir = 0; dir < 6; dir++) {
            int depth = this.samePieceInDirection(forWhom, model, move, dir);
            if (depth > 1) {
              total_flips += depth - 1;
            }
          }
          // If a tie occurs, the initial coord found is chosen as the most optimal move
          if (total_flips > maxFlips) {
            maxFlips = total_flips;
            optimalMove = move;
          }
        }
      }
    }

    return optimalMove;
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
  private int samePieceInDirection(PlayerPiece player, ReadonlyReversi model, Coord coord, int dir) {
    int depth = 1;
    int current_row = coord.row;
    int current_col = coord.col;
    int height = model.getBoardHeight();
    while (depth < height) {
      // This switch statement aids in navigating our coordinate system since adjacency between
      //  cells changes after the halfway point.
      switch (dir) {
        case 0: // left
          current_col--;
          break;
        case 1: // up left
          if (current_row <= height / 2) {
            current_col--;
          }
          current_row--;
          break;
        case 2: // up right
          if (current_row > height / 2) {
            current_col++;
          }
          current_row--;
          break;
        case 3: // right
          current_col++;
          break;
        case 4: // down right
          if (current_row < height / 2) {
            current_col++;
          }
          current_row++;
          break;
        case 5: // down left
          if (current_row >= height / 2) {
            current_col--;
          }
          current_row++;
          break;
        default:
          return -1;
      }
      PlayerPiece player_at_cell;
      // Terminate loop if the cell reaches an empty cell or out-of-bounds
      try {
        player_at_cell = model.getPlayerAtCell(Coord.coordAt(current_row, current_col));
      }
      catch (IllegalArgumentException e) {
        return -1;
      }
      if (player_at_cell == PlayerPiece.EMPTY) {
        return -1;
      }
      // if the loop finds another cell with the same color piece, return the depth of that piece
      if (player == player_at_cell) {
        return depth;
      }
      depth++;
    }
    return -1;
  }
}
