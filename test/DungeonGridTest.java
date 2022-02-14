import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import dungeon.Dungeon;
import dungeon.DungeonGrid;
import dungeoncomponents.Health;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for DungeonGrid.
 */
public class DungeonGridTest {
  DungeonGrid d1;
  DungeonGrid d2;
  DungeonGrid d3;
  DungeonGrid d4;
  DungeonGrid d5;
  DungeonGrid d6;

  /**
   * Set up for DungeonGridTest test class.
   */
  @Before
  public void setUp() {
    //Non-Wrapping dungeon with 0 interconnectivity
    d1 = new Dungeon.DungeonBuilder().isWrapping(false)
            .rows(4).columns(5).degreeOfInterconnectivity(0).percent(20).seed(50).build();

    //Wrapping dungeon with 0 interconnectivity
    d2 = new Dungeon.DungeonBuilder().isWrapping(true).percent(100).seed(150).build();
    //Non-Wrapping dungeon with interconnectivity = 4
    d3 = new Dungeon.DungeonBuilder().isWrapping(false).rows(8).columns(7)
            .degreeOfInterconnectivity(4).percent(8).seed(100).build();
    //Wrapping dungeon with 0 interconnectivity = 3
    d4 = new Dungeon.DungeonBuilder().isWrapping(true).rows(4).columns(5)
            .degreeOfInterconnectivity(3).percent(12).seed(200).build();

    //Wrapping dungeon with 0 interconnectivity = 3
    d5 = new Dungeon.DungeonBuilder().isWrapping(true).rows(4).columns(5)
            .degreeOfInterconnectivity(3).percent(12).seed(200).numOfOtyugh(3).build();

    d6 = new Dungeon.DungeonBuilder().isWrapping(true).rows(4).columns(5)
            .degreeOfInterconnectivity(3).percent(12).seed(200).numOfOtyugh(1).build();
  }

