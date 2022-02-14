import static org.junit.Assert.assertEquals;

import controller.DungeonConsoleController;
import controller.DungeonController;
import dungeon.DungeonModel;
import dungeon.DungeonModelImpl;
import java.io.StringReader;
import org.junit.Test;

/**
 * Test cases for the Dungeon controller, using mocks for readable and
 * appendable.
 */
public class DungeonConsoleControllerTest {

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    DungeonModel d = new DungeonModelImpl.DungeonModelBuilder().build();
    Readable input = new StringReader("M NORTH S 2 EAST P treasure P arrow M WEST");
    Appendable gameLog = new FailingAppendable();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(d);
  }

  @Test
  public void playGame1() {
    DungeonModel d = new DungeonModelImpl.DungeonModelBuilder()
            .playerName("Player 1")
            .isWrapping(true)
            .rows(4)
            .columns(5)
            .degreeOfInterconnectivity(4)
            .percent(20)
            .numOfOtyugh(4)
            .seed(150)
            .build();

    Readable in = new StringReader("M NORTH M WEST");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(in, gameLog);
    c.playGame(d);
    String expected = "Game has started.\n"
            + "You are in a cave\n"
            + "Doors lead to the WEST, EAST, NORTH\n"
            + "You smell a faint stench\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Where to? \n"
            + "You are in a cave\n"
            + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
            + "You smell something awful nearby\n"
            + "Move, Pickup, or Shoot (M-P-S)?\n"
            + "Where to? \n"
            + "Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
            + "Better luck next time\n";
    assertEquals(expected, gameLog.toString());
  }

  @Test
  public void playGame2() {
    DungeonModel m = new DungeonModelImpl.DungeonModelBuilder()
            .playerName("Player 1")
            .isWrapping(true)
            .rows(4)
            .columns(5)
            .degreeOfInterconnectivity(4)
            .percent(20)
            .numOfOtyugh(4)
            .seed(150)
            .build();

    Readable in = new StringReader("M NORTH S 1 WEST S 1 WEST M WEST P arrow P treasure "
           + "M NORTH M EAST M EAST S 1 WEST S 1 SOUTH S 1 SOUTH M SOUTH P treasure P arrow "
           + "M SOUTH M SOUTH M NORTH M NORTH M WEST M WEST M WEST M SOUTH M NORTH P arrow M NORTH"
           + " S 1 SOUTH S 1 WEST S 1 WEST M WEST SOUTH P arrow P treasure M EAST M SOUTH M SOUTH");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(in, gameLog);
    c.playGame(m);
    String expected = "Game has started.\n"
           + "You are in a cave\n"
           + "Doors lead to the WEST, EAST, NORTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You hear a great howl in the distance\n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "You picked up the arrows \n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Total Treasure collected: {DIAMOND=112, RUBY=80}\n"
           + "You are in a cave\n"
           
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the EAST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the NORTH, WEST, EAST, SOUTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "Shot missed!\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You hear a great howl in the distance\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Total Treasure collected: {DIAMOND=176, RUBY=112}\n"
           + "You are in a cave\n"
           
           + "Arrows are available in the cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "You picked up the arrows \n"
           + "You are in a cave\n"
           
           + "Doors lead to the SOUTH, NORTH, WEST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the WEST, SOUTH, NORTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the NORTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the WEST, SOUTH, NORTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           
           + "Doors lead to the SOUTH, NORTH, WEST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the NORTH, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "Invalid move!\n"
           + "You are in a tunnel \n"
           + "that continues to the NORTH, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "You picked up the arrows \n"
           + "You are in a cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "Shot missed!\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You hear a great howl in the distance\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Invalid input!\n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Cave does not contain arrow\n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Total Treasure collected: {DIAMOND=240, RUBY=144}\n"
           + "You are in a cave\n"
           
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the NORTH, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n";
    assertEquals(expected, gameLog.toString());
  }

  @Test
  public void testPlayGame3() {
    DungeonModel m = new DungeonModelImpl.DungeonModelBuilder()
            .playerName("Player 1")
            .isWrapping(true)
            .rows(4)
            .columns(5)
            .degreeOfInterconnectivity(4)
            .percent(20)
            .numOfOtyugh(4)
            .seed(150)
            .build();

    Readable in = new StringReader("M NORTH S 1 WEST S 1 WEST M WEST P treasure M WEST M NORTH"
          +  " M NORTH S 1 WEST S 1 WEST M SOUTH M WEST P arrow P treasure M EAST M SOUTH M EAST"
          +  " P arrow M WEST M NORTH M NORTH S 1 WEST M WEST P treasure M EAST M SOUTH P arrow "
          +  "M SOUTH M EAST M EAST S 1 EAST S 1 EAST M EAST P treasure M NORTH M SOUTH P arrow"
          +  " M WEST M WEST M WEST M NORTH M NORTH M WEST");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(in, gameLog);
    c.playGame(m);
    String expected = "Game has started.\n"
           + "You are in a cave\n"
           + "Doors lead to the WEST, EAST, NORTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You hear a great howl in the distance\n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Total Treasure collected: {DIAMOND=112, RUBY=80}\n"
           + "You are in a cave\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the NORTH, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "No arrows left in your arsenal\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Cave does not contain arrow\n"
           + "You are in a cave\n"
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Total Treasure collected: {DIAMOND=176, RUBY=112}\n"
           + "You are in a cave\n"
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the NORTH, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "You picked up the arrows \n"
           + "You are in a cave\n"
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the NORTH, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You hear a great howl in the distance\n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Total Treasure collected: {DIAMOND=240, RUBY=144}\n"
           + "You are in a cave\n"
           
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "You picked up the arrows \n"
           + "You are in a cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the NORTH, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You hear a great howl in the distance\n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Treasures in the cave: [DIAMOND, DIAMOND, RUBY]\n"
           + "Arrows are available in the cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "Total Treasure collected: {DIAMOND=304, RUBY=176}\n"
           + "You are in a cave\n"
           
           + "Arrows are available in the cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           
           + "Arrows are available in the cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "What (treasure/arrow)? \n"
           + "You picked up the arrows \n"
           + "You are in a cave\n"
           
           + "Doors lead to the SOUTH, NORTH, WEST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the SOUTH, NORTH, WEST, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           
           + "Doors lead to the NORTH, WEST, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the NORTH, EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the NORTH, WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           
           + "Doors lead to the EAST\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n";
    assertEquals(expected, gameLog.toString());
  }

  @Test
  public void testPlayGame4() {
    DungeonModel m = new DungeonModelImpl.DungeonModelBuilder()
            .playerName("Player 1")
            .isWrapping(true)
            .rows(4)
            .columns(5)
            .degreeOfInterconnectivity(4)
            .percent(20)
            .numOfOtyugh(4)
            .seed(150)
            .build();
    Readable in = new StringReader("M WEST M WEST M WEST S 1 NORTH S 1 NORTH M NORTH");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(in, gameLog);
    c.playGame(m);
    String expected = "Game has started.\n"
           + "You are in a cave\n"
           + "Doors lead to the WEST, EAST, NORTH\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a cave\n"
           + "Doors lead to the WEST, EAST, SOUTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the WEST, EAST\n"
           + "You smell a faint stench\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You are in a tunnel \n"
           + "that continues to the EAST, NORTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You are in a tunnel \n"
           + "that continues to the EAST, NORTH\n"
           + "You smell something awful nearby\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "No. of caves?\n"
           + "Where to?\n"
           + "You hear a great howl in the distance\n"
           + "You are in a tunnel \n"
           + "that continues to the EAST, NORTH\n"
           + "Move, Pickup, or Shoot (M-P-S)?\n"
           + "Where to? \n"
           + "You have Won!!\n";
    assertEquals(expected, gameLog.toString());
  }

}