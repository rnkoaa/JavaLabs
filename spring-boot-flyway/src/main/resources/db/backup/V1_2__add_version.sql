ALTER TABLE PERSON
ADD version BIGINT DEFAULT 0;

insert into PERSON (first_name, last_name, version) values ('Nana', 'Kwame', 1);