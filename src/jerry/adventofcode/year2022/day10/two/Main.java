package jerry.adventofcode.year2022.day10.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;

/*
sample output:
##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....

real output: EFUGLPAP
 */
public class Main {

  static int step = 0;
  static int x = 0;
  static char[] crt = new char[240];

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day10/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);

    for (String line : lines) {
      if (step > 260) {
        break;
      }
      if (line.startsWith("noop")) {
        incStep();
      } else {
        int val = Integer.parseInt(line.split(" ")[1]);
        incStep();
        incStep();
        x += val;
      }
    }
    for (int i = 0; i < 6; ++i) {
      for (int j = 0; j < 40; ++j) {
        System.out.print(crt[i * 40 + j]);
      }
      System.out.println();
    }
  }

  static void incStep() {
    if ((x == (step % 40)) || (x + 1 == (step % 40)) || (x + 2 == (step % 40))) {
      crt[step] = '#';
    } else {
      crt[step] = '.';
    }
    ++step;
  }
}
