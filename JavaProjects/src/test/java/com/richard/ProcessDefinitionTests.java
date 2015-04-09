package com.richard;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rnkoaa on 4/8/15.
 */
public class ProcessDefinitionTests {
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deserializeSingleResponseObject() throws IOException {
        String data = readFile("process_definition.json");
        assertThat(data).isNotNull().isNotEmpty();

        ProcessDefinition processDefinition = objectMapper.readValue(data, ProcessDefinition.class);
        assertThat(processDefinition).isNotNull()
                .is(new Condition<ProcessDefinition>() {
                    @Override
                    public boolean matches(ProcessDefinition value) {
                        return
                                value.getVersion() == 1 &&
                                        Objects.equals(value.getCategory(), "Examples") &&
                                        Objects.equals(value.getId(), "oneTaskProcess:1:4") &&
                                        Objects.equals(value.getKey(), "oneTaskProcess") &&
                                        Objects.equals(value.getDeploymentId(), "2") &&
                                        !value.isSuspended() &&
                                        value.isGraphicalNotationDefined() &&
                                        !value.isStartFormDefined();
                    }
                });
    }

    @Test
    public void deserializeProperCollectionResponseWithOneDataObject() throws IOException {
        String data = readFile("process_definitions.json");
        assertThat(data).isNotNull().isNotEmpty();

        RestResponse<List<ProcessDefinition>> restResponse = objectMapper.readValue(data,
                new TypeReference<RestResponse<List<ProcessDefinition>>>() {
                });

        assertThat(restResponse).isNotNull();
        assertThat(restResponse).is(new Condition<RestResponse<List<ProcessDefinition>>>() {
            @Override
            public boolean matches(RestResponse<List<ProcessDefinition>> conditionResponse) {
                return conditionResponse.getData() != null &&
                        conditionResponse.getData().size() == 1 &&
                        conditionResponse.getSize() == 1 &&
                        conditionResponse.getSort().equals("name") &&
                        conditionResponse.getOrder().equals("asc") &&
                        conditionResponse.getStart() == 0;
            }
        });

        ProcessDefinition processDefinition = restResponse.getData().get(0);
        assertThat(processDefinition).isNotNull()
                .is(new Condition<ProcessDefinition>() {
                    @Override
                    public boolean matches(ProcessDefinition value) {
                        return
                                value.getVersion() == 1 &&
                                        Objects.equals(value.getCategory(), "Examples") &&
                                        Objects.equals(value.getId(), "oneTaskProcess:1:4") &&
                                        Objects.equals(value.getKey(), "oneTaskProcess") &&
                                        Objects.equals(value.getDeploymentId(), "2") &&
                                        !value.isSuspended() &&
                                        value.isGraphicalNotationDefined() &&
                                        !value.isStartFormDefined();
                    }
                });


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
