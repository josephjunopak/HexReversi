package reversi.provider.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import cs3500.reversi.controller.PlayerListener;
import cs3500.reversi.model.Disc;


/**
 * The view interface.
 */
public interface IView {
  /**
   * Gets the reversi panel.
   *
   * @return the reversi panel.
   */
  ReversiPanel getReversiPanel();

  /**
   * Gets the listeners.
   *
   * @return the listeners.
   */
  List<PlayerListener> getListeners();

  /**
   * Sets the canvas and repaints panel after each move.
   */
  void setCanvas();

  /**
   * Make the view visible. This is usually called after the view is constructed.
   */
  void makeVisible();

  /**
   * Displays the error message if the called command can not be run.
   *
   * @param error an error
   */
  void showError(String error);

  /**
   * Sets the mouse listener.
   *
   * @param listener the mouse listener.
   */
  void setMouseListener(MouseListener listener);

  /**
   * Sets the key listener.
   *
   * @param listener the key listener.
   */
  void setKeyListener(KeyListener listener);

  /**
   * Brief pause between moves.
   */
  void briefPause();

  /**
   * Notifies listeners about user moves.
   *
   * @param d disc to flip
   */
  void notifyUserMove(Disc d);

  /**
   * Notifies listeners about user pass.
   */
  void notifyUserPass();

  /**
   * Sets the title of a view.
   *
   * @param title title to change.
   */
  void setTitle(String title);

  /**
   * Adds a player listener.
   *
   * @param l listener to add.
   */
  void addPlayerListener(PlayerListener l);
}
