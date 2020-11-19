package com.simple.mongo.config;

import com.simple.mongo.Address;
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
//        Address address = document.get("address", Address.class);
//        ItemId itemId = document.get("itemId", ItemId.class);

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
        return new Person(UUID.fromString(id), name, age, null, null);
    }

    @Override
    public void encode(BsonWriter writer, Person value, EncoderContext encoderContext) {
        Codec<Document> documentCodec = codecRegistry.get(Document.class);
        Codec<Address> addressCodec = codecRegistry.get(Address.class);

        Document document = new Document();
        document.put("_id", value.getId().toString());
        document.put("name", value.getName());
        document.put("age", value.getAge());

        document.put("address", value.getAddress());
        document.put("itemId", value.getItemId());
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
        return document.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(Person document) {
        if (!documentHasId(document)) {
            throw new IllegalStateException("The document does not contain an _id");
        }

        return new BsonString(document.getId().toString());
    }
}
