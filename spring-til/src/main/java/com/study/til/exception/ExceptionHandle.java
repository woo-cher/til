package com.study.til.exception;

import java.io.FileWriter;
import java.io.IOException;

public class ExceptionHandle {

    /**
     * @see CheckedException#writeTryCatch()
     */
    public static void normalTryCatch() {}

    public static void exceptionThrow() throws IOException {
        FileWriter f = new FileWriter("myFile.txt");
        // do something
    }

    public static void exceptionConvert() {
        try {
            FileWriter f = new FileWriter("myFile.txt");
        } catch (Exception e) {
            // throw new someException();
        }
    }

    /**
     *  Using AutoClosable
     *  @see java.lang.AutoCloseable
     */
    public static void tryWithResource() {
        try (FileWriter f = new FileWriter("myFile.txt")) {
            f.write("write file!");
        } catch (IOException e) {
            // do something
        }
    }
}
