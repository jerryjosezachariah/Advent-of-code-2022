package jerry.adventofcode.year2022.day12.one;

import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.stringToTwoDCharArray;

import java.util.LinkedList;

/*
sample output: 31
real output: 449
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day12/in.txt";
    char[][] grid = stringToTwoDCharArray(readFileAsStringArray(file));
    int M = grid.length;
    int N = grid[0].length;
    int[][] vis = new int[M][N];
    Node start = null;
    Node end = null;
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        if (grid[i][j] == 'S') {
          start = new Node(i, j, 1);
          grid[i][j] = 'a';
        } else if (grid[i][j] == 'E') {
          end = new Node(i, j);
          grid[i][j] = 'z';
        }
      }
    }
    LinkedList<Node> neighbors = new LinkedList<>();
    neighbors.add(start);
    while (!neighbors.isEmpty()) {
      Node cur = neighbors.remove();
      if (cur.x == end.x && cur.y == end.y) {
        System.out.println(cur.dist - 1);
        return;
      }
      LinkedList<Node> curNeighbors = getNeighbors(M, N, cur, grid);
      for (Node n : curNeighbors) {
        if (vis[n.x][n.y] == 0) {
          neighbors.add(n);
          vis[n.x][n.y] = cur.dist;
        }
      }
    }
    System.out.println("NO PATH FOUND");
  }

  private static LinkedList<Node> getNeighbors(int M, int N, Node cur, char[][] grid) {
    int x = cur.x;
    int y = cur.y;
    int dist = cur.dist;
    LinkedList<Node> neighbors = new LinkedList<>();
    if (x > 0) { // T
      neighbors.add(new Node(x - 1, y, dist + 1));
    }
    if (y > 0) { // L
      neighbors.add(new Node(x, y - 1, dist + 1));
    }
    if (y < N - 1) { // R
      neighbors.add(new Node(x, y + 1, dist + 1));
    }
    if (x < M - 1) { // B
      neighbors.add(new Node(x + 1, y, dist + 1));
    }
    neighbors.removeIf(n -> ((grid[n.x][n.y] - grid[cur.x][cur.y]) > 1));
    return neighbors;
  }

  private static class Node {
    int x;
    int y;
    int dist;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public Node(int x, int y, int dist) {
      this.x = x;
      this.y = y;
      this.dist = dist;
    }
  }
}
