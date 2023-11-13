package reversi.view;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import reversi.model.ReadonlyReversi;

public class HexReversiPanel extends JPanel {
  private final ReadonlyReversi model;

  private boolean mouseIsDown;
  private final int boardSize;

  private static final double CELL_WIDTH = 20;
  private static final double CELL_HEIGHT = CELL_WIDTH * 2 / Math.sqrt(3);

  public HexReversiPanel(ReadonlyReversi model) {
    this.model = Objects.requireNonNull(model);
    this.boardSize = (this.model.getBoardHeight() + 1) / 2;
  }

  private void drawHexagon(Graphics2D g2d, Point2D center, double size) {
    g2d.setColor(Color.black);
    g2d.setStroke(new BasicStroke(2f));
    AffineTransform oldTransform = g2d.getTransform();
    g2d.translate(center.getX(), center.getY());
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

  private void drawPlayer(Graphics2D g2d, Point2D center, ReadonlyReversi.Player player) {
    if (player == ReadonlyReversi.Player.BLACK) {
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
        this.drawHexagon(g2d, this.convertIndexToCoords(row, col), CELL_HEIGHT / 2);
        ReadonlyReversi.Player cell =  this.model.getPlayerAtCell(row, col);
        if (cell != ReadonlyReversi.Player.EMPTY) {
          this.drawPlayer(g2d, this.convertIndexToCoords(row, col), cell);
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
    ret.scale(preferred.getWidth()/ getWidth(), preferred.getHeight() / getHeight());
    return ret;
  }

  private Point2D convertIndexToCoords(int row, int col) {
    double x_offset = (Math.abs(row - this.boardSize + 1) + 1) * CELL_WIDTH / 2;
    return new Point2D.Double(col * CELL_WIDTH + x_offset,
    row * CELL_HEIGHT * 3 / 4 + CELL_HEIGHT / 2);
  }

  private Point2D convertCoordsToIndex(double x, double y) {
    int row = (int) Math.round((y - CELL_HEIGHT / 2) * 4 / (CELL_HEIGHT * 3));
    double x_offset = (Math.abs(row - this.boardSize + 1) + 1) * CELL_WIDTH / 2;
    int col = (int) Math.round((x - x_offset) / CELL_WIDTH);
    return new Point(row, col);
  }

}
