package reversi.controller;


import reversi.model.Coord;

public interface Features {
  void passMove();

  void playMove(Coord coord);
}

/*
In view:
Features feature;

... on button press -> features.passMove();
                 or -> features.playMove(selected_Cell);

 */