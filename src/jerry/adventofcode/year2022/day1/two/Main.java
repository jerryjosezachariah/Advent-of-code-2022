package jerry.adventofcode.year2022.day1.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.Comparator;

/*
sample output: 45000
real output: 195625
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day1/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    ArrayList<Integer> sums = new ArrayList<>();
    int sum = 0;
    for (String line : lines) {
      if (line.isBlank()) {
        sums.add(sum);
        sum = 0;
      } else {
        sum += Integer.parseInt(line);
      }
    }
    sums.add(sum);
    System.out.println(
        sums.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(i -> i).sum());
  }
}
