package reversi;

import reversi.model.HexReversi;
import reversi.model.Reversi;
import reversi.view.GUIView;
import reversi.view.HexReversiGUIView;

public class HexReversiGame {
  public static void main(String[] args) {
    Reversi model = new HexReversi();
    model.startGame(6);
    GUIView view = new HexReversiGUIView(model);
    view.display(true);
  }
}
