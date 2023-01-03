package jerry.adventofcode.year2022.day9.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;

/*
sample output: 1
real output: 2493
 */
public class Main {
  public static final int KNOT_COUNT = 10;
  public static int[] x = new int[KNOT_COUNT];
  public static int[] y = new int[KNOT_COUNT];

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day9/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    final int XM = 500;
    final int YM = 500;
    boolean[][] touched = new boolean[XM][YM];

    touched[XM / 2][YM / 2] = true;
    for (int i = 0; i < KNOT_COUNT; ++i) {
      x[i] = XM / 2;
      y[i] = YM / 2;
    }

    for (String line : lines) {
      int steps = Integer.parseInt(line.split(" ")[1]);
      for (int i = 0; i < steps; ++i) {
        if (line.startsWith("R")) { // update position of head
          y[0] += 1;
        } else if (line.startsWith("U")) {
          x[0] -= 1;
        } else if (line.startsWith("L")) {
          y[0] -= 1;
        } else if (line.startsWith("D")) {
          x[0] += 1;
        }
        for (int j = 0; j < KNOT_COUNT - 1; ++j) {
          updatePos(touched, j, j + 1); // update positions of other knots
        }
      }
    }

    int count = 0; // count number of unique positions of tail
    for (int i = 0; i < XM; ++i) {
      for (int j = 0; j < YM; ++j) {
        if (touched[i][j]) {
          ++count;
        }
      }
    }

    System.out.println(count);
  }

  public static void updatePos(boolean[][] touched, int h, int t) {
    int xdiff = x[h] - x[t];
    int ydiff = y[h] - y[t];
    if (Math.abs(xdiff) > 1 || Math.abs(ydiff) > 1) { // check if movement is required for next knot
      if (xdiff > 0) {
        x[t] += 1;
      } else if (xdiff < 0) {
        x[t] -= 1;
      }
      if (ydiff > 0) {
        y[t] += 1;
      } else if (ydiff < 0) {
        y[t] -= 1;
      }
    }
    if (t == KNOT_COUNT - 1) { // update unique positions of tail
      touched[x[KNOT_COUNT - 1]][y[KNOT_COUNT - 1]] = true;
    }
  }
}
