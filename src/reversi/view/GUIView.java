package reversi.view;

import reversi.controller.Controller;

/**
 * This interface controls the visibility of the GUI using the display function.
 */
public interface GUIView {
  void display(boolean show);

  void addFeatureListener(Controller controller);
}
