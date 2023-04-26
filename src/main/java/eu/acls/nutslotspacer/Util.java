package eu.acls.nutslotspacer;

public class Util {

  private Util() {
  }

  public static double inchesToMillimeters(double inch) {
    return inch * 25.4;
  }

  public static double millimetersToInches(double mm) {
    return mm / 25.4;
  }

  public static double thousandsToMillimeters(int thousandsOfAnInch) {
    return truncateDecimal(inchesToMillimeters(thousandsOfAnInch / 1000.0), 8);
  }

  public static double truncateDecimal(double input, int nDecimals) {
    double scale = Math.pow(10, nDecimals);
    return Math.round(input * scale) / scale;
  }
}
