package jerry.adventofcode.year2022.day18.one;

import static jerry.adventofcode.Util.readFileAsStringArray;
import static jerry.adventofcode.Util.stringToIntArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/*
sample output: 64
real output: 3550
 */
public class Main {

  public static void main(String[] args) throws Exception {
    HashMap<String, Cube> cubesMap = new HashMap<>();
    String file = "src/jerry/adventofcode/year2022/day18/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);

    for (String line : lines) {
      ArrayList<Integer> coords = stringToIntArray(line, ",");
      Cube cube = new Cube(coords.get(0), coords.get(1), coords.get(2));
      cubesMap.put(line, cube);
    }

    cubesMap
        .values()
        .forEach(cube -> cube.neighbors += getNeighbors(cube.x, cube.y, cube.z, cubesMap).size());

    int exposedSides = cubesMap.values().stream().mapToInt(c -> (6 - c.neighbors)).sum();
    System.out.println(exposedSides);
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
