package eu.acls.nutslotspacer;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

  private int nStrings;

  // In millimeters
  private double edgeSpacingBass;
  private double edgeSpacingTreble;
  private double nutWidth;
  private boolean useStringCenterToFingerboardEdge; // default use string edge instead of center
  private boolean useInchesAsInput; // default use millimeters instead of inches

  // In 1000s of an inch
  private List<Integer> stringDiameters = new ArrayList<>();

  public Calculator() {
  }

  public Calculator(double edgeSpacingBass, double edgeSpacingTreble, double nutWidth, boolean useStringCenterToFingerboardEdge) {
    this.edgeSpacingBass = edgeSpacingBass;
    this.edgeSpacingTreble = edgeSpacingTreble;
    this.nutWidth = nutWidth;
    this.useStringCenterToFingerboardEdge = useStringCenterToFingerboardEdge;
  }

  public int getnStrings() {
    return nStrings;
  }

  public void setnStrings(int nStrings) {
    this.nStrings = nStrings;
  }

  public double getEdgeSpacingBass() {
    return edgeSpacingBass;
  }

  public void setEdgeSpacingBass(double edgeSpacingBass) {
    this.edgeSpacingBass = edgeSpacingBass;
  }

  public double getEdgeSpacingTreble() {
    return edgeSpacingTreble;
  }

  public void setEdgeSpacingTreble(double edgeSpacingTreble) {
    this.edgeSpacingTreble = edgeSpacingTreble;
  }

  public double getNutWidth() {
    return nutWidth;
  }

  public void setNutWidth(double nutWidth) {
    this.nutWidth = nutWidth;
  }

  public boolean isUseStringCenterToFingerboardEdge() {
    return useStringCenterToFingerboardEdge;
  }

  public void setUseStringCenterToFingerboardEdge(boolean useStringCenterToFingerboardEdge) {
    this.useStringCenterToFingerboardEdge = useStringCenterToFingerboardEdge;
  }

  public boolean isUseInchesAsInput() {
    return useInchesAsInput;
  }

  public void setUseInchesAsInput(boolean useInchesAsInput) {
    this.useInchesAsInput = useInchesAsInput;
  }

  public List<Integer> getStringDiameters() {
    return stringDiameters;
  }

  public void setStringDiameters(List<Integer> stringDiameters) {
    this.stringDiameters = stringDiameters;
  }

  public void addStringDiameter(int... diameters) {
    for (int diameter : diameters) {
      stringDiameters.add(diameter);
    }
  }

  public Result calculate() {

    Result result = new Result();

    checkInchesAsInput();

    nStrings = stringDiameters.size();
    double stringArea = nutWidth - edgeSpacingBass - edgeSpacingTreble;
    double totalStringSpacing = subtractStringDiameters(stringArea);

    double stringSpacing = 0.0;
    if (getnStrings() > 1) {
      stringSpacing = totalStringSpacing / (getnStrings() - 1);
      stringSpacing = Util.truncateDecimal(stringSpacing, 10);
    }

    result.setStringSpacing(stringSpacing);
    calculateStringCenterLinePositions(result);

    return result;
  }

  private void checkInchesAsInput() {

    if (isUseInchesAsInput()) {
      nutWidth = Util.inchesToMillimeters(nutWidth);
      edgeSpacingBass = Util.inchesToMillimeters(edgeSpacingBass);
      edgeSpacingTreble = Util.inchesToMillimeters(edgeSpacingTreble);
    }
  }

  private void calculateStringCenterLinePositions(Result result) {

    double positionFromBassEdge = edgeSpacingBass;
    boolean isFirstOuterString = true;
    for (int stringDiameter : getStringDiameters()) {

      double stringDiameterMm = Util.thousandsToMillimeters(stringDiameter);

      if (!isFirstOuterString || !this.isUseStringCenterToFingerboardEdge()) {
        positionFromBassEdge += 0.5 * stringDiameterMm;
      }

      result.addStringCenterLineFromBassEdge(Util.truncateDecimal(positionFromBassEdge, 8));
      result.addStringCenterLineFromTrebleEdge(Util.truncateDecimal(nutWidth - positionFromBassEdge, 8));

      positionFromBassEdge += 0.5 * stringDiameterMm;
      positionFromBassEdge += result.getStringSpacing();
      isFirstOuterString = false;
    }
  }

  private double subtractStringDiameters(double stringArea) {

    int index = 0;
    for (int stringDiameter : getStringDiameters()) {
      double stringDiameterMm = Util.thousandsToMillimeters(stringDiameter);

      if (isOuterString(index, nStrings) && isUseStringCenterToFingerboardEdge()) {
        stringDiameterMm *= 0.5;
      }

      stringArea -= stringDiameterMm;
      index++;
    }

    return stringArea;
  }

  private boolean isOuterString(int index, int nStrings) {
    return index == 0 || index == nStrings - 1;
  }
}
