CREATE TABLE customer (
  customer_id  BIGINT(20)   NOT NULL AUTO_INCREMENT,
  email        VARCHAR(255) NOT NULL,
  first_name   VARCHAR(255) NOT NULL,
  last_name    VARCHAR(255) NOT NULL,
  password     VARCHAR(255) NOT NULL,
  version      BIGINT(20)            DEFAULT NULL,
  created_time      TIMESTAMP    NOT NULL DEFAULT(CURRENT_TIMESTAMP()) ,
  last_updated TIMESTAMP    NOT NULL DEFAULT(CURRENT_TIMESTAMP()),
  PRIMARY KEY (customer_id),
  UNIQUE KEY UK_EMAIL (email)
);

CREATE TABLE role (
  role_id      BIGINT(20)   NOT NULL AUTO_INCREMENT,
  role_name    VARCHAR(255) NOT NULL,
  version      BIGINT(20)            DEFAULT 0,
  created_time      TIMESTAMP    NOT NULL DEFAULT(CURRENT_TIMESTAMP()) ,
  last_updated TIMESTAMP    NOT NULL DEFAULT(CURRENT_TIMESTAMP()),
  PRIMARY KEY (role_id),
  UNIQUE KEY (role_name)
);

CREATE TABLE customer_roles (
  customer_id BIGINT(20) NOT NULL,
  role_id     BIGINT(20) NOT NULL,
  PRIMARY KEY (customer_id, role_id),
  CONSTRAINT FK_customer_id_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  CONSTRAINT FK_role_id_role FOREIGN KEY (role_id) REFERENCES role (role_id)
);

CREATE TABLE PERSON (
  id         BIGINT(20)   NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL
);

INSERT INTO customer (customer_id, email, first_name, last_name, password, version)
VALUES
  (1, 'richard.agyei@gmail.com', 'Richard', 'Agyei', '$2a$10$43EgcnWmiF9pXohPmzXEL.YD9xAxZNOTgZSV.byGlnD299Nlg3nhu', 0);

INSERT INTO role (role_id, role_name, version)
VALUES (1, 'USER', 0);

INSERT INTO customer_roles (customer_id, role_id) VALUES (1, 1)