package controller;

import dungeon.DungeonModel;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import view.DungeonView;
import view.InterfaceView;

/**
 * This class represents GUI controller for the Dungeon game.
 */
public class DungeonViewControllerImpl implements DungeonViewController {
  private DungeonModel model;
  private InterfaceView view;
  private boolean shoot;

  /**
   * Construct the controller for the GUI of dungeon game.

   * @param d dungeon model
   * @param v view for the GUI of dungeon game
   */
  public DungeonViewControllerImpl(DungeonModel d, InterfaceView v) {
    this.checkNonNull(d);
    this.checkNonNull(v);
    this.view = v;
    this.model = d;
    shoot = false;
    String playerName;
    playerName = "Player 1";
  }

  private void checkNonNull(Object m) {
    try {
      Objects.requireNonNull(m);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Invalid Model");
    }
  }

  @Override
  public void run() {
    try {
      //this.view.menuBarActionListeners(this);
      this.view.startGameListener(this);
      this.view.reuseGameListener(this);
      this.view.removePlayerIcon();
      this.view.showVisitedCaves();
      this.view.addClickListener(this);
      this.view.addKeyBoardListener(this);
      this.view.makeVisible();

    } catch (IllegalStateException | IllegalArgumentException | NullPointerException e) {
      this.view.showMessages(e.getMessage());
    }
  }

  @Override
  public void handleCellClick(int row, int col) {
    try {
      int  cnt = 0;
      boolean flag = false;
      for (int i = 0; i < model.getDungeon().getDungeonMatrix().length; i++) {
        for (int j = 0; j < model.getDungeon().getDungeonMatrix()[0].length; j++) {
          cnt++;
          if (i == row && j == col) {
            flag = true;
            break;
          }
        }
        if (flag) {
          break;
        }
      }
      actionHelper(cnt);
    } catch (IllegalStateException | IllegalArgumentException | NullPointerException e) {
      this.view.showMessages(e.getMessage());
    }

  }

  @Override
  public void handleKeyType(int c) {
    try {
      int currLoc = model.getPlayer().getLocation();
      int cnt = 0;
      if (!shoot) {
        if (c == 37) { //left
          cnt = currLoc - 1;
        } else if (c == 38) { //up
          cnt = (currLoc - model.getDungeon().getDungeonMatrix().length);
          if (cnt < 0) {
            cnt = cnt + model.getDungeon().getDungeonMatrix().length
                    * model.getDungeon().getDungeonMatrix()[0].length;
          }
        } else if (c == 39) { //right
          cnt = currLoc + 1;
        } else if (c == 40) { //down
          cnt = currLoc + model.getDungeon().getDungeonMatrix().length;
          if (cnt > model.getDungeon().getDungeonMatrix().length
                  * model.getDungeon().getDungeonMatrix()[0].length) {
            cnt = cnt % model.getDungeon().getDungeonMatrix().length;
          }
        }
      } else {
        shoot = false;
        if (c == 37) { //left
          model.getPlayer().slay("WEST", 1);
        } else if (c == 38) { //up
          model.getPlayer().slay("NORTH", 1);
        } else if (c == 39) { //right
          model.getPlayer().slay("EAST", 1);
        } else if (c == 40) { //down
          model.getPlayer().slay("SOUTH", 1);
        }
        view.removeSmellIcon();
        view.smellOtyugh(model.getPlayer().smellOtyugh());
      }

      actionHelper(cnt);
      //Pickup logic
      if (c == 65) { //key A OR a - PICKUP ARROWS
        this.model.getPlayer().pickUpArrow();
        this.view.removeArrowIcon();
      }
      if (c == 84) { //key T OR t PICKUP TREASURE
        this.model.getPlayer().collectTreasure();
        this.view.removeTreasureIcon();
      }

      // Shoot logic
      if (c == 83) { //key S OR s
        //      this.view.addKeyBoardListener(this);
        shoot = true;
      }
    } catch (IllegalStateException | IllegalArgumentException | NullPointerException e) {
      this.view.showMessages(e.getMessage());
    }
  }

  @Override
  public void startNewGame(DungeonModel model) throws IOException {
    InterfaceView viewN = new DungeonView(model);
    this.view.closeView();
    this.view = viewN;
    this.model = model;
    this.run();
    this.view.refresh();
  }

  private void actionHelper(int cnt) {
    boolean flag = false;

    String direction = "";
    List<List<String>> validMoves = model.getPlayer().getPossibleMoves(model.getPlayer()
            .getLocation());
    for (int j = 0; j < validMoves.size(); j++) {
      if (Integer.parseInt(validMoves.get(j).get(1)) == cnt) {
        direction = validMoves.get(j).get(0);
        flag = true;
      }
    }
    if (flag) {
      view.removePlayerIcon();
      if (model.getPlayer().smellOtyugh() != 0) {
        view.removeSmellIcon();
      }
      view.removeTreasureIcon();
      view.removeArrowIcon();
      model.getPlayer().move(direction);
      view.showVisitedCaves();
      view.getPlayerInfo();
      view.smellOtyugh(model.getPlayer().smellOtyugh());
      //        view.showAvailableDungeonComponents();
    }
  }

}