  @Test
  public void testDungeonCreation() {
    try {
      DungeonGrid d5 = new Dungeon.DungeonBuilder().isWrapping(true).rows(4).columns(5)
              .degreeOfInterconnectivity(0).percent(190).seed(50).build();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid value for percentage of caves with treasure", e.getMessage());
    }
    try {
      DungeonGrid d5 = new Dungeon.DungeonBuilder().isWrapping(true).rows(4).columns(5)
              .degreeOfInterconnectivity(0).percent(-1).seed(50).build();
    } catch (IllegalArgumentException e) {
      assertEquals("Percent cannot be negative", e.getMessage());
    }

    try {
      DungeonGrid d5 = new Dungeon.DungeonBuilder().isWrapping(true).rows(4).columns(5)
              .degreeOfInterconnectivity(22).percent(1).seed(50).build();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid degree of interconnectivity", e.getMessage());
    }

    try {
      DungeonGrid d5 = new Dungeon.DungeonBuilder().isWrapping(false).rows(4).columns(5)
              .degreeOfInterconnectivity(13).percent(1).seed(50).build();

    } catch (IllegalArgumentException e) {
      assertEquals("Invalid degree of interconnectivity", e.getMessage());
    }

    try {
      DungeonGrid d5 = new Dungeon.DungeonBuilder().isWrapping(true).rows(2).columns(1)
              .degreeOfInterconnectivity(0).percent(1).seed(50).build();
    } catch (IllegalArgumentException e) {
      assertEquals("No. of rows and columns should be greater than 2", e.getMessage());
    }

    try {
      DungeonGrid d5 = new Dungeon.DungeonBuilder().isWrapping(false).rows(1).columns(5)
              .degreeOfInterconnectivity(0).percent(1).seed(50).build();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid row and column values", e.getMessage());
    }

    assertEquals("1\t*\t2\t*\t3\t-\t4\t*\t5\t*\t\n"
            + "|\t*\t|\t*\t|\t*\t|\t*\t|\t*\t\n"
            + "6\t-\t7\t-\t8\t*\t9\t-\t10\t*\t\n"
            + "|\t*\t\t*\t|\t*\t|\t*\t|\t*\t\n"
            + "11\t*\t12\t-\t13\t*\t14\t*\t15\t*\t\n"
            + "\t*\t|\t*\t\t*\t|\t*\t\t*\t\n"
            + "16\t-\t17\t-\t18\t*\t19\t-\t20\t*\t\n"
            + "\t*\t\t*\t\t*\t\t*\t\t*\t\n", d1.toString());
    //    System.out.println(d1.toString());
    assertEquals("1\t-\t2\t*\t3\t-\t4\t*\t5\t*\t\n"
            + "\t*\t|\t*\t|\t*\t\t*\t|\t*\t\n"
            + "6\t-\t7\t-\t8\t-\t9\t-\t10\t*\t\n"
            + "\t*\t|\t*\t\t*\t\t*\t\t*\t\n"
            + "11\t-\t12\t-\t13\t-\t14\t*\t15\t*\t\n"
            + "\t*\t\t*\t\t*\t\t*\t|\t*\t\n"
            + "16\t-\t17\t-\t18\t-\t19\t-\t20\t*\t\n"
            + "|\t*\t\t*\t\t*\t|\t*\t|\t*\t\n"
            + "21\t*\t22\t-\t23\t-\t24\t*\t25\t*\t\n"
            + "\t*\t|\t*\t\t*\t\t*\t\t*\t\n", d2.toString());

    assertEquals("1\t-\t2\t-\t3\t-\t4\t-\t5\t*\t6\t*\t7\t*\t\n"
            + "|\t*\t\t*\t|\t*\t|\t*\t\t*\t|\t*\t|\t*\t\n"
            + "8\t*\t9\t-\t10\t*\t11\t-\t12\t*\t13\t-\t14\t*\t\n"
            + "|\t*\t\t*\t\t*\t\t*\t|\t*\t|\t*\t\t*\t\n"
            + "15\t-\t16\t*\t17\t*\t18\t*\t19\t-\t20\t-\t21\t*\t\n"
            + "\t*\t\t*\t|\t*\t|\t*\t|\t*\t\t*\t|\t*\t\n"
            + "22\t-\t23\t*\t24\t-\t25\t*\t26\t-\t27\t-\t28\t*\t\n"
            + "|\t*\t|\t*\t\t*\t|\t*\t\t*\t|\t*\t\t*\t\n"
            + "29\t-\t30\t-\t31\t*\t32\t*\t33\t-\t34\t-\t35\t*\t\n"
            + "\t*\t\t*\t|\t*\t|\t*\t|\t*\t|\t*\t\t*\t\n"
            + "36\t*\t37\t*\t38\t-\t39\t*\t40\t*\t41\t-\t42\t*\t\n"
            + "|\t*\t|\t*\t|\t*\t|\t*\t\t*\t|\t*\t|\t*\t\n"
            + "43\t-\t44\t-\t45\t*\t46\t-\t47\t-\t48\t-\t49\t*\t\n"
            + "\t*\t|\t*\t\t*\t|\t*\t|\t*\t\t*\t|\t*\t\n"
            + "50\t-\t51\t*\t52\t-\t53\t*\t54\t-\t55\t-\t56\t*\t\n"
            + "\t*\t\t*\t\t*\t\t*\t\t*\t\t*\t\t*\t\n", d3.toString());

    assertEquals("1\t-\t2\t*\t3\t*\t4\t-\t5\t-\t1\n"
            + "\t*\t|\t*\t|\t*\t|\t*\t|\t*\t\n"
            + "6\t*\t7\t*\t8\t*\t9\t-\t10\t*\t\n"
            + "|\t*\t\t*\t|\t*\t|\t*\t|\t*\t\n"
            + "11\t*\t12\t*\t13\t-\t14\t*\t15\t-\t11\n"
            + "\t*\t|\t*\t|\t*\t|\t*\t\t*\t\n"
            + "16\t*\t17\t*\t18\t*\t19\t*\t20\t*\t\n"
            + "|\t*\t|\t*\t|\t*\t|\t*\t|\t*\t\n", d4.toString());

    try {
      DungeonGrid d5 = new Dungeon.DungeonBuilder().isWrapping(false).rows(4).columns(5)
              .degreeOfInterconnectivity(3).percent(1).numOfOtyugh(20).seed(50).build();

    } catch (IllegalArgumentException e) {
      assertEquals("Invalid number of Otyughs", e.getMessage());
    }

    try {
      DungeonGrid d5 = new Dungeon.DungeonBuilder().isWrapping(true).rows(4).columns(5)
              .degreeOfInterconnectivity(3).percent(1).numOfOtyugh(20).seed(50).build();

    } catch (IllegalArgumentException e) {
      assertEquals("Invalid number of Otyughs", e.getMessage());
    }
  }

