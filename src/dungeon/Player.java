package dungeon;

import dungeoncomponents.Arrow;
import dungeoncomponents.Health;
import dungeoncomponents.Treasure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import random.RandomNumberGenerator;

/**
 * Player is a character used by the gamer to play the game.
 */
public class Player implements InterfacePlayer {
  private final String name;
  private final Map<Treasure, Integer> treasureCollected;
  private final Dungeon dungeon;
  private final int seed;
  private int currLocation;
  private List<Integer> cavesWithTreasure;
  private List<Integer> cavesWithArrow;
  private List<String> treasure;
  private int numOfArrows;
  private boolean endGame;
  private List<Integer> cavesVisited;

  /**
   * Construct Player for the dungeon.
   *
   * @param name name of the Player
   * @param d    dungeon created for the game
   */
  public Player(String name, Dungeon d) {
    if (d == null) {
      throw new IllegalArgumentException("Provide valid Dungeon");
    }
    this.currLocation = d.getStart();
    this.name = name;
    this.treasure = new ArrayList<>();
    this.treasureCollected = new HashMap<>();
    this.dungeon = d;
    this.seed = 0;
    cavesWithTreasure = this.dungeon.getListOfCavesWithTreasure();
    this.numOfArrows = 3; //Starts with 3 arrows
    cavesWithArrow = this.dungeon.getListOfCavesWithArrows();
    endGame = false;
    cavesVisited = new ArrayList<>();
    cavesVisited.add(this.currLocation);
  }

  /**
   * Construct Player for the dungeon with seed for random number generation.
   *
   * @param name name of the Player
   * @param d    dungeon created for the game
   * @param seed seed for Random Number Generator
   */
  public Player(String name, DungeonGrid d, int seed) {
    this.currLocation = d.getStart();
    this.name = name;
    this.treasure = new ArrayList<>();
    this.treasureCollected = new HashMap<>();
    this.dungeon = (Dungeon) d;
    this.seed = seed;
    cavesWithTreasure = this.dungeon.getListOfCavesWithTreasure();
    this.numOfArrows = 3; //Starts with 3 arrows
    cavesWithArrow = this.dungeon.getListOfCavesWithArrows();
    endGame = false;
    cavesVisited = new ArrayList<>();
    cavesVisited.add(this.currLocation);
  }

  @Override
  public int getLocation() {
    int loc = this.currLocation;
    return loc;
  }

  @Override
  public boolean hasReachedEnd() {
    return (this.getLocation() == dungeon.getEnd());
  }

