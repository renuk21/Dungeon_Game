package dungeon;

//Code reference: https://algorithms.tutorialhorizon.com/djkstras-shortest-path-algorithm
// -adjacency-matrix-java-code/
//package-private
class DijkstraAdjacencyMatrix {

  static class Graph {
    int vertices;
    int[][] matrix;

    Graph(int vertices, int[][] matrix) {
      this.matrix = matrix;
      this.vertices = vertices;
    }

    //get the vertex with minimum distance which is not included in SPT
    int getMinimumVertex(boolean[] mst, int[] key) {
      int minKey = Integer.MAX_VALUE;
      int vertex = -1;
      for (int i = 0; i < vertices; i++) {
        if (!mst[i] && minKey > key[i]) {
          minKey = key[i];
          // vertex = i;
        }
        if (!mst[i]) {
          vertex = i;
        }
      }

      return vertex;
    }

    int dijkstraGetMinDistances(int sourceVertex, int dest) {

      boolean[] spt = new boolean[vertices];
      int[] distance = new int[vertices];
      int infinity = Integer.MAX_VALUE;

      //Initialize all the distance to infinity
      for (int i = 0; i < vertices; i++) {
        distance[i] = infinity;
        spt[i] = false;
      }

      //start from the vertex 0
      distance[sourceVertex] = 0;

      //create SPT
      for (int i = 0; i < vertices; i++) {

        //get the vertex with the minimum distance
        int vertexU = getMinimumVertex(spt, distance);
        //include this vertex in SPT
        spt[vertexU] = true;

        //iterate through all the adjacent vertices of above vertex and update the keys
        for (int vertexV = 0; vertexV < vertices; vertexV++) {
          //check of the edge between vertexU and vertexV
          if (matrix[vertexU][vertexV] > 0) {
            //check if this vertex 'vertexV' already in spt and
            // if distance[vertexV]!=Infinity

            if (!spt[vertexV] && matrix[vertexU][vertexV] != infinity) {
              //check if distance needs an update or not
              //means check total distance from source to vertexV is less than
              //the current distance value, if yes then update the distance
              int newKey = matrix[vertexU][vertexV] + distance[vertexU];
              if (newKey < distance[vertexV]) {
                distance[vertexV] = newKey;
              }
            }
          }
        }
      }
      return distance[dest];
    }

  }
}
