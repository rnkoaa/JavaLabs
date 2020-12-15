package org.richard.vavr.intro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.collection.List;
import java.util.Optional;
import java.util.UUID;
import org.immutables.value.Value;

public class MainActivity {

  public static ObjectMapper objectMapper() {
    var objectMapper = new ObjectMapper().findAndRegisterModules();
    objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

    // Ignore null values when writing json.
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    // Write times as a String instead of a Long so its human readable.
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE)
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.registerModule(new Jdk8Module());
    return objectMapper;
  }

  public static void main(String[] args) throws JsonProcessingException {
//    new Event.Builder()
    // useMatch();
//    List<Event> events =
//        List.of(
//            new Event.Builder().id(1).version(1L).name("Created").build(),
//            new Event.Builder().id(11).version(7L).name("TitleUpdated").createdAt(Instant.parse("2020-12-09T17:22:05.981538Z"))
//                .build(),
//            new Event.Builder().id(12).version(7L).name("UserApproved").createdAt(Instant.parse("2020-12-09T17:22:03.981537Z"))
//                .build(),
//            new Event.Builder().id(2).version(4L).name("ItemSold").createdAt(Instant.parse("2020-12-09T17:22:03.981535Z")).build(),
//            new Event.Builder().id(3).version(2L).name("TextUpdated").createdAt(Instant.parse("2020-12-07T17:22:05.981538Z")).build(),
//            new Event.Builder().id(4).version(3L).name("StateUpdated").createdAt(Instant.parse("2020-12-05T17:22:05.981538Z")).build(),
//            new Event.Builder().id(5).version(1L).name("TitleUpdated").createdAt(Instant.parse("2020-12-07T17:21:05.981538Z")).build(),
//            new Event.Builder().id(6).version(5L).name("TextUpdated").createdAt(Instant.parse("2020-12-07T17:21:02.981538Z")).build(),
//            new Event.Builder().id(7).version(1L).name("TitleUpdated").createdAt(Instant.parse("2020-12-06T17:21:05.981538Z")).build(),
//            new Event.Builder().id(10).version(1L).name("StateUpdated").createdAt(Instant.parse("2020-12-01T17:30:05.981538Z")).build()
//        );
//
//    events = events.sorted(Comparator.comparing(Event::getVersion).thenComparing(Event::getCreatedAt));
//    print(events);
    String eventString = """
        {
          "classified_ad_id": "87a7fef0-1527-4a47-b196-504d9f9ce0fe",
          "owner_id": "41feaaa5-b867-4359-9b77-d3d4955d6a83",
          "title": "fake classified ad title",
          "text": "fake classified ad"
        }
        """;
    System.out.println(eventString);

    CreateClassifiedEvent createClassifiedEvent = objectMapper().readValue(eventString, CreateClassifiedEvent.class);
    System.out.println(createClassifiedEvent);
  }

  public static void print(List<Event> events) {
    events.forEach(System.out::println);
  }
}

@Value.Immutable
@JsonDeserialize(as = ImmutableCreateClassifiedEvent.class)
@JsonSerialize(as = ImmutableCreateClassifiedEvent.class)
interface CreateClassifiedEvent {

  @JsonProperty("classified_ad_id")
  UUID getClassifiedAdId();

  @JsonProperty("owner_id")
  Optional<UUID> getOwnerId();

  Optional<String> getTitle();

  Optional<String> getText();

}
//
//  private static Try<Todo> deserialize(String objectString) {
//    return Try.of(() -> objectMapper().readValue(objectString, Todo.class));
//  }
//
//  static Try<String> serialize(Todo todo) {
//    return Try.of(() -> objectMapper().writeValueAsString(todo));
//  }

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
//  static void useMatch() {
//    // @formatter:off
//    int item = 1;
//    String result = Match(item).of(Case($(1), () -> "Matches"), Case($(), "Not Match"));
//    // @formatter:on
//    System.out.println(result);
//  }

/**
 * using try
 */
//  static void useTry() {
//    var todo = new Todo(1, "Do the work", "I'm doing the work");
//    Try<String> serialize = serialize(todo);
//    System.out.println(serialize.get());
//
//    Try<Todo> deserialized =
//        serialize
//            .flatMap(MainActivity::deserialize)
//            .onFailure(ex -> System.out.println(ex.getMessage()));
//    Todo todo1 = deserialized.get();
//    System.out.println(todo1);
//  }
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Todo {
//
//  int id;
//  String title;
//  String name;
//}
