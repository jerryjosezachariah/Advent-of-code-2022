package jerry.adventofcode.year2022.day23.one;

import static jerry.adventofcode.Util.padWithChar;
import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.stringToTwoDCharArray;

import java.util.ArrayList;
import java.util.LinkedList;
/*
sample output: 110
real output: 4236
 */
public class Main {
  private static int M, N;

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day23/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    final int MAX_ROUNDS = 10;
    M = lines.size();
    N = lines.get(0).length();
    char[][] grid = stringToTwoDCharArray(lines);
    grid = padWithChar(grid, '.', MAX_ROUNDS);
    M = M + (2 * MAX_ROUNDS);
    N = N + (2 * MAX_ROUNDS);
    int round = 0;
    while (round < MAX_ROUNDS) {
      LinkedList<Node>[][] proposedNodesGrid = populateProposedNodes(round, grid);
      grid = fixProposal(proposedNodesGrid);
      ++round;
    }
    System.out.println(getEmptyTileCountInSmallestRectangle(grid));
  }

  private static int getEmptyTileCountInSmallestRectangle(char[][] grid) {
    int x1 = Integer.MAX_VALUE;
    int y1 = Integer.MAX_VALUE;
    int x2 = Integer.MIN_VALUE;
    int y2 = Integer.MIN_VALUE;
    int emptyCount = 0;
    for (int i = 0; i < M; ++i) {
      for (int j = 0; j < N; ++j) {
        if (grid[i][j] == '#') {
          if (x1 > i) {
            x1 = i;
          }
          if (y1 > j) {
            y1 = j;
          }
          if (x2 < i) {
            x2 = i;
          }
          if (y2 < j) {
            y2 = j;
          }
        }
      }
    }
    for (int i = x1; i <= x2; ++i) {
      for (int j = y1; j <= y2; ++j) {
        if (grid[i][j] == '.') {
          ++emptyCount;
        }
      }
    }
    return emptyCount;
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
