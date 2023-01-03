package jerry.adventofcode.year2022.day20.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.stream.Collectors;

/*
sample output: 3
real output: 2275
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day20/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    ArrayList<Integer> in =
        lines.stream().map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
    ArrayList<Integer> out = new ArrayList<>(in);
    for (int i = 0; i < lines.size(); ++i) {
      int ch = out.remove(i);
      if (ch == 0) {
        out.add(i, ch);
        continue;
      }
      int index = (i + ch) % (lines.size() - 1);
      if (index <= 0) {
        index += lines.size() - 1;
      }
      out.add(index, 0);
      in.remove(i);
      in.add(index, ch);
      if (index > i) {
        --i;
      }
    }
    int zeroIndex = -1;
    for (int i = 0; i < lines.size(); ++i) {
      if (in.get(i) == 0) {
        zeroIndex = i;
        break;
      }
    }
    int a = in.get((zeroIndex + 1000) % lines.size());
    int b = in.get((zeroIndex + 2000) % lines.size());
    int c = in.get((zeroIndex + 3000) % lines.size());

    System.out.println(a + b + c);
  }
}
