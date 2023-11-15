package reversi.view;

import javax.swing.JFrame;
import reversi.model.ReadonlyReversi;

/**
 * An implementation of the GUIView for a game of Reversi.
 * This GUI contains a JPanel to represent the game state of our board.
 */
public class HexReversiGUIView extends JFrame implements GUIView  {

  /**
   * Constructor for our GUIView which takes this modesl
   * and creates a JPanel.
   *
   * @param model Reversi model
   */
  public HexReversiGUIView(ReadonlyReversi model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    HexReversiPanel panel = new HexReversiPanel(model);
    this.add(panel);
    this.pack();
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }
}
