package jerry.adventofcode.year2022.day23.two;

import static jerry.adventofcode.Util.padWithChar;
import static jerry.adventofcode.Util.printCurrentTime;
import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.stringToTwoDCharArray;

import java.util.ArrayList;
import java.util.LinkedList;

/*
sample output: 20
real output: 1023 // 76 seconds
 */
public class Main {
  private static int M, N;
  private static int moveCount;

  public static void main(String[] args) throws Exception {
    printCurrentTime();
    String file = "src/jerry/adventofcode/year2022/day23/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    final int MAX_ROUNDS = 1500;
    M = lines.size();
    N = lines.get(0).length();
    char[][] grid = stringToTwoDCharArray(lines);
    grid = padWithChar(grid, '.', MAX_ROUNDS);
    M = M + (2 * MAX_ROUNDS);
    N = N + (2 * MAX_ROUNDS);
    int round = 0;
    while (round < MAX_ROUNDS) {
      moveCount = 0;
      LinkedList<Node>[][] proposedNodesGrid = populateProposedNodes(round, grid);
      grid = fixProposal(proposedNodesGrid);
      if (moveCount == 0) {
        System.out.println(round + 1);
        printCurrentTime();
        return;
      }
      ++round;
    }
    printCurrentTime();
  }

  private static LinkedList<Node>[][] populateProposedNodes(int round, char[][] grid) {
    LinkedList<Node>[][] proposedNodesGrid = new LinkedList[M][N];
    char[] directions = {'N', 'S', 'W', 'E'};
    int directionIndex = round % 4;
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        proposedNodesGrid[i][j] = new LinkedList<>();
      }
    }
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        if (grid[i][j] == '#') {
          Node currentNode = new Node(i, j);
          Node proposedNode = new Node(i, j);
          if (isDirectionEmpty(grid, i, j, 'N')
              && isDirectionEmpty(grid, i, j, 'S')
              && isDirectionEmpty(grid, i, j, 'E')
              && isDirectionEmpty(grid, i, j, 'W')) {
            proposedNodesGrid[proposedNode.x][proposedNode.y].add(currentNode);
            continue;
          }
          for (int d = 0; d < 4; ++d) {
            if (isDirectionEmpty(grid, i, j, directions[(directionIndex + d) % 4])) {
              proposedNode = getProposedNode(i, j, directions[(directionIndex + d) % 4]);
              ++moveCount;
              break;
            }
          }
          proposedNodesGrid[proposedNode.x][proposedNode.y].add(currentNode);
        }
      }
    }
    return proposedNodesGrid;
  }

  private static boolean isDirectionEmpty(char[][] grid, int i, int j, char direction) {
    switch (direction) {
      case 'N':
        return (grid[i - 1][j - 1] == '.' && grid[i - 1][j] == '.' && grid[i - 1][j + 1] == '.');
      case 'S':
        return (grid[i + 1][j - 1] == '.' && grid[i + 1][j] == '.' && grid[i + 1][j + 1] == '.');
      case 'W':
        return (grid[i - 1][j - 1] == '.' && grid[i][j - 1] == '.' && grid[i + 1][j - 1] == '.');
      case 'E':
        return (grid[i - 1][j + 1] == '.' && grid[i][j + 1] == '.' && grid[i + 1][j + 1] == '.');
    }
    return false;
  }

  private static Node getProposedNode(int i, int j, char direction) {
    switch (direction) {
      case 'N':
        return new Node(i - 1, j);
      case 'S':
        return new Node(i + 1, j);
      case 'W':
        return new Node(i, j - 1);
      case 'E':
        return new Node(i, j + 1);
    }
    return null;
  }

  private static char[][] fixProposal(LinkedList<Node>[][] proposedNodesGrid) {
    char[][] grid = new char[M][N];
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        grid[i][j] = '.';
      }
    }
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        if (proposedNodesGrid[i][j].size() == 1) {
          grid[i][j] = '#';
        } else if (proposedNodesGrid[i][j].size() > 1) {
          moveCount -= proposedNodesGrid[i][j].size();
          for (Node incomingNode : proposedNodesGrid[i][j]) {
            grid[incomingNode.x][incomingNode.y] = '#';
          }
        }
      }
    }
    return grid;
  }

  private static class Node {
    public int x;
    public int y;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
