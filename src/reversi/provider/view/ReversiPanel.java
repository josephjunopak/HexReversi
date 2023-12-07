package reversi.provider.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import reversi.provider.model.Disc;
import reversi.provider.model.DiscType;
import reversi.provider.model.ReadonlyReversiModel;

/**
 * Represents a Reversi Panel.
 */
public class ReversiPanel extends JPanel {
  private ReadonlyReversiModel model;
  private List<Hexagon> hexagons;

  /**
   * Constructs a Reversi panel.
   *
   * @param m the model.
   */
  ReversiPanel(ReadonlyReversiModel m) {
    this.model = m;
    this.hexagons = new ArrayList<>();
    setFocusable(true);
  }

  /**
   * Gets total/max amount of hexagons.
   *
   * @return total/max amount of hexagons.
   */
  public int getNumHexagons() {
    int num = 0;
    for (List<Disc> rows : this.model.getBoard()) {
      for (Disc d : rows) {
        num++;
      }
    }
    return num;
  }

  /**
   * Resets the hexagons so they are not duplicated.
   */
  public void resetHexagons() {
    if (this.hexagons.size() > this.getNumHexagons()) {
      this.hexagons = new ArrayList<>();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.setBackground(Color.DARK_GRAY);
    Graphics2D g2d = (Graphics2D) g;
    if (!this.model.isGameOver()) {

      for (int r = 0; r < this.model.getBoardSize(); r++) {
        drawRow(g2d, r);
      }
      int y = (int) (this.hexagons.get(hexagons.size() - 1).getBounds2D().getMaxY() + 15);
      if (this.model.getCurrentTurn() == DiscType.BLACK) {
        g2d.setColor(Color.BLACK);
        g2d.drawString("Player 1's Turn", 15, 30);
      } else {
        g2d.setColor(Color.WHITE);
        g2d.drawString("Player 2's Turn", 15, 30);
      }

      g2d.setColor(Color.BLACK);
      g2d.drawString("Player 1's Score: " + this.model.getPlayer1Score(), 15, y);
      g2d.setColor(Color.WHITE);
      g2d.drawString("Player 2's Score: " + this.model.getPlayer2Score(), 15, y + 20);
    } else {
      this.winnerMessage(g2d);
    }
  }

  /**
   * Draws the final message when the game is over,
   * the message states who won or if it was a tie.
   *
   * @param g2d given Graphics2D
   */
  public void winnerMessage(Graphics2D g2d) {
    String text = "";
    try {
      DiscType winner = this.model.getWinner().getDiscPiece();
      if (winner == DiscType.BLACK) {
        g2d.setColor(Color.BLACK);
        text = "Player 1 Won!";
      } else if (winner == DiscType.WHITE) {
        g2d.setColor(Color.WHITE);
        text = "Player 2 Won!";
      }
    } catch (IllegalStateException e) {
      g2d.setColor(Color.CYAN);
      text = "Tie Game!";
    }
    FontMetrics textSize = g2d.getFontMetrics();
    int x = (this.getSize().width - textSize.stringWidth(text)) / 2;
    int y = (this.getSize().height - textSize.getHeight()) / 2;
    g2d.drawString(text, x, y);
  }

  /**
   * Draws a hexagon.
   *
   * @param g2d    given Graphics2D
   * @param x      x coordinate to move to
   * @param y      y coordinate to move to
   * @param d      disc to draw
   * @param rowNum disc row num
   * @param index  disc index
   */
  public void drawHexagon(Graphics2D g2d, int x, int y, Disc d, int rowNum, int index) {
    Hexagon hexagon = new Hexagon(40, rowNum, index);
    Hexagon newHexagon = new Hexagon(40, rowNum, index);

    AffineTransform originalTransform = g2d.getTransform();
    g2d.translate(x, y);

    if (d.getType() != DiscType.CLICKED) {
      g2d.setColor(Color.LIGHT_GRAY);
    } else {
      g2d.setColor(Color.CYAN);
    }
    g2d.fill(hexagon);
    g2d.setColor(Color.BLACK);
    g2d.draw(hexagon);
    double centerX = hexagon.getBounds2D().getCenterX();
    double centerY = hexagon.getBounds2D().getCenterY();
    int circleRadius = 20;
    AffineTransform newTransform = new AffineTransform();
    newTransform.translate(x, y);

    hexagon.transform(newTransform);
    boolean exists = false;
    for (Hexagon hex : hexagons) {
      if (hex.getRowNum() == rowNum && hex.getIndex() == index) {
        exists = true;
        break;
      }
    }

    if (!exists) {
      hexagons.add(hexagon);
    }


    if (d.getType() == DiscType.BLACK) {
      g2d.setColor(Color.BLACK);
      g2d.fillOval((int) (centerX - circleRadius), (int) (centerY - circleRadius),
              2 * circleRadius, 2 * circleRadius);
    } else if (d.getType() == DiscType.WHITE) {
      g2d.setColor(Color.WHITE);
      g2d.fillOval((int) (centerX - circleRadius), (int) (centerY - circleRadius),
              2 * circleRadius, 2 * circleRadius);
    }
    g2d.setTransform(originalTransform);
  }


  /**
   * Draws row.
   *
   * @param g2d    given Graphics2D
   * @param rowNum row number to draw
   */
  public void drawRow(Graphics2D g2d, int rowNum) {
    double horizDist = Math.sqrt(3) * 40;
    double vertDist = 60;
    int x;
    int size = this.model.getBoard().get(rowNum).size();
    if (size % 2 == 0) {
      x = (int) (horizDist + ((horizDist * (this.model.getBoardSize()) / 2))
              - (horizDist * (size / 2)));
    } else {
      x = (int) ((horizDist / 2) + ((horizDist * (this.model.getBoardSize()) / 2))
              - (horizDist * (size / 2)));
    }
    int y = (int) (40 + (vertDist * rowNum));
    for (int i = 0; i < size; i++) {
      drawHexagon(g2d, x, y, this.model.getBoard().get(rowNum).get(i), rowNum, i);
      x += (int) (Math.sqrt(3) * 40);
    }
  }


  /**
   * Gets lists of hexagons on view.
   *
   * @return lists of hexagons on view.
   */
  public List<Hexagon> getHexagons() {
    return this.hexagons;
  }


}