  @Override
  public void move(String direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction should not be null");
    }
    List<List<String>> validMoves = getPossibleMoves(currLocation);
    boolean f = false;
    this.hasReachedEnd(); // checks if player has reached the end cave. Action to be handled by
    // controller
    if (this.hasReachedEnd() || endGame) {
      throw new IllegalArgumentException("Game Has Ended.");
    }
    for (int i = 0; i < validMoves.size(); i++) {
      if (validMoves.get(i).contains(direction)) {
        currLocation = Integer.parseInt(validMoves.get(i).get(1));
        f = true;

        if (!cavesVisited.contains(currLocation)) {
          cavesVisited.add(currLocation);
        }
      }

    }
    if (!f) {
      throw new IllegalArgumentException("Invalid move!");
    }
    if (die()) {
      endGame = true;
      throw new IllegalStateException("Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
            +  "Better luck next time");
    }
  }


  @Override
  public Map<Treasure, Integer> getTreasureCollected() {
    TreeMap<Treasure, Integer> sorted = new TreeMap<>();
    sorted.putAll(treasureCollected);
    return sorted;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public List<String> getAvailableTreasure() {
    List<String> treasure = new ArrayList<>();

    if (cavesWithTreasure.contains(currLocation)) {
      RandomNumberGenerator r;
      if (seed != 0) {
        r = new RandomNumberGenerator(seed);
      } else {
        r = new RandomNumberGenerator();
      }

      int j = r.getRandomNumber(1, 3); //determines how many types of treasures a cave must have
      for (int k = 0; k < j; k++) {
        int i = r.getRandomNumber(1, 3); //determines the type of treasure
        switch (i) {
          case 1: {
            treasure.add(Treasure.DIAMOND.toString());
            break;
          }
          case 2: {
            treasure.add(Treasure.RUBY.toString());
            break;
          }
          case 3: {
            treasure.add(Treasure.SAPPHIRE.toString());
            break;
          }
          default:
            break;
        }
      }

    } else {
      treasure.add("");
    }
    this.treasure = treasure;
    return treasure;
  }

  @Override
  public List<Arrow> getAvailableArrows() {
    List<Arrow> arrow = new ArrayList<>();

    if (cavesWithArrow.contains(currLocation)) {
      arrow.add(Arrow.CROOKED);
      return arrow;
    }
    return null;
  }

  @Override
  public List<List<String>> getPossibleMoves(int currLocation) {

    List<List<String>> validMoves = new ArrayList<>();
    List<String> directions;

    for (int i = 0; i < dungeon.getDungeonMatrix().length; i++) {
      for (int j = 0; j < dungeon.getDungeonMatrix()[0].length; j++) {
        if (currLocation == dungeon.getDungeonMatrix()[i][j]) {
          for (int k = 0; k < dungeon.getDungeonPaths().length; k++) {
            if (dungeon.getDungeonPaths()[currLocation - 1][k] == 1) {
              // k+1 is connected to currLocation
              if (k + 1 == dungeon.getDungeonMatrix()[(i + 1) % dungeon
                      .getDungeonMatrix().length][j]) {
                //South of current node
                directions = new ArrayList<>();
                directions.add("SOUTH");
                directions.add(String.valueOf(dungeon.getDungeonMatrix()[(i + 1) % dungeon
                        .getDungeonMatrix().length][j]));
                validMoves.add(directions);
              } else if (k + 1 == dungeon.getDungeonMatrix()[(i + dungeon.getDungeonMatrix()
                      .length - 1) % dungeon.getDungeonMatrix().length][j]) {
                //North
                directions = new ArrayList<>();
                directions.add("NORTH");
                directions.add(String.valueOf(dungeon.getDungeonMatrix()[(i
                        + dungeon.getDungeonMatrix()
                        .length - 1) % dungeon
                        .getDungeonMatrix().length][j]));
                validMoves.add(directions);
              } else if (k + 1 == dungeon.getDungeonMatrix()[i][(j + 1)
                      % dungeon.getDungeonMatrix()[0].length]) {
                //EAST
                directions = new ArrayList<>();
                directions.add("EAST");
                directions.add(String.valueOf(dungeon.getDungeonMatrix()[i]
                        [(j + 1) % dungeon.getDungeonMatrix()[0].length]));
                validMoves.add(directions);
              } else if (k + 1 == dungeon.getDungeonMatrix()[i][(j
                      + dungeon.getDungeonMatrix()[0].length - 1)
                      % dungeon.getDungeonMatrix()[0].length]) {
                //WEST
                directions = new ArrayList<>();
                directions.add("WEST");
                directions.add(String.valueOf(dungeon.getDungeonMatrix()[i]
                        [(j + dungeon.getDungeonMatrix()[0].length - 1)
                        % dungeon.getDungeonMatrix()[0].length]));
                validMoves.add(directions);
              }


            }
          }
        }
      }
    }

    return validMoves;
  }

  @Override
  public void collectTreasure() {
    Treasure t;
    List<String> treasure = this.treasure;
    for (int i = 0; i < treasure.size(); i++) {
      if (treasure.get(i).equals("")) {
        throw new IllegalArgumentException("Cave does not contain treasure");
      } else {
        RandomNumberGenerator r;
        if (seed != 0) {
          r = new RandomNumberGenerator(seed);
        } else {
          r = new RandomNumberGenerator();
        }
        if (treasure.get(i).equals("DIAMOND")) {
          t = Treasure.DIAMOND;
        } else if (treasure.get(i).equals("RUBY")) {
          t = Treasure.RUBY;
        } else {
          t = Treasure.SAPPHIRE;
        }
        try {
          treasureCollected.put(t, r.getRandomNumber(1, 100) + treasureCollected.get(t));
        } catch (NullPointerException e) {
          //if there is initially no data in HashMap for a particular type of treasure,
          //exception will be thrown.
          // So, in that case, a new key is created and the count for that treasure is passed.
          e.getMessage();
          treasureCollected.put(t, r.getRandomNumber(1, 100));
        }
        for (int j = 0; j < cavesWithTreasure.size(); j++) {
          if (cavesWithTreasure.get(j) == currLocation) {
            cavesWithTreasure.remove(j);
            dungeon.updateListOfCavesWithTreasure(j);
          }
        }

      }
    }
  }

  @Override
  public boolean slay(String direction, int distance) {
    int loc = this.currLocation;
    boolean f = false;
    List<List<String>> possibleShots1 = this.getPossibleMoves(loc);
    for (int i = 0; i < possibleShots1.size(); i++) {
      if (this.getPossibleMoves(loc).get(i).get(0).equals(direction)) {
        f = true;
        loc = Integer.parseInt(this.getPossibleMoves(loc).get(i).get(1));
        break;
      }
    }
    if (!f) {
      numOfArrows--;
      throw new IllegalStateException("Shot missed!");
    }

    if (numOfArrows > 0) {
      numOfArrows--;
      while (distance > 0) {
        //        if (this.getPossibleMoves(loc).get(j).get(0).contains(direction)) {
        //          distance--;
        f = false;
        List<List<String>> possibleShots = this.getPossibleMoves(loc);
        for (int i = 0; i < possibleShots.size(); i++) {

          if (this.dungeon.getEntrances(loc) != 2) {

            distance--; //distance is counted for caves and not tunnels
            if (distance == 0) {
              f = true;
              break;
            }
            loc = Integer.parseInt(this.getPossibleMoves(loc).get(i).get(1));
            f = true;
            break;
          } else if (this.dungeon.getEntrances(loc) == 2) {
            String s = this.getPossibleMoves(loc).get(i).get(0);
            if ((direction.equals("NORTH") && !s.equals("SOUTH"))
                    || (direction.equals("SOUTH") && !s.equals("NORTH"))
                    || (direction.equals("EAST") && !s.equals("WEST"))
                    || (direction.equals("WEST") && !s.equals("EAST"))) {
              try {
                direction = this.getPossibleMoves(loc).get(i).get(0);
              } catch (IndexOutOfBoundsException e) {
                break;
              }
              loc = Integer.parseInt(this.getPossibleMoves(loc).get(i).get(1));
              //                this.getPossibleMoves(loc);

            }
            f = true; //no Otyugh in tunnels
          }
        }
        if (!f) {
          throw new IllegalStateException("Shot missed!");
        }
        //        }

      }
      if (dungeon.isOtyughPresent(loc)) {
        //Otyugh injured or killed depending on how many arrows hit it
        if (dungeon.getOtyughHealth(loc) == Health.HEALTHY) {
          dungeon.updateOtyughHealth(loc);
        } else {

          dungeon.killOtyugh(loc);
          return true;
        }
      } else {
        if (dungeon.getEntrances(loc) != 2) {
          throw new IllegalStateException("Shot missed!");
        }
      }

    } else {
      throw new IllegalStateException("No arrows left in your arsenal");
    }
    return false;
  }

  @Override
  public void pickUpArrow() {
    boolean f = false;
    for (int j = 0; j < cavesWithArrow.size(); j++) {
      if (cavesWithArrow.get(j) == currLocation) {
        cavesWithArrow.remove(j);
        dungeon.updateListOfCavesWithArrows(j);
        numOfArrows += 3; //increase the number of arrows in the player's arsenal by 3 each time
        //player picks up arrows
        f = true;
      }
    }
    if (!f) {
      throw new IllegalStateException("Cave does not contain arrow");
    }
  }

  @Override
  public int smellOtyugh() {
    int count = 0;
    List<List<String>> moves = this.getPossibleMoves(this.currLocation);
    for (int i = 0; i < moves.size(); i++) {
      if (dungeon.isOtyughPresent(Integer.parseInt(moves.get(i).get(1)))
              && dungeon.getOtyughHealth(Integer.parseInt(moves.get(i).get(1))) != Health.DEAD) {
        return 2; //Strong smell
      } else {
        List<List<String>> twoDistAway = this.getPossibleMoves(
                Integer.parseInt(moves.get(i).get(1)));
        for (int j = 0; j < twoDistAway.size(); j++) {
          if (dungeon.isOtyughPresent(Integer.parseInt(twoDistAway.get(j).get(1)))
                  && dungeon.getOtyughHealth(Integer.parseInt(twoDistAway.get(j).get(1)))
                  != Health.DEAD
                  && dungeon.getOtyughHealth(Integer.parseInt(twoDistAway.get(j).get(1))) != null) {
            count++;
          }
        }
        if (count > 1) {
          return 2; //strong smell
        } else if (count == 1) {
          return 1; // less pungent smell
        }
      }
    }
    return 0; //No Otyugh in the vicinity - no smell
  }

  private boolean die() {
    //50% chance of dying when otyugh is injured
    if (dungeon.getOtyughHealth(this.currLocation) == Health.INJURED) {
      RandomNumberGenerator r;
      if (seed != 0) {
        r = new RandomNumberGenerator(seed);
      } else {
        r = new RandomNumberGenerator();
      }
      return r.getRandomNumber(1, 2) == 1;
    }
    return dungeon.isOtyughPresent(this.currLocation)
            && dungeon.getOtyughHealth(this.currLocation) != Health.DEAD; //Game ends here
  }

  @Override
  public String getPlayerInfo() {
    StringBuilder sb = new StringBuilder("Player Name: " + this.getName());
    sb.append("\n");
    if (this.hasReachedEnd()) {
      sb.append("You Won!!");
      sb.append("\n");
    } else {
      sb.append("Treasure Collected:" + this.getTreasureCollected().toString());
      sb.append("\n");
      sb.append("Player's location: " + this.getLocation());
      sb.append("\n");
      sb.append("Treasure at location:" + this.getAvailableTreasure());
      sb.append("\n");
      sb.append("Possible moves: " + this.getPossibleMoves(this.getLocation()));
      sb.append("\n");
    }

    return sb.toString();
  }

  @Override
  public List<Integer> getListOfCavesVisited() {
    
    return new ArrayList<>(cavesVisited);
  }


}
