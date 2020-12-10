package org.richard.vavr.intro;

import java.time.Instant;
import org.immutables.value.Value;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Value.Immutable
//public abstract class Event {
//
//  abstract int getId();
//
//  long getVersion() {
//    return 0;
//  }
//
//  abstract String getName();
//
//  Instant getCreatedAt() {
//    return Instant.now();
//  }
//}
@Builded
@Value.Immutable
public interface Event {

  int getId();

  long getVersion();

  String getName();

  default Instant getCreatedAt() {
    return Instant.now();
  }
//
  class Builder extends BuilderFor_Event {

  }
}
