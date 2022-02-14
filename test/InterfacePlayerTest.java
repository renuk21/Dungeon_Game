import static org.junit.Assert.assertEquals;

import dungeon.Dungeon;
import dungeon.DungeonGrid;
import dungeon.InterfacePlayer;
import dungeon.Player;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for IPlayer.
 */
public class InterfacePlayerTest {
  InterfacePlayer p1;
  InterfacePlayer p2;

  /**
   * Set up for IPlayerTest.
   */
  @Before
  public void setUp() {
    DungeonGrid d1 = new Dungeon.DungeonBuilder().seed(50).build();
    DungeonGrid d2 = new Dungeon.DungeonBuilder().isWrapping(true)
            .percent(10)
            .degreeOfInterconnectivity(3)
            .rows(6)
            .columns(8)
            .seed(150)
            .build();
    p1 = new Player("Player1", (Dungeon) d1, 50);
    p2 = new Player("Player2", (Dungeon) d2, 50);
    //    System.out.println(d1.getListOfCavesWithTreasure());
  }

  @Test
  public void testPlayerCreation() {

    assertEquals("Player1", p1.getName());
    assertEquals(5, p1.getLocation());
  }

  @Test
  public void getLocation() {
    assertEquals(5, p1.getLocation());
    assertEquals(16, p2.getLocation());
  }

  @Test
  public void move() {
    try {
      p1.move("NORTH");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid move!", e.getMessage());
    }

    try {
      p1.move("EAST");

    } catch (IllegalArgumentException e) {
      assertEquals("Invalid move!", e.getMessage());
    }

    assertEquals(5, p1.getLocation());


  }

