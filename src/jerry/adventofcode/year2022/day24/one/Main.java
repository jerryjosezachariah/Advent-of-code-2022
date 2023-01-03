package jerry.adventofcode.year2022.day24.one;

import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.stringToTwoDCharArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
/*
sample output: 18
real output: 299
 */
public class Main {

  private static final Node initNode = new Node(0, 1);
  private static int M, N;
  private static Node ultimateNode;

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day24/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    M = lines.size();
    N = lines.get(0).length();
    final int STEPS = 1000;
    HashSet<Node> possiblePositions = new HashSet();
    Node initNode = new Node(0, 1);
    possiblePositions.add(initNode);
    ultimateNode = new Node(M - 1, N - 2);
    char[][] grid = stringToTwoDCharArray(lines);
    HashMap<String, List<Character>> blizzards = new HashMap<>();
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        final char ch = grid[i][j];
        if (ch == '>' || ch == 'v' || ch == '<' || ch == '^') {
          String key = "" + i + "#" + j;
          if (blizzards.containsKey(key)) {
            blizzards.get(key).add(ch);
          } else {
            LinkedList<Character> blizzardList = new LinkedList<>();
            blizzardList.add(ch);
            blizzards.put(key, blizzardList);
          }
        }
      }
    }

    for (int step = 0; step < STEPS; ++step) {
      HashSet<Node> newPossiblePositions = new HashSet<>();
      blizzards = moveBlizzards(blizzards);
      for (Node currentPosition : possiblePositions) {
        if (currentPosition.equals(ultimateNode)) {
          System.out.println(step);
          return;
        }
        LinkedList<Node> neighbors = getNextPositions(currentPosition, blizzards);
        for (Node neighbor : neighbors) {
          newPossiblePositions.add(neighbor);
        }
      }
      possiblePositions = newPossiblePositions;
      possiblePositions.add(initNode);
    }
    System.out.println("Increase STEPS from:" + STEPS);
  }

  private static HashMap<String, List<Character>> moveBlizzards(
      HashMap<String, List<Character>> blizzards) {
    HashMap<String, List<Character>> newBlizzards = new HashMap<>();
    for (String key : blizzards.keySet()) {
      if (blizzards.get(key) == null || blizzards.get(key).size() == 0) {
        continue;
      } else {
        int i = Integer.parseInt(key.split("#")[0]);
        int j = Integer.parseInt(key.split("#")[1]);
        for (Character ch : blizzards.get(key)) {
          int newI = i;
          int newJ = j;
          switch (ch) {
            case '>':
              newJ = j + 1;
              if (newJ >= N - 1) {
                newJ = 1;
              }
              break;
            case 'v':
              newI = i + 1;
              if (newI >= M - 1) {
                newI = 1;
              }
              break;
            case '<':
              newJ = j - 1;
              if (newJ <= 0) {
                newJ = N - 2;
              }
              break;
            case '^':
              newI = i - 1;
              if (newI <= 0) {
                newI = M - 2;
              }
              break;
          }
          String newKey = "" + newI + "#" + newJ;
          if (newBlizzards.containsKey(newKey)) {
            newBlizzards.get(newKey).add(ch);
          } else {
            LinkedList<Character> blizzardList = new LinkedList<>();
            blizzardList.add(ch);
            newBlizzards.put(newKey, blizzardList);
          }
        }
      }
    }
    return newBlizzards;
  }

  private static LinkedList<Node> getNextPositions(
      Node currentPosition, HashMap<String, List<Character>> blizzards) {
    LinkedList<Node> neighbors = new LinkedList<>();
    neighbors.add(currentPosition);
    neighbors.add(new Node(currentPosition.x - 1, currentPosition.y));
    neighbors.add(new Node(currentPosition.x + 1, currentPosition.y));
    neighbors.add(new Node(currentPosition.x, currentPosition.y - 1));
    neighbors.add(new Node(currentPosition.x, currentPosition.y + 1));

    return neighbors.stream()
        .filter(node -> (isWithinGrid(node)) && (isNotOnBlizzard(node, blizzards)))
        .collect(Collectors.toCollection(LinkedList::new));
  }

  private static boolean isWithinGrid(Node node) {
    return node.equals(initNode)
        || node.equals(ultimateNode)
        || (node.x > 0 && node.x < (M - 1) && node.y > 0 && node.y < (N - 1));
  }

  private static boolean isNotOnBlizzard(Node node, HashMap<String, List<Character>> blizzards) {
    return !blizzards.containsKey("" + node.x + "#" + node.y);
  }

  private static class Node {
    public int x;
    public int y;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node node = (Node) o;
      return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }
}
