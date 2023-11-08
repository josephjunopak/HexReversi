package reversi.view;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.geom.Point2D;

import reversi.model.ReadonlyReversi;

public class HexReversiPanel extends JPanel {
  private final ReadonlyReversi model;

  private boolean mouseIsDown;

  private static final int CELL_WIDTH = 80;

  public HexReversiPanel(ReadonlyReversi model) {
    this.model = Objects.requireNonNull(model);
//    MouseEventsListener listener = new MouseEventsListener();
//    this.addMouseListener(listener);
//    this.addMouseMotionListener(listener)
  }

  // size is the length of a side
  private void drawHexagon(Graphics2D g2d, double centerx, double centery, double size) {
    g2d.setColor(Color.black);
    g2d.setStroke(new BasicStroke(2f));
    AffineTransform oldTransform = g2d.getTransform();
    g2d.translate(centerx, centery);
    GeneralPath hexagon = new GeneralPath();
    hexagon.moveTo(-1 * Math.sqrt(3) * size / 2, (double) size / -2);
    hexagon.lineTo(-1 * Math.sqrt(3) * size / 2, (double) size / 2);
    hexagon.lineTo(0, size);
    hexagon.lineTo(Math.sqrt(3) * size / 2, (double) size / 2);
    hexagon.lineTo(Math.sqrt(3) * size / 2, - (double) size / 2);
    hexagon.lineTo(0, -1 * size);
    hexagon.lineTo(-1 * Math.sqrt(3) * size / 2, (double) size / -2);
    hexagon.closePath();
    g2d.draw(hexagon);
    g2d.setColor(Color.gray);
    g2d.fill(hexagon);
    g2d.setTransform(oldTransform);
  }

  private void drawReversiBoard(Graphics2D g2d, int size) {
    for (int row = 0; row < 10; row++) {
      int offset = CELL_WIDTH / -2 * (row - 1);
      for (int col = 0; col < 10; col++) {
        drawHexagon(g2d, col * CELL_WIDTH + offset,
                row * (1.5 * CELL_WIDTH / Math.sqrt(3)) + CELL_WIDTH / Math.sqrt(3),
                CELL_WIDTH / Math.sqrt(3));
      }
    }
  }

  private void drawShearReversiBoard(Graphics2D g2d, int size) {
    List<List<ReadonlyReversi.Player>> board = this.model.copyBoard();

    for (int y = 0; y < size; y++) {
      int x_offset = -1 * CELL_WIDTH * y + (size + 1) * CELL_WIDTH;
      int y_offset = CELL_WIDTH * y + size * CELL_WIDTH;
      for (int x = 0; x < this.model.getRowWidth(y); x++) {
        this.drawLogicalHexagon(g2d,
                new Point(2 * CELL_WIDTH * x + x_offset,
                        CELL_WIDTH * x + y_offset),
                CELL_WIDTH);
      }
    }
    for (int y = 0; y <= size; y++) {
      int x_offset = y * CELL_WIDTH + CELL_WIDTH;
      int y_offset = 2 * y * CELL_WIDTH + CELL_WIDTH * (2 * size);
      for (int x = 0; x < this.model.getRowWidth(y + size); x++) {
        this.drawLogicalHexagon(g2d,
                new Point(2 * CELL_WIDTH * x + x_offset,
                        CELL_WIDTH * x + y_offset),
                CELL_WIDTH);
      }
    }
  }

  private void drawLogicalHexagon(Graphics2D g2d, Point2D center, double size) {
    GeneralPath hexagon = new GeneralPath();
    AffineTransform oldTransform = g2d.getTransform();
    g2d.translate(center.getX(), center.getY());
    hexagon.moveTo(0, -1 * size);
    hexagon.lineTo(size, 0);
    hexagon.lineTo(size, 0);
    hexagon.lineTo(size, size);
    hexagon.lineTo(0, size);
    hexagon.lineTo(-1 * size, 0);
    hexagon.lineTo(-1 * size, -1 * size);
    hexagon.closePath();
    g2d.setColor(Color.gray);
    g2d.fill(hexagon);
    g2d.setColor(Color.black);
    g2d.setStroke(new BasicStroke(0.5f));
    g2d.draw(hexagon);
    g2d.setTransform(oldTransform);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    Rectangle bounds = this.getBounds();
    g2d.transform(transformLogicalToPhysical());
    drawReversiBoard(g2d, this.model.getBoardHeight() / 2);
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 750x750 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(750, 750);
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
    int size = model.getBoardHeight();
    return new Dimension(size * CELL_WIDTH,size * CELL_WIDTH);
  }

  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    return ret;
  }
//
//  private AffineTransform transformPhysicalToLogical() {
//    AffineTransform ret = new AffineTransform();
//    Dimension preferred = getPreferredLogicalSize();
//    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
//    ret.shear(0.5, 0);
//    ret.scale(1,  Math.sqrt(3));
//    ret.scale(1, -1);
//    return ret;
//  }

  /*
  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      HexReversiPanel.this.mouseIsDown = true;
      this.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      HexReversiPanel.this.mouseIsDown = false;
      if (HexReversiPanel.this.activeColorGuess != null) {
        for (ViewFeatures listener : HexReversiPanel.this.featuresListeners) {
          listener.selectedColor(JHexReversiPanel.this.activeColorGuess);
        }
      }
      HexReversiPanel.this.activeColorGuess = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      // This point is measured in actual physical pixels
      Point physicalP = e.getPoint();
      // For us to figure out which circle it belongs to, we need to transform it
      // into logical coordinates
      Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
      // TODO: Figure out whether this location is inside a circle, and if so, which one
    }
  }
   */

}
