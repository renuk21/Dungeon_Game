# Dungeon
## 1. Overview
The world for the game consists of a dungeon, a network of tunnels and caves that are interconnected so that player can explore the entire world by traveling from cave to cave through the tunnels that connect them. 
For example, a dungeon can be represented by a 6 x 8 two-dimensional grid. Each location in the grid represents a location in the dungeon where a player can explore and can be connected to at most four (4) other locations: one to the north, one to the east, one to the south, and one to the west. In a dungeon, locations may "wrap" to the one on the other side of the grid. For example, moving to the north from row 0 (at the top) in the grid moves the player to the location in the same column in row 5 (at the bottom). A location can further be classified as tunnel (which has exactly 2 entrances) or a cave (which has 1, 3 or 4 entrances). In the image above, we are representing caves as circular spaces. A dungeon may or may not be wrapping. Player collects treasure present in certain caves. Otyughs are smelly monsters waiting in some of the caves and there is atleast one Otyugh in the dungeon that is located in the end cave. The Otyugh can be slain by shooting at it twice with the arrows. Arrows travel in a straight line through the cave but travel freely through tunnels. Arrows can be picked up from tunnels and caves when available.

## 2. List of features
During the design phase, the following Classes and Interfaces were identified:
* DungeonGrid (Interface)
  * Dungeon (Implementing class)
   * Wrapping Dungeon 
   * NonWrapping Dungeon
* InterfacePlayer (Interface)
  * Player (Implementing class)
* Cave
* Treasure (Enumeration)     

The controller for the Dungeon model will have the following interface and class:
* DungeonController (Interface)
* DungeonConsoleController (Implementing Class)

The model for dungeon comprising of DungeonGrid and InterfacePlayer interfaces is passed to the controller.

## 3. How to Run 

* The JAR file, create a run configuration. 
1. Press Ctrl+Shift+A, find and run the Edit Configurations action.

2. In the Run/Debug Configurations dialog, click the Add button and select JAR Application.

3. Add a name for the new configuration.

4. In the Path to JAR field, click the Browse button and specify the path to the JAR file on your computer.

5. Under Before launch, click the Add button, select Build Artifacts in the dialog that opens.

6. Doing this means that the JAR is built automatically every time you execute the run configuration.

7. Run configurations allow you to define how you want to run your application, with which arguments and options. You can have multiple run configurations for the same application, each with its own settings.

#### Console Controller

If arguments are passed to the JAR file, text based controller will be used.

#### View based Controller

If no arguments are passed and the JAR is run directly, view based controller will be used.
The JAR needs to be run from intelliJ to maintain the relative path of the images. JAR can remain in the res folder while running from IntelliJ.

## 4. How to use the program
The flow of the program is explained through the driver class implementation (see pt. 5). 
1. The program is used to create both wrapping and nonwrapping dungeons. 
2. There is a path from every cave to every other cave in the dungeon.
3. Each dungeon can be constructed with a degree of interconnectivity. Interconnectivity = 0 when there is exactly one path from every cave in the dungeon to every other cave in the dungeon. Increasing the degree of interconnectivity increases the number of paths between caves.
4. Not all dungeons "wrap" from one side to the other (as defined above).
5. One cave is randomly selected as the start and one cave is randomly selected to be the end. The path between the start and the end locations should be at least of length 5.
6. The implementation of the controller provides for a text based interactive game of Dungeon with Otyughs, treasures and arrows.

## 5.1 Description of Examples (Console controller)
**_Run 1 : __ExampleRun3___**
1. Create the dungeon by passing command line arguments to the program in the order isWrapping, rows, columns, degreeOfInterConnectivity, percent.
2. Controller prints the clues about nearby caves 
3. Player navigates through the dungeons using 'M' and specifying directions - NORTH, SOUTH, EAST or WEST.
4. Player picks up arrow or treasure by using 'P' command and specifying 'arrow' or 'treasure'.
5. Player shoots an arrow by specifying 'S', distance in terms of number of caves (not tunnels) through which the arrow must pass through and direction in which arrow is to be shot. Player kills the Otyugh if he shoots 2 arrows at the same Otyugh.
6. Player reaches end cave and wins.

