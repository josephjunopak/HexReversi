package reversi.view;

/**
 * This interface controls the visibility of the GUI using the display function.
 */
public interface GUIView {
  /**
   * Turns the window display on or off.
   * @param show  shows the display window if show is true.
   */
  void display(boolean show);

  /**
   * Adds a feature listener to the view, which notifies the listeners
   * whenever a player action needs to be performed.
   * @param features  The listeners that will be notified when something happens.
   */
  void addFeatureListener(PlayerActions features);

  /**
   * Sets the title of the window.
   * @param title the text that the title of the window will be set to.
   */
  void setTitle(String title);

  /**
   * Shows a pop-up message with the given text.
   * @param msg the text that will be shown in the pop-up message.
   */
  void showMessage(String msg);

  /**
   * Refreshes the view window to keep the contents up-to-date.
   */
  void refresh();
}
