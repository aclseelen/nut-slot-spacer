package eu.acls.nutslotspacer;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

  @org.junit.jupiter.api.BeforeEach
  void setUp() {
  }

  @Test
  void calculate6StringsEdgeOfOuterStrings() {

    Calculator calculator = new Calculator(6, 3.175, 3.175, 43.0, false);
    calculator.getStringDiameters().clear();
    calculator.addStringDiameter(10, 13, 17, 26, 36, 46);
    calculator.getStringDiameters().sort(Comparator.reverseOrder());

    assertEquals(6, calculator.getStringDiameters().size());

    Result result = calculator.calculate();

    List<Double> stringCenterLinesFromBassEdge = result.getStringCenterLinesFromBassEdge();
    List<Double> stringCenterLinesFromTrebleEdge = result.getStringCenterLinesFromTrebleEdge();

    assertEquals(6, stringCenterLinesFromBassEdge.size());
    assertEquals(6, stringCenterLinesFromTrebleEdge.size());

    assertEquals(6.57816, result.getStringSpacing());

    // string E
    assertEquals(3.7592, stringCenterLinesFromBassEdge.get(0));
    assertEquals(39.2408, stringCenterLinesFromTrebleEdge.get(0));

    // string A
    assertEquals(11.37876, stringCenterLinesFromBassEdge.get(1));
    assertEquals(31.62124, stringCenterLinesFromTrebleEdge.get(1));

    // string D
    assertEquals(18.74432, stringCenterLinesFromBassEdge.get(2));
    assertEquals(24.25568, stringCenterLinesFromTrebleEdge.get(2));

    // string G
    assertEquals(25.86858, stringCenterLinesFromBassEdge.get(3));
    assertEquals(17.13142, stringCenterLinesFromTrebleEdge.get(3));

    // string B
    assertEquals(32.82774, stringCenterLinesFromBassEdge.get(4));
    assertEquals(10.17226, stringCenterLinesFromTrebleEdge.get(4));

    // string e
    assertEquals(39.698, stringCenterLinesFromBassEdge.get(5));
    assertEquals(3.302, stringCenterLinesFromTrebleEdge.get(5));
  }

  @Test
  void calculate6StringsCenterOfOuterStrings() {

    Calculator calculator = new Calculator(6, 3.175, 3.175, 43.0, true);
    calculator.getStringDiameters().clear();
    calculator.addStringDiameter(10, 13, 17, 26, 36, 46);
    calculator.getStringDiameters().sort(Comparator.reverseOrder());

    assertEquals(6, calculator.getStringDiameters().size());

    Result result = calculator.calculate();

    List<Double> stringCenterLinesFromBassEdge = result.getStringCenterLinesFromBassEdge();
    List<Double> stringCenterLinesFromTrebleEdge = result.getStringCenterLinesFromTrebleEdge();

    assertEquals(6, stringCenterLinesFromBassEdge.size());
    assertEquals(6, stringCenterLinesFromTrebleEdge.size());

    assertEquals(6.7204, result.getStringSpacing());

    // string E
    assertEquals(3.175, stringCenterLinesFromBassEdge.get(0));
    assertEquals(39.825, stringCenterLinesFromTrebleEdge.get(0));

    // string A
    assertEquals(10.9368, stringCenterLinesFromBassEdge.get(1));
    assertEquals(32.0632, stringCenterLinesFromTrebleEdge.get(1));

    // string D
    assertEquals(18.4446, stringCenterLinesFromBassEdge.get(2));
    assertEquals(24.5554, stringCenterLinesFromTrebleEdge.get(2));

    // string G
    assertEquals(25.7111, stringCenterLinesFromBassEdge.get(3));
    assertEquals(17.2889, stringCenterLinesFromTrebleEdge.get(3));

    // string B
    assertEquals(32.8125, stringCenterLinesFromBassEdge.get(4));
    assertEquals(10.1875, stringCenterLinesFromTrebleEdge.get(4));

    // string e
    assertEquals(39.825, stringCenterLinesFromBassEdge.get(5));
    assertEquals(3.175, stringCenterLinesFromTrebleEdge.get(5));
  }

  @org.junit.jupiter.api.AfterEach
  void tearDown() {
  }
}