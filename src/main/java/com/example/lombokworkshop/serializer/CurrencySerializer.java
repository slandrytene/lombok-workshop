package com.example.lombokworkshop.serializer;

import com.example.lombokworkshop.model.SupportedCurrency;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;


public class CurrencySerializer extends StdSerializer<SupportedCurrency> {

  protected CurrencySerializer(Class<SupportedCurrency> t) {
    super(t);
  }

  @Override
  public void serialize(SupportedCurrency supportedCurrency, JsonGenerator json,
      SerializerProvider serializerProvider) throws IOException {
    json.writeStartObject();
    json.writeString(supportedCurrency.name());
    json.writeEndObject();

  }
}
