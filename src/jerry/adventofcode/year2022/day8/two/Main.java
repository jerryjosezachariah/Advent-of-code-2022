package jerry.adventofcode.year2022.day8.two;

import static jerry.adventofcode.Util.readFileAsTwoDArray;

/*
sample output: 8
real output: 201600
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day8/in.txt";
    int[][] grid = readFileAsTwoDArray(file);
    int[][] l = new int[grid.length][grid[0].length];
    int[][] r = new int[grid.length][grid[0].length];
    int[][] t = new int[grid.length][grid[0].length];
    int[][] d = new int[grid.length][grid[0].length];

    // LOOK LEFT
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[0].length; ++j) {
        for (int k = j - 1; k >= 0; --k) {
          if (grid[i][k] >= grid[i][j]) {
            ++l[i][j];
            break;
          } else {
            ++l[i][j];
          }
        }
      }
    }

    // LOOK RIGHT
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[0].length; ++j) {
        for (int k = j + 1; k < grid[0].length; ++k) {
          if (grid[i][k] >= grid[i][j]) {
            ++r[i][j];
            break;
          } else {
            ++r[i][j];
          }
        }
      }
    }

    // LOOK TOP
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[0].length; ++j) {
        for (int k = i - 1; k >= 0; --k) {
          if (grid[k][j] >= grid[i][j]) {
            ++t[i][j];
            break;
          } else {
            ++t[i][j];
          }
        }
      }
    }

    // LOOK DOWN
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[0].length; ++j) {
        for (int k = i + 1; k < grid.length; ++k) {
          if (grid[k][j] >= grid[i][j]) {
            ++d[i][j];
            break;
          } else {
            ++d[i][j];
          }
        }
      }
    }

    int max = 0;
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[0].length; ++j) {
        int p = l[i][j] * r[i][j] * t[i][j] * d[i][j];
        if (p > max) {
          max = p;
        }
      }
    }

    System.out.println(max);
  }
}
