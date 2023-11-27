package reversi.controller;

import reversi.model.Coord;
import reversi.model.Reversi;
import reversi.view.GUIView;
import reversi.view.ViewFeatures;

public class Controller implements ViewFeatures {
  private final Reversi model;

  private final GUIView view;

  public Controller(Reversi model, GUIView view) {
    this.model = model;
    this.view = view;
    this.view.addFeatureListener(this);
  }

  @Override
  public void passMove() {

  }

  @Override
  public void playMove(Coord coord) {
    
  }
}
