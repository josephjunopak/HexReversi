package reversi.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import reversi.model.Coord;
import reversi.model.PlayerPiece;
import reversi.model.ReadonlyReversi;


/**
 * This class represents a panel to showcase our game of Reversi.
 * Contains the logic on how the panel should be displayed.
 */
public class HexReversiPanel extends JPanel {
  private final ReadonlyReversi model;

  private boolean mouseIsDown;
  private final int boardSize;
  private final List<PlayerActions> featuresListeners;
  // tracks the indices of the selected cell with where x is column, y is row as integers.
  private Coord selectedCell;

  private static final double CELL_WIDTH = 20;
  private static final double CELL_HEIGHT = CELL_WIDTH * 2 / Math.sqrt(3);

  /**
   * The constructor for a HexReversiPanel, which takes in a read-only version of the model to
   * parse for generating the GUI. This constructor also instantiates the event listeners for the
   * Panel.
   *
   * @param model ReadOnlyModel of Reversi
   */
  public HexReversiPanel(ReadonlyReversi model) {
    this.model = Objects.requireNonNull(model);
    this.boardSize = (this.model.getBoardHeight() + 1) / 2;
    this.featuresListeners = new ArrayList<>();
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    this.setFocusable(true); // make the panel focusable
    KeyEventsListener keyListener = new KeyEventsListener();
    this.addKeyListener(keyListener);
  }

  private void drawHexagon(Graphics2D g2d, Point2D center, double size, Color fill) {
    g2d.setColor(Color.black);
    g2d.setStroke(new BasicStroke(2f));
    AffineTransform oldTransform = g2d.getTransform();
    g2d.translate(center.getX(), center.getY());
    GeneralPath hexagon = new GeneralPath();
    hexagon.moveTo(-1 * Math.sqrt(3) * size / 2, size / -2);
    hexagon.lineTo(-1 * Math.sqrt(3) * size / 2, size / 2);
    hexagon.lineTo(0, size);
    hexagon.lineTo(Math.sqrt(3) * size / 2, size / 2);
    hexagon.lineTo(Math.sqrt(3) * size / 2, size / -2);
    hexagon.lineTo(0, -1 * size);
    hexagon.lineTo(-1 * Math.sqrt(3) * size / 2,  size / -2);
    hexagon.closePath();
    g2d.draw(hexagon);
    g2d.setColor(fill);
    g2d.fill(hexagon);
    g2d.setTransform(oldTransform);
  }

  private void drawPlayer(Graphics2D g2d, Point2D center, PlayerPiece player) {
    if (player == PlayerPiece.BLACK) {
      g2d.setColor(Color.black);
    }
    else {
      g2d.setColor(Color.white);
    }
    AffineTransform oldTransform = g2d.getTransform();
    g2d.translate(center.getX(), center.getY());
    Shape circle = new Ellipse2D.Double(
            -CELL_WIDTH / 4,     // left
            -CELL_WIDTH / 4,     // top
            2 * CELL_WIDTH / 4,  // width
            2 * CELL_WIDTH / 4); // height
    g2d.fill(circle);
    g2d.setTransform(oldTransform);
  }

  private void drawReversiBoard(Graphics2D g2d) {
    for (int row = 0; row < this.model.getBoardHeight(); row++) {
      for (int col = 0; col < this.model.getRowWidth(row); col++) {
        Point2D center = this.convertIndexToCoords(Coord.coordAt(row, col));
        this.drawHexagon(g2d, center, CELL_HEIGHT / 2, Color.gray);
        PlayerPiece cell =  this.model.getPlayerAtCell(Coord.coordAt(row, col));
        if (cell != PlayerPiece.EMPTY) {
          this.drawPlayer(g2d, center, cell);
        }
        else if (this.selectedCell != null
                && this.selectedCell.col == col
                && this.selectedCell.row == row) {
          this.drawHexagon(g2d, center, CELL_HEIGHT / 2, Color.cyan);
        }
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());
    drawReversiBoard(g2d);
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 750x750 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(750, (int) Math.round(750 * Math.sqrt(3) / 2));
  }

  /**
   * Conceptually, we can choose a different coordinate system
   * and pretend that our panel is 40x40 "cells" big. You can choose
   * any dimension you want here, including the same as your physical
   * size (in which case each logical pixel will be the same size as a physical
   * pixel, but perhaps your calculations to position things might be trickier)
   * @return Our preferred *logical* size.
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension((int) Math.round(this.model.getBoardHeight() * CELL_WIDTH),
            (int) Math.round(this.model.getBoardHeight() * CELL_HEIGHT * 3 / 4 + CELL_HEIGHT / 4));
  }

  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    return ret;
  }

  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    return ret;
  }

  private Point2D convertIndexToCoords(Coord coords) {
    double x_offset = (Math.abs(coords.row - this.boardSize + 1) + 1) * CELL_WIDTH / 2;
    return new Point2D.Double(coords.col * CELL_WIDTH + x_offset,
            coords.row * CELL_HEIGHT * 3 / 4 + CELL_HEIGHT / 2);
  }

  private Coord convertCoordsToIndex(Point2D point) {
    int row = (int) Math.round((point.getY() - CELL_HEIGHT / 2) * 4 / (CELL_HEIGHT * 3));
    double x_offset = (Math.abs(row - this.boardSize + 1) + 1) * CELL_WIDTH / 2;
    int col = (int) Math.round((point.getX() - x_offset) / CELL_WIDTH);
    return Coord.coordAt(row, col);
  }

  private boolean isCellValid(Coord cellIndex) {
    if (cellIndex.row < 0 || cellIndex.row >= this.model.getBoardHeight()) {
      return false;
    }
    return cellIndex.col >= 0 && cellIndex.col < this.model.getRowWidth(cellIndex.row);
  }

  public void addFeatureListener(PlayerActions features) {
    this.featuresListeners.add(features);
  }

  private class KeyEventsListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
      // There is no action to be done on a keyTyped event, but the function must be overridden
      // because this calss implements KeyListener.
    }

    @Override
    public void keyPressed(KeyEvent e) {
      int keyCode = e.getKeyCode();
      if (keyCode == KeyEvent.VK_ENTER) {
        PlayerPiece player = HexReversiPanel.this.model.getCurrentPlayer();
        if (selectedCell != null) {
          for (PlayerActions actions: featuresListeners) {
            actions.playMove(selectedCell);
          }
//          System.out.println(player + " moves to " + selectedCell);
        }
        else {
          System.out.println("Please select a cell");
        }
      }
      if (keyCode == KeyEvent.VK_P) {
        PlayerPiece player = HexReversiPanel.this.model.getCurrentPlayer();
        for (PlayerActions actions: featuresListeners) {
          actions.passMove();
        }
//        System.out.println(player + " passes");
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      // There is no action to be done on a keyReleased event, but the function must be overridden
      // because this calss implements KeyListener.
    }
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      HexReversiPanel.this.mouseIsDown = true;

      Point physicalPoint = e.getPoint();

      Coord cell_indices = HexReversiPanel.this.convertCoordsToIndex(
              transformPhysicalToLogical().transform(physicalPoint, null));
      if (cell_indices.equals(HexReversiPanel.this.selectedCell)
              || !HexReversiPanel.this.isCellValid(cell_indices)) {
        selectedCell = null;
      }
      else {
        selectedCell = cell_indices;
      }

      System.out.println("Coordinates of cell:" + HexReversiPanel.this.selectedCell);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      HexReversiPanel.this.mouseIsDown = false;
      HexReversiPanel.this.repaint();
    }
  }

}
