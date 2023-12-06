package reversi.provider.model;

import reversi.model.Coord;

public class Disc implements IDisc {
  DiscType type;
  DiscPosn posn;

  @Override
  public DiscType getType() {
    return type;
  }

  @Override
  public DiscPosn getPosn() {
    return posn;
  }

  @Override
  public boolean sameType(DiscType other) {
    return false;
  }

  @Override
  public IDisc updateType(DiscType t) {
    return null;
  }

  @Override
  public int getQ() {
    return this.posn.getQ();
  }

  @Override
  public int getR() {
    return this.posn.getR();
  }

  @Override
  public int getZ() {
    return this.posn.getZ();
  }

}
