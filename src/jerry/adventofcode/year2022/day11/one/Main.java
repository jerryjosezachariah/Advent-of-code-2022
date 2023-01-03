package jerry.adventofcode.year2022.day11.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

/*
sample output: 10605
real output: 78678
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day11/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    final int MONKEY_COUNT = Integer.parseInt(lines.get(lines.size() - 6).substring(7, 8)) + 1;
    LinkedList<Monkey> monkeys = new LinkedList();
    for (int i = 0; i < MONKEY_COUNT; ++i) {
      Monkey monkey = new Monkey(i);
      monkey.items =
          Arrays.stream(lines.get(i * 7 + 1).substring(18).split(", "))
              .map(Integer::parseInt)
              .collect(Collectors.toCollection(LinkedList::new));
      if (lines.get(i * 7 + 2).charAt(25) == 'o') {
        monkey.worryOperator = '^';
        monkey.worryFactor = 2;
      } else {
        monkey.worryOperator = lines.get(i * 7 + 2).charAt(23);
        monkey.worryFactor = Integer.parseInt(lines.get(i * 7 + 2).substring(25));
      }
      monkey.testDivisibleBy = Integer.parseInt(lines.get(i * 7 + 3).substring(21));
      monkey.trueMonkey = Integer.parseInt(lines.get(i * 7 + 4).substring(29));
      monkey.falseMonkey = Integer.parseInt(lines.get(i * 7 + 5).substring(30));
      monkeys.add(monkey);
    }

    for (int round = 1; round <= 20; ++round) {
      for (int i = 0; i < MONKEY_COUNT; ++i) {
        Monkey monkey = monkeys.get(i);
        for (int item : monkey.items) {
          ++monkey.itemsInspected;
          int newItem;
          if (monkey.worryOperator == '+') {
            newItem = (item + monkey.worryFactor) / 3;
          } else if (monkey.worryOperator == '*') {
            newItem = (item * monkey.worryFactor) / 3;
          } else {
            newItem = (item * item) / 3;
          }
          if (newItem % monkey.testDivisibleBy == 0) {
            monkeys.get(monkey.trueMonkey).items.add(newItem);
          } else {
            monkeys.get(monkey.falseMonkey).items.add(newItem);
          }
        }
        monkey.items = new LinkedList<>();
      }
    }

    long res =
        monkeys.stream()
            .map(monkey -> monkey.itemsInspected)
            .sorted(Comparator.reverseOrder())
            .limit(2)
            .mapToInt(i -> i)
            .reduce(1, (x, y) -> x * y);

    System.out.println(res);
  }

  private static class Monkey {
    int index;
    LinkedList<Integer> items;
    char worryOperator;
    int worryFactor;
    int testDivisibleBy;
    int trueMonkey;
    int falseMonkey;
    int itemsInspected;

    public Monkey(int index) {
      this.index = index;
    }
  }
}
