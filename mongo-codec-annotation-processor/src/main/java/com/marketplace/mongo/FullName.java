package com.marketplace.mongo;

import com.marketplace.framework.mongo.annotations.MongoRecordValue;

@MongoRecordValue
public record FullName(String firstName, String middleName, String lastName){}
