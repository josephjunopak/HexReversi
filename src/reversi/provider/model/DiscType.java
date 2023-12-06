package reversi.provider.model;

/**
 * Represents a disc type.
 */
public enum DiscType {
  BLACK, WHITE, EMPTY, CLICKED, BORDER;

  @Override
  public String toString() {
    if (this == BLACK) {
      return "X";
    } else if (this == WHITE) {
      return "O";
    } else {
      return "_";
    }
  }

  /**
   * Returns the opposite disc type.
   *
   * @return black if the disc is white, white otherwise.
   * @throws IllegalArgumentException if the type is blank
   */
  public DiscType opposite() {
    if (this == BLACK) {
      return DiscType.WHITE;
    } else if (this == WHITE) {
      return DiscType.BLACK;
    } else {
      throw new IllegalArgumentException("There is no opposite of an empty disc");
    }
  }
}