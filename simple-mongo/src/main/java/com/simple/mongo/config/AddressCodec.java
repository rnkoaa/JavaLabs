package com.simple.mongo.config;

import com.simple.mongo.Address;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class AddressCodec implements Codec<Address> {

    @Override
    public Address decode(BsonReader reader, DecoderContext decoderContext) {
//        Codec<Address> addressCodec = codecRegistry.get(Address.class);
//        String name = reader.readString("name");
//        UUID id = UUID.fromString(reader.readString("_id"));
//        int age = reader.readInt32("age");
//
////        BsonType bsonType = reader.readBsonType()
////        addressCodec.de
////        reader.read
////        ItemId id = UUID.fromString(reader.readString("_id"));
//        return new Person(id, name, age, new Address(), ItemId.newItemId());
//        Document document = new Document();
//
//        reader.readStartDocument();
//        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
//            String fieldName = reader.readName();
//            document.put(fieldName, reader.readValue(reader, decoderContext));
//        }
//
//        reader.readEndDocument();
//
//        return document;
        return null;
    }

    @Override
    public void encode(BsonWriter writer, Address value, EncoderContext encoderContext) {
//        Codec dateCodec = codecRegistry.get(LocalDate.class);j
        writer.writeStartDocument();
        writer.writeString("street", value.getStreet());
        writer.writeString("city", value.getCity());
        writer.writeString("zip", value.getZip());

//        writer.writeStartDocument();
//
//        writer.writeEndDocument();

        writer.writeEndDocument();
    }

    @Override
    public Class<Address> getEncoderClass() {
        return Address.class;
    }
}
