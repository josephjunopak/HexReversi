package reversi.provider.view;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * Represents a hexagon.
 */
public class Hexagon extends Path2D.Double {
  private int rowNum;
  private int index;

  /**
   * Constructs a hexagon.
   * @param sideLength length of a side.
   * @param r row number.
   * @param i index in row.
   */
  public Hexagon(double sideLength, int r, int i) {
    this.rowNum = r;
    this.index = i;

    //calculated the height and the half size of a given hexagon
    double h = sideLength * Math.sqrt(3);
    double halfSize = sideLength / 2.0;

    //moves to the top point and creates the rest of the hexagon
    moveTo(0, 0);

    lineTo(sideLength, 0);
    lineTo(sideLength + halfSize, h / 2);
    lineTo(sideLength, h);
    lineTo(0, h);
    lineTo(-halfSize, h / 2);
    closePath();

    AffineTransform transform = new AffineTransform();
    transform.rotate(Math.toRadians(90), halfSize, h / 2);
    this.transform(transform);
  }

  public int getRowNum() {
    return this.rowNum;
  }

  public int getIndex() {
    return this.index;
  }

}
