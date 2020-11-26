package com.simple.mongo.config;

import com.simple.mongo.ItemId;
import java.util.UUID;
import org.bson.Document;

public class ItemIdConverter {

  public ItemId convert(Document document) {
    if (document != null) {
      String value = document.getString("value");
      return new ItemId(UUID.fromString(value));
    }
    return null;
  }

}
