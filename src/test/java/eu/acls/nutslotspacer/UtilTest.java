package eu.acls.nutslotspacer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilTest {

  @Test
  void inchesToMillimeters() {

    assertEquals(25.4, Util.inchesToMillimeters(1));
  }

  @Test
  void millimetersToInches() {
    assertEquals(1, Util.millimetersToInches(25.4));
  }

  @Test
  void thousandsToMillimeters() {
    assertEquals(0.254, Util.thousandsToMillimeters(10));
  }

  @Test
  void truncateDecimal() {
    assertEquals(25.4567, Util.truncateDecimal(25.4567000000001, 5));
  }
}