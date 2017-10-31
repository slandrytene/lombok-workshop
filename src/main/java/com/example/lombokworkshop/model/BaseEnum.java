package com.example.lombokworkshop.model;

import com.fasterxml.jackson.annotation.JsonValue;

public interface BaseEnum<E extends Enum<E> & BaseEnum> {

  String getMessagePrefix();

  String name();

  @JsonValue
  default String asString() {
    return name().toLowerCase();
  }

  static <T extends Enum<T>> T fromString(T tEnum, String key) {
    return Enum.valueOf(tEnum.getDeclaringClass(), key.toUpperCase());
  }

  default LocalizedMessage getLocalizedMessage() {
    return new LocalizedMessage(getMessagePrefix() + "." + asString());
  }
}
