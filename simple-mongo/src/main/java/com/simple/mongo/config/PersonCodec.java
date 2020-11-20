package com.simple.mongo.config;

import com.simple.mongo.Address;
import com.simple.mongo.ItemId;
import com.simple.mongo.Person;
import org.bson.*;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.UUID;

public class PersonCodec implements CollectibleCodec<Person> {

  private final CodecRegistry codecRegistry;

  public PersonCodec(CodecRegistry codecRegistry) {
    this.codecRegistry = codecRegistry;
  }

  @Override
  public Person decode(BsonReader reader, DecoderContext decoderContext) {
    Codec<Document> documentCodec = codecRegistry.get(Document.class);
    Document document = documentCodec.decode(reader, decoderContext);
    String id = document.getString("_id");
    String name = document.getString("name");
    int age = document.getInteger("age");

    Document addressDocument = document.get("address", Document.class);
    Address address = new AddressConverter().convert(addressDocument);

    Document itemDocument = document.get("itemId", Document.class);
    ItemId itemId = new ItemIdConverter().convert(itemDocument);

    return new Person(UUID.fromString(id), name, age, address, itemId);
  }

  @Override
  public void encode(BsonWriter writer, Person value, EncoderContext encoderContext) {
    Codec<Document> documentCodec = codecRegistry.get(Document.class);

    Document document = new Document();
    document.put("_id", value.id().toString());
    document.put("name", value.name());
    document.put("age", value.age());

    document.put("address", value.address());
    document.put("itemId", value.itemId());
    documentCodec.encode(writer, document, encoderContext);
  }

  @Override
  public Class<Person> getEncoderClass() {
    return Person.class;
  }

  @Override
  public Person generateIdIfAbsentFromDocument(Person person) {

    return person;
  }

  @Override
  public boolean documentHasId(Person document) {
    return document.id() != null;
  }

  @Override
  public BsonValue getDocumentId(Person document) {
    if (!documentHasId(document)) {
      throw new IllegalStateException("The document does not contain an _id");
    }

    return new BsonString(document.id().toString());
  }
}
