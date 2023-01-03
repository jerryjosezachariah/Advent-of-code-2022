package jerry.adventofcode.year2022.day16.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
/*
sample output: 1651
real output: 1724
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day16/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    HashMap<String, Node> nodesMap = new HashMap<>();
    int index = 0;
    for (String line : lines) {
      String nodeName = line.substring(6, 8);
      int nodeRate = Integer.parseInt(line.split(";")[0].substring(23));
      String tunnelsInfo = line.split(";")[1];
      String[] neighborNames =
          tunnelsInfo.substring(tunnelsInfo.indexOf("valve") + 6).replaceAll(" ", "").split(",");
      Node node = nodesMap.get(nodeName);
      if (node == null) {
        node = new Node(nodeName);
        nodesMap.put(nodeName, node);
      }
      if (node.rate < 0) {
        node.rate = nodeRate;
        if (node.rate > 0) {
          node.index = index;
          ++index;
        }
      }

      for (String neighborName : neighborNames) {
        Node neighbor = nodesMap.get(neighborName);
        if (neighbor == null) {
          neighbor = new Node(neighborName);
          nodesMap.put(neighborName, neighbor);
        }
        node.neighbors.add(neighbor);
      }
    }

    for (Node node : nodesMap.values()) {
      if (node.name.equals("AA") || node.rate > 0) {
        calculateDistToUsefulNodes(node);
      }
    }

    long maxGain = calculateMaxGain(30, nodesMap.get("AA"), 0);
    System.out.println(maxGain);
  }

  private static long calculateMaxGain(int timeLeft, Node currentNode, int bitMap) {
    if (timeLeft < 1) {
      return 0;
    }
    long maxGain = 0;
    long gainFromCurrentNode = (long) currentNode.rate * timeLeft;
    for (Entry<Node, Integer> n : currentNode.distanceToUsefulNodes.entrySet()) {
      int timeToSwitchOnNeighbor = n.getValue() + 1;
      if ((!isValveOn(n.getKey(), bitMap)) && (timeToSwitchOnNeighbor < timeLeft)) {
        long maxGainFromNeighbor =
            calculateMaxGain(
                timeLeft - timeToSwitchOnNeighbor, n.getKey(), setValveOn(n.getKey(), bitMap));
        if (maxGain < maxGainFromNeighbor) {
          maxGain = maxGainFromNeighbor;
        }
      }
    }
    return gainFromCurrentNode + maxGain;
  }

  private static boolean isValveOn(Node n, int bitMap) {
    if (n.index < 0) {
      throw new IllegalArgumentException(
          "Cannot check if valve is open if the valve has 0 flow rate");
    }
    int bit = 1;
    bit = bit << n.index;
    return bit == (bitMap & bit);
  }

  private static int setValveOn(Node n, int bitMap) {
    if (n.index < 0) {
      throw new IllegalArgumentException("Cannot open valve if the valve has 0 flow rate");
    }
    int bit = 1;
    bit = bit << n.index;
    return bitMap | bit;
  }

  private static void calculateDistToUsefulNodes(Node sourceNode) {
    HashSet<Node> visited = new HashSet<>();
    LinkedList<Node> queue = new LinkedList<>();
    visited.add(sourceNode);
    queue.add(sourceNode);
    int step = 1;
    while (!queue.isEmpty()) {
      LinkedList<Node> newQueue = new LinkedList<>();
      while (!queue.isEmpty()) {
        Node currentNode = queue.remove();
        for (Node neighbor : currentNode.neighbors) {
          if (visited.contains(neighbor)) {
            continue;
          }
          if (neighbor.rate > 0) {
            sourceNode.distanceToUsefulNodes.put(neighbor, step);
          }
          visited.add(neighbor);
          newQueue.add(neighbor);
        }
      }
      ++step;
      queue = newQueue;
    }
  }

  private static class Node {
    public String name;
    public int rate;
    public int index;
    public LinkedList<Node> neighbors;
    public HashMap<Node, Integer> distanceToUsefulNodes;

    public Node(String name) {
      this.name = name;
      this.rate = -1;
      this.index = -1;
      this.neighbors = new LinkedList<>();
      this.distanceToUsefulNodes = new HashMap<>();
    }
  }
}
