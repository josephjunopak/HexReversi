package reversi;

import reversi.controller.HumanPlayer;
import reversi.controller.MachinePlayer;
import reversi.controller.Player;
import reversi.model.ReadonlyReversi;
import reversi.model.Reversi;
import reversi.provider.model.MachineProviderPlayerAdapter;
import reversi.provider.model.ReadonlyModelAdapter;
import reversi.provider.model.ReadonlyReversiModel;
import reversi.provider.strategy.AvoidNearCorner;
import reversi.provider.strategy.BestStrategy;
import reversi.provider.strategy.FirstOpening;
import reversi.provider.strategy.GetMostPieces;
import reversi.provider.strategy.GoForCorners;
import reversi.provider.strategy.StrategyAdapter;
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
    CAPTURE_MAX,
    PROVIDER_AVOID_NEAR_CORNER,
    PROVIDER_FIRST_OPENING,
    PROVIDER_GET_MOST_PIECES,
    PROVIDER_GO_FOR_CORNERS,
  }

  /**
   * Static method that creates a Player object given the model and the PlayerType.
   *
   * @param model Full Reversi game model
   * @param type The type of player to be created
   * @return Instance of a player given the PlayerType
   */
  public static Player create(ReadonlyReversi model, PlayerType type) {
    ReadonlyReversiModel providerModel = new ReadonlyModelAdapter(model);
    switch (type) {
      case HUMAN:
        return new HumanPlayer(model);
      case CAPTURE_MAX:
        return new MachinePlayer(model, new CaptureMax());
      case PROVIDER_FIRST_OPENING:
        return new MachineProviderPlayerAdapter(model,
                new StrategyAdapter(providerModel, new FirstOpening()));
      case PROVIDER_AVOID_NEAR_CORNER:
        return new MachineProviderPlayerAdapter(model,
              new StrategyAdapter(providerModel, new AvoidNearCorner()));
      case PROVIDER_GET_MOST_PIECES:
        return new MachineProviderPlayerAdapter(model,
                new StrategyAdapter(providerModel, new GetMostPieces()));
      case PROVIDER_GO_FOR_CORNERS:
        return new MachineProviderPlayerAdapter(model,
                new StrategyAdapter(providerModel, new GoForCorners()));
      default:
        throw new IllegalArgumentException("Invalid PlayerType");
    }
  }

}
