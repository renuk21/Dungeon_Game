package dungeon;

import dungeoncomponents.Arrow;
import dungeoncomponents.Treasure;
import java.util.List;
import java.util.Map;

/**
 * This interface represents a Player of Dungeon game.
 */
public interface InterfacePlayer {

  /**
   * Gets current location of the Player.
   *
   * @return cave location
   */
  int getLocation();

  /**
   * Checks if player has reached the end cave.
   *
   * @return true/false
   */
  boolean hasReachedEnd();

  /**
   * Moves the player from one cave to another.
   *
   * @param direction the direction (East,West,North or South)
   */
  void move(String direction);

  /**
   * gets the treasure collected by the player.
   *
   * @return treasure collected by the player
   */
  Map<Treasure, Integer> getTreasureCollected();

  /**
   * Get the player's name.
   *
   * @return name
   */
  String getName();

  /**
   * Get treasure available at a particular cave.
   *
   * @return treasure available
   */
  List<String> getAvailableTreasure(); //treasure in the room

  List<Arrow> getAvailableArrows();

  /**
   * get possible moves - direction and the next cave.
   *
   * @param currLoc current location
   * @return list of possible moves
   */
  List<List<String>> getPossibleMoves(int currLoc);

  /**
   * Player collects all the available treasure in the cave.
   */
  void collectTreasure();

  boolean slay(String direction, int distance);

  void pickUpArrow();

  int smellOtyugh();

  /**
   * Return player information.
   *
   * @return Player information
   */
  String getPlayerInfo();


  /**
   * Get list of caves visited by the player.
   *
   * @return list of caves visited by the player
   */
  List<Integer> getListOfCavesVisited();
}
