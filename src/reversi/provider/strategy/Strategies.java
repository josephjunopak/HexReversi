package reversi.provider.strategy;

import reversi.provider.model.AIPlayer;
import reversi.provider.model.HumanPlayer;
import reversi.provider.model.Player;
import reversi.provider.model.ReversiModel;

/**
 * Represents the types of game modes.
 */
public enum Strategies {
  HUMAN, GETFIRSTAVAILABLE, MOSTDISCS, CORNERS, AVOIDCORNERS, MAXMIN;

  /**
   * Gets the strategy based on the given value.
   *
   * @param num the given number
   * @return the strategy based on the given value.
   */
  public static Strategies getStratByVal(int num) {
    if (num == 1) {
      return HUMAN;
    } else if (num == 2) {
      return GETFIRSTAVAILABLE;
    } else if (num == 3) {
      return MOSTDISCS;
    } else if (num == 4) {
      return CORNERS;
    } else if (num == 5) {
      return AVOIDCORNERS;
    } else if (num == 6) {
      return MAXMIN;
    } else {
      throw new IllegalArgumentException("Invalid Value");
    }
  }

  @Override
  public String toString() {
    if (this == HUMAN) {
      return "controlled by human player";
    } else if (this == GETFIRSTAVAILABLE) {
      return "goes for the first available space";
    } else if (this == MOSTDISCS) {
      return "goes for the move that gets the most discs";
    } else if (this == CORNERS) {
      return "goes for the upper leftmost valid corner";
    } else if (this == AVOIDCORNERS) {
      return "avoids going next to corners";
    } else {
      return "goes for the move that makes it hardest for the other player";
    }
  }

  /**
   * Displays the strategies in a string in a numbered list.
   *
   * @return string of all the strategies
   */
  public static String asAList() {
    String ans = "";
    for (int i = 1; i < 6; i++) {
      ans += i + ". " + getStratByVal(i) + ", ";
      if (i == 3) {
        ans += "\n";
      }
    }
    ans += 6 + ". " + getStratByVal(6) + ", ";
    return ans;
  }

  /**
   * Creates a game based on given mode.
   *
   * @param strategy the strategy the user selects
   * @return the player
   */
  public static Player createPlayer(Strategies strategy, ReversiModel model) {
    Player p;
    switch (strategy) {
      case HUMAN:
        return new HumanPlayer(model);
      case GETFIRSTAVAILABLE:
        return new AIPlayer(model, new FirstOpening());
      case MOSTDISCS:
        return new AIPlayer(model, new GetMostPieces());
      case CORNERS:
        return new AIPlayer(model, new GoForCorners());
      case AVOIDCORNERS:
        return new AIPlayer(model, new AvoidNearCorner());
      case MAXMIN:
        return new AIPlayer(model, new BestStrategy());
      default:
        throw new IllegalArgumentException("Unsupported strategy type");
    }
  }
}
