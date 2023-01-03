package jerry.adventofcode.year2022.day22.one;

import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.splitStringToDigitsAndCharacters;
import static jerry.adventofcode.Util.stringToTwoDCharArray;

import java.util.ArrayList;
import java.util.stream.Collectors;

/*
sample output: code is specific for real input only
real output: 27436 // final position: 27 109 >
 */
public class Main {

  private static int M, N, row = 0, col = 0;
  private static char facing = '>';

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day22/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    ArrayList<String> gridStrings = new ArrayList<>();
    ArrayList<String> commandStrings = new ArrayList<>();
    boolean isFirstPartCompleted = false;
    for (String line : lines) {
      if (line.isEmpty()) {
        isFirstPartCompleted = true;
        continue;
      }
      if (!isFirstPartCompleted) {
        gridStrings.add(line);
      } else {
        commandStrings.add(line);
      }
    }
    M = gridStrings.size();
    N = gridStrings.get(0).length();
    ArrayList<String> tempGridStrings = new ArrayList<>();
    for (String gridString : gridStrings) {
      String tempGridString =
          gridString
              .chars()
              .mapToObj(ch -> (char) ch)
              .map(ch -> (ch != ' ') ? (ch.toString()) : "o")
              .collect(Collectors.joining(""));
      tempGridString = String.format("%-" + N + "s", tempGridString).replaceAll(" ", "o");
      tempGridStrings.add(tempGridString);
    }
    gridStrings = tempGridStrings;
    String[] commands = splitStringToDigitsAndCharacters(commandStrings.get(0));

    char[][] grid = stringToTwoDCharArray(gridStrings);

    // Find top most left most empty point as starting point
    for (int j = 0; j < N; ++j) {
      if (grid[row][j] == '.') {
        col = j;
        break;
      }
    }

    process(commands, grid);

    int facingVal = -1;
    switch (facing) {
      case '>':
        facingVal = 0;
        break;
      case 'v':
        facingVal = 1;
        break;
      case '<':
        facingVal = 2;
        break;
      case '^':
        facingVal = 3;
    }
    System.out.println((row + 1) + " " + (col + 1) + " " + facing);
    System.out.println((1000 * (row + 1)) + (4 * (col + 1)) + (facingVal));
  }

  private static void process(String[] commands, char[][] grid) {
    for (String command : commands) {
      if (command.equals("R")) {
        switch (facing) {
          case '>':
            facing = 'v';
            break;
          case 'v':
            facing = '<';
            break;
          case '<':
            facing = '^';
            break;
          case '^':
            facing = '>';
            break;
        }
      } else if (command.equals("L")) {
        switch (facing) {
          case '>':
            facing = '^';
            break;
          case 'v':
            facing = '>';
            break;
          case '<':
            facing = 'v';
            break;
          case '^':
            facing = '<';
            break;
        }
      } else {
        int steps = Integer.parseInt(command);
        move(steps, grid);
      }
    }
  }

  private static void move(int steps, char[][] grid) {
    if (steps == 0) {
      return;
    }
    int nextRow, nextCol;
    switch (facing) {
      case '>':
        nextCol = (col + 1) % N;
        if (grid[row][nextCol] == '#') {
          return;
        } else if (grid[row][nextCol] == 'o') {
          while (grid[row][nextCol] == 'o') {
            nextCol = (nextCol + 1) % N;
          }
          if (grid[row][nextCol] == '#') {
            return;
          }
          col = nextCol;
          move(steps - 1, grid);
        } else {
          col = nextCol;
          move(steps - 1, grid);
        }
        break;
      case 'v':
        nextRow = (row + 1) % M;
        if (grid[nextRow][col] == '#') {
          return;
        } else if (grid[nextRow][col] == 'o') {
          while (grid[nextRow][col] == 'o') {
            nextRow = (nextRow + 1) % M;
          }
          if (grid[nextRow][col] == '#') {
            return;
          }
          row = nextRow;
          move(steps - 1, grid);
        } else {
          row = nextRow;
          move(steps - 1, grid);
        }
        break;
      case '<':
        nextCol = (col - 1) % N;
        if (nextCol < 0) {
          nextCol += N;
        }
        if (grid[row][nextCol] == '#') {
          return;
        } else if (grid[row][nextCol] == 'o') {
          while (grid[row][nextCol] == 'o') {
            nextCol = (nextCol - 1) % N;
            if (nextCol < 0) {
              nextCol += N;
            }
          }
          if (grid[row][nextCol] == '#') {
            return;
          }
          col = nextCol;
          move(steps - 1, grid);
        } else {
          col = nextCol;
          move(steps - 1, grid);
        }
        break;
      case '^':
        nextRow = (row - 1) % M;
        if (nextRow < 0) {
          nextRow += M;
        }
        if (grid[nextRow][col] == '#') {
        } else if (grid[nextRow][col] == 'o') {
          while (grid[nextRow][col] == 'o') {
            nextRow = (nextRow - 1) % M;
            if (nextRow < 0) {
              nextRow += M;
            }
          }
          if (grid[nextRow][col] == '#') {
            return;
          }
          row = nextRow;
          move(steps - 1, grid);
        } else {
          row = nextRow;
          move(steps - 1, grid);
        }
    }
  }
}
