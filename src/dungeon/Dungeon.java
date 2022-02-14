package dungeon;

import static java.lang.Math.ceil;

import dungeoncomponents.Health;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import random.RandomNumberGenerator;

/**
 * This class represents a dungeon of interconnected caves and tunnels.
 * Caves can have 1,3 and 4 entrances. Tunnels are Caves with 2 entrances.
 */
public class Dungeon implements DungeonGrid {
  private final boolean isWrapping;
  private final int rows;
  private final int columns;
  private final int degreeOfInterconnectivity;
  private final Cave[][] dungeonMatrix;
  private final double percent;
  private final int[][] edgeAdjacency;
  private final HashMap<Integer, Cave> horizontalEdges; //horizontal edges
  private final HashMap<Integer, Cave> verticalEdges; //vertical edges
  private final ArrayList<String> dungeonEdges;
  private final ArrayList<String> leftOver;
  private final ArrayList<Integer> listOfCavesWithTreasure;
  private final int seed;
  private final int numOfOtyugh;
  private final Map<Integer, Health> listOfCavesWithOtyughs;
  private final ArrayList<Integer> listOfCavesWithArrows;
  private int start;
  private int end;

  /**
   * Constructor a dungeon with interconnected caves and tunnels.
   *
   * @param isWrapping                is the dungeon a wrapping dungeon or not
   * @param rows                      number of rows in the dungeon
   * @param columns                   number of columns in the dungeon
   * @param degreeOfInterconnectivity degree of interconnectivity of the dungeon
   * @param percent                   percentage of caves of the dungeon that contain treasure
   * @param numOfOtyugh               number of Otyughs in the dungeon
   * @param seed                      seed for random number generation
   */
  private Dungeon(boolean isWrapping, int rows, int columns, int degreeOfInterconnectivity,
                  double percent, int numOfOtyugh, int seed) {

    this.constructorValidations(isWrapping, rows, columns, degreeOfInterconnectivity, percent);

    this.seed = seed;
    this.isWrapping = isWrapping;
    this.rows = rows;
    this.columns = columns;
    this.degreeOfInterconnectivity = degreeOfInterconnectivity;
    this.dungeonMatrix = this.createDungeonMatrix(this.rows, this.columns);
    this.horizontalEdges = new HashMap<>(); //horizontal edges
    this.verticalEdges = new HashMap<>(); //vertical edges
    this.dungeonEdges = new ArrayList<>();
    this.leftOver = new ArrayList<>();
    edgeAdjacency = new int[rows * columns][rows * columns];
    this.createEdges(this.dungeonMatrix);
    this.createDungeonEdges(horizontalEdges, verticalEdges);
    this.start = 0;
    this.end = 0;
    this.percent = percent;
    this.listOfCavesWithTreasure = new ArrayList<>();
    this.assignTreasureToCaves();
    this.assignStartEnd();
    boolean isOtyughPresent = false;
    this.numOfOtyugh = numOfOtyugh;
    this.listOfCavesWithOtyughs = new HashMap<>();
    this.assignOtyugh();
    this.listOfCavesWithArrows = new ArrayList<>();
    this.assignArrow();
  }

  private void constructorValidations(boolean isWrapping, int rows, int columns,
                                      int degreeOfInterconnectivity, double percent) {
    if (rows < 3 && columns < 3) {
      throw new IllegalArgumentException("No. of rows and columns should be greater than 2");
    }
    if (percent > 100 || percent < 0) {
      throw new IllegalArgumentException("Invalid value for percentage of caves with treasure");
    }
    if (((degreeOfInterconnectivity > (rows * columns * 2 - rows - columns - (rows * columns + 1)))
            && !isWrapping) || ((degreeOfInterconnectivity > (rows * columns * 2
            - (rows * columns + 1))) && isWrapping)) {
      if (degreeOfInterconnectivity <= 0) {
        throw new IllegalArgumentException("Invalid row and column values");
      }

      throw new IllegalArgumentException("Invalid degree of interconnectivity");

    }


  }

