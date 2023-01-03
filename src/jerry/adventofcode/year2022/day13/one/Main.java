package jerry.adventofcode.year2022.day13.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;

/*
sample output: 13
real output: 5292
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day13/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);

    int sum = 0;
    for (int i = 0; i < lines.size(); i += 3) {
      String a = lines.get(i);
      String b = lines.get(i + 1);
      if (compare(a, b) == 1) {
        sum += (i / 3) + 1;
      }
    }

    System.out.println(sum);
  }

  public static int compare(String a, String b) { // 0 same, 1 in order (a<b), 2 out of order (a>b)
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

  public static ArrayList<String> tokenize(String a) {
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
