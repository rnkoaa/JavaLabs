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


  /*
  Match(i).of(
    Case($(is(1)), 1.0),
    Case($(is(2)), 2.0),
    Case($(), o -> run(() -> {
        throw new IllegalArgumentException();
    }))
  );
   */

  /*
  final Integer result = Match(i).of(
    // if i instanceof Object.class == true
    Case($(instanceOf(Object.class)), i),
    // if i is one of the elements
    Case($(isIn(3, 4, 5)), 0),
    // if i fulfills any of given predicates
    Case($(anyOf(is(instanceOf(Integer.class)), isNull())), -1),
    // if i fulfills all of given predicates
    Case($(allOf(isNotNull(), isIn(6, 7, 8))), -2),
    // In other case...
    Case($(), -10)
  );
   */

  /*
  Match(tryInt).of(
    // If tryInt is success and fulfills given predicate
    Case($Success($(isIn(2, 3, 4, 5))), value -> {
        System.out.println(value);
        return value + 1;
    }),
    // if tryInt is only success
    Case($Success($()), value -> value + 2),
    // if tryInt is failure
    Case($Failure($()), v -> {
        System.out.println("Error");
        return v;
    })
);
   */
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
