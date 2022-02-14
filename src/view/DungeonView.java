package view;

import controller.DungeonViewController;
import dungeon.DungeonModel;
import dungeon.DungeonModelImpl;
import dungeoncomponents.Health;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;
import random.RandomNumberGenerator;

/**
 * This class represents the View for the Dungeon Game.
 */
public class DungeonView extends JFrame implements InterfaceView {

  private DungeonModel model;
  private int seed;
  private DungeonPanel dungeonPanel;
  private int rowVal;
  private int colVal;
  private double percentVal;
  private int numOfOtyughVal;
  private int interconnectivityVal;
  private String playerNameVal;
  private boolean isWrappingVal;
  private GridBagConstraints constraints;
  private boolean buildModel;
  private JPanel playerInfoPanel;
  private JTextArea playerInfo;
  private MenuView textBoxFrame;
  private JMenuItem start;
  private JMenuItem reuse;
  private JMenuItem playerName;
  private JMenuItem row;
  private JMenuItem columns;
  private JMenuItem interconnectivity;
  private JMenu isWrapping;
  private JMenuItem wrapping;
  private JMenuItem nonwrapping;
  private JMenuItem percent;
  private JMenuItem numOfOtyugh;

  /**
   * Construct view for the Dungeon game.

   * @param model Dungeon model read only
   * @throws IOException expected IO exception
   */
  public DungeonView(DungeonModel model) throws IOException {

    super();
    //default values
    rowVal = 5;
    colVal = 5;
    percentVal = 20;
    numOfOtyughVal = 1;
    interconnectivityVal = 0;
    playerNameVal = "Player 1";
    isWrappingVal = false;
    buildModel = false;
    this.model = model;

    //frame
    this.setTitle("Dungeon");
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.makeVisible();

    this.playerInfoPanel = new JPanel();
    this.playerInfo = new JTextArea();

    this.paintDungeon();

    this.addArrows();
    this.addTreasure();
    this.addOtyughs();

    JMenuBar menuBar;
    //menu bar
    menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);

    JMenu settings;
    settings = new JMenu("Game Settings");
    menuBar.add(settings);

    playerName = new JMenuItem("Player Name");
    settings.add(playerName);

    JMenu size;
    size = new JMenu("Dungeon size");
    settings.add(size);


    row = new JMenuItem("Number of rows");
    columns = new JMenuItem("Number of columns");
    size.add(row);
    size.add(columns);

    interconnectivity = new JMenuItem("Degree of Interconnectivity");
    settings.add(interconnectivity);


    isWrapping = new JMenu("Type of Dungeon");
    //    isWrapping.addMenuListener(new MenuListenerImpl());

    settings.add(isWrapping);

    wrapping = new JMenuItem("Wrapping Dungeon");
    isWrapping.add(wrapping);

    nonwrapping = new JMenuItem("Non-Wrapping Dungeon");
    isWrapping.add(nonwrapping);

    percent = new JMenuItem("Percentage of Caves containing treasure");

    settings.add(percent);

    numOfOtyugh = new JMenuItem("Number of Otyughs");

    settings.add(numOfOtyugh);

    JMenu options;
    options = new JMenu("Options...");

    InterfaceView view;
    menuBar.add(options);
    view = this;


    start = new JMenuItem("Start new/Restart game");

    options.add(start);


    reuse = new JMenuItem("Reuse game");

    options.add(reuse);

