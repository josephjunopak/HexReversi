package reversi;

import reversi.controller.HumanPlayer;
import reversi.controller.MachinePlayer;
import reversi.controller.Player;
import reversi.model.Reversi;
import reversi.strategy.CaptureMax;

/**
 * Factory class for creating Player objects in a game of Reversi.
 */
public class PlayerCreator {

  /**
   * Enum that represents the type of players that can be created.
   * HUMAN represents a human-controlled player
   * CAPTURE_MAX represents a computer player that utilizes the strategy to capture the max pieces
   */
  public enum PlayerType {
    HUMAN,
    CAPTURE_MAX
  }

  /**
   * Static method that creates a Player object given the model and the PlayerType.
   *
   * @param model Full Reversi game model
   * @param type The type of player to be created
   * @return Instance of a player given the PlayerType
   */
  public static Player create(Reversi model, PlayerType type) {
    switch (type) {
      case HUMAN:
        return new HumanPlayer(model);
      case CAPTURE_MAX:
        return new MachinePlayer(model, new CaptureMax());
      default:
        throw new IllegalArgumentException("Invalid PlayerType");
    }
  }

}
