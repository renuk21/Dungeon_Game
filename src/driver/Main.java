package driver;

import controller.DungeonConsoleController;
import controller.DungeonViewController;
import controller.DungeonViewControllerImpl;
import dungeon.DungeonModel;
import dungeon.DungeonModelImpl;
import java.io.IOException;
import java.io.InputStreamReader;
import view.DungeonView;
import view.InterfaceView;


/**
 * Run a Dungeon text based game interactively on the console.
 */
public class Main {
  /**
   * Run a Dungeon text based game interactively on the console.
   */
  public static void main(String[] args) throws IOException {
    boolean isWrapping;
    int rows = 4;
    int columns = 5;
    int degreeOfInterConnectivity = 4;
    double percent = 20;
    isWrapping = true;
    int numOfOtyugh = 4;
    if (args.length != 0) { //Console based implementation
      isWrapping = Boolean.parseBoolean(args[0]);
      rows = Integer.parseInt(args[1]);
      columns = Integer.parseInt(args[2]);
      degreeOfInterConnectivity = Integer.parseInt(args[3]);
      percent = Double.parseDouble(args[4]);
      numOfOtyugh = Integer.parseInt(args[5]);

      int seed = 0;
      if (isWrapping) {
        seed = 150;
      } else {
        seed = 50;
      }

      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      DungeonModel m = new DungeonModelImpl.DungeonModelBuilder()
              .isWrapping(isWrapping)
              .rows(rows)
              .columns(columns)
              .percent(percent)
              .numOfOtyugh(numOfOtyugh)
              .degreeOfInterconnectivity(degreeOfInterConnectivity)
              .seed(seed)
              .build();
      new DungeonConsoleController(input, output).playGame(m);
    } else { //GUI based implementation
      DungeonModel model = new DungeonModelImpl.DungeonModelBuilder().build();
      InterfaceView view = new DungeonView(model);
      DungeonViewController controller = new DungeonViewControllerImpl(model, view);
      controller.run();
    }
  }

}
