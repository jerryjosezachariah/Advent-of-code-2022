package jerry.adventofcode.year2022.day5.one;

import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.reverseLinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/*
sample output: CMZ
real output: LJSVLTWQM
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day5/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    ArrayList<String> cratesString = new ArrayList<>();
    ArrayList<String> steps = new ArrayList<>();
    LinkedList<Character>[] crateStacks = null;
    int CRATE_STACKS_COUNT = 0;

    boolean partOneCompleted = false;
    for (String line : lines) {
      if (!partOneCompleted && Character.isDigit(line.charAt(1))) {
        CRATE_STACKS_COUNT = Integer.parseInt(line.split("\\s")[line.split("\\s").length - 1]);
        crateStacks = new LinkedList[CRATE_STACKS_COUNT];
        partOneCompleted = true;
        continue;
      }
      if (!partOneCompleted) {
        cratesString.add(line);
      } else {
        if (line.isEmpty()) {
          continue;
        }
        steps.add(line.replaceAll("move ", "").replaceAll(" from ", " ").replaceAll(" to ", " "));
      }
    }

    for (int i = 0; i < CRATE_STACKS_COUNT; ++i) {
      crateStacks[i] = new LinkedList<>();
    }

    for (String line : cratesString) {
      for (int i = 0; i < CRATE_STACKS_COUNT; ++i) {
        int crateIndex = 1 + (i * 4);
        if (crateIndex >= line.length()) {
          break;
        }
        if (line.charAt(crateIndex) != ' ') {
          crateStacks[i].push(line.charAt(1 + (i * 4)));
        }
      }
    }

    for (int i = 0; i < CRATE_STACKS_COUNT; ++i) {
      crateStacks[i] = reverseLinkedList(crateStacks[i]);
    }

    for (String line : steps) {
      ArrayList<Integer> lineInt =
          Arrays.stream(line.split(" "))
              .map(Integer::parseInt)
              .collect(Collectors.toCollection(ArrayList::new));
      move(lineInt.get(0), lineInt.get(1) - 1, lineInt.get(2) - 1, crateStacks);
    }

    String result = "";
    for (int i = 0; i < CRATE_STACKS_COUNT; ++i) {
      Character ch = crateStacks[i].pop();
      if (ch != ' ') {
        result += ch;
      }
    }

    System.out.println(result);
  }

  public static void move(int count, int from, int to, LinkedList<Character>[] crates) {
    for (int i = 0; i < count; ++i) {
      crates[to].push(crates[from].pop());
    }
  }
}
