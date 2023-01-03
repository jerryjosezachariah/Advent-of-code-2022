package jerry.adventofcode.year2022.day17.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
/*
sample output: 3068
real output: 3179
 */
public class Main {

  static int M = 3500;
  static int N = 7;
  static char[][] grid;
  static int floor = 0;

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day17/in.txt";
    String movements = readFileAsStringArray(file).get(0);
    int nextMovement = 0;

    grid = new char[M][N];
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        grid[i][j] = '.';
      }
    }
    for (int i = 0; i < 2022; ++i) {
      Rock r = new Rock(i % 5);
      boolean movedDown = true;
      while (movedDown) {
        r.moveHorizontally(movements.charAt(nextMovement));
        nextMovement = (nextMovement + 1) % movements.length();
        movedDown = r.moveDown();
      }
    }
    System.out.println(floor);
  }

  private static class Rock {
    public int rockType;
    public ArrayList<Node> nodes;

    public Rock(int rockType) {
      this.rockType = rockType;
      initRock();
      for (Node node : nodes) {
        grid[node.x][node.y] = '@';
      }
    }

    private boolean canMoveLeft() {
      boolean a, b, c, d;
      switch (rockType) {
        case 0:
          a = (nodes.get(0).y > 0) && (grid[nodes.get(0).x][nodes.get(0).y - 1] == '.');
          return a;
        case 1:
          a = (grid[nodes.get(0).x][nodes.get(0).y - 1] == '.');
          b = (nodes.get(1).y > 0) && (grid[nodes.get(1).x][nodes.get(1).y - 1] == '.');
          c = (grid[nodes.get(4).x][nodes.get(4).y - 1] == '.');
          return a && b && c;
        case 2:
          a = (grid[nodes.get(0).x][nodes.get(0).y - 1] == '.');
          b = (grid[nodes.get(1).x][nodes.get(1).y - 1] == '.');
          c = (nodes.get(2).y > 0) && (grid[nodes.get(2).x][nodes.get(2).y - 1] == '.');
          return a && b && c;
        case 3:
          a = (nodes.get(0).y > 0) && (grid[nodes.get(0).x][nodes.get(0).y - 1] == '.');
          b = (nodes.get(1).y > 0) && (grid[nodes.get(1).x][nodes.get(1).y - 1] == '.');
          c = (nodes.get(2).y > 0) && (grid[nodes.get(2).x][nodes.get(2).y - 1] == '.');
          d = (nodes.get(3).y > 0) && (grid[nodes.get(3).x][nodes.get(3).y - 1] == '.');
          return a && b && c && d;
        case 4:
          a = (nodes.get(0).y > 0) && (grid[nodes.get(0).x][nodes.get(0).y - 1] == '.');
          b = (nodes.get(2).y > 0) && (grid[nodes.get(2).x][nodes.get(2).y - 1] == '.');
          return a && b;
      }
      throw new IllegalArgumentException();
    }

    private boolean canMoveRight() {
      boolean a, b, c, d;
      switch (rockType) {
        case 0:
          a = (nodes.get(3).y < N - 1) && (grid[nodes.get(3).x][nodes.get(3).y + 1] == '.');
          return a;
        case 1:
          a = (grid[nodes.get(0).x][nodes.get(0).y + 1] == '.');
          b = (nodes.get(3).y < N - 1) && (grid[nodes.get(3).x][nodes.get(3).y + 1] == '.');
          c = (grid[nodes.get(4).x][nodes.get(4).y + 1] == '.');
          return a && b && c;
        case 2:
          a = (nodes.get(0).y < N - 1) && (grid[nodes.get(0).x][nodes.get(0).y + 1] == '.');
          b = (nodes.get(1).y < N - 1) && (grid[nodes.get(1).x][nodes.get(1).y + 1] == '.');
          c = (nodes.get(4).y < N - 1) && (grid[nodes.get(4).x][nodes.get(4).y + 1] == '.');
          return a && b && c;
        case 3:
          a = (nodes.get(0).y < N - 1) && (grid[nodes.get(0).x][nodes.get(0).y + 1] == '.');
          b = (nodes.get(1).y < N - 1) && (grid[nodes.get(1).x][nodes.get(1).y + 1] == '.');
          c = (nodes.get(2).y < N - 1) && (grid[nodes.get(2).x][nodes.get(2).y + 1] == '.');
          d = (nodes.get(3).y < N - 1) && (grid[nodes.get(3).x][nodes.get(3).y + 1] == '.');
          return a && b && c && d;
        case 4:
          a = (nodes.get(1).y < N - 1) && (grid[nodes.get(1).x][nodes.get(1).y + 1] == '.');
          b = (nodes.get(3).y < N - 1) && (grid[nodes.get(3).x][nodes.get(3).y + 1] == '.');
          return a && b;
      }
      throw new IllegalArgumentException();
    }

    private void moveLeft() {
      for (Node node : nodes) {
        grid[node.x][node.y] = '.';
        --node.y;
      }
      for (Node node : nodes) {
        grid[node.x][node.y] = '@';
      }
    }

    private void moveRight() {
      for (Node node : nodes) {
        grid[node.x][node.y] = '.';
        ++node.y;
      }
      for (Node node : nodes) {
        grid[node.x][node.y] = '@';
      }
    }

    public void moveHorizontally(char leftRight) {
      if (leftRight == '<') {
        if (canMoveLeft()) {
          moveLeft();
        }
      } else {
        if (canMoveRight()) {
          moveRight();
        }
      }
    }

    private boolean canMoveDown() {
      boolean a, b, c, d;
      switch (rockType) {
        case 0:
          a = (nodes.get(0).x > 0) && (grid[nodes.get(0).x - 1][nodes.get(0).y] == '.');
          b = (nodes.get(1).x > 0) && (grid[nodes.get(1).x - 1][nodes.get(1).y] == '.');
          c = (nodes.get(2).x > 0) && (grid[nodes.get(2).x - 1][nodes.get(2).y] == '.');
          d = (nodes.get(3).x > 0) && (grid[nodes.get(3).x - 1][nodes.get(3).y] == '.');
          return a && b && c && d;
        case 1:
          a = (grid[nodes.get(1).x - 1][nodes.get(1).y] == '.');
          b = (nodes.get(4).x > 0) && (grid[nodes.get(4).x - 1][nodes.get(4).y] == '.');
          c = (grid[nodes.get(3).x - 1][nodes.get(3).y] == '.');
          return a && b && c;
        case 2:
          a = (nodes.get(2).x > 0) && (grid[nodes.get(2).x - 1][nodes.get(2).y] == '.');
          b = (nodes.get(3).x > 0) && (grid[nodes.get(3).x - 1][nodes.get(3).y] == '.');
          c = (nodes.get(4).x > 0) && (grid[nodes.get(4).x - 1][nodes.get(4).y] == '.');
          return a && b && c;
        case 3:
          a = (nodes.get(3).x > 0) && (grid[nodes.get(3).x - 1][nodes.get(3).y] == '.');
          return a;
        case 4:
          a = (nodes.get(2).x > 0) && (grid[nodes.get(2).x - 1][nodes.get(2).y] == '.');
          b = (nodes.get(3).x > 0) && (grid[nodes.get(3).x - 1][nodes.get(3).y] == '.');
          return a && b;
      }
      throw new IllegalArgumentException();
    }

    public boolean moveDown() {
      if (canMoveDown()) {
        for (Node node : nodes) {
          grid[node.x][node.y] = '.';
          --node.x;
        }
        for (Node node : nodes) {
          grid[node.x][node.y] = '@';
        }
        return true;
      }
      for (Node node : nodes) {
        grid[node.x][node.y] = '#';
        if (floor < (node.x + 1)) {
          floor = node.x + 1;
        }
      }
      return false;
    }

    private void initRock() {
      nodes = new ArrayList<>();
      switch (rockType) {
        case 0:
          this.nodes.add(new Node(floor + 3, 2));
          this.nodes.add(new Node(floor + 3, 3));
          this.nodes.add(new Node(floor + 3, 4));
          this.nodes.add(new Node(floor + 3, 5));
          break;
        case 1:
          this.nodes.add(new Node(floor + 5, 3));
          this.nodes.add(new Node(floor + 4, 2));
          this.nodes.add(new Node(floor + 4, 3));
          this.nodes.add(new Node(floor + 4, 4));
          this.nodes.add(new Node(floor + 3, 3));
          break;
        case 2:
          this.nodes.add(new Node(floor + 5, 4));
          this.nodes.add(new Node(floor + 4, 4));
          this.nodes.add(new Node(floor + 3, 2));
          this.nodes.add(new Node(floor + 3, 3));
          this.nodes.add(new Node(floor + 3, 4));
          break;
        case 3:
          this.nodes.add(new Node(floor + 6, 2));
          this.nodes.add(new Node(floor + 5, 2));
          this.nodes.add(new Node(floor + 4, 2));
          this.nodes.add(new Node(floor + 3, 2));
          break;
        case 4:
          this.nodes.add(new Node(floor + 4, 2));
          this.nodes.add(new Node(floor + 4, 3));
          this.nodes.add(new Node(floor + 3, 2));
          this.nodes.add(new Node(floor + 3, 3));
          break;
        default:
          throw new IllegalArgumentException();
      }
    }
  }

  private static class Node {
    public int x;
    public int y;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
