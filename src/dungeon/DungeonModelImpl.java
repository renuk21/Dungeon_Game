package dungeon;

/**
 * This class represents all the components associated with the Dungeon Game.
 */
public class DungeonModelImpl implements DungeonModel {
  InterfacePlayer player;
  DungeonGrid dungeon;

  private DungeonModelImpl(String playerName, boolean isWrapping,
                          int row, int col, int degreeOfInterConnectivity, double percent,
                           int numOfOtyughs, int seed) {

    dungeon = new Dungeon.DungeonBuilder()
            .isWrapping(isWrapping)
            .rows(row).columns(col)
            .degreeOfInterconnectivity(degreeOfInterConnectivity)
            .percent(percent)
            .numOfOtyugh(numOfOtyughs)
            .seed(seed).build();

    player = new Player(playerName, dungeon, seed);
  }

  @Override
  public DungeonGrid getDungeon() {
    final DungeonGrid dungeon = this.dungeon;
    return dungeon;
  }

  @Override
  public InterfacePlayer getPlayer() {
    final InterfacePlayer player = this.player;
    return player;
  }

  /**
   * Builder class for Dungeon.
   */
  public static class DungeonModelBuilder {

    private boolean isWrapping;
    private int rows;
    private int columns;
    private int degreeOfInterconnectivity;
    private double percent;
    private int seed;
    private int numOfOtyugh;
    private String playerName;

    /**
     * Builder with default values for dungeon.
     */
    public DungeonModelBuilder() {
      //default values
      this.seed = 0;
      this.isWrapping = false;
      this.rows = 5;
      this.columns = 5;
      this.degreeOfInterconnectivity = 0;
      this.percent = 20;
      this.numOfOtyugh = 1;
      this.playerName = "Player 1";
    }

    /**
     * Dungeon Builder for isWrapping.
     *
     * @param isWrapping whether the dungeon is wrapping or not
     * @return this dungeon
     */
    public DungeonModelBuilder isWrapping(boolean isWrapping) {
      this.isWrapping = isWrapping;
      return this;
    }

    /**
     * Dungeon Builder for rows.
     *
     * @param rows number of rows
     * @return this dungeon
     */
    public DungeonModelBuilder rows(int rows) {
      if (rows < 0) {
        throw new IllegalArgumentException("Number of rows cannot be negative");
      }
      this.rows = rows;
      return this;
    }

    /**
     * Dungeon Builder for columns.
     *
     * @param columns number of columns
     * @return this dungeon
     */
    public DungeonModelBuilder columns(int columns) {
      if (columns < 0) {
        throw new IllegalArgumentException("Number of columns cannot be negative");
      }
      this.columns = columns;
      return this;
    }

    /**
     * Dungeon Builder for degreeOfInterconnectivity.
     *
     * @param degreeOfInterconnectivity degree of interconnectivity
     * @return this dungeon
     */
    public DungeonModelBuilder degreeOfInterconnectivity(int degreeOfInterconnectivity) {
      if (degreeOfInterconnectivity < 0) {
        throw new IllegalArgumentException("Degree of Interconnectivity cannot be negative");
      }
      this.degreeOfInterconnectivity = degreeOfInterconnectivity;
      return this;
    }

    /**
     * Dungeon Builder for percentage of caves with treasure.
     *
     * @param percent percentage of caves with treasure
     * @return this dungeon
     */
    public DungeonModelBuilder percent(double percent) {
      if (percent < 0) {
        throw new IllegalArgumentException("Percent cannot be negative");
      }
      this.percent = percent;
      return this;
    }

    /**
     * Dungeon Builder for number of Otyughs in the dungeon.
     *
     * @param numOfOtyugh number of Otyughs in the dungeon
     * @return this dungeon
     */
    public DungeonModelBuilder numOfOtyugh(int numOfOtyugh) {
      if (numOfOtyugh < 0) {
        throw new IllegalArgumentException("Number of Otyughs cannot be negative");
      }
      this.numOfOtyugh = numOfOtyugh;
      return this;
    }

    /**
     * Sets the seed parameter.
     *
     * @param seed seed for Random Number Generator
     * @return this dungeon
     */
    public DungeonModelBuilder seed(int seed) {
      this.seed = seed;
      return this;
    }

    /**
     * Sets the playerName.
     *
     * @param playerName Player Name
     * @return this dungeon
     */
    public DungeonModelBuilder playerName(String playerName) {
      this.playerName = playerName;
      return this;
    }

    /**
     * Use the currently set values to create the Dungeon object with the only private constructor
     * above.
     *
     * @return Dungeon object
     */
    public DungeonModelImpl build() {
      return new DungeonModelImpl(playerName, isWrapping, rows, columns, degreeOfInterconnectivity,
              percent, numOfOtyugh,
              seed);
    }
  }
}
