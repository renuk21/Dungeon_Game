package controller;

import dungeon.DungeonModel;
import java.io.IOException;

/**
 * Represents a Controller for Dungeon: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface DungeonViewController {

  /**
   * Execute a single game of Dungeon given Dungeon Model. When the game is over,
   * the playGame method ends.
   */
  void run();


  /**
   * Handle an action in a single cave of the dungeon, such as to make a move.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  void handleCellClick(int row, int col);

  void handleKeyType(int c);

  void startNewGame(DungeonModel model) throws IOException;

}
