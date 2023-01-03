package jerry.adventofcode.year2022.day11.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

/*
sample output: 2713310158
real output: 15333249714
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day11/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    final int MONKEY_COUNT = Integer.parseInt(lines.get(lines.size() - 6).substring(7, 8)) + 1;
    LinkedList<Monkey> monkeys = new LinkedList();
    int worryReductionFactor = 1;
    for (int i = 0; i < MONKEY_COUNT; ++i) {
      Monkey monkey = new Monkey(i);
      monkey.items =
          Arrays.stream(lines.get(i * 7 + 1).substring(18).split(", "))
              .map(Long::parseLong)
              .collect(Collectors.toCollection(LinkedList::new));
      if (lines.get(i * 7 + 2).charAt(25) == 'o') {
        monkey.worryOperator = '^';
        monkey.worryFactor = 2;
      } else {
        monkey.worryOperator = lines.get(i * 7 + 2).charAt(23);
        monkey.worryFactor = Integer.parseInt(lines.get(i * 7 + 2).substring(25));
      }
      monkey.testDivisibleBy = Integer.parseInt(lines.get(i * 7 + 3).substring(21));
      worryReductionFactor *= monkey.testDivisibleBy;
      monkey.trueMonkey = Integer.parseInt(lines.get(i * 7 + 4).substring(29));
      monkey.falseMonkey = Integer.parseInt(lines.get(i * 7 + 5).substring(30));
      monkeys.add(monkey);
    }
    for (int round = 1; round <= 10_000; ++round) {
      for (int i = 0; i < MONKEY_COUNT; ++i) {
        Monkey monkey = monkeys.get(i);
        for (long item : monkey.items) {
          ++monkey.itemsInspected;
          long newItem = item;
          if (monkey.worryOperator == '+') {
            newItem += monkey.worryFactor;
          } else if (monkey.worryOperator == '*') {
            newItem *= monkey.worryFactor;
          } else {
            newItem *= newItem;
          }
          newItem %= worryReductionFactor;
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
            .mapToLong(i -> i)
            .reduce(1, (x, y) -> x * y);

    System.out.println(res);
  }

  private static class Monkey {
    int index;
    LinkedList<Long> items;
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
