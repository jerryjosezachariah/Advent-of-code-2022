package jerry.adventofcode.year2022.day12.two;

import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.stringToTwoDCharArray;

import java.util.LinkedList;

/*
sample output: 29
real output: 443
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day12/in.txt";
    char[][] grid = stringToTwoDCharArray(readFileAsStringArray(file));
    int M = grid.length;
    int N = grid[0].length;
    Node end = null;
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        if (grid[i][j] == 'E') {
          end = new Node(i, j);
          grid[i][j] = 'z';
        }
      }
    }
    int minDist = Integer.MAX_VALUE;
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        if (grid[i][j] == 'a') {
          int dist = getShortestPathFrom(M, N, new Node(i, j, 1), end, grid);
          if (dist < minDist) {
            minDist = dist;
          }
        }
      }
    }
    System.out.println(minDist);
  }

  private static int getShortestPathFrom(int M, int N, Node start, Node end, char[][] grid) {
    int[][] vis = new int[M][N];
    LinkedList<Node> neighbors = new LinkedList<>();
    neighbors.add(start);
    while (!neighbors.isEmpty()) {
      Node cur = neighbors.remove();
      if (cur.x == end.x && cur.y == end.y) {
        return (cur.dist - 1);
      }
      LinkedList<Node> curNeighbors = getNeighbors(M, N, cur, grid);
      for (Node n : curNeighbors) {
        if (vis[n.x][n.y] == 0) {
          neighbors.add(n);
          vis[n.x][n.y] = cur.dist;
        }
      }
    }
    return Integer.MAX_VALUE;
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
