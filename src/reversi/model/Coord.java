package reversi.model;

/**
 * Represents coordinates in the game of Reversi.
 * Each board will consist of rows and columns.
 * The coordinates containing information of the row and col.
 * The row is the top-down oriented row the 0th row is the 1st row.
 * The col is the left-right oriented column
 * where the 0th col is the left-most cell.<br>
 * Hex-Grid Example:<br>
 * rows&nbsp;&nbsp;&nbsp;cols<br>
 * &nbsp;&nbsp;0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0 1<br>
 * &nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0 1 2<br>
 * &nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0 1
 */
public final class Coord {
  public final int row;
  public final int col;

  private Coord(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public static Coord coordAt(int row, int col) {
    return new Coord(row, col);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }

    Coord coordinates = (Coord) obj;

    return (coordinates.row == this.row && coordinates.col == this.col);
  }

  @Override
  public int hashCode() {
    return this.row * this.col + row;
  }

  @Override
  public String toString() {
    return "row:" + this.row + ", col: " + this.col;
  }

}
