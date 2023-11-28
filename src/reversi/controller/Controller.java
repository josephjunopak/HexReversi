package reversi.controller;

import reversi.model.Coord;
import reversi.model.ModelFeatures;
import reversi.model.Reversi;
import reversi.view.GUIView;
import reversi.view.PlayerActions;

/**
 * This class handles interactions from both the view and the model by implementing
 * both features interfaces.
 */
public class Controller implements PlayerActions, ModelFeatures {
  private final Reversi model;

  private final GUIView view;

  private final Player player;

  public Controller(Reversi model, Player player, GUIView view) {
    this.model = model;
    this.view = view;
    this.player = player;
    model.addPlayer(player);
    this.model.addFeatures(this);
    this.view.addFeatureListener(this);
    this.player.initializePiece();
    this.player.addListener(this);
    this.view.setTitle(this.player.toString());
  }

  @Override
  public void passMove() {
    if (this.model.getCurrentPlayer() == this.player.getPiece()) {
      System.out.println("Turn passed");
      try {
        this.model.passTurn();
        System.out.println("Turn passed");
      }
      catch (IllegalStateException e) {
        view.showMessage("Game hasn't started");
      }
    }
    else {
      view.showMessage("It's not your turn.");
    }
    this.view.refresh();
  }

  @Override
  public void playMove(Coord coord) {
    if (this.model.getCurrentPlayer() == this.player.getPiece()) {
      System.out.println("move made");
      try {
        this.model.makeMove(coord);
        System.out.println("Move made");
      }
      catch (IllegalArgumentException e) {
        view.showMessage("Illegal move for " + this.player.toString());
      }
      catch (IllegalStateException e) {
        view.showMessage("Invalid move for " + this.player.toString());
      }
    }
    else {
      view.showMessage("It's not your turn.");
    }
    this.view.refresh();
  }

  @Override
  public void yourTurn() {
    if (this.model.getCurrentPlayer() == this.player.getPiece()) {
      player.requestMove();
    }
    this.view.refresh();
    if (this.model.isGameOver()) {
      view.showMessage("Game is over");
    }
  }
}
