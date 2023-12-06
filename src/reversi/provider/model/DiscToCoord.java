package reversi.provider.model;

import reversi.model.Coord;

public class DiscToCoord {
  public static Coord toCoord(IDisc disc, int boardHeight) {
    int offset = (boardHeight + 1) / 2;
    int row = disc.getR() + offset;
    int col = (disc.getR() > 0) ? disc.getQ() + offset : offset - disc.getZ();
    return Coord.coordAt(row, col);
  }
}
