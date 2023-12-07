package reversi.provider.view;

import javax.swing.*;

import reversi.model.Reversi;
import reversi.provider.controller.FeaturesAdapter;
import reversi.provider.model.ReadonlyReversiModel;
import reversi.view.GUIView;
import reversi.view.PlayerActions;

public class ViewAdapter extends ReversiGraphicsView implements GUIView {


  /**
   * Constructs a ReversiGraphicsView.
   *
   * @param m model view is based on.
   */
  public ViewAdapter(ReadonlyReversiModel m) {
    super(m);
  }

  @Override
  public void display(boolean show) {
    if (show) super.makeVisible();
  }

  @Override
  public void addFeatureListener(PlayerActions features) {
    super.addPlayerListener(new FeaturesAdapter(features, model.getBoardSize()));
  }

  @Override
  public void setTitle(String title) {
    super.setTitle(title);
  }

  @Override
  public void showMessage(String msg) {
    JOptionPane.showMessageDialog(getReversiPanel(), msg);;
  }

  @Override
  public void refresh() {
    super.setCanvas();
  }
}
