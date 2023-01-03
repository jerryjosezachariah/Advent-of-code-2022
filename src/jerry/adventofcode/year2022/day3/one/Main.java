package jerry.adventofcode.year2022.day3.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/*
sample output: 157
real output: 8401
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day3/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    int score = 0;
    for (String line : lines) {
      String a = line.substring(0, line.length() / 2);
      String b = line.substring(line.length() / 2);
      Set<Character> setA = a.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
      Set<Character> setB = b.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
      setA.retainAll(setB);
      for (char ch : setA) {
        score += getScore(ch);
      }
    }

    System.out.println(score);
  }

  public static int getScore(char ch) {
    if ('a' <= ch && 'z' >= ch) {
      return (ch - 'a' + 1);
    } else {
      return (ch - 'A' + 27);
    }
  }
}