**_Run 2 : __ExampleRun4___**
1. Create the dungeon by passing command line arguments to the program in the order isWrapping, rows, columns, degreeOfInterConnectivity, percent.
2. Controller prints the clues about nearby caves 
3. Player navigates through the dungeons using 'M' and specifying directions - NORTH, SOUTH, EAST or WEST.
4. Player picks up arrow or treasure by using 'P' command and specifying 'arrow' or 'treasure'.
5. Player shoots an arrow by specifying 'S', distance in terms of number of caves (not tunnels) through which the arrow must pass through and direction in which arrow is to be shot. Player kills the Otyugh if he shoots 2 arrows at the same Otyugh.
6. Player gets eaten by Otyugh and loses.

## 5.2 Description of Examples (View based controller)

1. Screenshots of the Game play are included in the /res folder for initial conditions, win, loss and mid-game state to explain the functioning of the MVC based Dungeon Game.

## 6. Design/Model Changes
There are a few revisions in the design. The new design does not include a different classes for wrapping and unwrapping because, the way wrapping/non-wrapping cases are handled, the logic is common between the two. So, I no longer required those to be separate. In the revised class the Dungeon class is concrete. The public constructor initializes the values for ability and there are no other public methods in the class. So, that is no longer included in the new design.The revisions are indicated in the revised UML. These changes were small improvements made to improve readability and to make the break down large operations to smaller operations, in line with SOLID principles. 

For implementing the controller, the interface and class for controller was added to the UML. The methods, class variables and enums corresponding to Otyughs and arrows were added to the model. The cave is represented as a part of the dungeon using composition, so that additional components like Otyughs and arrows that were added later can be handled without disturbing the dungeon class much. 

Before creating the interface and classes for view based controller and the view, I combined the interfaces of player and dungeon to get a Dungeon Model to be passed to the controller. I did not make any changes to the existing textbased controller or the model.

## 7. Assumptions
1. Player can visit any cave any number of times back and forth.
2. collectTreasure() will pick up all the treasure in a particular cave.
3. After player collects the treasure from a cave, the cave no loger has a treasure.

## 8. Limitations

    Since random numbers are used for most components ofthe code, it sometimes takes longer to get the output.


## 9. Citations
1. Compile and build applications with IntelliJ IDEA | IntelliJ IDEA. (n.d.). IntelliJ IDEA Help. Retrieved October 2, 2021, from https://www.jetbrains.com/help/idea/compiling-applications.html#run_packaged_jar

2. Getting Started | Markdown Guide. (n.d.). Www.Markdownguide.Org. Retrieved October 2, 2021, from https://www.markdownguide.org/getting-started/

3. https://www.makeareadme.com/

4. Mastering Markdown · GitHub Guides. (n.d.). Github.Com. Retrieved October 2, 2021, from https://guides.github.com/features/mastering-markdown/

5. https://www.techiedelight.com/kruskals-algorithm-for-finding-minimum-spanning-tree/

6. https://algorithms.tutorialhorizon.com/djkstras-shortest-path-algorithm

7. Controller output format - https://northeastern.instructure.com/courses/90366/assignments/1125667

8.  https://stackoverflow.com/questions/12543206/java-menu-item-enabling-within-event-listener

9.  https://stackoverflow.com/questions/9862165/jmenu-actionlistener

10.  https://stackoverflow.com/questions/27649378/adding-to-a-textfield-in-panel1-from-a-button-in-panel2/27649995
11.   https://stackoverflow.com/questions/37552623/image-files-wont-load-in-2d-java-game
12.  https://www.youtube.com/watch?v=dwLkDGm5EBc&list=PLDD1BAED03F791E1B&index=5