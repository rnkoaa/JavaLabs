package com.richard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.richard.model.User;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by rnkoaa on 2/23/15.
 */
public class MainActivityTest {

    Gson gson;

    @Before
    public void setup() {
        gson = GsonFactory.create();
    }

    @Test
    public void testSuccessfulResponseFile() throws IOException {
        String fileName = "src/main/resources/success_response.json";
        Type userType = new TypeToken<RestResponse<User>>() {
        }.getType();
        String fileContent = readFile(fileName);
        assertNotNull(fileContent);

        RestResponse<User> restResponse = gson.fromJson(fileContent, userType);
        assertNotNull(restResponse);
        assertTrue(restResponse.getResponse() != null);
        assertTrue(restResponse.getResponse().getErrorCode() == 0);
        assertTrue(restResponse.getResponse().isSuccessful());
    }

    private String readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return new String(Files.readAllBytes(path));
    }

    @Test
    public void testReadFile() throws IOException {
        String fileName = "src/main/resources/response_with_multiple_errors.json";
        Type userType = new TypeToken<RestResponse<User>>() {
        }.getType();
        String fileContent = readFile(fileName);
        assertNotNull(fileContent);

        RestResponse<User> restResponse = gson.fromJson(fileContent, userType);
        assertNotNull(restResponse);
        assertTrue(restResponse.getResponse() != null);
        assertTrue(restResponse.getResponse().getMessage().equals("Not logged in"));
        assertTrue(restResponse.getResponse().getErrorCode() == 3010);
        assertTrue(!restResponse.getResponse().isSuccessful());
        //assertTrue(restResponse.getErrors().size() == 2);
    }

    @Test
    public void testResponseWithSingleError() throws IOException {
        String fileName = "src/main/resources/response_with_single_error.json";
        Type userType = new TypeToken<RestResponse<User>>() {
        }.getType();
        String fileContent = readFile(fileName);
        assertNotNull(fileContent);

        RestResponse<User> restResponse = gson.fromJson(fileContent, userType);
        assertNotNull(restResponse);
        assertTrue(restResponse.getResponse() != null);
        assertTrue(restResponse.getResponse().getMessage().equals(""));
        assertTrue(restResponse.getResponse().getErrorCode() == 2001);
        assertTrue(!restResponse.getResponse().isSuccessful());
        assertTrue(restResponse.getErrors().size() == 1);
    }

    @Test
    public void testResponseWithSingleErrorWithMessage() throws IOException {
        String fileName = "src/main/resources/response_with_single_error_2.json";
        Type userType = new TypeToken<RestResponse<User>>() {
        }.getType();
        String fileContent = readFile(fileName);
        assertNotNull(fileContent);

        RestResponse<User> restResponse = gson.fromJson(fileContent, userType);
        assertNotNull(restResponse);
        assertTrue(restResponse.getResponse() != null);
        assertTrue(restResponse.getResponse().getMessage().equals("The user name is already in use"));
        assertTrue(restResponse.getResponse().getErrorCode() == 3013);
        assertTrue(!restResponse.getResponse().isSuccessful());
        assertTrue(restResponse.getErrors().size() == 1);
    }
}
