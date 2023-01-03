package jerry.adventofcode.year2022.day10.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
/*
sample output: 13140
real output: 15020
 */
public class Main {

  static int step = 0;
  static int x = 1;
  static int sum = 0;

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day10/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);

    for (String line : lines) {
      if (step > 220) {
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

    System.out.println(sum);
  }

  static void incStep() {
    if ((step == 20 - 1)
        || ((step > 20) && ((step + 1 - 20) % 40 == 0))) { // step=19,59,99,139,179,219
      int signalStrength = (step + 1) * x;
      sum += signalStrength;
    }
    ++step;
  }
}
