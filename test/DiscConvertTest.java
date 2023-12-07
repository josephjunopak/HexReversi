import org.junit.Assert;
import org.junit.Test;

import reversi.model.Coord;
import reversi.model.PlayerPiece;
import reversi.provider.model.Disc;
import reversi.provider.model.DiscConversion;
import reversi.provider.model.DiscPosn;

public class DiscConvertTest {
  @Test
  public void testConvertCoordToDiscToCoord() {
    int boardSize = 7;
    Coord middle = Coord.coordAt(3, 3);
    Disc discP = new Disc(middle, PlayerPiece.EMPTY, 7);
    Assert.assertEquals(middle, DiscConversion.toCoord(discP, boardSize));
    System.out.println(discP.getR() + " " + discP.getQ() + " " + discP.getZ());
  }

  @Test
  public void testConvertCoordToDiscToCoordAbove() {
    int boardSize = 7;
    Coord middle = Coord.coordAt(2, 4);
    Disc discP = new Disc(middle, PlayerPiece.EMPTY, 7);
    Assert.assertEquals(middle, DiscConversion.toCoord(discP, boardSize));
    System.out.println(discP.getR() + " " + discP.getQ() + " " + discP.getZ());
  }

  @Test
  public void testConvertCoordToDiscToCoordBelow() {
    int boardSize = 7;
    Coord middle = Coord.coordAt(5, 4);
    Disc discP = new Disc(middle, PlayerPiece.EMPTY, 7);
    Assert.assertEquals(middle, DiscConversion.toCoord(discP, boardSize));
    System.out.println(discP.getR() + " " + discP.getQ() + " " + discP.getZ());
  }

}