package com.ccicnet.gd.customer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DtoTest {
    public static void main(String[] args) throws IOException {
        List<String> inStrings = Files.readAllLines(Paths.get("D:\\customer.sql"), StandardCharsets.UTF_8);
        List<String> outStrings = new ArrayList<>(inStrings.size() + 200);
        int i = 1;
        for (String inString: inStrings) {
            outStrings.add(inString);
            if (i++ % 100 == 0) {
                outStrings.add("commit;");
            }
        }
        outStrings.add("commit;");
        Files.write(Paths.get("D:\\customer-new.sql"), outStrings, StandardCharsets.UTF_8);
    }
}
