package controller;

import dungeon.DungeonModel;

/**
 * Represents a Controller for Dungeon: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface DungeonController {

  /**
   * Execute a single game of Dungeon given Dungeon Model. When the game is over,
   * the playGame method ends.
   *
   * @param d a non-null Dungeon Model
   */
  void playGame(DungeonModel d);
}
