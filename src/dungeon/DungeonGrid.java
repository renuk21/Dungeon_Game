package dungeon;

import dungeoncomponents.Health;
import java.util.List;
import java.util.Map;

/**
 * Interface represents a dungeon grid of interconnected caves and tunnels.
 */
public interface DungeonGrid {
  //  Health getOtyughHealth(int caveId);

  //  void updateOtyughHealth(int caveId);

  //  void killOtyugh(int caveId);

  /**
   * Get list of caves with Treasure.
   *
   * @return list of caves that contain treasure.
   */
  List<Integer> getListOfCavesWithTreasure();

  /**
   * Get Map of caves with Otyughs.
   *
   * @return HashMap of caves with Otyughs and their current health
   */
  Map<Integer, Health> getListOfCavesWithOtyughs();

  /**
   * Get list of Caves and tunnels with Arrows.
   *
   * @return list of caves and tunnel that contain arrows.
   */
  List<Integer> getListOfCavesWithArrows();

  /**
   * Checks whether a cave contains an Otyugh or not.
   *
   * @param caveId the cave id of a particular cave in the dungeon
   * @return boolean true or false
   */
  boolean isOtyughPresent(int caveId);

  /**
   * Check if arrow is present in the caveor not.
   *
   * @param caveId the cave id of a particular cave in the dungeon
   * @return boolean true or false
   */
  boolean isArrowPresent(int caveId);

  /**
   * Get degree of Interconnectivity.
   *
   * @return degree of interconnectivity
   */
  int getDegreeOfInterconnectivity();

  /**
   * Get start cave.
   *
   * @return start cave
   */
  int getStart();

  /**
   * Get end cave.
   *
   * @return end cave
   */
  int getEnd();

  /**
   * get the matrix of dungeon caves.
   *
   * @return dungeon matrix
   */
  int[][] getDungeonMatrix();

  /**
   * get the adjacency matrix of caves.
   *
   * @return matrix of path
   */
  int[][] getDungeonPaths();

  /**
   * get the number of entrances of a cave.
   *
   * @param caveId the cave id of a particular cave in the dungeon
   * @return number of entrances of the cave
   */
  int getEntrances(int caveId);
}
