package com.richard;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

/**
 * Created by rnkoaa on 2/23/15.
 */
public class MainActivityTest {

    @Before
    public void setup() {

    }

    @Test
    public void testReadFile() throws IOException {
        String fileName = "";
        Path path = Paths.get("src/main/resources/success_response.json");

        String fileContent = new String(Files.readAllBytes(path));
        assertNotNull(fileContent);
    }
}
