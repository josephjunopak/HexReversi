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

    int hexagonX = 100;
    int hexagonY = 50;

    for (List<ReadonlyReversi.Player> cells : board) {
      for (ReadonlyReversi.Player cell : cells) {
        drawHexagon(g2d, new Point(hexagonX,hexagonY), 50 / Math.sqrt(3));
        hexagonX += 50;
      }
      hexagonY += 40;
      hexagonX = 75;
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    Rectangle bounds = this.getBounds();
    drawReversiBoard(g2d, 100);
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
    return new Dimension(40, 40);
  }


}
