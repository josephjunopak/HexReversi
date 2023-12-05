package reversi.provider.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.reversi.controller.PlayerListener;
import cs3500.reversi.model.Disc;
import cs3500.reversi.model.ReadonlyReversiModel;

/**
 * An implementation of {@link IView}
 * that uses Java Swing to display a Reversi game and displays error messages if needed.
 */
public class ReversiGraphicsView extends JFrame implements IView {
  private ReversiPanel reversiPanel;
  private ReadonlyReversiModel model;
  private int width;
  private int height;
  private List<PlayerListener> listeners = new ArrayList<>();


  /**
   * Constructs a ReversiGraphicsView.
   *
   * @param m model view is based on.
   */
  public ReversiGraphicsView(ReadonlyReversiModel m) {
    super();
    int bs = m.getBoardSize() + 1;
    this.width = (bs * 80);
    this.height = (int) (bs * (Math.sqrt(3) * 40) + 40);
    this.setTitle("Reversi");
    this.setSize(width, height);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.reversiPanel = new ReversiPanel(m);
    this.reversiPanel.setPreferredSize(new Dimension(width, height));
    JScrollPane reversiPane = new JScrollPane(reversiPanel);
    this.add(reversiPane, BorderLayout.CENTER);
    setVisible(true);
  }

  @Override
  public ReversiPanel getReversiPanel() {
    return this.reversiPanel;
  }

  @Override
  public List<PlayerListener> getListeners() {
    return this.listeners;
  }

  @Override
  public void setCanvas() {
    this.reversiPanel.setPreferredSize(new Dimension(width, height));
    this.reversiPanel.resetHexagons();
    this.reversiPanel.revalidate();
    this.reversiPanel.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void showError(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    reversiPanel.addMouseListener(listener);
  }

  @Override
  public void setKeyListener(KeyListener listener) {
    reversiPanel.addKeyListener(listener);
  }

  @Override
  public void briefPause() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void notifyUserMove(Disc d) {
    for (PlayerListener listener : this.listeners) {
      listener.flip(d);
    }
  }

  @Override
  public void notifyUserPass() {
    for (PlayerListener listener : this.listeners) {
      listener.pass();
    }
  }

  @Override
  public void addPlayerListener(PlayerListener l) {
    this.listeners.add(l);
  }

}
