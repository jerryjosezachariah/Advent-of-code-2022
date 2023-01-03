package jerry.adventofcode.year2022.day14.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.Arrays;
/*
sample output: 93
real output: 29044
*/
public class Main {
  private static final int M1 = 0, M = 1001, N1 = 0, N = 200;

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day14/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    int[][] grid = new int[M - M1][N - N1];

    for (String line : lines) {
      ArrayList<String> points = new ArrayList(Arrays.asList(line.split(" -> ")));
      for (int i = 0; i < points.size() - 1; ++i) {
        buildRock(grid, points.get(i), points.get(i + 1));
      }
    }
    int yMax = Integer.MIN_VALUE;
    for (int i = M1; i < M; ++i) {
      for (int j = N1; j < N; ++j) {
        if ((grid[i - M1][j - N1] == 1) && (yMax < j)) {
          yMax = j;
        }
      }
    }
    yMax += 2;
    buildRock(grid, "" + M1 + "," + yMax, "" + (M - 1) + "," + yMax);

    while (produceSand(grid))
      ;

    int count = 1;
    for (int i = M1; i < M; ++i) {
      for (int j = N1; j < N; ++j) {
        if (grid[i - M1][j - N1] == 2) {
          ++count;
        }
      }
    }

    System.out.println(count);
  }

  public static boolean produceSand(int[][] grid) {
    int x = 500, y = 0;
    boolean moved = true;
    while (moved) {
      moved = false;
      if (grid[x - M1][y + 1 - N1] == 0) {
        ++y;
        moved = true;
      } else if (grid[x - 1 - M1][y + 1 - N1] == 0) {
        --x;
        ++y;
        moved = true;
      } else if (grid[x + 1 - M1][y + 1 - N1] == 0) {
        ++x;
        ++y;
        moved = true;
      }
    }
    if (x == 500 && y == 0) {
      return false;
    }
    grid[x - M1][y - N1] = 2;
    return true;
  }

  public static void buildRock(int[][] grid, String p1, String p2) {
    int x1 = Integer.parseInt(p1.split(",")[0]);
    int y1 = Integer.parseInt(p1.split(",")[1]);
    int x2 = Integer.parseInt(p2.split(",")[0]);
    int y2 = Integer.parseInt(p2.split(",")[1]);
    if (x1 == x2) {
      if (y1 > y2) {
        int temp = y1;
        y1 = y2;
        y2 = temp;
      }
      for (int i = y1; i <= y2; ++i) {
        grid[x1 - M1][i - N1] = 1;
      }
    } else if (y1 == y2) {
      if (x1 > x2) {
        int temp = x1;
        x1 = x2;
        x2 = temp;
      }
      for (int i = x1; i <= x2; ++i) {
        grid[i - M1][y1 - N1] = 1;
      }
    }
  }
}
