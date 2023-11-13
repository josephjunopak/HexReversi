package reversi.model;

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
    return this.row + ", " + this.col;
  }

}
