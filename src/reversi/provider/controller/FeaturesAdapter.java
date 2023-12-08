package reversi.provider.controller;

import reversi.provider.model.Disc;
import reversi.provider.model.DiscConversion;
import reversi.view.PlayerActions;

public class FeaturesAdapter implements PlayerListener {

  private PlayerActions actions;
  private final int boardHeight;

  public FeaturesAdapter(PlayerActions listener, int boardHeight) {
    this.actions = listener;
    this.boardHeight = boardHeight;
  }

  @Override
  public void flip(Disc d) {
    actions.playMove(DiscConversion.toCoord(d, this.boardHeight));
  }

  @Override
  public void pass() {
    actions.passMove();
  }
}
