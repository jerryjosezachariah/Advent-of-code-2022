package jerry.adventofcode.year2022.day19.two;

import static jerry.adventofcode.Util.printCurrentTime;
import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;

/*
sample output: 3472 // 85 milliseconds
real output: 37367 // 30 milliseconds
 */
public class Main {
  private static int optimumCost = 0;

  public static void main(String[] args) throws Exception {
    printCurrentTime();
    String file = "src/jerry/adventofcode/year2022/day19/in.txt";
    ArrayList<String> lines = readFileAsStringArray(file);
    ArrayList<Blueprint> blueprints = new ArrayList<>();

    int result = 1;
    int count = 3;
    for (String line : lines) {
      if (count == 0) {
        break;
      }
      String blueprintInfo = line.split(":")[0];
      String botInfo = line.split(":")[1];
      int id = Integer.parseInt(blueprintInfo.substring(10));
      int oreBotCost = Integer.parseInt(botInfo.split("\\.")[0].substring(22, 23));
      int clayBotCost = Integer.parseInt(botInfo.split("\\.")[1].substring(23, 24));
      int obsidianBotOreCost = Integer.parseInt(botInfo.split("\\.")[2].substring(27, 28));
      int obsidianBotClayCost = Integer.parseInt(botInfo.split("\\.")[2].substring(37, 39).trim());
      int geodeBotOreCost = Integer.parseInt(botInfo.split("\\.")[3].substring(24, 25));
      int geodeBotObsidianCost = Integer.parseInt(botInfo.split("\\.")[3].substring(34, 36).trim());
      Blueprint blueprint =
          new Blueprint(
              id,
              oreBotCost,
              clayBotCost,
              obsidianBotOreCost,
              obsidianBotClayCost,
              geodeBotOreCost,
              geodeBotObsidianCost);
      blueprints.add(blueprint);
      optimize(blueprint, 1, 0, 0, 0, 0, 0, 0, 0, 32);
      result *= optimumCost;
      optimumCost = 0;
      --count;
    }
    System.out.println(result);
    printCurrentTime();
  }

