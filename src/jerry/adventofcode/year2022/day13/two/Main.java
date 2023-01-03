package jerry.adventofcode.year2022.day13.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.stream.Collectors;

/*
sample output: 140
real output: 23868
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day13/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);

    lines.add("[[2]]");
    lines.add("[[6]]");

    ArrayList<String> sortedLines =
        lines.stream()
            .filter(line -> !line.isBlank())
            .sorted(
                (a, b) -> {
                  int comp = compare(a, b);
                  if (comp == 0) {
                    return 0;
                  }
                  if (comp == 1) {
                    return -1;
                  }
                  return 1;
                })
            .collect(Collectors.toCollection(ArrayList::new));
    int start = -1;
    for (int i = 0; i < sortedLines.size(); ++i) {
      if ("[[2]]".equals(sortedLines.get(i))) {
        start = i + 1;
      }
      if ("[[6]]".equals(sortedLines.get(i))) {
        System.out.println(start * (i + 1));
        return;
      }
    }
  }

  private static int compare(String a, String b) { // 0 same, 1 in order (a<b), 2 out of order (a>b)
    if (a.equals(b)) {
      return 0;
    }
    if (a.startsWith("[") && !b.startsWith("[")) {
      return compare(a, "[" + b + "]");
    }
    if (b.startsWith("[") && !a.startsWith("[")) {
      return compare("[" + a + "]", b);
    }
    if (!a.startsWith("[") && !b.startsWith("[")) {
      if (Integer.parseInt(a) == Integer.parseInt(b)) {
        return 0;
      } else if (Integer.parseInt(a) < Integer.parseInt(b)) {
        return 1;
      } else {
        return 2;
      }
    }
    ArrayList<String> aT = tokenize(a);
    ArrayList<String> bT = tokenize(b);
    for (int i = 0; i < aT.size(); ++i) {
      if (i >= bT.size()) {
        return 2;
      }
      int comp = compare(aT.get(i), bT.get(i));
      if (comp == 0) {
        continue;
      }
      return comp;
    }
    return 1;
  }

  private static ArrayList<String> tokenize(String a) {
    ArrayList<String> tokens = new ArrayList<>();
    if (!a.startsWith("[")) {
      tokens.add(a);
      return tokens;
    }
    int bracketCount = 0;
    String token = "";
    for (int i = 1; i < a.length() - 1; ++i) {
      char ch = a.charAt(i);
      if (ch == '[') {
        token += ch;
        ++bracketCount;
        continue;
      }
      if (ch == ']') {
        token += ch;
        --bracketCount;
        if (bracketCount == 0) {
          tokens.add(token);
          token = "";
        }
        continue;
      }
      if (ch == ',' && bracketCount == 0 && token != "") {
        tokens.add(token);
        token = "";
        continue;
      }
      if (ch == ',' && token == "") {
        continue;
      }
      token += ch;
    }
    if (token != "") {
      tokens.add(token);
    }
    return tokens;
  }
}
