package dungeon;

/**
 * Model for Dungeon and Player Interfaces.
 */
public interface DungeonModel {

  /**
   * Gets the dungeon specific components.

   * @return Dungeon Components
   */
  DungeonGrid getDungeon();

  /**
   * Gets player specific components.

   * @return Player components
   */
  InterfacePlayer getPlayer();
}
