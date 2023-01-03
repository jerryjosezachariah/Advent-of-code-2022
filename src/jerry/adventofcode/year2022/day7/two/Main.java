package jerry.adventofcode.year2022.day7.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.List;

/*
sample output: 24933642
real output: 3979145
 */
public class Main {

  static long neededSpace = 0;
  static long min = Long.MAX_VALUE;

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day7/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);

    Node root = new Node();
    root.name = "/";
    Node cur = root;

    for (String line : lines) {
      if (line.startsWith("$ cd ..")) {
        cur = cur.parent;
      } else if (line.startsWith("$ cd ")) {
        String dirName = line.substring(5);
        for (Node dir : cur.dirs) {
          if (dir.name.equals(dirName)) {
            cur = dir;
          }
        }
      } else if (!line.startsWith("$")) {
        if (line.startsWith("dir")) {
          String dirName = line.substring(4);
          Node dirNode = new Node();
          dirNode.name = dirName;
          dirNode.parent = cur;
          cur.dirs.add(dirNode);
        } else if (Character.isDigit(line.charAt(0))) {
          long fileSize = Long.parseLong(line.split(" ")[0]);
          String fileName = line.split(" ")[1];
          Node fileNode = new Node();
          fileNode.name = fileName;
          fileNode.size = fileSize;
          fileNode.parent = cur;
          cur.files.add(fileNode);
        }
      }
    }

    long rootSize = getSize(root);
    long unusedSpace = 70_000_000 - rootSize;
    neededSpace = 30_000_000 - unusedSpace;
    smallestMoreThan(root);
    System.out.println(min);
  }

  public static void smallestMoreThan(Node node) {
    if (node.size == 0) {
      return;
    }
    if ((node.size > neededSpace) && (min > node.size)) {
      min = node.size;
    }
    for (Node dir : node.dirs) {
      smallestMoreThan(dir);
    }
  }

  public static long getSize(Node node) {
    if (node.size > 0) {
      return node.size;
    }
    long size = 0;
    for (Node file : node.files) {
      size += file.size;
    }
    for (Node dir : node.dirs) {
      size += getSize(dir);
    }
    node.size = size;
    return size;
  }

  private static class Node {
    Node parent;
    String name;
    List<Node> dirs;
    List<Node> files;
    long size;

    public Node() {
      dirs = new ArrayList<>();
      files = new ArrayList<>();
    }
  }
}
