package com.richard;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by rnkoaa on 8/31/14.
 */
public class StreamsTesting {

    private List<String> names;
    List<Person> persons;

    @Before
    public void setup() {
        names = Arrays.asList("Jacque", "Jones");
        persons = Arrays.asList(
                new Person("kwame", "od", 31),
                new Person("Kwame", "ag", 25),
                new Person("Ama", "se", 28),
                new Person("Abena", "he", 33),
                new Person("Afia", "he", 14)
        );
    }

    @Test
    public void testFilters() {
        long count = names.stream()
                .filter(name -> name.startsWith("Ja"))
                .count();
        assertThat(count).isEqualTo(1);
       long personsUnder30 = persons.stream().filter(person -> person.getAge() < 30).count();
        assertThat(personsUnder30).isEqualTo(3);

        List<Person> personsWithLastName = persons
                .stream()
                .filter(person -> person.getLastName().startsWith("he"))
                .collect(Collectors.toList());
        assertThat(personsWithLastName).hasSize(2);
    }
}
