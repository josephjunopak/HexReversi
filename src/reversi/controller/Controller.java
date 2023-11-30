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

  private boolean gameIsOver;

  private static final String GAME_OVER_MSG = "Game is over.";

  /**
   * Constructor to create a Controller for Reversi.
   *
   * @param model Full model
   * @param player Player that is controlled by this controller
   * @param view GUI view
   */
  public Controller(Reversi model, Player player, GUIView view) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.model.addPlayer(player);
    this.model.addFeatures(this);
    this.view.addFeatureListener(this);
    this.player.initializePiece();
    this.player.addListener(this);
    this.view.setTitle(this.player.toString());
    gameIsOver = false;
  }

  /**
   * Handles the action of passing on a given turn.
   */
  @Override
  public void passMove() {
    if (this.model.isGameOver()) {
      view.showMessage(GAME_OVER_MSG);
      return;
    }

    if (this.model.getCurrentPlayer() == this.player.getPiece()) {
      try {
        this.model.passTurn();
      }
      catch (IllegalStateException e) {
        view.showMessage("Game hasn't started.");
      }
    }
    else {
      view.showMessage("It's not your turn.");
    }
    this.view.refresh();
  }

  /**
   * This method is called by the view when a player tries to place a piece on the board.
   * Based on the specified coordinates. If the tried move throws an exception,
   * the view will give notify the user that an illegal move has tried to be played.
   *
   * @param coord The coordinates where the player has placed their piece.
   */
  @Override
  public void playMove(Coord coord) {
    if (this.model.isGameOver()) {
      view.showMessage(GAME_OVER_MSG);
      return;
    }

    if (this.model.getCurrentPlayer() == this.player.getPiece()) {
      try {
        this.model.makeMove(coord);
      }
      catch (IllegalArgumentException e) {
        view.showMessage("Illegal move for " + this.player);
      }
      catch (IllegalStateException e) {
        view.showMessage("Invalid move for " + this.player);
      }
    }
    else {
      view.showMessage("It's not your turn.");
    }
    this.view.refresh();
  }

  /**
   * Notifies the player that it is their turn to make a move.
   * Once a player makes a move, it updates the view.
   * In the case where the game is over, the view will show
   * a prompt stating that the game is over.
   */
  @Override
  public void yourTurn() {
    if (this.model.isGameOver()) {
      if (!gameIsOver) {
        view.showMessage(GAME_OVER_MSG);
      }
      gameIsOver = true;
      return;
    }
    else if (this.model.getCurrentPlayer() == this.player.getPiece()) {
      player.requestMove();
    }
    this.view.refresh();
  }
}
