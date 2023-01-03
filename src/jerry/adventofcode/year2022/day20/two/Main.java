package jerry.adventofcode.year2022.day20.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;

/*
sample output: 1623178306
real output: 4090409331120
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day20/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    int[] indices = new int[lines.size()];
    ArrayList<Node> in = new ArrayList<>();
    for (int i = 0; i < lines.size(); ++i) {
      indices[i] = i;
      long val = Long.parseLong(lines.get(i)) * 811589153L;
      in.add(new Node(i, val));
    }

    int mixCount = 10;
    while (mixCount > 0) {
      for (int curIndex : indices) {
        Node ch = in.remove(curIndex);
        int newIndex = (int) ((curIndex + ch.value) % (lines.size() - 1));
        if (newIndex <= 0) {
          newIndex += lines.size() - 1;
        }
        in.add(newIndex, ch);
        indices = updateIndices(in, indices);
      }

      --mixCount;
    }

    int zeroIndex = -1;
    for (int i = 0; i < lines.size(); ++i) {
      if (in.get(i).value == 0) {
        zeroIndex = i;
        break;
      }
    }
    long a = in.get((zeroIndex + 1000) % lines.size()).value;
    long b = in.get((zeroIndex + 2000) % lines.size()).value;
    long c = in.get((zeroIndex + 3000) % lines.size()).value;

    System.out.println(a + b + c);
  }

  private static int[] updateIndices(ArrayList<Node> in, int[] indices) {
    for (int i = 0; i < in.size(); ++i) {
      Node node = in.get(i);
      indices[node.originalIndex] = i;
    }
    return indices;
  }

  private static class Node {
    int originalIndex;
    long value;

    public Node(int originalIndex, long value) {
      this.originalIndex = originalIndex;
      this.value = value;
    }
  }
}