  @Test
  public void getTreasureCollected() {
    assertEquals("{}", p1.getTreasureCollected().toString());
    assertEquals("{}", p2.getTreasureCollected().toString());
    try {
      p1.collectTreasure();
    } catch (IllegalArgumentException e) {
      assertEquals("Cave does not contain treasure", e.getMessage());
    }
    assertEquals("[[WEST, 4]]", p1.getPossibleMoves(p1.getLocation()).toString());
    //    p1.slay("WEST", 1);
    //    p1.slay("WEST", 1);
    p1.move("WEST");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    assertEquals("[[EAST, 5], [SOUTH, 9]]", p1.getPossibleMoves(p1.getLocation()).toString());
    //    p1.slay("SOUTH", 1);
    //    p1.slay("SOUTH", 1);
    p1.move("SOUTH");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    assertEquals("[[NORTH, 4], [SOUTH, 14]]", p1.getPossibleMoves(p1.getLocation()).toString());

    p1.move("SOUTH");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    assertEquals("[[NORTH, 9], [SOUTH, 19]]", p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("SOUTH");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    try {
      p1.collectTreasure();
    } catch (IllegalArgumentException e) {
      assertEquals("Cave does not contain treasure", e.getMessage());
    }

    assertEquals("[[NORTH, 14], [WEST, 18], [EAST, 20], [SOUTH, 24]]",
            p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("EAST");
    assertEquals("[DIAMOND, SAPPHIRE]",
            p1.getAvailableTreasure().toString());
    p1.collectTreasure();
    assertEquals("{DIAMOND=89, SAPPHIRE=89}", p1.getTreasureCollected().toString());
    assertEquals("[[NORTH, 15], [WEST, 19], [SOUTH, 25]]",
            p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("WEST");
    assertEquals("[[NORTH, 14], [WEST, 18], [EAST, 20], [SOUTH, 24]]",
            p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("SOUTH");
    assertEquals("[[NORTH, 19], [WEST, 23]]",
            p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("WEST");
    assertEquals("[[WEST, 22], [EAST, 24]]",
            p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("WEST");
    assertEquals("[[WEST, 21], [EAST, 23]]", p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("WEST");
    assertEquals("[[NORTH, 16], [EAST, 22]]", p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("NORTH");
    assertEquals("[[NORTH, 11], [EAST, 17], [SOUTH, 21]]",
            p1.getPossibleMoves(p1.getLocation()).toString());
    assertEquals(false, p1.hasReachedEnd());

    try {
      p1.slay("EAST", 1);
      p1.slay("EAST", 1);
      p1.move("EAST");
    } catch (IllegalArgumentException e) {
      //assertEquals("Game Has Ended. You have reached the end cave", e.getMessage());
      //action will be handled by the controller. The model can check if player has reached the
      // end cave
    }
    assertEquals(true, p1.hasReachedEnd());
  }

  @Test
  public void getName() {
    assertEquals("Player1", p1.getName());
    assertEquals("Player2", p2.getName());
  }

  @Test
  public void getAvailableTreasure() {
    assertEquals("[]", p1.getAvailableTreasure().toString());
    assertEquals("[]", p2.getAvailableTreasure().toString());
    p1.move("WEST");
    p1.getAvailableTreasure();
    p1.move("SOUTH");
    p1.getAvailableTreasure();
    p1.getPossibleMoves(p1.getLocation());
    p1.move("SOUTH");
    p1.getAvailableTreasure();
    p1.getPossibleMoves(p1.getLocation());
    p1.move("SOUTH");
    p1.getAvailableTreasure();
    p1.getPossibleMoves(p1.getLocation());
    p1.move("SOUTH");
    p1.getAvailableTreasure();
    p1.getPossibleMoves(p1.getLocation());
    p1.move("WEST");
    p1.getAvailableTreasure();
    p1.getPossibleMoves(p1.getLocation());
    p1.move("WEST");
    p1.getAvailableTreasure();
    p1.getPossibleMoves(p1.getLocation());
    p1.move("WEST");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    p1.move("NORTH");
    p1.getAvailableTreasure();
    assertEquals("[]", p1.getAvailableTreasure().toString());
  }

  @Test
  public void getPossibleMoves() {
    assertEquals("[[WEST, 4]]", p1.getPossibleMoves(p1.getLocation()).toString());
    assertEquals("[[NORTH, 8], [EAST, 9], [WEST, 15], [SOUTH, 24]]",
            p2.getPossibleMoves(p2.getLocation()).toString());
  }

  @Test
  public void collectTreasure() {
    try {
      p1.collectTreasure();
    } catch (IllegalArgumentException e) {
      assertEquals("Cave does not contain treasure", e.getMessage());
    }
    assertEquals("[[WEST, 4]]", p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("WEST");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    assertEquals("[[EAST, 5], [SOUTH, 9]]", p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("SOUTH");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    assertEquals("[[NORTH, 4], [SOUTH, 14]]", p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("SOUTH");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    assertEquals("[[NORTH, 9], [SOUTH, 19]]", p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("SOUTH");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    try {
      p1.collectTreasure();
    } catch (IllegalArgumentException e) {
      assertEquals("Cave does not contain treasure", e.getMessage());
    }

    assertEquals("[[NORTH, 14], [WEST, 18], [EAST, 20], [SOUTH, 24]]",
            p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("EAST");
    assertEquals("[DIAMOND, SAPPHIRE]", p1.getAvailableTreasure().toString());
    p1.collectTreasure();
    assertEquals("{DIAMOND=89, SAPPHIRE=89}", p1.getTreasureCollected().toString());
    assertEquals("[[NORTH, 15], [WEST, 19], [SOUTH, 25]]",
            p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("NORTH");
    assertEquals("[]", p1.getAvailableTreasure().toString());
    assertEquals("[[NORTH, 10], [SOUTH, 20]]", p1.getPossibleMoves(p1.getLocation()).toString());
    p1.move("NORTH");
    assertEquals("[DIAMOND, SAPPHIRE]", p1.getAvailableTreasure().toString());
    p1.collectTreasure();
    assertEquals("{DIAMOND=107, SAPPHIRE=107}", p1.getTreasureCollected().toString());
    assertEquals("[[SOUTH, 15]]", p1.getPossibleMoves(p1.getLocation()).toString());
  }

  @Test
  public void testAccessToEveryCave() {
    DungeonGrid d = new Dungeon.DungeonBuilder().seed(150).rows(3).columns(3)
            .degreeOfInterconnectivity(0).percent(15).isWrapping(true).build();
    InterfacePlayer p = new Player("Player XYZ", (Dungeon) d);
    assertEquals("1\t-\t2\t-\t3\t*\t\n"
            + "|\t*\t\t*\t|\t*\t\n"
            + "4\t*\t5\t-\t6\t*\t\n"
            + "\t*\t\t*\t\t*\t\n"
            + "7\t*\t8\t-\t9\t*\t\n"
            + "|\t*\t\t*\t|\t*\t\n", d.toString());
    assertEquals(3, p.getLocation()); //Cave 3
    p.getAvailableTreasure().toString();
    assertEquals("[[WEST, 2], [SOUTH, 6], [NORTH, 9]]",
            p.getPossibleMoves(p.getLocation()).toString());
    p.move("WEST"); //Cave 2
    assertEquals("[[WEST, 1], [EAST, 3]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("WEST"); //Cave 1
    assertEquals("[[EAST, 2], [SOUTH, 4], [NORTH, 7]]",
            p.getPossibleMoves(p.getLocation()).toString());
    p.move("SOUTH"); //Cave 4
    assertEquals("[[NORTH, 1]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("NORTH"); //Cave 1
    assertEquals("[[EAST, 2], [SOUTH, 4], [NORTH, 7]]",
            p.getPossibleMoves(p.getLocation()).toString());
    p.move("EAST"); //Cave 2
    assertEquals("[[WEST, 1], [EAST, 3]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("EAST"); //Cave 3
    assertEquals("[[WEST, 2], [SOUTH, 6], [NORTH, 9]]",
            p.getPossibleMoves(p.getLocation()).toString());
    p.move("SOUTH"); //Cave 6
    assertEquals("[[NORTH, 3], [WEST, 5]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("WEST"); //Cave 5
    assertEquals("[[EAST, 6]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("EAST"); //Cave 6
    assertEquals("[[NORTH, 3], [WEST, 5]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("NORTH"); //Cave 3
    assertEquals("[[WEST, 2], [SOUTH, 6], [NORTH, 9]]",
            p.getPossibleMoves(p.getLocation()).toString());
    p.move("NORTH"); //Cave 9
    assertEquals("[[SOUTH, 3], [WEST, 8]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("WEST"); //Cave 8
    assertEquals("[[EAST, 9]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("EAST"); //Cave 9
    assertEquals("[[SOUTH, 3], [WEST, 8]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("SOUTH"); //Cave 3
    assertEquals("[[WEST, 2], [SOUTH, 6], [NORTH, 9]]",
            p.getPossibleMoves(p.getLocation()).toString());
    p.move("WEST"); //Cave 2
    assertEquals("[[WEST, 1], [EAST, 3]]", p.getPossibleMoves(p.getLocation()).toString());
    p.move("WEST"); //Cave 1
    assertEquals("[[EAST, 2], [SOUTH, 4], [NORTH, 7]]",
            p.getPossibleMoves(p.getLocation()).toString());
    assertEquals(false, p.hasReachedEnd());
    p.slay("NORTH", 1);
    p.slay("NORTH", 1);
    p.move("NORTH"); //Cave 7
    assertEquals("[[SOUTH, 1]]", p.getPossibleMoves(p.getLocation()).toString());
    assertEquals(true, p.hasReachedEnd());

  }

  @Test
  public void testGetPlayerInfo() {
    assertEquals("Player Name: Player1\n"
            + "Treasure Collected:{}\n"
            + "Player's location: 5\n"
            + "Treasure at location:[]\n"
            + "Possible moves: [[WEST, 4]]\n", p1.getPlayerInfo());

    p1.move("WEST");


  }

  @Test
  public void testSmellOtyugh() {
    assertEquals(0, p1.smellOtyugh());
    DungeonGrid d3 = new Dungeon.DungeonBuilder().isWrapping(true)
            .percent(10)
            .degreeOfInterconnectivity(3)
            .rows(6)
            .columns(8)
            .seed(150)
            .numOfOtyugh(7)
            .build();
    InterfacePlayer p3 = new Player("Player1", (Dungeon) d3, 50);
    //    System.out.println(d3.toString());
    //    System.out.println(d3.getListOfCavesWithOtyughs());
    assertEquals(16, p3.getLocation());
    p3.pickUpArrow();
    assertEquals(2, p3.smellOtyugh()); //Strong smell
    assertEquals("[[NORTH, 8], [EAST, 9], [WEST, 15], [SOUTH, 24]]",
            p3.getPossibleMoves(p3.getLocation()).toString());

    //    p3.slay("EAST", 2);
    p3.slay("EAST", 1);
    p3.slay("EAST", 1);
    assertEquals(0, p3.smellOtyugh()); //no smell after Otyugh is killed
    p3.move("EAST");
    assertEquals(1, p3.smellOtyugh()); //1 Otyugh two positions away
    try {
      p3.slay("SOUTH", 2);
    } catch (IllegalStateException  e) {
      assertEquals("Shot missed!", e.getMessage());
    }
    p3.move("NORTH");
    assertEquals(2, p3.smellOtyugh());
    p3.slay("EAST", 1);
    p3.slay("EAST", 1);
    assertEquals(0, p3.smellOtyugh());
    p3.move("EAST");
    assertEquals(0, p3.smellOtyugh());
    p3.move("WEST");
    assertEquals(0, p3.smellOtyugh());


  }

  @Test
  public void testSmellOtyugh1() {
    assertEquals(0, p1.smellOtyugh());
    DungeonGrid d3 = new Dungeon.DungeonBuilder().isWrapping(true)
            .percent(10)
            .degreeOfInterconnectivity(3)
            .rows(6)
            .columns(8)
            .seed(150)
            .numOfOtyugh(7)
            .build();
    InterfacePlayer p3 = new Player("Player1", (Dungeon) d3, 50);
    assertEquals(16, p3.getLocation());
    p3.pickUpArrow();
    p3.move("WEST");
    p3.move("SOUTH");
    assertEquals(1, p3.smellOtyugh());
    p3.slay("SOUTH", 1);
    p3.slay("SOUTH", 1);
    assertEquals(0, p3.smellOtyugh());
  }

  @Test
  public void testSmellOtyugh2() {
    assertEquals(0, p1.smellOtyugh());
    DungeonGrid d3 = new Dungeon.DungeonBuilder().isWrapping(true)
            .percent(10)
            .degreeOfInterconnectivity(3)
            .rows(6)
            .columns(8)
            .seed(150)
            .numOfOtyugh(7)
            .build();
    InterfacePlayer p3 = new Player("Player1", (Dungeon) d3, 50);
    //    System.out.println(d3.toString());
    //    System.out.println(d3.getListOfCavesWithOtyughs());
    assertEquals(16, p3.getLocation());
    p3.pickUpArrow();
    assertEquals(2, p3.smellOtyugh()); //Strong smell
    assertEquals("[[NORTH, 8], [EAST, 9], [WEST, 15], [SOUTH, 24]]",
            p3.getPossibleMoves(p3.getLocation()).toString());

    //    p3.slay("EAST", 2);
    p3.slay("EAST", 1);
    p3.slay("EAST", 1);
    assertEquals(0, p3.smellOtyugh()); //no smell after Otyugh is killed
    p3.move("EAST");
    assertEquals(1, p3.smellOtyugh()); //1 Otyugh two positions away
    p3.move("NORTH");
    assertEquals(2, p3.smellOtyugh());
    try {
      p3.slay("EAST", 2);
    } catch (IllegalStateException  e) {
      assertEquals("Shot missed!", e.getMessage());
    }
    p3.slay("EAST", 1);
    p3.slay("EAST", 1);
    assertEquals(0, p3.smellOtyugh());
  }

  @Test
  public void testSlay() {
    assertEquals(0, p1.smellOtyugh());
    DungeonGrid d3 = new Dungeon.DungeonBuilder().isWrapping(true)
            .percent(10)
            .degreeOfInterconnectivity(3)
            .rows(6)
            .columns(8)
            .seed(150)
            .numOfOtyugh(7)
            .build();
    InterfacePlayer p3 = new Player("Player1", (Dungeon) d3, -1);
    //    System.out.println(d3.toString());
    //    System.out.println(d3.getListOfCavesWithOtyughs());
    assertEquals(16, p3.getLocation());
    p3.pickUpArrow();
    assertEquals(2, p3.smellOtyugh()); //Strong smell
    assertEquals("[[NORTH, 8], [EAST, 9], [WEST, 15], [SOUTH, 24]]",
            p3.getPossibleMoves(p3.getLocation()).toString());

    //    p3.slay("EAST", 2);
    p3.slay("EAST", 1);
    try {
      p3.move("EAST"); //Player gets eaten by injured Otyugh (50% chance)
    } catch (IllegalStateException e) {
      assertEquals("Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
             + "Better luck next time", e.getMessage());
    }
    try {
      p3.move("NORTH");
      assertEquals(2, p3.smellOtyugh());
    } catch (IllegalArgumentException  e) {
      assertEquals("Game Has Ended.", e.getMessage());
    }

  }

  @Test
  public void testSlay1() {
    assertEquals(0, p1.smellOtyugh());
    DungeonGrid d3 = new Dungeon.DungeonBuilder().isWrapping(true)
            .percent(10)
            .degreeOfInterconnectivity(3)
            .rows(6)
            .columns(8)
            .seed(150)
            .numOfOtyugh(7)
            .build();
    InterfacePlayer p3 = new Player("Player1", (Dungeon) d3, -1);
    assertEquals(16, p3.getLocation());
    p3.pickUpArrow();
    p3.move("WEST");
    p3.move("SOUTH");
    assertEquals(1, p3.smellOtyugh());
    p3.slay("SOUTH", 1);
    p3.move("SOUTH"); //player escaapes injured Otyugh (50%) chance
  }

  @Test
  public void testSlay3() {
    assertEquals(0, p1.smellOtyugh());
    DungeonGrid d3 = new Dungeon.DungeonBuilder().isWrapping(true)
            .percent(10)
            .degreeOfInterconnectivity(3)
            .rows(6)
            .columns(8)
            .seed(150)
            .numOfOtyugh(7)
            .build();
    InterfacePlayer p3 = new Player("Player1", (Dungeon) d3, -1);
    //    System.out.println(d3.toString());
    //    System.out.println(d3.getListOfCavesWithOtyughs());
    assertEquals(16, p3.getLocation());
    p3.pickUpArrow();
    assertEquals(2, p3.smellOtyugh()); //Strong smell
    assertEquals("[[NORTH, 8], [EAST, 9], [WEST, 15], [SOUTH, 24]]",
            p3.getPossibleMoves(p3.getLocation()).toString());

    try {
      p3.move("EAST"); //Player gets eaten by healthy Otyugh
    } catch (IllegalStateException e) {
      assertEquals("Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
             + "Better luck next time", e.getMessage());
    }
    try {
      p3.move("NORTH");
      assertEquals(2, p3.smellOtyugh());
    } catch (IllegalArgumentException  e) {
      assertEquals("Game Has Ended.", e.getMessage());
    }

  }

}