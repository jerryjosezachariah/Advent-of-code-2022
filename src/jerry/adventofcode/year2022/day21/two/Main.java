package jerry.adventofcode.year2022.day21.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.HashMap;

/*
sample output: 301
real output: 3757272361782
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
    root.resolve(monkeyMap);
    root.operation = '-';
    root.reverseResolve(0, monkeyMap);

    System.out.println(monkeyMap.get("humn").value);
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

    public void reverseResolve(long forceValue, HashMap<String, Monkey> monkeyMap) {
      if (this.operation == '\u0000') {
        this.value = forceValue;
        return;
      }
      if (monkeyMap.get(this.monkey1).leadsToHuman(monkeyMap)) {
        long newForceValue;
        long value2 = monkeyMap.get(this.monkey2).value;
        switch (this.operation) {
          case '+':
            newForceValue = forceValue - value2;
            break;
          case '-':
            newForceValue = forceValue + value2;
            break;
          case '/':
            newForceValue = forceValue * value2;
            break;
          case '*':
            newForceValue = forceValue / value2;
            break;
          default:
            throw new IllegalArgumentException();
        }
        monkeyMap.get(this.monkey1).reverseResolve(newForceValue, monkeyMap);
      } else if (monkeyMap.get(this.monkey2).leadsToHuman(monkeyMap)) {
        long newForceValue;
        long value1 = monkeyMap.get(this.monkey1).value;
        switch (this.operation) {
          case '+':
            newForceValue = forceValue - value1;
            break;
          case '-':
            newForceValue = value1 - forceValue;
            break;
          case '/':
            newForceValue = value1 / forceValue;
            break;
          case '*':
            newForceValue = forceValue / value1;
            break;
          default:
            throw new IllegalArgumentException();
        }
        monkeyMap.get(this.monkey2).reverseResolve(newForceValue, monkeyMap);
      } else {
        System.out.println(this.name);
        throw new IllegalArgumentException();
      }
    }

    private boolean leadsToHuman(HashMap<String, Monkey> monkeyMap) {
      if (this.name.equals("humn")) {
        return true;
      }
      if (this.operation == '\u0000') {
        return false;
      }
      return (monkeyMap.get(this.monkey1).leadsToHuman(monkeyMap)
          || monkeyMap.get(this.monkey2).leadsToHuman(monkeyMap));
    }
  }
}