    JButton button = new JButton("Play");
    menuBar.add(button);
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          menuBarActionListeners();
        } catch (NullPointerException err) {
          //exception
        }
      }
    });

    JMenuItem exit;
    exit = new JMenuItem("Exit");
    exit.addActionListener((ActionEvent e) -> System.exit(0));
    options.add(exit);

    //    System.out.println(model.getDungeon().getStart());
    this.pack();
  }




  private void menuBarActionListeners() {

    playerName.addActionListener(new MenuItemListenerImpl());
    if (String.valueOf(textBoxFrame) != null) {
      playerNameVal = textBoxFrame.getText();
    }


    row.addActionListener(new MenuItemListenerImpl());
    try {
      rowVal = Integer.parseInt(textBoxFrame.getText());
      //      listener.handleRow(rowVal);
    } catch (NumberFormatException e) {
      //If there is no user input for the parameters, a NumberFormatException is raised since,
      // textBoxFrame will be null
    }

    columns.addActionListener(new MenuItemListenerImpl());
    try {
      colVal = Integer.parseInt(textBoxFrame.getText());
      //      listener.handleCol(colVal);
    } catch (NumberFormatException e) {
      //If there is no user input for the parameters, a NumberFormatException is raised since,
      // textBoxFrame will be null
    }


    interconnectivity.addActionListener(new MenuItemListenerImpl());
    try {
      interconnectivityVal = Integer.parseInt(textBoxFrame.getText());
    } catch (NumberFormatException e) {
      //If there is no user input for the parameters, a NumberFormatException is raised since,
      // textBoxFrame will be null
    }

    isWrapping.addActionListener(new MenuItemListenerImpl());

    wrapping.addActionListener(new MenuItemListenerImpl1());

    nonwrapping.addActionListener(new MenuItemListenerImpl1());

    percent.addActionListener(new MenuItemListenerImpl());
    try {
      percentVal = Double.parseDouble(textBoxFrame.getText());
    } catch (NumberFormatException e) {
      //If there is no user input for the parameters, a NumberFormatException is raised since,
      // textBoxFrame will be null
    }


    numOfOtyugh.addActionListener(new MenuItemListenerImpl());
    try {
      numOfOtyughVal = Integer.parseInt(textBoxFrame.getText());
    } catch (NumberFormatException e) {
      //If there is no user input for the parameters, a NumberFormatException is raised since,
      // textBoxFrame will be null
    }



  }


  private void paintDungeon() {
    //Panels
    //    framePanel = new JPanel();
    //    framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.X_AXIS));
    dungeonPanel = new DungeonPanel(new GridBagLayout());
    dungeonPanel.setSize(64 * 5, 64 * 5);

    //Scroll pane
    JScrollPane scrollPane;
    scrollPane = new JScrollPane(dungeonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    this.add(scrollPane);
    JPanel framePanel;
    framePanel = new JPanel();
    framePanel.setLayout(new GridBagLayout());
    GridBagConstraints frameConstraints = new GridBagConstraints();
    frameConstraints.gridx = 0;
    frameConstraints.gridy = 0;
    framePanel.add(scrollPane, frameConstraints);

    this.getPlayerInfo();
    frameConstraints.gridx = 1;
    frameConstraints.gridy = 0;
    framePanel.add(playerInfoPanel);
    this.add(framePanel);

    constraints = new GridBagConstraints();

    //    constraints.insets = new Insets(10, 10, 10, 10);

    ImageIcon blank = new ImageIcon("res\\dungeon-images\\blank.png");
    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        constraints.gridx = i;
        constraints.gridy = j;
        JLabel disp = new JLabel();
        disp.setLayout(new OverlayLayout(disp));
        disp.setSize(64, 64);
        disp.setIcon(blank);
        dungeonPanel.add(disp, constraints);
      }
    }

    ImageIcon player = new ImageIcon("res\\dungeon-images\\player.png");
    int currLoc = model.getPlayer().getLocation();
    int cnt = 0;

    Component[] c = dungeonPanel.getComponents();
    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        if (currLoc == cnt) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          disp.setSize(64, 64);
          JLabel pla = new JLabel();
          pla.setIcon(player);
          disp.add(pla);
          dungeonPanel.repaint();
        }

      }
    }
  }

  @Override
  public void makeVisible() {
    setVisible(true);
    this.requestFocusInWindow();
  }

  @Override
  public void addClickListener(DungeonViewController listener) {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int x = e.getX();
        int y = e.getY();
        listener.handleCellClick((int) (Math.floor(y / 64)), (int) (Math.floor(x / 64))); //swapping
        // because of different convention of indices in GridBagLayout versus the dungeon matrix
      }
    };
    dungeonPanel.addMouseListener(clickAdapter);
  }


  @Override
  public void startGameListener(DungeonViewController listener) {
    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          listener.startNewGame(getModelParameters(false));
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };

    start.addActionListener(actionListener);
  }


  @Override
  public void reuseGameListener(DungeonViewController listener) {
    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          listener.startNewGame(getModelParameters(true));
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    };

    reuse.addActionListener(actionListener);
  }

  @Override
  public void addKeyBoardListener(DungeonViewController listener) {
    KeyListener keyListener = new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
      }

      @Override
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        int c = e.getKeyCode();
        listener.handleKeyType(c);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
      }
    };
    this.addKeyListener(keyListener);

  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void showMessages(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void getPlayerInfo() {
    //    this.playerInfo = new JTextArea();
    String t = model.getPlayer().getPlayerInfo();
    this.playerInfo.setText(t);
    this.playerInfo.setEditable(false);
    //    this.playerInfoPanel = new JPanel();
    playerInfoPanel.add(this.playerInfo);
    playerInfo.repaint();
    this.pack();
  }

  @Override
  public void showVisitedCaves() {

    List<Integer> listOfCavesVisited =  model.getPlayer().getListOfCavesVisited();

    int cnt = 0;

    Component[] c = dungeonPanel.getComponents();
    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        if (listOfCavesVisited.contains(cnt)) {
          constraints.gridx = j;
          constraints.gridy = i;
          JLabel disp = (JLabel) c[j * rowVal + i];
          disp.setSize(64, 64);
          ImageIcon icon = this.getImageIcon(cnt);
          disp.setIcon(icon);
          //          disp.revalidate();
          dungeonPanel.add(disp, constraints, j * rowVal + i);
          //          dungeonPanel.add(disp, constraints);
          dungeonPanel.repaint();
        }

      }
    }

    ImageIcon player = new ImageIcon("res\\dungeon-images\\player.png");
    int currLoc = model.getPlayer().getLocation();
    cnt = 0;
    c = dungeonPanel.getComponents();
    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        if (currLoc == cnt) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          disp.setSize(64, 64);
          JLabel pla = new JLabel();
          pla.setIcon(player);
          disp.add(pla);
          dungeonPanel.repaint();
        }

      }
    }

    this.addOtyughs();
    this.addTreasure();
    this.addArrows();
    this.pack();
  }

  @Override
  public void removeTreasureIcon() {

    int currLoc = model.getPlayer().getLocation();
    int cnt = 0;

    Component[] c = dungeonPanel.getComponents();

    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        if (currLoc == cnt) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          Component[] test = disp.getComponents();
          for (int k = 0; k < test.length; k++) {
            if (String.valueOf(test[k]).contains("diamond")) {
              disp.remove(k);
              Component[] copy = new Component[test.length - 1];

              for (int l = 0, m = 0; l < test.length; l++) {
                if (l != k) {
                  copy[m] = test[l];
                  m = m + 1;
                }
              }
              test = copy;
              disp.repaint();
              dungeonPanel.repaint();
            }
          }

          dungeonPanel.repaint();
        }

      }
    }

  }

  @Override
  public void removeArrowIcon() {

    int currLoc = model.getPlayer().getLocation();
    int cnt = 0;

    Component[] c = dungeonPanel.getComponents();

    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        if (currLoc == cnt) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          Component[] test = disp.getComponents();
          for (int k = 0; k < test.length; k++) {
            if (String.valueOf(test[k]).contains("arrow-white")) {
              disp.remove(k);
              Component[] copy = new Component[test.length - 1];

              for (int l = 0, m = 0; l < test.length; l++) {
                if (l != k) {
                  copy[m] = test[l];
                  m = m + 1;
                }
              }
              test = copy;
            }
            disp.repaint();
            dungeonPanel.repaint();
          }

        }

      }
    }

  }

  @Override
  public void addTreasure() {
    List<Integer> listOfCavesVisited =  model.getPlayer().getListOfCavesVisited();

    //    ImageIcon ruby = new ImageIcon("res\\dungeon-images\\ruby.png");
    ImageIcon diamond = new ImageIcon("res\\dungeon-images\\diamond.png");
    //    ImageIcon sapphire = new ImageIcon("res\\dungeon-images\\sapphire.png");
    //    ImageIcon arrow = new ImageIcon("res\\dungeon-images\\arrow-white");
    //    int currLoc = model.getPlayer().getLocation();

    List<Integer> listOfCavesWithTreasure = model.getDungeon().getListOfCavesWithTreasure();
    //    List<Integer> listOfCavesWithArrows = model.getDungeon().getListOfCavesWithArrows();
    //    Map<Integer, Health > listOfCavesWithOtyugh = model.getDungeon()
    //    .getListOfCavesWithOtyughs();

    Component[] c = dungeonPanel.getComponents();
    int cnt = 0;

    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        //        if (currLoc == cnt) {

        if (listOfCavesWithTreasure.contains(cnt) && listOfCavesVisited.contains(cnt)) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          disp.setSize(64, 64);
          JLabel treasure = new JLabel();
          treasure.setIcon(diamond);
          Component[] test = disp.getComponents();
          for (int k = 0; k < test.length; k++) {
            if (String.valueOf(test[k]).contains("arrow-white")) {
              disp.remove(k);
              disp.repaint();
              dungeonPanel.repaint();
              Component[] copy = new Component[test.length - 1];

              for (int l = 0, m = 0; l < test.length; l++) {
                if (l != k) {
                  copy[m] = test[l];
                  m = m + 1;
                }
              }
              test = copy;
            }
          }

          disp.add(treasure);


          dungeonPanel.repaint();
        }

      }
    }

    this.pack();

  }

  @Override
  public void addArrows() {
    List<Integer> listOfCavesVisited =  model.getPlayer().getListOfCavesVisited();
    ImageIcon arrow = new ImageIcon("res\\dungeon-images\\arrow-white.png");

    List<Integer> listOfCavesWithArrows = model.getDungeon().getListOfCavesWithArrows();

    Component[] c = dungeonPanel.getComponents();
    int cnt = 0;

    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        //        if (currLoc == cnt) {

        if (listOfCavesWithArrows.contains(cnt) && listOfCavesVisited.contains(cnt)) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          disp.setSize(64, 64);
          JLabel arrows = new JLabel();
          arrows.setIcon(arrow);
          Component[] test = disp.getComponents();
          for (int k = 0; k < test.length; k++) {
            if (String.valueOf(test[k]).contains("diamond")) {
              disp.remove(k);
              disp.repaint();
              dungeonPanel.repaint();
              Component[] copy = new Component[test.length - 1];

              for (int l = 0, m = 0; l < test.length; l++) {
                if (l != k) {
                  copy[m] = test[l];
                  m = m + 1;
                }
              }
              test = copy;
            }
          }

          disp.add(arrows);
        }

        dungeonPanel.repaint();
        //        }

      }
    }

    this.pack();

  }


  @Override
  public void addOtyughs() {
    List<Integer> listOfCavesVisited =  model.getPlayer().getListOfCavesVisited();
    ImageIcon otyugh = new ImageIcon("res\\dungeon-images\\otyugh.png");

    Map<Integer, Health> listOfCavesWithOtyugh = model.getDungeon().getListOfCavesWithOtyughs();
    Component[] c = dungeonPanel.getComponents();
    int cnt = 0;

    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        //        if (currLoc == cnt) {

        if (listOfCavesWithOtyugh.containsKey(cnt) && listOfCavesVisited.contains(cnt)) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          disp.setSize(64, 64);
          JLabel otyughs = new JLabel();
          otyughs.setIcon(otyugh);
          //              if (disp.getComponents().length > 1) {
          //                removeTreasureIcon();
          //              }
          disp.add(otyughs);
        }

        dungeonPanel.repaint();
        //        }

      }
    }

    this.pack();

  }

  private ImageIcon getImageIcon(int caveId) {
    ImageIcon east = new ImageIcon("res\\dungeon-images\\color-cells\\E.png");
    ImageIcon west = new ImageIcon("res\\dungeon-images\\color-cells\\W.png");
    ImageIcon north = new ImageIcon("res\\dungeon-images\\color-cells\\N.png");
    ImageIcon south = new ImageIcon("res\\dungeon-images\\color-cells\\S.png");
    ImageIcon eastwest = new ImageIcon("res\\dungeon-images\\color-cells\\EW.png");
    ImageIcon eastnorth = new ImageIcon("res\\dungeon-images\\color-cells\\NE.png");
    ImageIcon eastsouth = new ImageIcon("res\\dungeon-images\\color-cells\\SE.png");
    ImageIcon westnorth = new ImageIcon("res\\dungeon-images\\color-cells\\NW.png");
    ImageIcon westsouth = new ImageIcon("res\\dungeon-images\\color-cells\\SW.png");
    ImageIcon northsouth = new ImageIcon("res\\dungeon-images\\color-cells\\NS.png");
    ImageIcon northsoutheast = new ImageIcon("res\\dungeon-images\\color-cells\\NSE.png");
    ImageIcon northsouthwest = new ImageIcon("res\\dungeon-images\\color-cells\\NSW.png");
    ImageIcon northeastwest = new ImageIcon("res\\dungeon-images\\color-cells\\NEW.png");
    ImageIcon southeastwest = new ImageIcon("res\\dungeon-images\\color-cells\\SEW.png");
    ImageIcon northsoutheastwest = new ImageIcon("res\\dungeon-images\\color-cells\\NSEW.png");

    List<List<String>> directions = model.getPlayer().getPossibleMoves(caveId);
    String s = "";
    for (int j = 0; j < directions.size(); j++) {

      s = s + directions.get(j).get(0).charAt(0);

    }

    int n = model.getDungeon().getEntrances(caveId);
    if (n == 1) {
      if (s.compareTo("N") == 0) {
        return north;
      } else if (s.compareTo("S") == 0) {
        return south;
      } else if (s.compareTo("E") == 0) {
        return east;
      } else {
        return west;
      }
    } else if (n == 2) {
      if (s.charAt(0) == 'N') {
        if (s.charAt(1) == 'S') {
          return northsouth;
        } else if (s.charAt(1) == 'E') {
          return eastnorth;
        } else if (s.charAt(1) == 'W') {
          return westnorth;
        }
      } else if (s.charAt(0) == 'S') {
        if (s.charAt(1) == 'N') {
          return northsouth;
        } else if (s.charAt(1) == 'E') {
          return eastsouth;
        } else if (s.charAt(1) == 'W') {
          return westsouth;
        }
      } else if (s.charAt(0) == 'E') {
        if (s.charAt(1) == 'S') {
          return eastsouth;
        } else if (s.charAt(1) == 'N') {
          return eastnorth;
        } else if (s.charAt(1) == 'W') {
          return eastwest;
        }
      } else {
        if (s.charAt(1) == 'S') {
          return westsouth;
        } else if (s.charAt(1) == 'E') {
          return eastwest;
        } else if (s.charAt(1) == 'N') {
          return westnorth;
        }
      }
    } else if (n == 3) {
      if (s.charAt(0) == 'N') {
        if (s.charAt(1) == 'S') {
          if (s.charAt(2) == 'E') {
            return northsoutheast;
          } else {
            return northsouthwest;
          }
        } else if (s.charAt(1) == 'E') {
          if (s.charAt(2) == 'W') {
            return northeastwest;
          } else {
            return northsoutheast;
          }
        } else if (s.charAt(1) == 'W') {
          if (s.charAt(2) == 'E') {
            return northeastwest;
          } else {
            return northsouthwest;
          }
        }
      } else if (s.charAt(0) == 'S') {
        if (s.charAt(1) == 'N') {
          if (s.charAt(2) == 'E') {
            return northsoutheast;
          } else {
            return northsouthwest;
          }
        } else if (s.charAt(1) == 'E') {
          if (s.charAt(2) == 'N') {
            return northsoutheast;
          } else {
            return southeastwest;
          }
        } else if (s.charAt(1) == 'W') {
          if (s.charAt(2) == 'E') {
            return southeastwest;
          } else {
            return northsouthwest;
          }
        }
      } else if (s.charAt(0) == 'E') {
        if (s.charAt(1) == 'S') {
          if (s.charAt(2) == 'N') {
            return northsoutheast;
          } else {
            return southeastwest;
          }
        } else if (s.charAt(1) == 'N') {
          if (s.charAt(2) == 'S') {
            return northsoutheast;
          } else {
            return northeastwest;
          }
        } else if (s.charAt(1) == 'W') {
          if (s.charAt(2) == 'N') {
            return northeastwest;
          } else {
            return southeastwest;
          }
        }
      } else {
        if (s.charAt(1) == 'S') {
          if (s.charAt(2) == 'E') {
            return southeastwest;
          } else {
            return northsouthwest;
          }
        } else if (s.charAt(1) == 'E') {
          if (s.charAt(2) == 'N') {
            return northeastwest;
          } else {
            return southeastwest;
          }
        } else if (s.charAt(1) == 'N') {
          if (s.charAt(2) == 'S') {
            return northsouthwest;
          } else {
            return northeastwest;
          }
        }
      }
    }
    return northsoutheastwest;
  }

  @Override
  public DungeonModel getModelParameters(boolean reuse) {
    RandomNumberGenerator r = new RandomNumberGenerator();
    //    if () //if restart is selected, use same seed

    if (reuse) {
      model = new DungeonModelImpl.DungeonModelBuilder()
              .isWrapping(isWrappingVal)
              .rows(rowVal)
              .columns(colVal)
              .percent(percentVal)
              .degreeOfInterconnectivity(interconnectivityVal)
              .numOfOtyugh(numOfOtyughVal)
              .seed(seed)
              .playerName(playerNameVal)
              .build();

      return model;
    }

    seed = r.getRandomNumber(50, 150);

    //if the input params are not changed, it is the case of restart with different seed,
    // otherwise, it will be a new game
    model = new DungeonModelImpl.DungeonModelBuilder()
              .isWrapping(isWrappingVal)
              .rows(rowVal)
              .columns(colVal)
              .percent(percentVal)
              .degreeOfInterconnectivity(interconnectivityVal)
              .numOfOtyugh(numOfOtyughVal)
              .seed(seed)
              .playerName(playerNameVal)
              .build();


    return model;
  }

  @Override
  public boolean isBuildModelWithUserDefinedParams() {
    boolean f = buildModel;
    return f;
  }

  @Override
  public void removePlayerIcon() {
    int currLoc = model.getPlayer().getLocation();
    int cnt = 0;

    Component[] c = dungeonPanel.getComponents();

    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        if (currLoc == cnt) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          Component[] test = disp.getComponents();
          for (int k = 0; k < test.length; k++) {
            if (String.valueOf(test[k]).contains("player")) {
              disp.remove(k);
              Component[] copy = new Component[test.length - 1];

              for (int l = 0, m = 0; l < test.length; l++) {
                if (l != k) {
                  copy[m] = test[l];
                  m = m + 1;
                }
              }
              test = copy;
            }
          }

          disp.repaint();
          dungeonPanel.repaint();
        }

      }
    }

  }

  @Override
  public void removeSmellIcon() {
    int currLoc = model.getPlayer().getLocation();
    int cnt = 0;

    Component[] c = dungeonPanel.getComponents();

    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        if (currLoc == cnt) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          Component[] test = disp.getComponents();
          for (int k = 0; k < test.length; k++) {
            if (String.valueOf(test[k]).contains("stench")) {
              disp.remove(k);
              Component[] copy = new Component[test.length - 1];

              for (int l = 0, m = 0; l < test.length; l++) {
                if (l != k) {
                  copy[m] = test[l];
                  m = m + 1;
                }
              }
              test = copy;
            }
          }
          disp.repaint();
          dungeonPanel.repaint();
        }

      }
    }

  }

  @Override
  public void smellOtyugh(int strengthOfSmell) {
    ImageIcon strongSmell = new ImageIcon("res\\dungeon-images\\stench02.png");
    ImageIcon slightSmell = new ImageIcon("res\\dungeon-images\\stench01.png");

    Component[] c = dungeonPanel.getComponents();
    int currLoc = model.getPlayer().getLocation();
    int cnt = 0;

    for (int i = 0; i < rowVal; i++) {
      for (int j = 0; j < colVal; j++) {
        cnt++;
        if (currLoc == cnt) {
          JLabel disp = (JLabel) c[j * rowVal + i];
          disp.setSize(64, 64);
          if (strengthOfSmell == 1) {
            JLabel smell1 = new JLabel();
            smell1.setIcon(slightSmell);
            disp.add(smell1);
          } else if (strengthOfSmell == 2) {
            JLabel smell2 = new JLabel();
            smell2.setIcon(strongSmell);
            disp.add(smell2);
          }
          dungeonPanel.repaint();
        }

      }
    }

    this.pack();
  }

  @Override
  public void closeView() throws IOException {
    this.dispose();
  }

  class MenuItemListenerImpl implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      textBoxFrame = new MenuView();
    }
  }

  class MenuItemListenerImpl1 implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      //set true or false based on wrapping/non-wrapping
      if (e.getActionCommand().equals("Wrapping Dungeon")) {
        isWrappingVal = true;
      } else {
        isWrappingVal = false;
      }
    }
  }


}
