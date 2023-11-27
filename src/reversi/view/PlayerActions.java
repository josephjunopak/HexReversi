package reversi.view;


import reversi.model.Coord;

public interface PlayerActions {
  void passMove();

  void playMove(Coord coord);
}

/*
In view:
Features feature;

... on button press -> features.passMove();
                 or -> features.playMove(selected_Cell);

 */