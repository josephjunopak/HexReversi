package reversi.provider.model;

import java.util.Objects;
import reversi.model.Coord;

/**
 * Represents the position of a disc.
 */
public class DiscPosn {
  private int q;
  private int r;
  private int z;

  /**
   * Constructs a DiscPosn.
   *
   * @param qPos the q-coordinate
   * @param rPos the r-coordinate
   * @param zPos the z-coordinate
   */
  public DiscPosn(int qPos, int rPos, int zPos) {
    this.q = qPos;
    this.r = rPos;
    this.z = zPos;
  }

  /**
   * Gets the disc's the q-coordinate.
   *
   * @return the disc's the q-coordinate
   */
  public int getQ() {
    return this.q;
  }

  /**
   * Gets the disc's the r-coordinate.
   *
   * @return the disc's the r-coordinate
   */
  public int getR() {
    return this.r;
  }

  /**
   * Gets the disc's the z-coordinate.
   *
   * @return the disc's the z-coordinate
   */
  public int getZ() {
    return this.z;
  }

  @Override
  public String toString() {
    return "(" + this.q + ", " + this.r + ", " + this.z + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DiscPosn)) {
      return false;
    }
    DiscPosn other = (DiscPosn) o;
    return this.q == other.q && this.r == other.r
            && this.z == other.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.q, this.r, this.z);
  }

}
