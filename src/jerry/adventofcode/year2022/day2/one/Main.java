package jerry.adventofcode.year2022.day2.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.Map;

/*
sample output: 15
real output: 13682
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day2/in.txt";
    ArrayList<String> input = readFileAsStringArray(file);
    Map<Character, Integer> shapeScore =
        Map.of(
            'X', 1,
            'Y', 2,
            'Z', 3);
    int score = 0;
    for (String line : input) {
      char opponent = line.charAt(0);
      char me = line.charAt(2);
      score += winLoseDrawScore(opponent, me) + shapeScore.get(me);
    }
    System.out.println(score);
  }

  public static int winLoseDrawScore(char opponent, char me) {
    if ((opponent == 'A' && me == 'X')
        || (opponent == 'B' && me == 'Y')
        || (opponent == 'C' && me == 'Z')) {
      return 3;
    }
    if ((opponent == 'A' && me == 'Y')
        || (opponent == 'B' && me == 'Z')
        || (opponent == 'C' && me == 'X')) {
      return 6;
    } else {
      return 0;
    }
  }
}
