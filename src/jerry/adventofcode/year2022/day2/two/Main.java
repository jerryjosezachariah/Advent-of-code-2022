package jerry.adventofcode.year2022.day2.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.Map;

/*
sample output: 12
real output: 12881
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day2/in.txt";
    ArrayList<String> input = readFileAsStringArray(file);
    Map<Character, Integer> lose =
        Map.of(
            'A', 3,
            'B', 1,
            'C', 2);
    Map<Character, Integer> draw =
        Map.of(
            'A', 1,
            'B', 2,
            'C', 3);
    Map<Character, Integer> win =
        Map.of(
            'A', 2,
            'B', 3,
            'C', 1);
    Map<Character, Integer> winLoseDrawScore =
        Map.of(
            'X', 0,
            'Y', 3,
            'Z', 6);
    Map<Character, Map<Character, Integer>> shapeScore =
        Map.of(
            'X', lose,
            'Y', draw,
            'Z', win);
    int score = 0;
    for (String line : input) {
      char opponent = line.charAt(0);
      char me = line.charAt(2);
      score += winLoseDrawScore.get(me) + shapeScore.get(me).get(opponent);
    }

    System.out.println(score);
  }
}
