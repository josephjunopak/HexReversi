package reversi;

import reversi.controller.HumanPlayer;
import reversi.controller.MachinePlayer;
import reversi.controller.Player;
import reversi.model.Reversi;
import reversi.strategy.CaptureMax;

public class PlayerCreator {

  public enum PlayerType {
    HUMAN,
    CAPTUREMAX
  }

  public static Player create(Reversi model, PlayerType type) {
    switch (type) {
      case HUMAN:
        return new HumanPlayer(model);
      case CAPTUREMAX:
        return new MachinePlayer(model, new CaptureMax());
      default:
        throw new IllegalArgumentException("Invalid PlayerType");
    }
  }

}
