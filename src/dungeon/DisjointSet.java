package dungeon;

import java.util.HashMap;
import java.util.Map;

/* reference:
 https://www.techiedelight.com/kruskals-algorithm-for-finding-minimum-spanning-tree/
   */
// A class to represent a disjoint set - package private
class DisjointSet {
  Map<Integer, Integer> parent = new HashMap<>();

  // perform MakeSet operation
  void makeSet(int n) {
    // create `n` disjoint sets (one for each vertex)
    for (int i = 1; i <= n; i++) {
      parent.put(i, i);
    }
  }

  // Find the root of the set in which element `k` belongs
  int find(int k) {
    // if `k` is root
    if (parent.get(k) == k) {
      return k;
    }

    // recur for the parent until we find the root
    return find(parent.get(k));
  }

  // Perform Union of two subsets
  void union(int a, int b) {
    // find the root of the sets in which elements
    // `x` and `y` belongs
    int x = find(a);
    int y = find(b);

    parent.put(y, x);
  }
}