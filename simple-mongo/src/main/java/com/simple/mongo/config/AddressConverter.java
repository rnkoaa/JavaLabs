package com.simple.mongo.config;

import com.simple.mongo.Address;
import org.bson.Document;

public class AddressConverter {

  public Address convert(Document document) {
    String street = document.getString("street");
    String city = document.getString("city");
    String zip = document.getString("zip");
    return new Address(street, city, zip);
  }
}
