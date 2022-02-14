package view;

import controller.DungeonViewController;
import dungeon.DungeonModel;
import java.io.IOException;

/**
 * Interface for the GUI of Dungeon Game.
 */
public interface InterfaceView {

  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();

  /**
   * Provide the view with an action listener for
   * click of the mouse that should cause the program to
   * process a command. This is so that when the mouse
   * is clicked, control goes to the action listener

   * @param listener mouse listener
   */
  void addClickListener(DungeonViewController listener);

  void startGameListener(DungeonViewController listener);

  void reuseGameListener(DungeonViewController listener);

  /**
   * Provide the view with an action listener for
   * the key-press that should cause the program to
   * process a command. This is so that when the key
   * is pressed, control goes to the action listener

   * @param listener Key listener
   */
  void addKeyBoardListener(DungeonViewController listener);

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Transmit a message to the view that needs to
   * be displayed to the user.

   * @param message expected exceptions and error messages
   */
  void showMessages(String message);

  /**
   * Provide the view with the current status
   * of the dungeon and player.
   */
  void getPlayerInfo();

  /**
   * Provide the view with the visited caves to be drawn.
   */
  void showVisitedCaves();

  void removeTreasureIcon();

  void addTreasure();

  void addArrows();

  void addOtyughs();

  DungeonModel getModelParameters(boolean reuse);

  boolean isBuildModelWithUserDefinedParams();

  void removePlayerIcon();

  void smellOtyugh(int strengthOfSmell);

  void removeSmellIcon();

  void removeArrowIcon();

  void closeView() throws IOException;
}
