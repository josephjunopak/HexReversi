package reversi;

import reversi.model.HexReversi;
import reversi.model.Reversi;
import reversi.view.GUIView;
import reversi.view.HexReversiGUIView;
import reversi.controller.Player;
import reversi.controller.Controller;

/**
 * Main points of entry for a game of Hex Reversi.
 * Creates the model and starts the game.
 */
public class HexReversiGame {
  /**
   * Main method for a game of Hex Reversi.
   * Initializes the game as well as the GUI view.
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Number of arguments must be 2");
    }

    PlayerCreator.PlayerType playerType1 = PlayerCreator.PlayerType.valueOf(args[0].toUpperCase());
    PlayerCreator.PlayerType playerType2 = PlayerCreator.PlayerType.valueOf(args[1].toUpperCase());

    Reversi model = new HexReversi(6);
    GUIView viewPlayer1 = new HexReversiGUIView(model);
    GUIView viewPlayer2 = new HexReversiGUIView(model);

    Player player1 = PlayerCreator.create(model, playerType1);
    Player player2 = PlayerCreator.create(model, playerType2);

    Controller controller1 = new Controller(model, player1, viewPlayer1);
    Controller controller2 = new Controller(model, player2, viewPlayer2);
    model.startGame();
    viewPlayer1.display(true);
    viewPlayer2.display(true);
  }
}
