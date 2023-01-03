package jerry.adventofcode.year2022.day1.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
/*
sample output: 24000
real output: 65912
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day1/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    int sum = 0;
    int maxSum = 0;
    for (String line : lines) {
      if (line.isBlank()) {
        if (maxSum < sum) maxSum = sum;
        sum = 0;
      } else {
        sum += Integer.parseInt(line);
      }
    }
    if (maxSum < sum) maxSum = sum;
    System.out.println(maxSum);
  }
}