  private Cave[][] createDungeonMatrix(int rows, int cols) {
    Cave[][] dungeonMatrix = new Cave[rows][cols];
    int caveIndex = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        caveIndex++;
        dungeonMatrix[i][j] = new Cave(caveIndex);
      }
    }
    return dungeonMatrix;
  }

  private void createEdges(Cave[][] dungeonMatrix) {
    int len = dungeonMatrix[0].length - 1;
    for (int i = 0; i < dungeonMatrix.length; i++) {
      for (int j = 0; j < dungeonMatrix[0].length; j++) {
        if (this.isWrapping) {
          this.horizontalEdges.put(dungeonMatrix[i][len].getCaveId(), dungeonMatrix[i][0]);
        }
        if (j + 1 < dungeonMatrix[0].length) {
          this.horizontalEdges.put(dungeonMatrix[i][j].getCaveId(), dungeonMatrix[i][j + 1]);

        }
      }
    }

    for (int j = 0; j < dungeonMatrix[0].length; j++) {
      for (int i = 0; i < dungeonMatrix.length; i++) {
        if (this.isWrapping) {
          this.verticalEdges.put(dungeonMatrix[dungeonMatrix.length - 1][j].getCaveId(),
                  dungeonMatrix[0][j]);
        }
        if (i + 1 < dungeonMatrix.length) {
          this.verticalEdges.put(dungeonMatrix[i][j].getCaveId(), dungeonMatrix[i + 1][j]);

        }
      }
    }
  }

  private void createDungeonEdges(HashMap<Integer, Cave> horizontalEdges, HashMap<Integer,
          Cave> verticalEdges) {
    RandomNumberGenerator random;
    if (this.seed != 0) {
      random = new RandomNumberGenerator(seed);
    } else {
      random = new RandomNumberGenerator();
    }

    int i = dungeonMatrix.length * dungeonMatrix[0].length;
    int index = 0;
    int src = 0;
    int dest = 0;

    //make copies of horizontal and vertical edges
    HashMap<Integer, Cave> horizonalEdgesCopy = new HashMap<>();
    HashMap<Integer, Cave> verticalEdgesCopy = new HashMap<>();

    for (int j = 0; j < horizontalEdges.size(); j++) {

      Object h = horizontalEdges.keySet().toArray()[j];
      src = Integer.parseInt(h.toString());
      horizonalEdgesCopy.put(src, horizontalEdges.get(src));
    }
    for (int j = 0; j < verticalEdges.size(); j++) {
      Object h = verticalEdges.keySet().toArray()[j];
      src = Integer.parseInt(h.toString());
      //      Cave h = (Cave) horizonalEdgesCopy.keySet().toArray()[j];
      //      src = h.getCaveID();
      verticalEdgesCopy.put(src, verticalEdges.get(src));
    }


    DisjointSet ds = new DisjointSet();
    ds.makeSet(i);
    int lenH = horizontalEdges.size();
    int lenV = verticalEdges.size();
    int len = horizontalEdges.size() + verticalEdges.size();
    while (dungeonEdges.size() + leftOver.size() < len) {
      //select at random between vertical/horizontal edge set
      if (random.getRandomNumber(1, 2) == 1) { //horizontal
        index = random.getRandomNumber(0, lenH - 1);
        Object h = horizonalEdgesCopy.keySet().toArray()[index];
        src = Integer.parseInt(h.toString());
        //        Cave h = (Cave) horizonalEdgesCopy.keySet().toArray()[index];
        //        src = h.getCaveID();
        Cave c = horizonalEdgesCopy.get(src);
        dest = c.getCaveId();
        horizontalEdges.remove(src);
      } else { //vertical
        index = random.getRandomNumber(0, lenV - 1);
        Object v = verticalEdgesCopy.keySet().toArray()[index];
        src = Integer.parseInt(v.toString());
        Cave c = verticalEdgesCopy.get(src);
        dest = c.getCaveId();
        verticalEdges.remove(src);

      }

      int x = ds.find(src);
      int y = ds.find(dest);

      if (x != y) {
        edgeAdjacency[src - 1][dest - 1] = 1;
        edgeAdjacency[dest - 1][src - 1] = 1;
        dungeonEdges.add(src + "-" + dest);
        ds.union(x, y);
      } else {
        //don't add edges to leftOver if they are already present in kruskal's mst dungeonEdges,
        // since, random numbers generated may repeat an index
        if (!dungeonEdges.contains(src + "-" + dest)) {
          leftOver.add(src + "-" + dest);
          edgeAdjacency[src - 1][dest - 1] = 0;
          edgeAdjacency[dest - 1][src - 1] = 0;
        }
      }
    }

    if (this.degreeOfInterconnectivity > 0) {
      int j = 0;
      while (j != degreeOfInterconnectivity) {
        String edge = leftOver.get(j);
        if (!dungeonEdges.contains(edge)) {
          int[] vertices = this.edgeToVertices(edge);
          dungeonEdges.add(vertices[0] + "-" + vertices[1]);
          edgeAdjacency[vertices[0] - 1][vertices[1] - 1] = 1;
          edgeAdjacency[vertices[1] - 1][vertices[0] - 1] = 1;
          j++;
        }
      }
    }

  }

  /**
   * Convert edges to vertices and return as vertex pair.
   *
   * @param edge edge
   * @return vertices that the edge is made up of
   */
  private int[] edgeToVertices(String edge) { //package-private
    String[] v = edge.split("-");
    int[] vertices = new int[2];
    vertices[0] = Integer.parseInt(v[0]);
    vertices[1] = Integer.parseInt(v[1]);
    return vertices;
  }

  private void assignStartEnd() {
    RandomNumberGenerator r1;
    RandomNumberGenerator r2;
    if (this.seed != 0) {
      r1 = new RandomNumberGenerator(seed);
      r2 = new RandomNumberGenerator(seed * 2);
    } else {
      r1 = new RandomNumberGenerator();
      r2 = new RandomNumberGenerator();
    }

    while (true) {
      int start = r1.getRandomNumber(1, rows * columns / 2);
      int end = r2.getRandomNumber(rows * columns / 2 + 1, rows * columns);
      if (start == end) {
        continue;
      }
      int cnt1 = 0;
      int cnt2 = 0;
      for (int i = 0; i < this.dungeonEdges.size(); i++) {
        int[] v = this.edgeToVertices(this.dungeonEdges.get(i));
        if (v[0] == start || v[1] == start) {
          cnt1++;
        }
        if (v[0] == end || v[1] == end) {
          cnt2++;
        }
      }
      if (cnt1 == 2 || cnt2 == 2) {
        continue; //tunnel should not be a start or end cave, so start over
      }
      int x = 0;
      int y = 0;
      int a = 0;
      int b = 0;
      //find indices of start and end caves in the dungeon matrix
      for (int i = 0; i < dungeonMatrix.length; i++) {
        for (int j = 0; j < dungeonMatrix[0].length; j++) {
          if (dungeonMatrix[i][j].getCaveId() == start) {
            a = i;
            b = j;
          }
          if (dungeonMatrix[i][j].getCaveId() == end) {
            x = i;
            y = j;
          }
        }
      }

      DijkstraAdjacencyMatrix.Graph g = new DijkstraAdjacencyMatrix.Graph(rows * columns,
              edgeAdjacency);
      int l = g.dijkstraGetMinDistances(start - 1, end - 1);
      if (l >= 5) {
        this.start = start;
        this.end = end;
        break;
      }
    }
  }

  private ArrayList<Integer> assignDungeonCompoHelper() {
    ArrayList<Integer> caves = new ArrayList<>();
    for (int i = 0; i < dungeonMatrix.length; i++) {
      for (int j = 0; j < dungeonMatrix[0].length; j++) {
        if (this.getEntrances(dungeonMatrix[i][j].getCaveId()) != 2) {
          caves.add(dungeonMatrix[i][j].getCaveId());
        }
      }
    }

    return caves;
  }

  private RandomNumberGenerator randHelper() {
    RandomNumberGenerator rand;
    if (seed != 0) {
      rand = new RandomNumberGenerator(seed);
    } else {
      rand = new RandomNumberGenerator();
    }
    return rand;
  }

  private void assignOtyugh() {

    ArrayList<Integer> caves = this.assignDungeonCompoHelper();
    RandomNumberGenerator rand = this.randHelper();

    if (numOfOtyugh > caves.size()) {
      throw new IllegalArgumentException("Invalid number of Otyughs");
    }
    int count = 0;
    while (count < numOfOtyugh - 1) {
      int r = rand.getRandomNumber(0, caves.size() - 1);
      if (!listOfCavesWithOtyughs.containsKey(caves.get(r)) && caves.get(r) != end
              && caves.get(r) != start) { //Otyugh is never present at the start
        listOfCavesWithOtyughs.put(caves.get(r), Health.HEALTHY);
        count++;
      }

    }
    listOfCavesWithOtyughs.put(end, Health.HEALTHY); //Otyugh is definitely present at the end

    boolean f = false;
    for (int i = 0; i < listOfCavesWithOtyughs.size(); i++) {
      for (int j = 0; j < dungeonMatrix.length; j++) {
        for (int k = 0; k < dungeonMatrix[0].length; k++) {
          if (dungeonMatrix[j][k].getCaveId()
                  == Integer.parseInt(listOfCavesWithOtyughs.keySet().toArray()[i].toString())) {
            dungeonMatrix[j][k].assignOtyugh();
            f = true;
            break;
          }
        }
        if (f) {
          f = false;
          break;
        }
      }
    }
  }

  Health getOtyughHealth(int caveId) {
    Health h = this.listOfCavesWithOtyughs.get(caveId);
    return h;
  }

  void updateOtyughHealth(int caveId) {
    listOfCavesWithOtyughs.put(caveId, Health.INJURED);
  }

  void killOtyugh(int caveId) {
    listOfCavesWithOtyughs.put(caveId, Health.DEAD);
  }

  private void assignArrow() {
    ArrayList<Integer> caves = new ArrayList<>();
    for (int i = 0; i < dungeonMatrix.length; i++) {
      for (int j = 0; j < dungeonMatrix[0].length; j++) {
        caves.add(dungeonMatrix[i][j].getCaveId()); //arrows can be found in caves and tunnels
      }
    }
    RandomNumberGenerator rand = this.randHelper();

    double percentOfCaves = 0;
    while (ceil(percentOfCaves) < percent) {
      int r = rand.getRandomNumber(0, caves.size() - 1);
      if (!listOfCavesWithArrows.contains(caves.get(r))) {
        listOfCavesWithArrows.add(caves.get(r));
        percentOfCaves = ((double) listOfCavesWithArrows.size() / (double) (caves.size())) * 100;
      }

    }

    boolean f = false;
    for (int i = 0; i < listOfCavesWithArrows.size(); i++) {
      for (int j = 0; j < dungeonMatrix.length; j++) {
        for (int k = 0; k < dungeonMatrix[0].length; k++) {
          if (dungeonMatrix[j][k].equals(listOfCavesWithArrows.get(i))) {
            dungeonMatrix[j][k].assignArrow();
            f = true;
            break;
          }
        }
        if (f) {
          f = false;
          break;
        }
      }
    }
  }


  private void assignTreasureToCaves() {
    int count = 0;
    ArrayList<Integer> caves = this.assignDungeonCompoHelper();

    RandomNumberGenerator rand = this.randHelper();

    double percentOfCaves = 0;
    while (ceil(percentOfCaves) < percent) {
      int r = rand.getRandomNumber(0, caves.size() - 1);
      if (!listOfCavesWithTreasure.contains(caves.get(r))) {
        listOfCavesWithTreasure.add(caves.get(r));
        percentOfCaves = ((double) listOfCavesWithTreasure.size() / (double) (caves.size())) * 100;
      }

    }
  }

  @Override
  public List<Integer> getListOfCavesWithTreasure() {
    List cavesWithTreasure = new ArrayList();
    cavesWithTreasure.addAll(listOfCavesWithTreasure);
    return cavesWithTreasure;
  }

  @Override
  public Map<Integer, Health> getListOfCavesWithOtyughs() {
    Map cavesWithOtyughs = new HashMap();
    cavesWithOtyughs.putAll(listOfCavesWithOtyughs);
    return cavesWithOtyughs;
  }

  @Override
  public List<Integer> getListOfCavesWithArrows() {
    List cavesWithArrows = new ArrayList();
    cavesWithArrows.addAll(listOfCavesWithArrows);
    return cavesWithArrows;
  }

  //package-private
  void updateListOfCavesWithArrows(int index) {
    listOfCavesWithArrows.remove(index);
  }

  void updateListOfCavesWithTreasure(int index) {
    listOfCavesWithTreasure.remove(index);
  }

  @Override
  public boolean isOtyughPresent(int caveId) {
    for (int i = 0; i < dungeonMatrix.length; i++) {
      for (int j = 0; j < dungeonMatrix[0].length; j++) {
        if (caveId == dungeonMatrix[i][j].getCaveId()) {
          return dungeonMatrix[i][j].isOtyughPresent();
        }
      }
    }
    return false;
  }

  @Override
  public boolean isArrowPresent(int caveId) {
    for (int i = 0; i < dungeonMatrix.length; i++) {
      for (int j = 0; j < dungeonMatrix[0].length; j++) {
        if (caveId == dungeonMatrix[i][j].getCaveId()) {
          return dungeonMatrix[i][j].isArrowPresent();
        }
      }
    }
    return false;
  }


  @Override
  public int getDegreeOfInterconnectivity() {
    return this.degreeOfInterconnectivity;
  }

  @Override
  public int getStart() {
    int start = this.start;
    return start;
  }

  @Override
  public int getEnd() {
    int end = this.end;
    return end;
  }

  @Override
  public String toString() {
    boolean flag = false;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < dungeonMatrix.length; i++) {
      for (int j = 0; j < dungeonMatrix[0].length; j++) {
        if (sb.indexOf(String.valueOf(dungeonMatrix[i][j].getCaveId())) == -1) {
          sb.append(dungeonMatrix[i][j].getCaveId());
        }
        for (int k = 0; k < dungeonEdges.size(); k++) {
          if (dungeonMatrix[i][j].getCaveId() == Integer.parseInt(dungeonEdges.get(k).substring(0,
                  dungeonEdges.get(k).indexOf('-')))) {
            if ((j + 1) % dungeonMatrix[0].length < dungeonMatrix[0].length) {
              if (String.valueOf(dungeonMatrix[i][(j + 1) % dungeonMatrix[0].length].getCaveId())
                      .equals(dungeonEdges.get(k)
                              .substring(dungeonEdges.get(k).indexOf('-') + 1))) {
                sb.append("\t-\t");
                sb.append(dungeonMatrix[i][(j + 1) % dungeonMatrix[0].length].getCaveId());
                flag = true;
              }
            }
          }
        }
        if (!flag) {
          sb.append("\t*\t");
        } else {
          flag = false;
        }
      }


      sb.append("\n");

      for (int j = 0; j < dungeonMatrix[0].length; j++) {
        for (int k = 0; k < dungeonEdges.size(); k++) {
          if (dungeonMatrix[i][j].getCaveId() == Integer.parseInt(dungeonEdges.get(k)
                  .substring(0, dungeonEdges.get(k).indexOf('-')))) {
            if ((i + 1) % dungeonMatrix.length < dungeonMatrix.length) {
              if (String.valueOf(dungeonMatrix[(i + 1) % dungeonMatrix.length][j].getCaveId())
                      .equals(dungeonEdges.get(k)
                              .substring(dungeonEdges.get(k).indexOf('-') + 1))) {
                sb.append("|\t");
                sb.append("*\t");
                flag = true;

              }
            }
          }
        }
        if (!flag) {
          sb.append("\t*\t");
        } else {
          flag = false;
        }
      }

      sb.append("\n");

    }
    return sb.toString();
  }

  @Override
  public int[][] getDungeonMatrix() {
    int[][] dungeonMat = new int[this.rows][this.columns];
    for (int i = 0; i < dungeonMatrix.length; i++) {
      for (int j = 0; j < dungeonMatrix[i].length; j++) {
        dungeonMat[i][j] = dungeonMatrix[i][j].getCaveId();
      }
    }
    return dungeonMat;
  }

  @Override
  public int[][] getDungeonPaths() {
    int[][] dungeonPaths = new int[rows * columns][rows * columns];
    for (int i = 0; i < edgeAdjacency.length; i++) {
      for (int j = 0; j < edgeAdjacency.length; j++) {
        dungeonPaths[i][j] = edgeAdjacency[i][j];
      }
    }
    return dungeonPaths;
  }

  /**
   * Get number of entrances of a Cave.
   *
   * @param caveId cave id
   * @return number of Entrances
   */
  @Override
  public int getEntrances(int caveId) {
    if (caveId < 1) {
      throw new IllegalArgumentException("Cave ID should atleast be 1");
    }
    int numOfEntrances = 0;

    for (int j = 0; j < this.getDungeonPaths()[0].length; j++) {
      if (this.getDungeonPaths()[caveId - 1][j] == 1) {
        numOfEntrances++;
      }

    }


    return numOfEntrances;
  }

  /**
   * Builder class for Dungeon.
   */
  public static class DungeonBuilder {

    private boolean isWrapping;
    private int rows;
    private int columns;
    private int degreeOfInterconnectivity;
    private double percent;
    private int seed;
    private int numOfOtyugh;

    /**
     * Builder with default values for dungeon.
     */
    public DungeonBuilder() {
      //default values
      this.seed = 0;
      this.isWrapping = false;
      this.rows = 5;
      this.columns = 5;
      this.degreeOfInterconnectivity = 0;
      this.percent = 20;
      this.numOfOtyugh = 1;
    }

    /**
     * Dungeon Builder for isWrapping.
     *
     * @param isWrapping whether the dungeon is wrapping or not
     * @return this dungeon
     */
    public DungeonBuilder isWrapping(boolean isWrapping) {
      this.isWrapping = isWrapping;
      return this;
    }

    /**
     * Dungeon Builder for rows.
     *
     * @param rows number of rows
     * @return this dungeon
     */
    public DungeonBuilder rows(int rows) {
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
    public DungeonBuilder columns(int columns) {
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
    public DungeonBuilder degreeOfInterconnectivity(int degreeOfInterconnectivity) {
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
    public DungeonBuilder percent(double percent) {
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
    public DungeonBuilder numOfOtyugh(int numOfOtyugh) {
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
    public DungeonBuilder seed(int seed) {
      this.seed = seed;
      return this;
    }

    /**
     * Use the currently set values to create the Dungeon object with the only private constructor
     * above.
     *
     * @return Dungeon object
     */
    public Dungeon build() {
      return new Dungeon(isWrapping, rows, columns, degreeOfInterconnectivity, percent, numOfOtyugh,
              seed);
    }
  }

}
