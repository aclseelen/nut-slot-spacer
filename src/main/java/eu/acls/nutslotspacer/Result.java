package eu.acls.nutslotspacer;

import java.util.ArrayList;
import java.util.List;

public class Result {

  // In millimeters
  private List<Double> stringCenterLinesFromBassEdge = new ArrayList<>();
  private List<Double> stringCenterLinesFromTrebleEdge = new ArrayList<>();
  private double stringSpacing;

  public List<Double> getStringCenterLinesFromBassEdge() {
    return stringCenterLinesFromBassEdge;
  }

  public void setStringCenterLinesFromBassEdge(List<Double> stringCenterLinesFromBassEdge) {
    this.stringCenterLinesFromBassEdge = stringCenterLinesFromBassEdge;
  }

  public List<Double> getStringCenterLinesFromTrebleEdge() {
    return stringCenterLinesFromTrebleEdge;
  }

  public void setStringCenterLinesFromTrebleEdge(List<Double> stringCenterLinesFromTrebleEdge) {
    this.stringCenterLinesFromTrebleEdge = stringCenterLinesFromTrebleEdge;
  }

  public double getStringSpacing() {
    return stringSpacing;
  }

  public void setStringSpacing(double stringSpacing) {
    this.stringSpacing = stringSpacing;
  }

  public void addStringCenterLineFromBassEdge(double stringCenterLineFromBassEdge) {
    this.stringCenterLinesFromBassEdge.add(stringCenterLineFromBassEdge);
  }

  public void addStringCenterLineFromTrebleEdge(double stringCenterLineFromTrebleEdge) {
    this.stringCenterLinesFromTrebleEdge.add(stringCenterLineFromTrebleEdge);
  }
}
