package com.xeiam.xchange.kraken.dto.trade;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public enum KrakenOrderFlags {

  VIQC, FCIB, FCIQ, NOMPP;

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public static KrakenOrderFlags fromString(String orderTypeString) {

    return fromString.get(orderTypeString.toLowerCase());
  }

  private static final Map<String, KrakenOrderFlags> fromString = new HashMap<String, KrakenOrderFlags>();
  static {
    for (KrakenOrderFlags orderFlag : values())
      fromString.put(orderFlag.toString(), orderFlag);
  }

  static class KrakenOrderFlagsDeserializer extends JsonDeserializer<Set<KrakenOrderFlags>> {

    @Override
    public Set<KrakenOrderFlags> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String orderFlagsString = node.textValue();
      Set<KrakenOrderFlags> orderFlags = EnumSet.noneOf(KrakenOrderFlags.class);
      if (!orderFlagsString.isEmpty()) {
        for (String orderFlag : orderFlagsString.split(","))
          orderFlags.add(fromString(orderFlag));
      }
      return orderFlags;
    }
  }
}