  @Test
  public void testGetListOfCavesWithTreasure() {
    List<Integer> listOfCaves1 = d1.getListOfCavesWithTreasure();
    assertEquals("[8, 11, 2]", listOfCaves1.toString()); //20% of caves have treasure
    //i.e. 20% of caves that are not tunnels. Here there are 14/20 such caves that are not tunnels.
    // 14 * 0.2 = 2.8 ~ 3 considering ceil(2.8)

    List<Integer> listOfCaves2 = d2.getListOfCavesWithTreasure();
    assertEquals("[19, 14, 20, 11, 8, 25, 1, 21, 7, 2, 5, 4, 6, 12, 15]",
            listOfCaves2.toString());

    List<Integer> listOfCaves3 = d3.getListOfCavesWithTreasure();
    assertEquals("[47, 19, 7]", listOfCaves3.toString());


    List<Integer> listOfCaves4 = d4.getListOfCavesWithTreasure();
    assertEquals("[6, 9]", listOfCaves4.toString());
  }

  @Test
  public void testOtyugh() {
    Map<Integer, Health> listOfCaves1 = d5.getListOfCavesWithOtyughs();
    assertEquals("{16=HEALTHY, 9=HEALTHY, 14=HEALTHY}", listOfCaves1.toString());

    assertEquals(false, d5.isOtyughPresent(1));
    assertEquals(true, d5.isOtyughPresent(16));
  }

  @Test
  public void testGetDegreeOfInterconnectivity() {
    assertEquals(0, d1.getDegreeOfInterconnectivity());
    assertEquals(0, d2.getDegreeOfInterconnectivity());
    assertEquals(4, d3.getDegreeOfInterconnectivity());
    assertEquals(3, d4.getDegreeOfInterconnectivity());
  }

  @Test
  public void testGetStart() {
    assertEquals(9, d1.getStart());
    assertEquals(4, d2.getStart());
    assertEquals(25, d3.getStart());
    assertEquals(6, d4.getStart());
    assertEquals(1, d4.getEntrances(d4.getStart()));
  }

  @Test
  public void testGetEnd() {
    assertEquals(11, d1.getEnd());
    assertEquals(14, d2.getEnd());
    assertEquals(35, d3.getEnd());
    assertEquals(16, d4.getEnd());
  }


