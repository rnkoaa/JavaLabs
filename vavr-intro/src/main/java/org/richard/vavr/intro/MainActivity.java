package org.richard.vavr.intro;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MainActivity {
  public static ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
  }

  public static void main(String[] args) {
    useMatch();
  }

  private static Try<Todo> deserialize(String objectString) {
    return Try.of(() -> objectMapper().readValue(objectString, Todo.class));
  }

  static Try<String> serialize(Todo todo) {
    return Try.of(() -> objectMapper().writeValueAsString(todo));
  }

  static void useMatch() {
    // @formatter:off
    int item = 1;
    String result = Match(item).of(
        Case($(1), () -> "Matches"),
        Case($(), "Not Match"));
    // @formatter:on
    System.out.println(result);
  }

  /** using try */
  static void useTry() {
    var todo = new Todo(1, "Do the work", "I'm doing the work");
    Try<String> serialize = serialize(todo);
    System.out.println(serialize.get());

    Try<Todo> deserialized =
        serialize
            .flatMap(MainActivity::deserialize)
            .onFailure(ex -> System.out.println(ex.getMessage()));
    Todo todo1 = deserialized.get();
    System.out.println(todo1);
  }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Todo {
  int id;
  String title;
  String name;
}
