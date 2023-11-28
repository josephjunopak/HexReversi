package reversi.view;

import javax.swing.*;

import reversi.model.ReadonlyReversi;

/**
 * An implementation of the GUIView for a game of Reversi.
 * This GUI contains a JPanel to represent the game state of our board.
 */
public class HexReversiGUIView extends JFrame implements GUIView  {
  private final HexReversiPanel panel;

  /**
   * Constructor for our GUIView which takes this models
   * and creates a JPanel.
   *
   * @param model Reversi model
   */
  public HexReversiGUIView(ReadonlyReversi model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new HexReversiPanel(model);
    this.add(this.panel);
    this.pack();
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void addFeatureListener(PlayerActions features) {
    this.panel.addFeatureListener(features);
  }

  public void showMessage(String msg) {
    JOptionPane.showMessageDialog(this.panel, msg);
  }

  @Override
  public void refresh() {
    super.revalidate();
    super.repaint();
  }
}
