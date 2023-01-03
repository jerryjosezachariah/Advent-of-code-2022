package jerry.adventofcode.year2022.day9.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;

/*
sample output: 13
real output: 6087
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day9/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    final int XM = 500;
    final int YM = 500;

    int hx = XM / 2, hy = YM / 2, tx = XM / 2, ty = YM / 2;
    boolean[][] touched = new boolean[XM][YM];
    touched[XM / 2][YM / 2] = true;

    for (String line : lines) {
      int steps = Integer.parseInt(line.split(" ")[1]);
      for (int i = 0; i < steps; ++i) {
        if (line.startsWith("R")) { // update position of head
          hy += 1;
        } else if (line.startsWith("U")) {
          hx -= 1;
        } else if (line.startsWith("L")) {
          hy -= 1;
        } else if (line.startsWith("D")) {
          hx += 1;
        }
        int xdiff = hx - tx;
        int ydiff = hy - ty;
        if (Math.abs(xdiff) > 1 || Math.abs(ydiff) > 1) { // update positions of tail if required
          if (xdiff > 0) {
            tx += 1;
          } else if (xdiff < 0) {
            tx -= 1;
          }
          if (ydiff > 0) {
            ty += 1;
          } else if (ydiff < 0) {
            ty -= 1;
          }
        }

        touched[tx][ty] = true;
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
}
