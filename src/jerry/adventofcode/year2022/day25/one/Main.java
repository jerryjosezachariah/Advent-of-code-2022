package jerry.adventofcode.year2022.day25.one;

import static jerry.adventofcode.Util.readFileAsStringArray;

import java.util.ArrayList;

/*
sample output: 2=-1=0
real output: 2=1-=02-21===-21=200
 */
public class Main {

  public static void main(String[] args) throws Exception {
    String file = "src/jerry/adventofcode/year2022/day25/in.txt";

    ArrayList<String> lines = readFileAsStringArray(file);

    long sum = 0;
    for (String line : lines) {
      sum += snafuToLong(line);
    }

    System.out.println(longToSnafu(sum));
  }

  private static String longToSnafu(long sum) {
    if (sum == 0) {
      return "";
    }
    int rem = (int) (sum % 5);
    switch (rem) {
      case 0:
        return longToSnafu(sum / 5) + "0";
      case 1:
        return longToSnafu(sum / 5) + "1";
      case 2:
        return longToSnafu(sum / 5) + "2";
      case 3:
        return longToSnafu((sum + 2) / 5) + "=";
      case 4:
        return longToSnafu((sum + 1) / 5) + "-";
    }
    return "";
  }

  private static long snafuToLong(String snafu) {
    long sum = 0;
    for (int i = 0; i < snafu.length(); ++i) {
      sum += Math.pow(5, (snafu.length() - 1 - i)) * snafuDigitValue(snafu.charAt(i));
    }
    return sum;
  }

  private static long snafuDigitValue(char ch) {
    switch (ch) {
      case '2':
        return 2;
      case '1':
        return 1;
      case '0':
        return 0;
      case '-':
        return -1;
      case '=':
        return -2;
    }
    return -1;
  }
}
