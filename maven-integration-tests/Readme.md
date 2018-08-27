# Sample Maven Integration Tests

To Run unit tests only, run `mvn test`

To Run integration tests, run `mvn integration-test`

To Run integration tests only, run `mvn -DskipTests integration-test`

Other goals that can run:

```bash
mvn clean verify -Pfailsafe
mvn clean verify -Psurefire
```