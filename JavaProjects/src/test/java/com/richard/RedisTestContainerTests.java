package com.richard;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@org.testcontainers.junit.jupiter.Testcontainers
class RedisTestContainerTests {

  static final String REDIS_IMAGE = "redis:6-alpine";

  @Container
  private final GenericContainer<?> redisContainer = new GenericContainer<>(REDIS_IMAGE).withExposedPorts(6379);

  @Test
    void top_level_container_should_be_running() {
      assertThat(redisContainer.isRunning()).isTrue();
    }

}
