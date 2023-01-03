package jerry.adventofcode.year2022.day22.two;

import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.splitStringToDigitsAndCharacters;
import static jerry.adventofcode.Util.stringToTwoDCharArray;

import java.util.ArrayList;
import java.util.stream.Collectors;

/*
sample output: code is specific for real input only
real output: real output: 15426 // final position: 15 106 <
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
    int nextRow = row, nextCol = col;
    switch (facing) {
      case '>':
        nextCol = (col + 1) % N;
        break;
      case 'v':
        nextRow = (row + 1) % M;
        break;
      case '<':
        nextCol = (col - 1) % N;
        if (nextCol < 0) {
          nextCol += N;
        }
        break;
      case '^':
        nextRow = (row - 1) % M;
        if (nextRow < 0) {
          nextRow += M;
        }
    }

    if (grid[nextRow][nextCol] == '#') {
    } else if (grid[nextRow][nextCol] == 'o') {
      CubePoint currentCubePoint = CubePoint.coordsToCubePoint(row, col, facing);
      CubePoint nextCubePoint = currentCubePoint.getNextCubePoint();
      if (grid[nextCubePoint.realRow][nextCubePoint.realCol] == '#') {
        return;
      }
      col = nextCubePoint.realCol;
      row = nextCubePoint.realRow;
      facing = nextCubePoint.realFacing;
      move(steps - 1, grid);
    } else {
      row = nextRow;
      col = nextCol;
      move(steps - 1, grid);
    }
  }

  private static class CubePoint {
    public char cubeFace;
    int cubeRow;
    int cubeCol;
    char realFacing;
    int realRow;
    int realCol;

    public static CubePoint coordsToCubePoint(int row, int col, char facing) {
      CubePoint cubePoint = new CubePoint();
      cubePoint.realRow = row;
      cubePoint.realCol = col;
      cubePoint.realFacing = facing;
      int colDiv = col / 50;
      int rowDiv = row / 50;
      cubePoint.cubeRow = row % 50;
      cubePoint.cubeCol = col % 50;
      if (colDiv == 1 && rowDiv == 0) {
        cubePoint.cubeFace = 'A';
      } else if (colDiv == 2 && rowDiv == 0) {
        cubePoint.cubeFace = 'B';
      } else if (colDiv == 1 && rowDiv == 1) {
        cubePoint.cubeFace = 'C';
      } else if (colDiv == 0 && rowDiv == 2) {
        cubePoint.cubeFace = 'E';
      } else if (colDiv == 1 && rowDiv == 2) {
        cubePoint.cubeFace = 'D';
      } else if (colDiv == 0 && rowDiv == 3) {
        cubePoint.cubeFace = 'F';
      } else {
        throw new IllegalArgumentException();
      }
      return cubePoint;
    }

    public CubePoint getNextCubePoint() {
      CubePoint nextCubePoint = new CubePoint();
      switch (this.cubeFace) {
        case 'A':
          if (this.realFacing == '^') {
            nextCubePoint.cubeFace = 'F';
            nextCubePoint.cubeRow = this.cubeCol;
            nextCubePoint.realFacing = '>';
          } else if (this.realFacing == '<') {
            nextCubePoint.cubeFace = 'E';
            nextCubePoint.cubeRow = 49 - this.cubeRow;
            nextCubePoint.realFacing = '>';
          }
          break;
        case 'B':
          if (this.realFacing == '^') {
            nextCubePoint.cubeFace = 'F';
            nextCubePoint.cubeCol = this.cubeCol;
            nextCubePoint.realFacing = '^';
          } else if (this.realFacing == '>') {
            nextCubePoint.cubeFace = 'D';
            nextCubePoint.cubeRow = 49 - this.cubeCol;
            nextCubePoint.realFacing = '<';
          } else if (this.realFacing == 'v') {
            nextCubePoint.cubeFace = 'C';
            nextCubePoint.cubeRow = this.cubeCol;
            nextCubePoint.realFacing = '<';
          }
          break;
        case 'C':
          if (this.realFacing == '>') {
            nextCubePoint.cubeFace = 'B';
            nextCubePoint.cubeCol = this.cubeRow;
            nextCubePoint.realFacing = '^';
          } else if (this.realFacing == '<') { // left
            nextCubePoint.cubeFace = 'E';
            nextCubePoint.cubeCol = this.cubeRow;
            nextCubePoint.realFacing = 'v';
          }
          break;
        case 'D':
          if (this.realFacing == '>') {
            nextCubePoint.cubeFace = 'B';
            nextCubePoint.cubeRow = 49 - this.cubeRow;
            nextCubePoint.realFacing = '<';
          } else if (this.realFacing == 'v') {
            nextCubePoint.cubeFace = 'F';
            nextCubePoint.cubeRow = this.cubeCol;
            nextCubePoint.realFacing = '<';
          }
          break;
        case 'E':
          if (this.realFacing == '^') {
            nextCubePoint.cubeFace = 'C';
            nextCubePoint.cubeRow = this.cubeCol;
            nextCubePoint.realFacing = '>';
          } else if (this.realFacing == '<') {
            nextCubePoint.cubeFace = 'A';
            nextCubePoint.cubeRow = 49 - this.cubeRow;
            nextCubePoint.realFacing = '>';
          }
          break;
        case 'F':
          if (this.realFacing == '<') {
            nextCubePoint.cubeFace = 'A';
            nextCubePoint.cubeCol = this.cubeRow;
            nextCubePoint.realFacing = 'v';
          } else if (this.realFacing == 'v') {
            nextCubePoint.cubeFace = 'B';
            nextCubePoint.cubeCol = this.cubeCol;
            nextCubePoint.realFacing = 'v';
          } else if (this.realFacing == '>') {
            nextCubePoint.cubeFace = 'D';
            nextCubePoint.cubeCol = this.cubeRow;
            nextCubePoint.realFacing = '^';
          }
      }

      switch (nextCubePoint.realFacing) {
        case '>':
          nextCubePoint.cubeCol = 0;
          break;
        case 'v':
          nextCubePoint.cubeRow = 0;
          break;
        case '<':
          nextCubePoint.cubeCol = 49;
          break;
        case '^':
          nextCubePoint.cubeRow = 49;
      }

      nextCubePoint.populateRealCoords();
      return nextCubePoint;
    }

    private void populateRealCoords() {
      switch (this.cubeFace) {
        case 'A':
          this.realRow = this.cubeRow;
          this.realCol = 50 + this.cubeCol;
          break;
        case 'B':
          this.realRow = this.cubeRow;
          this.realCol = 100 + this.cubeCol;
          break;
        case 'C':
          this.realRow = 50 + this.cubeRow;
          this.realCol = 50 + this.cubeCol;
          break;
        case 'D':
          this.realRow = 100 + this.cubeRow;
          this.realCol = 50 + this.cubeCol;
          break;
        case 'E':
          this.realRow = 100 + this.cubeRow;
          this.realCol = this.cubeCol;
          break;
        case 'F':
          this.realRow = 150 + this.cubeRow;
          this.realCol = this.cubeCol;
          break;
        default:
          throw new IllegalArgumentException();
      }
    }
  }
}
