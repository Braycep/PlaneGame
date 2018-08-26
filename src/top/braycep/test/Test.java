package top.braycep.test;

import java.io.File;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) {
        try {
            File images = new File(Test.class.getResource("\\").toURI());
            System.out.println(images.getAbsolutePath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
