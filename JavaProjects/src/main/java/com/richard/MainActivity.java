package com.richard;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

/**
 * Created by rnkoaa on 4/8/15.
 */
public class MainActivity {
    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String fileContent = new MainActivity().readFile("process_definitions.json");

        RestResponse<Collection<ProcessDefinition>> restResponse = objectMapper.readValue(fileContent,
                new TypeReference<RestResponse<Collection<ProcessDefinition>>>() {
                });

        if(restResponse == null)
            System.out.println("rest response is null");
        else
            System.out.println("rest response is not null");
        System.out.println(restResponse.getTotal());
        System.out.println(restResponse.getData().size());
    }

    public String readFile(String fileName) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return stringBuilder.toString();
    }
}
