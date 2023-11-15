package reversi;

import reversi.model.HexReversi;
import reversi.model.Reversi;
import reversi.view.GUIView;
import reversi.view.HexReversiGUIView;

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
    Reversi model = new HexReversi();
    model.startGame(6);
    GUIView view = new HexReversiGUIView(model);
    view.display(true);
  }
}
