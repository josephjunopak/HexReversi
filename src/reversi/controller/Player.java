package reversi.controller;

import reversi.model.PlayerPiece;
import reversi.view.PlayerActions;

public interface Player {

  void requestMove();

  void addListener(PlayerActions features);

  PlayerPiece getPiece();

  void initializePiece();

  String toString();
}
