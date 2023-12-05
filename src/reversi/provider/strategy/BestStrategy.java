package reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.Disc;
import cs3500.reversi.model.DiscPosn;
import cs3500.reversi.model.DiscType;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;

/**
 * Strategy that finds the best move by using the minimax algorithm.
 */
public class BestStrategy implements MoveStrategy {
  @Override
  public Disc chooseDisc(ReadonlyReversiModel model, DiscType player) {
    int bestValue = Integer.MIN_VALUE;

    List<List<Disc>> oldBoard = new ArrayList<>(model.getBoard());
    ReversiModel mutableModel = new BasicReversi(model.getBoard(), player);

    Disc bestMove = mutableModel.allMovesLeft(player).get(0);
    for (Disc d : mutableModel.allMovesLeft(player)) {
      try {
        mutableModel.flipDiscs(d);
        int moveValue = minimax(mutableModel, 5, false, player);
        mutableModel.newBoard(oldBoard);
        if (moveValue > bestValue) {
          bestMove = new Disc(DiscType.EMPTY, new DiscPosn(d.getQ(), d.getR(), d.getZ()));
          bestValue = moveValue;
        }
      } catch (IllegalStateException e) {
        // ignores invalid moves
      }
    }
    return bestMove;
  }

  /**
   * Minimax algorithm that helps find the best disc.
   *
   * @param model  model game is based on.
   * @param depth  depth searching into.
   * @param isMax  if finding max.
   * @param player disc type of player algorithm is finding the best move for.
   * @return integer value of player 1's score relative to the game
   */
  public int minimax(ReversiModel model, int depth, Boolean isMax, DiscType player) {
    int size = 0;
    for (List<Disc> rows : model.getBoard()) {
      for (Disc d : rows) {
        size++;
      }
    }
    if (model.allMovesLeft(DiscType.WHITE).isEmpty()
            && model.allMovesLeft(DiscType.BLACK).isEmpty()) {
      if (model.getWinner().getPiece() == player) {
        return size;
      } else {
        return -1 * size;
      }
    } else if (depth == 0) {
      return model.getPlayerScore(player) - model.getPlayerScore(player.opposite());
    }

    if (isMax) {
      int highestVal = Integer.MIN_VALUE;
      for (Disc d : model.allMovesLeft(player)) {
        List<List<Disc>> oldBoard = new ArrayList<>();
        oldBoard.addAll(model.getBoard());
        try {
          model.flipDiscs(d);
          highestVal = Math.max(highestVal, minimax(model,
                  depth - 1, false, player));
          model.newBoard(oldBoard);
        } catch (IllegalStateException e) {
          // ignores invalid moves
        }
      }
      return highestVal;
    } else {
      int lowestVal = Integer.MAX_VALUE;
      for (Disc d : model.allMovesLeft(player.opposite())) {
        try {
          List<List<Disc>> oldBoard = new ArrayList<>();
          oldBoard.addAll(model.getBoard());
          model.flipDiscs(d);
          lowestVal = Math.min(lowestVal, minimax(model,
                  depth - 1, false, player));
          model.newBoard(oldBoard);
        } catch (IllegalStateException e) {
          // ignores invalid moves
        }
      }
      return lowestVal;
    }
  }

}
