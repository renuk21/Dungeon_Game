package controller;

import dungeon.DungeonGrid;
import dungeon.DungeonModel;
import dungeon.InterfacePlayer;
import dungeoncomponents.Arrow;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Controller for Dungeon and Player models.
 */
public class DungeonConsoleController implements DungeonController {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  private void checkNonNull(Object m) {
    try {
      Objects.requireNonNull(m);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Invalid Model");
    }
  }

  @Override
  public void playGame(DungeonModel m) { //pass a single model to the controller
    String element = "";
    this.checkNonNull(m);
    DungeonGrid d = m.getDungeon();
    InterfacePlayer p = m.getPlayer();
    int i = 0;
    int n = 0;
    String direction = "";
    try {

      out.append("Game has started." + "\n");

      while (true) {
        try {
          String s = "";
          for (int j = 0; j < p.getPossibleMoves(p.getLocation()).size(); j++) {
            if (j > 0) {
              s = s + ", " + p.getPossibleMoves(p.getLocation()).get(j).get(0);
            } else {
              s = p.getPossibleMoves(p.getLocation()).get(j).get(0);
            }
          }
          // create a method to write these conditions within the model interface, the controller
          //should only pass the values to the model
          if (d.getEntrances(p.getLocation()) == 2) {
            out.append("You are in a tunnel \n");

            out.append("that continues to the " + s + "\n");
          } else {
            out.append("You are in a cave" + "\n");
            List<Integer> treasureCaves = d.getListOfCavesWithTreasure();
            int cave = 0;
            for (Integer j : treasureCaves) {
              if (j == p.getLocation()) {
                cave = j;
                break;
              }
            }
            if (cave != 0) {
              out.append("Treasures in the cave: " + p.getAvailableTreasure().toString() + "\n");
            }

            List<Arrow> arrows = p.getAvailableArrows();

            if (arrows != null) {
              out.append("Arrows are available in the cave" + "\n");
            }
            out.append("Doors lead to the " + s + "\n");

          }

          if (p.smellOtyugh() == 2) {
            out.append("You smell something awful nearby" + "\n");
          } else if (p.smellOtyugh() == 1) {
            out.append("You smell a faint stench" + "\n");
          }

          out.append("Move, Pickup, or Shoot (M-P-S)?" + "\n");


          boolean f = false;
          if (!scan.hasNext()) {
            element = "ret";
          } else {
            element = scan.next();
            f = true;
          }

          if (!f) {
            element = "ret";
          }

          boolean flag = false;

          switch (element) {
            case "ret":
              scan.close();
              return;
            case "M":
              out.append("Where to? \n");
              try {
                p.move(scan.next());
              } catch (IllegalStateException e) {
                out.append(e.getMessage() + "\n");
                scan.close();
                return;
              }
              break;
            case "S":
              flag = true;

              out.append("No. of caves?" + "\n");
              n = Integer.parseInt(scan.next());

              out.append("Where to?" + "\n");
              direction = scan.next();

              break;
            case "P":
              out.append("What (treasure/arrow)? " + "\n");
              String element1 = scan.next();
              switch (element1) {
                case "treasure":
                  p.collectTreasure();
                  out.append("Total Treasure collected: " + p.getTreasureCollected() + "\n");
                  break;
                case "arrow":
                  p.pickUpArrow();
                  out.append("You picked up the arrows \n");
                  break;
                default:
                  out.append("Invalid input!" + "\n");
                  break;
              }
              break;
            default:
              out.append("Invalid input!" + "\n");
              break;
          }

          if (flag) {
            boolean isKilled = p.slay(direction, n);
            if (isKilled) {
              out.append("You hear a great howl in the distance" + "\n");
            }
          }

          if (p.getLocation() == d.getEnd()) {
            out.append("You have Won!!" + "\n");
            scan.close();
            return;
          }
        } catch (IllegalArgumentException | IllegalStateException e) {
          out.append(e.getMessage() + "\n");
        }
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }
}