  private static void optimize(
      Blueprint blueprint,
      int oreBotCount,
      int clayBotCount,
      int obsidianBotCount,
      int geodeBotCount,
      int oreCount,
      int clayCount,
      int obsidianCount,
      int geodeCount,
      int remainingTime) {
    if (remainingTime == 1) {
      int cost = geodeBotCount + geodeCount;
      if (optimumCost < cost) {
        optimumCost = cost;
      }
      return;
    }

    // create geode bot
    if (obsidianBotCount > 0) {
      int oreNeededToCreateGeodeBot = blueprint.geodeBotOreCost - oreCount;
      int timeNeededToCreateGeodeBotWithOre =
          Math.max(0, (int) Math.ceil((float) oreNeededToCreateGeodeBot / oreBotCount));
      int obsidianNeededToCreateGeodeBot = blueprint.geodeBotObsidianCost - obsidianCount;
      int timeNeededToCreateGeodeBotWithObsidian =
          Math.max(0, (int) Math.ceil((float) obsidianNeededToCreateGeodeBot / obsidianBotCount));
      int timeNeededToCreateGeodeBot =
          Math.max(timeNeededToCreateGeodeBotWithOre, timeNeededToCreateGeodeBotWithObsidian) + 1;
      if (timeNeededToCreateGeodeBot < remainingTime) {
        optimize(
            blueprint,
            oreBotCount,
            clayBotCount,
            obsidianBotCount,
            geodeBotCount + 1,
            oreCount + oreBotCount * timeNeededToCreateGeodeBot - blueprint.geodeBotOreCost,
            clayCount + clayBotCount * timeNeededToCreateGeodeBot,
            obsidianCount
                + obsidianBotCount * timeNeededToCreateGeodeBot
                - blueprint.geodeBotObsidianCost,
            geodeCount + geodeBotCount * timeNeededToCreateGeodeBot,
            remainingTime - timeNeededToCreateGeodeBot);
      }
    }

    // create obsidian bot
    if (remainingTime > 2
        && clayBotCount > 0
        && obsidianBotCount < blueprint.maxObsidianNeededPerRound
        && ((obsidianCount + obsidianBotCount * remainingTime)
            < (blueprint.maxObsidianNeededPerRound * remainingTime))) {

      int oreNeededToCreateObsidianBot = blueprint.obsidianBotOreCost - oreCount;
      int timeNeededToCreateObsidianBotWithOre =
          Math.max(0, (int) Math.ceil((float) oreNeededToCreateObsidianBot / oreBotCount));
      int clayNeededToCreateObsidianBot = blueprint.obsidianBotClayCost - clayCount;
      int timeNeededToCreateObsidianBotWithClay =
          Math.max(0, (int) Math.ceil((float) clayNeededToCreateObsidianBot / clayBotCount));
      int timeNeededToCreateObsidianBot =
          Math.max(timeNeededToCreateObsidianBotWithOre, timeNeededToCreateObsidianBotWithClay) + 1;
      if (timeNeededToCreateObsidianBot < remainingTime) {
        optimize(
            blueprint,
            oreBotCount,
            clayBotCount,
            obsidianBotCount + 1,
            geodeBotCount,
            oreCount + oreBotCount * timeNeededToCreateObsidianBot - blueprint.obsidianBotOreCost,
            clayCount
                + clayBotCount * timeNeededToCreateObsidianBot
                - blueprint.obsidianBotClayCost,
            obsidianCount + obsidianBotCount * timeNeededToCreateObsidianBot,
            geodeCount + geodeBotCount * timeNeededToCreateObsidianBot,
            remainingTime - timeNeededToCreateObsidianBot);
      }
    }

    // create clay bot
    if (remainingTime > 2
        && clayBotCount < blueprint.maxClayNeededPerRound
        && ((clayCount + clayBotCount * remainingTime)
            < (blueprint.maxClayNeededPerRound * remainingTime))) {

      int oreNeededToCreateClayBot = blueprint.clayBotCost - oreCount;
      int timeNeededToCreateClayBot =
          Math.max(0, (int) Math.ceil((float) oreNeededToCreateClayBot / oreBotCount)) + 1;
      if (timeNeededToCreateClayBot < remainingTime) {
        optimize(
            blueprint,
            oreBotCount,
            clayBotCount + 1,
            obsidianBotCount,
            geodeBotCount,
            oreCount + oreBotCount * timeNeededToCreateClayBot - blueprint.clayBotCost,
            clayCount + clayBotCount * timeNeededToCreateClayBot,
            obsidianCount + obsidianBotCount * timeNeededToCreateClayBot,
            geodeCount + geodeBotCount * timeNeededToCreateClayBot,
            remainingTime - timeNeededToCreateClayBot);
      }
    }

    // create ore bot
    if (remainingTime > 2
        && oreBotCount < blueprint.maxOreNeededPerRound
        && ((oreCount + oreBotCount * remainingTime)
            < (blueprint.maxOreNeededPerRound * remainingTime))) {

      int oreNeededToCreateOreBot = blueprint.oreBotCost - oreCount;
      int timeNeededToCreateOreBot =
          Math.max(0, (int) Math.ceil((float) oreNeededToCreateOreBot / oreBotCount)) + 1;
      if (timeNeededToCreateOreBot < remainingTime) {
        optimize(
            blueprint,
            oreBotCount + 1,
            clayBotCount,
            obsidianBotCount,
            geodeBotCount,
            oreCount + oreBotCount * timeNeededToCreateOreBot - blueprint.oreBotCost,
            clayCount + clayBotCount * timeNeededToCreateOreBot,
            obsidianCount + obsidianBotCount * timeNeededToCreateOreBot,
            geodeCount + geodeBotCount * timeNeededToCreateOreBot,
            remainingTime - timeNeededToCreateOreBot);
      }
    }

    // if not enough time to create any bot
    int cost = geodeBotCount * remainingTime + geodeCount;
    if (optimumCost < cost) {
      optimumCost = cost;
    }
  }

  private static class Blueprint {
    public int id;
    public int oreBotCost;
    public int clayBotCost;
    public int obsidianBotOreCost;
    public int obsidianBotClayCost;
    public int geodeBotOreCost;
    public int geodeBotObsidianCost;
    public int maxOreNeededPerRound;
    public int maxClayNeededPerRound;
    public int maxObsidianNeededPerRound;

    public Blueprint(
        int id,
        int oreBotCost,
        int clayBotCost,
        int obsidianBotOreCost,
        int obsidianBotClayCost,
        int geodeBotOreCost,
        int geodeBotObsidianCost) {
      this.id = id;
      this.oreBotCost = oreBotCost;
      this.clayBotCost = clayBotCost;
      this.obsidianBotOreCost = obsidianBotOreCost;
      this.obsidianBotClayCost = obsidianBotClayCost;
      this.geodeBotOreCost = geodeBotOreCost;
      this.geodeBotObsidianCost = geodeBotObsidianCost;

      this.maxOreNeededPerRound =
          Math.max(
              Math.max(this.oreBotCost, this.clayBotCost),
              Math.max(this.obsidianBotOreCost, this.geodeBotOreCost));
      this.maxClayNeededPerRound = this.obsidianBotClayCost;
      this.maxObsidianNeededPerRound = this.geodeBotObsidianCost;
    }
  }
}
