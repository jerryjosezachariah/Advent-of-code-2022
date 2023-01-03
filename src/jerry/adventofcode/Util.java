package jerry.adventofcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Util {

  /*
  input:abc\ndef\nghi
  output:["abc","def","ghi"]
   */
  public static ArrayList<String> readFileAsStringArray(String file) throws Exception {
    ArrayList<String> in = new ArrayList<>();
    Scanner sc = new Scanner(new BufferedReader(new FileReader(new File(file))));
    while (sc.hasNextLine()) {
      in.add(sc.nextLine());
    }
    return in;
  }

  /*
  input:123
        456
        789
  output:[[1,2,3],[4,5,6],[7,8,9]]
   */
  public static int[][] readFileAsTwoDArray(String file) throws Exception {
    ArrayList<String> grid = new ArrayList<>();
    Scanner sc = new Scanner(new BufferedReader(new FileReader(new File(file))));
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      grid.add(line);
    }
    return stringToTwoDIntArray(grid);
  }

  public static void writeToFile(String file, ArrayList<String> out) throws Exception {
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    for (String line : out) {
      writer.write(line + "\n");
    }
    writer.close();
  }

  /*
  input:["123","456","789"]
  output:[[1,2,3],[4,5,6],[7,8,9]]
   */
  public static int[][] stringToTwoDIntArray(ArrayList<String> grid) {
    int[][] in = new int[grid.size()][grid.get(0).length()];
    for (int i = 0; i < grid.size(); ++i) {
      for (int j = 0; j < grid.get(0).length(); ++j) {
        in[i][j] = Character.getNumericValue(grid.get(i).charAt(j));
      }
    }
    return in;
  }

  /*
  input:["abc","def","ghi"]
  output:[['a','b','c'],['d','e','f'],['g','h','i']]
   */
  public static char[][] stringToTwoDCharArray(ArrayList<String> grid) {
    char[][] in = new char[grid.size()][grid.get(0).length()];
    for (int i = 0; i < grid.size(); ++i) {
      for (int j = 0; j < grid.get(0).length(); ++j) {
          in[i][j]=grid.get(i).charAt(j);
      }
    }
    return in;
  }

  /*
  input: "1A20B334"
  output: ["1","A","20","B","334"]
   */
  public static String[] splitStringToDigitsAndCharacters(String in) {
    return in.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
  }
  /*
  input: "1X2X3X4"
  output: [1,2,3,4]
   */
  public static ArrayList<Integer> stringToIntArray(String line, String separator)
      throws Exception {
    return Arrays.stream(line.split(separator))
        .map(Integer::parseInt)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /*
  input: "ABCD"
  output: {'A','B','C','D'}
   */
  public static HashSet<Character> stringToHashSet(String string) {
    HashSet<Character> output = new HashSet<>();
    string.chars().forEach(ch -> output.add((char) ch));
    return output;
  }

  /*
  input:"1011"
  output:11
   */
  public static int binToDec(String bin) {
    int dec = 0;
    for (int i = 0; i < bin.length(); ++i) {
      if (bin.charAt(i) == '1') {
        dec += Math.pow(2, bin.length() - 1 - i);
      }
    }
    return dec;
  }

  /*
  input:"AEF"
  output:"101011101111"
   */
  public static String hexToBin(String hex) {
    String bin = "";
    HashMap<Character, String> mapping = new HashMap<>();
    mapping.put('0', "0000");
    mapping.put('1', "0001");
    mapping.put('2', "0010");
    mapping.put('3', "0011");
    mapping.put('4', "0100");
    mapping.put('5', "0101");
    mapping.put('6', "0110");
    mapping.put('7', "0111");
    mapping.put('8', "1000");
    mapping.put('9', "1001");
    mapping.put('A', "1010");
    mapping.put('B', "1011");
    mapping.put('C', "1100");
    mapping.put('D', "1101");
    mapping.put('E', "1110");
    mapping.put('F', "1111");
    for (int i = 0; i < hex.length(); ++i) {
      bin += mapping.get(hex.charAt(i));
    }
    return bin;
  }

  // Display a 2D int array
  public static void displayGrid(int[][] grid, int M, int N) {
    for (int f = 0; f < M; ++f) {
      for (int g = 0; g < N; ++g) {
        System.out.print(grid[f][g] + " ");
      }
      System.out.println();
    }
  }

  // returns 4 neighbors of a cell in a 2D array
  // copy paste and then modify to use custom Node class
  public static ArrayList<Node> getFourNeighbors(int M, int N, int i, int j) {
    ArrayList<Node> neighbors = new ArrayList<>();
    if (i > 0) { // T
      neighbors.add(new Node(i - 1, j));
    }
    if (j > 0) { // L
      neighbors.add(new Node(i, j - 1));
    }
    if (j < N - 1) { // R
      neighbors.add(new Node(i, j + 1));
    }
    if (i < M - 1) { // B
      neighbors.add(new Node(i + 1, j));
    }
    return neighbors;
  }

  // returns 8 neighbors of a cell in a 2D array
  // copy paste and then modify to use custom Node class
  public static ArrayList<Node> getEightNeighbors(int M, int N, int i, int j) {
    ArrayList<Node> neighbors = new ArrayList<>();
    if (i > 0 && j > 0) { // LT
      neighbors.add(new Node(i - 1, j - 1));
    }
    if (i > 0) { // T
      neighbors.add(new Node(i - 1, j));
    }
    if (i > 0 && j < N - 1) { // RT
      neighbors.add(new Node(i - 1, j + 1));
    }
    if (j > 0) { // L
      neighbors.add(new Node(i, j - 1));
    }
    if (j < N - 1) { // R
      neighbors.add(new Node(i, j + 1));
    }
    if (i < M - 1 && j > 0) { // LB
      neighbors.add(new Node(i + 1, j - 1));
    }
    if (i < M - 1) { // B
      neighbors.add(new Node(i + 1, j));
    }
    if (i < M - 1 && j < N - 1) { // RB
      neighbors.add(new Node(i + 1, j + 1));
    }
    return neighbors;
  }

  public static char[][] padWithChar(char[][] grid, char padChar, int padLength) {
    int M=grid.length;
    int N=grid[0].length;
    char[][] newGrid = new char[M + (2 * padLength)][N + (2 * padLength)];
    for (int i = 0; i < M + (2 * padLength); ++i) {
      for (int j = 0; j < N + (2 * padLength); ++j) {
        newGrid[i][j] = padChar;
      }
    }
    for (int i = 0; i < M; ++i) {
      System.arraycopy(grid[i], 0, newGrid[i + padLength], padLength, N);
    }
    return newGrid;
  }

  public static <T> LinkedList<T> reverseLinkedList(LinkedList<T> list) {
    LinkedList<T> reverdedLinkedList = new LinkedList<>();
    for (int i = list.size() - 1; i >= 0; i--) {

      // Append the elements in reverse order
      reverdedLinkedList.add(list.get(i));
    }
    // Return the reversed kinked list
    return reverdedLinkedList;
  }

  public static void printCurrentTime() {
    System.out.println(java.time.Clock.systemUTC().instant());
  }

  public static class Node {
    public int x, y;

    Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  public static class Interval implements Comparable {
    public int xa, xb;

    public Interval(int xa, int xb) {
      this.xa = xa;
      this.xb = xb;
    }

    public static boolean overlaps(Interval i1, Interval i2) {
      return (i1.xa <= i2.xa && i2.xa <= i1.xb);
    }

    public static ArrayList<Interval> merge(ArrayList<Interval> intervals) {
      Collections.sort(intervals);
      boolean merged = true;
      while (merged && intervals.size() > 1) {
        merged = false;
        ArrayList<Interval> mergedIntervals = new ArrayList<>();
        for (int i = 0; i < intervals.size() - 1; ++i) {
          if (overlaps(intervals.get(i), intervals.get(i + 1))) {
            merged = true;
            mergedIntervals.add(
                new Interval(
                    Math.min(intervals.get(i).xa, intervals.get(i + 1).xa),
                    Math.max(intervals.get(i).xb, intervals.get(i + 1).xb)));
            for (int j = i + 2; j < intervals.size(); ++j) {
              mergedIntervals.add(intervals.get(j));
            }
            break;
          } else {
            mergedIntervals.add(intervals.get(i));
            if (i == intervals.size() - 2) {
              mergedIntervals.add(intervals.get(i + 1));
            }
          }
        }
        intervals = mergedIntervals;
      }

      return intervals;
    }

    @Override
    public int compareTo(Object o) {
      Interval i = (Interval) o;
      return this.xa - i.xa;
    }
  }
}
