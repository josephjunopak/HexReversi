package reversi.provider.model;

import reversi.model.Coord;

public class DiscConversion {
  public static Coord toCoord(IDisc disc, int boardHeight) {
    int offset = (boardHeight - 1) / 2;
    int row = disc.getR() + offset;
    int col = (disc.getR() > 0) ? disc.getQ() + offset : offset - disc.getZ();
    return Coord.coordAt(row, col);
  }

  public static DiscPosn toPosn(Coord coord, int boardHeight) {
    int offset = (boardHeight - 1) / 2;
    int r = coord.row - offset;
    int q, z;
    if (coord.row > offset) {
      q = coord.col - offset;
      z = -(r + q);
    }
    else {
      z = offset - coord.col;
      q = -(r + z);
    }
    return new DiscPosn(q, r, z);
  }


  public static DiscType toDiscType(PlayerPiece piece) {
    switch (piece) {
      case EMPTY:
        return DiscType.EMPTY;
      case BLACK:
        return DiscType.BLACK;
      case WHITE:
        return DiscType.WHITE;
      default:
        throw new RuntimeException("Invalid piece enum");
    }
  }

  public static PlayerPiece toPlayerPiece(DiscType discType) {
    switch (discType) {
      case EMPTY:
        return PlayerPiece.EMPTY;
      case BLACK:
        return PlayerPiece.BLACK;
      case WHITE:
        return PlayerPiece.WHITE;
      default:
        throw new RuntimeException("Invalid piece enum");
    }
  }

}
