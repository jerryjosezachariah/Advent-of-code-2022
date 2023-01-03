package jerry.adventofcode.year2022.day6.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.HashSet;

/*
sample output: 11
real output: 1275
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day6/in.txt";
    String in = readFileAsStringArray(file).get(0);
    int DISTINCT_COUNT = 4;

    for (int i = DISTINCT_COUNT - 1; i < in.length(); ++i) {
      HashSet<Character> set = new HashSet<>();
      for (int j = 0; j < DISTINCT_COUNT; ++j) {
        set.add(in.charAt(i - j));
        if (set.size() == DISTINCT_COUNT) {
          System.out.println(i + 1);
          return;
        }
      }
    }
  }
}
