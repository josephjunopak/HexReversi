package reversi.provider.model;

import reversi.model.Coord;

public interface IDisc {

  /**
   * Returns the disc's type.
   *
   * @return the disc's type
   */
  DiscType getType();

  /**
   * Returns the disc's position.
   *
   * @return the disc's position
   */
  DiscPosn getPosn();

  /**
   * Returns true if they're the same type, false otherwise.
   *
   * @return true if they're the same type, false otherwise
   */
  boolean sameType(DiscType other);

  /**
   * Updates the disc type and returns it.
   *
   * @param t the new type
   * @return the disc with its new type
   */
  IDisc updateType(DiscType t);

  /**
   * Gets the disc's the q-coordinate.
   *
   * @return the disc's the q-coordinate
   */
  int getQ();

  /**
   * Gets the disc's the r-coordinate.
   *
   * @return the disc's the r-coordinate
   */
  int getR();

  /**
   * Gets the disc's the z-coordinate.
   *
   * @return the disc's the z-coordinate
   */
  int getZ();
}
