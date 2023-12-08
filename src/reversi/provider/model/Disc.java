package reversi.provider.model;

import reversi.model.Coord;
import reversi.model.PlayerPiece;

public class Disc implements IDisc {
  private final PlayerPiece piece;
  private final Coord coord;

  private final int boardHeight;
  private final DiscPosn discPosn;

  public Disc(Coord coord, PlayerPiece piece, int boardHeight) {
    this.piece = piece;
    this.coord = coord;
    this.boardHeight = boardHeight;
    this.discPosn = DiscConversion.toPosn(this.coord, this.boardHeight);
  }

  @Override
  public DiscType getType() {
    return DiscConversion.toDiscType(this.piece);
  }

  @Override
  public DiscPosn getPosn() {
    return this.discPosn;
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
    return this.discPosn.getQ();
  }

  @Override
  public int getR() {
    return this.discPosn.getR();
  }

  @Override
  public int getZ() {
    return this.discPosn.getZ();
  }

  public PlayerPiece getPiece() {
    return this.piece;
  }

  public Coord getCoord() {
    return this.coord;
  }
}
