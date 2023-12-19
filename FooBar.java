package org.rsm;

import java.util.ArrayList;

public class FooBar {

  public void fooBar(String stringOfInts) {
    isNullOrEmpty(stringOfInts);
    var list = new ArrayList<String>();
    var arr = stringOfInts.split(",");
    for (String s : arr) {
      // check if each item is null or empty, trim white space
      var val = isNullOrEmpty(s);
      // check if it is divisible by 3 or 5 or both
      val = isDivisible(val);
      // check if it is a copy
      if (list.contains(val)) {
        val += "-copy";
      }
      list.add(val);
    }
    System.out.print(String.join(",", list));
  }

  private static String isNullOrEmpty(String val) {
    if (val == null || val.trim().equals("")) {
      throw new IllegalArgumentException("FooBar method was called with null or empty argument.");
    }
    return val.trim();
  }

  private static String isDivisible(String origVal) {
    var i = Integer.parseInt(origVal);
    var mod3 = i % 3;
    var mod5 = i % 5;

    if (mod3 != 0 && mod5 != 0) {
      return origVal;
    }

    var result = "";
    if (mod3 == 0)
      result += "foo";
    if (mod5 == 0)
      result += "bar";
    return result;
  }
}
