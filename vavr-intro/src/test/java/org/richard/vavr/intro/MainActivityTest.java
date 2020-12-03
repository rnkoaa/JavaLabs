package org.richard.vavr.intro;

import io.vavr.control.Try;
import java.net.URI;
import org.junit.jupiter.api.Test;

public class MainActivityTest {

  @Test
  void createUrl() {
    Try<URI> tryUrl = Try.of(() -> URI.create("devoxx.pl"));
    String orElse = tryUrl.map(URI::toString)
        .filter(f -> true)
        .onFailure(ex -> System.err.println(ex.getMessage()))
        .recover(NullPointerException.class, s -> "42")
        .getOrElse("hello.com");
  }
}
