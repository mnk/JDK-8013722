package org.example;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String classResourceName = Main.class.getName().replace(".", "/") + ".class";
        try (InputStream in = Main.class.getClassLoader().getResourceAsStream(classResourceName)) {
            if (in != null) {
                in.readAllBytes();
            }
        }
    }
}
