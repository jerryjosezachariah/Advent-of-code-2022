package jerry.adventofcode.year2022.day3.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/*
sample output: 70
real output: 2641
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day3/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    int score = 0;
    for (int i = 0; i < lines.size(); i += 3) {
      String a = lines.get(i);
      String b = lines.get(i + 1);
      String c = lines.get(i + 2);
      Set<Character> setA = a.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
      Set<Character> setB = b.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
      Set<Character> setC = c.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
      setA.retainAll(setB);
      setA.retainAll(setC);
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
