package jerry.adventofcode.year2022.day17.two;

import static jerry.adventofcode.Util.printCurrentTime;
import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
/*
sample output: 1514285714288 // 18 seconds
real output: 1567723342929 // 1 second
 */
public class Main {

  static int M = 2_000_000;
  static int N = 7;
  static char[][] grid;
  static int floor = 0;

  public static void main(String[] args) throws Exception {
    printCurrentTime();
    String file = "src/jerry/adventofcode/year2022/day17/in.txt";
    String movements = readFileAsStringArray(file).get(0);
    int nextMovement = 0;

    grid = new char[M][N];
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        grid[i][j] = '.';
      }
    }

    // initial experiment to discover cyclicity
    int maxCyclicCounter = Integer.MIN_VALUE;
    int maxCyclicMovementIndex = -1;
    int cyclicHeightDiff = 0;
    int iDiff = 0;
    int prevI = 0;
    int[][] previousHeight = new int[5][movements.length()];
    int[][] previousHeightDiff = new int[5][movements.length()];
    int[][] previousHeightDiffSameCounter = new int[5][movements.length()];
    for (int i = 0; i < 1_000_000; ++i) {
      /*if(nextMovement==591){
        System.out.println("i,previousHeightDiff:"+i+" "+previousHeightDiff[i%5][nextMovement]);
      }*/
      if (previousHeight[i % 5][nextMovement] > 0) {
        int currentHeightDiff = floor - previousHeight[i % 5][nextMovement];
        if (currentHeightDiff == previousHeightDiff[i % 5][nextMovement]) {
          ++previousHeightDiffSameCounter[i % 5][nextMovement];
          if (maxCyclicCounter < previousHeightDiffSameCounter[i % 5][nextMovement]) {
            maxCyclicCounter = previousHeightDiffSameCounter[i % 5][nextMovement];
            maxCyclicMovementIndex = nextMovement;
            cyclicHeightDiff = currentHeightDiff;
            iDiff = i - prevI;
            prevI = i;
          }
        }
        previousHeightDiff[i % 5][nextMovement] = currentHeightDiff;
      }
      previousHeight[i % 5][nextMovement] = floor;
      Rock r = new Rock(i % 5);
      boolean movedDown = true;
      while (movedDown) {
        r.moveHorizontally(movements.charAt(nextMovement));
        nextMovement = (nextMovement + 1) % movements.length();
        movedDown = r.moveDown();
      }
    }
    // System.out.println("maxCyclicCounter,maxCyclicShape,maxCyclicMovementIndex,cyclicHeightDiff:"+maxCyclicCounter+" "+maxCyclicShape+" "+maxCyclicMovementIndex+" "+cyclicHeightDiff);

    // cleanup after experiment
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        grid[i][j] = '.';
      }
    }
    floor = 0;
    nextMovement = 0;

    long newI = 0, floorDelta = 0; // cyclic nextMovement=591,idiff=1735,floordiff=2720
    for (long i = 0; i < 1_000_000_000_000L; ++i) {
      if (nextMovement == maxCyclicMovementIndex && i > 3564) {
        newI = i + iDiff;
        while (newI < 1_000_000_000_000L) {
          i += iDiff;
          floorDelta += cyclicHeightDiff;
          newI += iDiff;
        }
      }

      Rock r = new Rock((int) (i % 5));
      boolean movedDown = true;
      while (movedDown) {
        r.moveHorizontally(movements.charAt(nextMovement));
        nextMovement = (nextMovement + 1) % movements.length();
        movedDown = r.moveDown();
      }
    }
    System.out.println(floor + floorDelta); // 1567723342929
    printCurrentTime();
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
