package reversi.provider.controller;

import reversi.provider.model.Disc;
import reversi.provider.model.ReversiModel;
import reversi.provider.view.IView;

/**
 * Interface for the controller.
 */
public interface IController {

  /**
   * Retrieves the model.
   *
   * @return the model.
   */
  ReversiModel getModel();

  /**
   * Sets the canvas after a turn.
   */
  void setCanvas();

  /**
   * Sets a disc to clicked.
   *
   * @param d the disc clicked.
   */
  void click(Disc d);

  /**
   * Sets the mouse listener.
   */
  void setMouseListener();

  /**
   * Gets the game view.
   *
   * @return the game view.
   */
  IView getGameView();

  /**
   * Sets the key listener.
   */
  void setKeyListener();

}
