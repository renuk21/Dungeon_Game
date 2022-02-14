package dungeon;

/**
* The dungeon is made of interconnected caves (caves with 2 entrances are tunnels).
**/
public class Cave {
  private int caveId;
  private boolean isOtyughPresent;
  private boolean isTreasurePresent;
  private boolean isArrowPresent;

  Cave(int caveId) {
    this.caveId = caveId;
    this.isOtyughPresent = false;
    this.isArrowPresent = false;
    this.isTreasurePresent = false;
  }

  int getCaveId() {
    int id = this.caveId;
    return id;
  }

  void assignOtyugh() {
    this.isOtyughPresent = true;
  }

  void assignArrow() {
    this.isArrowPresent = true;
  }

  void assignTreasure() {
    this.isTreasurePresent = true;
  }

  boolean isOtyughPresent() {
    return isOtyughPresent;
  }

  boolean isTreasurePresent() {
    return isTreasurePresent;
  }

  boolean isArrowPresent() {
    return isArrowPresent;
  }

}
