package com.simple.mongo.config;

import com.simple.mongo.Address;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class AddressCodec implements Codec<Address> {

  private final CodecRegistry codecRegistry;

  public AddressCodec(CodecRegistry codecRegistry) {
    this.codecRegistry = codecRegistry;
  }

  @Override
  public Address decode(BsonReader reader, DecoderContext decoderContext) {
    Codec<Document> documentCodec = codecRegistry.get(Document.class);
    Document document = documentCodec.decode(reader, decoderContext);
    String street = document.getString("street");
    String city = document.getString("city");
    String zip = document.getString("zip");
    return new Address(street, city, zip);
  }

  @Override
  public void encode(BsonWriter writer, Address value, EncoderContext encoderContext) {
    Codec<Document> documentCodec = codecRegistry.get(Document.class);
    Document document = new Document();
    document.put("street", value.getStreet());
    document.put("city", value.getCity());
    document.put("zip", value.getZip());

    documentCodec.encode(writer, document, encoderContext);
  }

  @Override
  public Class<Address> getEncoderClass() {
    return Address.class;
  }
}
