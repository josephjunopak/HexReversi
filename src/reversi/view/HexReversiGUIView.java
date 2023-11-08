package reversi.view;

import javax.swing.*;

import reversi.model.ReadonlyReversi;

public class HexReversiGUIView extends JFrame implements GUIView  {

  private final HexReversiPanel panel;

  public HexReversiGUIView(ReadonlyReversi model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new HexReversiPanel(model);
    this.add(panel);
    this.pack();
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }
}
