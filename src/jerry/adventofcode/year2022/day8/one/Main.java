package jerry.adventofcode.year2022.day8.one;

import static jerry.adventofcode.Util.readFileAsTwoDArray;

/*
sample output: 21
real output: 1849
 */
public class Main {
  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day8/in.txt";
    int[][] grid = readFileAsTwoDArray(file);
    boolean[][] vis = new boolean[grid.length][grid[0].length];

    // L->R
    for (int i = 0; i < grid.length; ++i) {
      int tallest = grid[i][0] - 1;
      for (int j = 0; j < grid[0].length; ++j) {
        if (tallest < grid[i][j]) {
          vis[i][j] = true;
          tallest = grid[i][j];
        }
      }
    }

    // R->L
    for (int i = 0; i < grid.length; ++i) {
      int tallest = grid[i][grid[0].length - 1] - 1;
      for (int j = grid[0].length - 1; j >= 0; --j) {
        if (tallest < grid[i][j]) {
          vis[i][j] = true;
          tallest = grid[i][j];
        }
      }
    }

    // T->B
    for (int j = 0; j < grid[0].length; ++j) {
      int tallest = grid[0][j] - 1;
      for (int i = 0; i < grid.length; ++i) {
        if (tallest < grid[i][j]) {
          vis[i][j] = true;
          tallest = grid[i][j];
        }
      }
    }

    // B->T
    for (int j = 0; j < grid[0].length; ++j) {
      int tallest = grid[grid.length - 1][j] - 1;
      for (int i = grid.length - 1; i >= 0; --i) {
        if (tallest < grid[i][j]) {
          vis[i][j] = true;
          tallest = grid[i][j];
        }
      }
    }

    int count = 0;
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[0].length; ++j) {
        if (vis[i][j]) {
          ++count;
        }
      }
    }

    System.out.println(count);
  }
}
