package com.simple.mongo.config;

import com.simple.mongo.ItemId;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class ItemIdCodec implements Codec<ItemId> {
    @Override
    public ItemId decode(BsonReader reader, DecoderContext decoderContext) {
        String id = reader.readString();
        if (id != null && !id.isEmpty()) {
            return ItemId.fromString(id);
        }
        return null;
    }

    @Override
    public void encode(BsonWriter writer, ItemId value, EncoderContext encoderContext) {
        writer.writeString(value.toString());
    }

    @Override
    public Class<ItemId> getEncoderClass() {
        return ItemId.class;
    }
}
