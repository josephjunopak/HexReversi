package reversi.view;


import reversi.model.Coord;

public interface PlayerActions {
  void passMove();

  void playMove(Coord coord);
}
