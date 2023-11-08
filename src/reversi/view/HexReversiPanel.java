package reversi.view;

import java.awt.geom.AffineTransform;
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

  public HexReversiPanel(ReadonlyReversi model) {
    this.model = Objects.requireNonNull(model);
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

  private void drawReversiBoard(Graphics2D g2d, int size) {
    List<List<ReadonlyReversi.Player>> board = this.model.copyBoard();

    int hexagonX = 50;
    int hexagonY = 50;

    for (List<ReadonlyReversi.Player> cells : board) {
      for (ReadonlyReversi.Player cell : cells) {
        drawHexagon(g2d, new Point(hexagonX,hexagonY), 50 / Math.sqrt(3));
        hexagonX += 50;
      }
      hexagonY += 50;
      hexagonX = 50;
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
    g2d.setColor(Color.black);
    g2d.draw(hexagon);
    g2d.setColor(Color.gray);
    g2d.fill(hexagon);
    g2d.setTransform(oldTransform);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    Rectangle bounds = this.getBounds();
    g2d.transform(transformLogicalToPhysical());
//    drawReversiBoard(g2d, 100);
    for (int i = 0; i < 4; i++) {
      int offset = -10 * i + 50;
      for (int j = 0; j < 4 + i; j++) {
        this.drawLogicalHexagon(g2d, new Point(20 * j + offset, 10 * j + 10 * i + 50), 10);
      }
    }
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
    return new Dimension(200, 200);
  }

  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
//    ret.translate(0, getHeight());
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
//    ret.scale(1, -1);
//    ret.scale(Math.sqrt(3) / 2, 1);
//    ret.shear(0, -0.5);
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

}
