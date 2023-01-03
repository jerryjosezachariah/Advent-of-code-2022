package jerry.adventofcode.year2022.day15.two;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;
import jerry.adventofcode.Util.Interval;

/*
sample output: x=14,y=11,result=56000011
real output: x=3257428,y=2573243,result=13029714573243
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day15/in.txt";
    ArrayList<Sensor> sensors = new ArrayList<>();
    int ymax = 20; // ymax = 4_000_000 for real input
    ArrayList<String> lines = readFileAsStringArray(file);
    for (String line : lines) {
      String sensorInfo = line.split(":")[0];
      String beaconInfo = line.split(":")[1];
      int sx = Integer.parseInt(sensorInfo.split(",")[0].substring(12));
      int sy = Integer.parseInt(sensorInfo.split(",")[1].substring(3));
      int bx = Integer.parseInt(beaconInfo.split(",")[0].substring(24));
      int by = Integer.parseInt(beaconInfo.split(",")[1].substring(3));
      Sensor sensor = new Sensor(sx, sy, bx, by);
      sensors.add(sensor);
    }

    for (int y = 0; y <= ymax; ++y) {
      ArrayList<Interval> intervals = new ArrayList<>();
      for (Sensor s : sensors) {
        if (dist(s.sx, y, s.sx, s.sy) <= s.dist) {
          int remainingDist = s.dist - dist(s.sx, y, s.sx, s.sy);
          intervals.add(new Interval(s.sx - remainingDist, s.sx + remainingDist));
        }
      }

      ArrayList<Interval> mergedIntervals = Interval.merge(intervals);

      if (mergedIntervals.size() == 2) {
        long resultX = mergedIntervals.get(0).xb + 1;
        System.out.println(resultX + " " + y + " " + ((resultX * 4_000_000) + y));
        return;
      }
    }
  }

  private static int dist(int x1, int y1, int x2, int y2) {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }

  private static class Sensor {
    int sx, sy;
    int bx, by;
    int dist;

    public Sensor(int sx, int sy, int bx, int by) {
      this.sx = sx;
      this.sy = sy;
      this.bx = bx;
      this.by = by;
      this.dist = Math.abs(sx - bx) + Math.abs(sy - by);
    }
  }
}
