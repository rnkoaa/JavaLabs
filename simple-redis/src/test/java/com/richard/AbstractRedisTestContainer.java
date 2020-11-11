package com.richard;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class AbstractRedisTestContainer {

  static class RedisBackedCache {
    private RedisClient redisClient;
    private ObjectMapper objectMapper;
    private StatefulRedisConnection<String, String> connection;
    RedisCommands<String, String> syncCommands;

    public RedisBackedCache(ObjectMapper objectMapper, String host, int port) {
      this.redisClient = RedisClient.create(String.format("redis://%s:%d", host, port));
      this.objectMapper = objectMapper;
    }

    void start() {
      connection = redisClient.connect();
      syncCommands = connection.sync();
    }

    public void put(String key, Object value) {
      if (value != null) {
        try {
          String serialized = this.objectMapper.writeValueAsString(value);
          syncCommands.set(key, serialized);
        } catch (JsonProcessingException ex) {
        }
      }
    }

    public void close() {
      connection.close();
      redisClient.shutdown();
    }

    public <T> Optional<T> get(String key, Class<T> clzz) {
      var value = syncCommands.get(key);
      System.out.println("Value from Redis: " + value);
      if (value != null && value != "") {
        try {
          T results = this.objectMapper.readValue(value, clzz);
          return Optional.of(results);
        } catch (Exception e) {
          return Optional.<T>empty();
        }
      }
      return Optional.<T>empty();
    }
  }

  RedisBackedCache redisBackedCache;
  ObjectMapper objectMapper;

  @Container
  public GenericContainer<?> redis =
      new GenericContainer<>(DockerImageName.parse("redis:6-alpine")).withExposedPorts(6379);

  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper().findAndRegisterModules();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
    // objectMapper.enableDefaultTyping(
    //     ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);
    // ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);
    // ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);
    System.out.printf("RedisHost: %s\n", redis.getHost());
    System.out.printf("RedisPort: %d\n", redis.getMappedPort(6379));
    redisBackedCache =
        new RedisBackedCache(objectMapper, redis.getHost(), redis.getMappedPort(6379));
    redisBackedCache.start();
  }

  @AfterEach
  void cleanup() {
    redisBackedCache.close();
  }

  @Test
  void objectCanBeAddedAndFoundFromCache() throws JsonProcessingException {
    String item = "Hello, World";
    redisBackedCache.put("hello", item);

    var found = redisBackedCache.get("hello", String.class);

    assertThat(found).isPresent();
  }

  @Test
  void testRedisBackedCacheAvailable() throws JsonProcessingException {
    assertThat(redisBackedCache).isNotNull();
  }
}