  @Test
  public void testGetDungeonMatrix() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < d1.getDungeonMatrix().length; i++) {
      for (int j = 0; j < d1.getDungeonMatrix()[0].length; j++) {
        sb.append(d1.getDungeonMatrix()[i][j] + " ");
      }
      sb.append("\n");
    }
    assertEquals("1 2 3 4 5 \n"
            + "6 7 8 9 10 \n"
            + "11 12 13 14 15 \n"
            + "16 17 18 19 20 \n", sb.toString());
  }

  @Test
  public void testGetDungeonPaths() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < d4.getDungeonPaths().length; i++) {
      for (int j = 0; j < d4.getDungeonPaths()[0].length; j++) {
        sb.append(d4.getDungeonPaths()[i][j] + " ");
      }
      sb.append("\n");
    }
    assertEquals("0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 \n"
            + "1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 \n"
            + "0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 \n"
            + "0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 \n"
            + "1 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 \n"
            + "0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 \n"
            + "0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 \n"
            + "0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 \n"
            + "0 0 0 1 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 \n"
            + "0 0 0 0 1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 \n"
            + "0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 \n"
            + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 \n"
            + "0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 1 0 0 \n"
            + "0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 1 0 \n"
            + "0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 \n"
            + "1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 \n"
            + "0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 \n"
            + "0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 \n"
            + "0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 \n"
            + "0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 \n", sb.toString());
  }

  @Test
  public void testCavesWithTreasure() {
    //Caves that contain treasure are not tunnels
    List<Integer> caves = d1.getListOfCavesWithTreasure();
    int count = 0;
    for (int i = 0; i < caves.size(); i++) {
      if (d1.getEntrances(caves.get(i)) != 2) {
        count++;
      }

    }
    assertEquals(caves.size(), count); //All the caves that contain treasure are caves and not
    //tunnels
  }

  @Test
  public void testOtyughAtEnd() {
    assertEquals(11, d1.getEnd());
    assertEquals(true, d1.isOtyughPresent(11));
    assertEquals(true, d2.isOtyughPresent(d2.getEnd()));
    assertEquals(true, d3.isOtyughPresent(d3.getEnd()));
    assertEquals(true, d4.isOtyughPresent(d4.getEnd()));
    assertEquals(true, d5.isOtyughPresent(d5.getEnd()));
    assertEquals(true, d6.isOtyughPresent(d6.getEnd()));
  }

  @Test
  public void testNoOtyughAtStart() {
    assertEquals(9, d1.getStart());
    assertEquals(false, d1.isOtyughPresent(9));
    assertEquals(false, d2.isOtyughPresent(d2.getStart()));
    assertEquals(false, d3.isOtyughPresent(d3.getStart()));
    assertEquals(false, d4.isOtyughPresent(d4.getStart()));
    assertEquals(false, d5.isOtyughPresent(d5.getStart()));
    assertEquals(false, d6.isOtyughPresent(d6.getStart()));
  }

  @Test
  public void testOtyughInCavesOnly() {
    assertEquals("{16=HEALTHY, 9=HEALTHY, 14=HEALTHY}",
            d5.getListOfCavesWithOtyughs().toString());
    assertEquals(1, d5.getEntrances(16)); //not a tunnel
    assertEquals(3, d5.getEntrances(9)); //not a tunnel
    assertEquals(3, d5.getEntrances(14)); //not a tunnel

    assertEquals("{16=HEALTHY}", d6.getListOfCavesWithOtyughs().toString());
    assertNotEquals(2, d6.getEntrances(16));
  }

  @Test
  public void testOtyughAndOtherItems() {
    assertEquals("[6, 9]", d5.getListOfCavesWithTreasure().toString()); //9 also has
    // otyugh ...refer test testOtyughInCavesOnly()
  }

  @Test
  public void testArrowsTreasureOccurrence() {
    //Treasure and arrows may not always be found together in a Cave.
    assertEquals("[8, 11, 2]", d1.getListOfCavesWithTreasure().toString());
    assertEquals("[18, 9, 14, 13]", d1.getListOfCavesWithArrows().toString());

    //Caves 11, 1, 2, 4, 25 have both treasure and arrows).
    assertEquals("[19, 14, 20, 11, 8, 25, 1, 21, 7, 2, 5, 4, 6, 12, 15]",
            d2.getListOfCavesWithTreasure().toString());
    assertEquals("[7, 5, 13, 8, 15, 16, 24, 11, 9, 19, 22, 12, 10, 3, 18, 23,"
           + " 6, 17, 1, 2, 14, 20, 25, 4, 21]", d2.getListOfCavesWithArrows().toString());

    assertEquals(2, d2.getEntrances(13)); //Arrows are found in tunnels too
    assertEquals(4, d2.getEntrances(7)); //Arrows are found in caves
    assertEquals(2, d2.getEntrances(18)); //Arrows are found in tunnels too
  }
}