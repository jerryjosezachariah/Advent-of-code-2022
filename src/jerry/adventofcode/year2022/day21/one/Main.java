package jerry.adventofcode.year2022.day21.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.HashMap;

/*
sample output: 152
real output: 309248622142100
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day21/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    HashMap<String, Monkey> monkeyMap = new HashMap<>();

    for (String line : lines) {
      String monkeyName = line.split(": ")[0];
      String expression = line.split(": ")[1];
      Monkey monkey = new Monkey(monkeyName, expression);
      monkeyMap.put(monkeyName, monkey);
    }

    Monkey root = monkeyMap.get("root");
    System.out.println(root.resolve(monkeyMap));
  }

  private static class Monkey {
    public String name;
    public long value;
    public String monkey1, monkey2;
    public char operation;
    public boolean resolved;

    public Monkey(String name, String expression) {
      this.name = name;
      if (expression.contains(" ")) {
        this.monkey1 = expression.substring(0, 4);
        this.operation = expression.charAt(5);
        this.monkey2 = expression.substring(7);
      } else {
        this.value = Long.parseLong(expression);
        this.resolved = true;
      }
    }

    public long resolve(HashMap<String, Monkey> monkeyMap) {
      if (this.resolved) {
        return this.value;
      } else {
        Monkey monkey1 = monkeyMap.get(this.monkey1);
        Monkey monkey2 = monkeyMap.get(this.monkey2);
        long value1 = monkey1.resolve(monkeyMap);
        long value2 = monkey2.resolve(monkeyMap);
        switch (operation) {
          case '+':
            this.value = value1 + value2;
            break;
          case '-':
            this.value = value1 - value2;
            break;
          case '/':
            this.value = value1 / value2;
            break;
          case '*':
            this.value = value1 * value2;
            break;
          default:
            throw new IllegalArgumentException();
        }
        this.resolved = true;
        return this.value;
      }
    }
  }
}
