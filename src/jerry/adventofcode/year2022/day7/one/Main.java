package jerry.adventofcode.year2022.day7.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.List;

/*
sample output: 95437
real output: 1908462
 */
public class Main {

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

    getSize(root);
    long totalSize = getTotalSize(root);
    System.out.println(totalSize);
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

  public static long getTotalSize(Node node) {
    long size = 0;
    if (node.size <= 100_000) {
      size += node.size;
    }
    for (Node dir : node.dirs) {
      size += getTotalSize(dir);
    }
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
