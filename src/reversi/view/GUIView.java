package reversi.view;

/**
 * This interface controls the visibility of the GUI using the display function.
 */
public interface GUIView {
  void display(boolean show);

  void addFeatureListener(PlayerActions features);

  void setTitle(String title);

  void showInvalidMoveMessage(String player);

  void refresh();
}
