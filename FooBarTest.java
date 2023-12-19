package org.rsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FooBarTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final FooBar fb = new FooBar();

  @BeforeEach
  public void setup() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void teardown() {
    System.setOut(originalOut);
  }

  @ParameterizedTest
  @CsvSource({
      "0, foobar",
      "3, foo",
      "5, bar",
      "7, 7",
      "15, foobar",
      "'1,2,3,4,5,6,45', '1,2,foo,4,bar,foo-copy,foobar'",
      "'1,2,3,1,2,3,1,1', '1,2,foo,1-copy,2-copy,foo-copy,1-copy,1-copy'",
      "'1,1,3,3,5,5,45,45', '1,1-copy,foo,foo-copy,bar,bar-copy,foobar,foobar-copy'",
      "'1,1,3,3,5,5,45,2147483647,2147483647', '1,1-copy,foo,foo-copy,bar,bar-copy,foobar,2147483647,2147483647-copy'",
      "' 1,2 ,3,4,5,6,45', '1,2,foo,4,bar,foo-copy,foobar'",
  })
  void fooBarTest(String input, String expected) {
    fb.fooBar(input);
    assertEquals(expected, outContent.toString());
  }

  @Test
  void fooBarTest_NullValue() {
    var exception = assertThrows(
        IllegalArgumentException.class, () -> {
          fb.fooBar(null);
        });
    assertEquals("FooBar method was called with null or empty argument.", exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "' '",      
      "'1,2,,4'",
      "'1,2, ,4'",
      "'1,2,          ,4'"
  })
  void fooBarTest_EmptyItem(String input) {
    var exception = assertThrows(
        IllegalArgumentException.class, () -> {
          fb.fooBar(input);
        });
    assertEquals("FooBar method was called with null or empty argument.", exception.getMessage());
  }

  @Test
  void fooBarTest_NonInteger() {
    var exception = assertThrows(
        NumberFormatException.class, () -> {
          fb.fooBar("1,2,bad item,4");
        });
    assertEquals("For input string: \"bad item\"", exception.getMessage());
  }

  @Test
  void fooBarTest_TooBigInteger() {
    var exception = assertThrows(
        NumberFormatException.class, () -> {
          fb.fooBar("1,2,2147483648,4");
        });
    assertEquals("For input string: \"2147483648\"", exception.getMessage());
  }
}
