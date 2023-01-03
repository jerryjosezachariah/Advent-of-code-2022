package jerry.adventofcode.year2022.day4.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;

/*
sample output: 4
real output: 911
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day4/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    int count = 0;
    for (String line : lines) {
      int a = Integer.parseInt(line.split(",")[0].split("-")[0]);
      int b = Integer.parseInt(line.split(",")[0].split("-")[1]);
      int c = Integer.parseInt(line.split(",")[1].split("-")[0]);
      int d = Integer.parseInt(line.split(",")[1].split("-")[1]);
      if ((a <= c && b >= d) || (c <= a && d >= b) || (a >= c && a <= d) || (c >= a && c <= b)) {
        ++count;
      }
    }

    System.out.println(count);
  }
}
