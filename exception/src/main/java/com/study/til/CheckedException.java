package com.study.til;

import java.io.FileWriter;
import java.io.IOException;

public class CheckedException {
  public static void writeTryCatch() {
    FileWriter f = null;
    try {
      f = new FileWriter("myFile.txt");
      f.write("write file!");
    } catch (IOException e) {
      // do something
    } finally {
      try {
        if (f != null) {
          f.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
