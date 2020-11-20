package com.marketplace.mongo;

import com.marketplace.framework.mongo.annotations.MongoRecordValue;
import java.time.LocalDate;

@MongoRecordValue
public record Person(String name, int age, double weight, LocalDate birthdate) {

}
