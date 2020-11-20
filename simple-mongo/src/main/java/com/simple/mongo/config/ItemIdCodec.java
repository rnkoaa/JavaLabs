package com.simple.mongo.config;

import com.simple.mongo.ItemId;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class ItemIdCodec implements Codec<ItemId> {

    private final CodecRegistry codecRegistry;

    public ItemIdCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public ItemId decode(BsonReader reader, DecoderContext decoderContext) {
        Codec<Document> documentCodec = codecRegistry.get(Document.class);
        Document document = documentCodec.decode(reader, decoderContext);
        String value = document.getString("value");
        if (value != null && !value.isEmpty()) {
            return ItemId.fromString(value);
        }
        return null;
    }

    @Override
    public void encode(BsonWriter writer, ItemId value, EncoderContext encoderContext) {
        Codec<Document> documentCodec = codecRegistry.get(Document.class);
        Document document = new Document();
        document.put("value", value.id().toString());
        documentCodec.encode(writer, document, encoderContext);
    }

    @Override
    public Class<ItemId> getEncoderClass() {
        return ItemId.class;
    }
}
