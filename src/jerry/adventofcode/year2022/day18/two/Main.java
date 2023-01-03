package jerry.adventofcode.year2022.day18.two;

import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.stringToIntArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/*
sample output: 58
real output: 2028
 */
public class Main {

  public static void main(String[] args) throws Exception {
    HashMap<String, Cube> cubesMap = new HashMap<>();
    HashMap<String, Cube> floodFilledMap;
    String file = "src/jerry/adventofcode/year2022/day18/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);

    for (String line : lines) {
      ArrayList<Integer> coords = stringToIntArray(line, ",");
      Cube cube = new Cube(coords.get(0), coords.get(1), coords.get(2));
      cubesMap.put(line, cube);
    }

    int xMin = cubesMap.values().stream().mapToInt(c -> c.x).min().getAsInt(); // 0
    int yMin = cubesMap.values().stream().mapToInt(c -> c.y).min().getAsInt(); // 0
    int zMin = cubesMap.values().stream().mapToInt(c -> c.z).min().getAsInt(); // 0
    int xMax = cubesMap.values().stream().mapToInt(c -> c.x).max().getAsInt(); // 19
    int yMax = cubesMap.values().stream().mapToInt(c -> c.y).max().getAsInt(); // 19
    int zMax = cubesMap.values().stream().mapToInt(c -> c.z).max().getAsInt(); // 19

    floodFilledMap =
        floodFill(xMin - 2, yMin - 2, zMin - 2, xMax + 2, yMax + 2, zMax + 2, cubesMap);

    cubesMap
        .values()
        .forEach(
            cube -> cube.neighbors += getNeighbors(cube.x, cube.y, cube.z, floodFilledMap).size());

    int externalSides = cubesMap.values().stream().mapToInt(c -> c.neighbors).sum();
    System.out.println(externalSides);
  }

  private static HashMap<String, Cube> floodFill(
      int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, HashMap<String, Cube> cubesMap) {
    LinkedList<Cube> queue = new LinkedList<>();
    HashMap<String, Cube> floodFilledcubes = new HashMap<>();
    Cube initialCube = new Cube(xMin, yMin, zMin);
    floodFilledcubes.put(key(xMin, yMin, zMin), initialCube);
    queue.add(initialCube);
    while (!queue.isEmpty()) {
      Cube cur = queue.pollFirst();
      Cube n = new Cube((cur.x - 1), cur.y, cur.z);
      if ((n.x >= xMin)
          && (!cubesMap.containsKey(key(n.x, n.y, n.z)))
          && (!floodFilledcubes.containsKey(key(n.x, n.y, n.z)))) {
        floodFilledcubes.put(key(n.x, n.y, n.z), n);
        queue.add(n);
      }
      n = new Cube((cur.x + 1), cur.y, cur.z);
      if ((n.x <= xMax)
          && (!cubesMap.containsKey(key(n.x, n.y, n.z)))
          && (!floodFilledcubes.containsKey(key(n.x, n.y, n.z)))) {
        floodFilledcubes.put(key(n.x, n.y, n.z), n);
        queue.add(n);
      }
      n = new Cube(cur.x, (cur.y - 1), cur.z);
      if ((n.y >= yMin)
          && (!cubesMap.containsKey(key(n.x, n.y, n.z)))
          && (!floodFilledcubes.containsKey(key(n.x, n.y, n.z)))) {
        floodFilledcubes.put(key(n.x, n.y, n.z), n);
        queue.add(n);
      }
      n = new Cube(cur.x, (cur.y + 1), cur.z);
      if ((n.y <= yMax)
          && (!cubesMap.containsKey(key(n.x, n.y, n.z)))
          && (!floodFilledcubes.containsKey(key(n.x, n.y, n.z)))) {
        floodFilledcubes.put(key(n.x, n.y, n.z), n);
        queue.add(n);
      }
      n = new Cube(cur.x, cur.y, (cur.z - 1));
      if ((n.z >= zMin)
          && (!cubesMap.containsKey(key(n.x, n.y, n.z)))
          && (!floodFilledcubes.containsKey(key(n.x, n.y, n.z)))) {
        floodFilledcubes.put(key(n.x, n.y, n.z), n);
        queue.add(n);
      }
      n = new Cube(cur.x, cur.y, (cur.z + 1));
      if ((n.z <= zMax)
          && (!cubesMap.containsKey(key(n.x, n.y, n.z)))
          && (!floodFilledcubes.containsKey(key(n.x, n.y, n.z)))) {
        floodFilledcubes.put(key(n.x, n.y, n.z), n);
        queue.add(n);
      }
    }
    return floodFilledcubes;
  }

  private static LinkedList<Cube> getNeighbors(int x, int y, int z, HashMap<String, Cube> map) {
    LinkedList<Cube> neighbors = new LinkedList<>();
    if (map.containsKey(key((x - 1), y, z))) {
      neighbors.add(map.get(key((x - 1), y, z)));
    }
    if (map.containsKey(key((x + 1), y, z))) {
      neighbors.add(map.get(key((x + 1), y, z)));
    }
    if (map.containsKey(key(x, (y - 1), z))) {
      neighbors.add(map.get(key(x, (y - 1), z)));
    }
    if (map.containsKey(key(x, (y + 1), z))) {
      neighbors.add(map.get(key(x, (y + 1), z)));
    }
    if (map.containsKey(key(x, y, (z - 1)))) {
      neighbors.add(map.get(key(x, y, (z - 1))));
    }
    if (map.containsKey(key(x, y, (z + 1)))) {
      neighbors.add(map.get(key(x, y, (z + 1))));
    }
    return neighbors;
  }

  private static String key(int x, int y, int z) {
    return x + "," + y + "," + z;
  }

  private static class Cube {
    public int x, y, z;
    public int neighbors;

    public Cube(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
      neighbors = 0;
    }
  }
}
