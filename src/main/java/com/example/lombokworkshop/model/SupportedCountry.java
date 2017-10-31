package com.example.lombokworkshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.List;

public enum SupportedCountry implements BaseEnum {
  CA(6, 3), US(5, 5), NOT_SUPPORTED(0, 0);

  private int deliveryCodeNormalizedLength;
  private int deliveryCodeLength;

  private SupportedCountry(int deliveryCodeLength, int deliveryCodeNormalizedLength) {
    this.deliveryCodeLength = deliveryCodeLength;
    this.deliveryCodeNormalizedLength = deliveryCodeNormalizedLength;
  }

  @Override
  public String getMessagePrefix() {
    return "country";
  }

  @JsonCreator
  public static SupportedCountry fromString(String key) {
    return key == null ? null : SupportedCountry.getCountry(key.toUpperCase());
  }

  public String asCountryCode() {
    return name();
  }

  @Override
  @JsonValue
  public String asString() {
    return name();
  }

  public static String[] getSupportedCountryCodes() {
    return Arrays.stream(values()).map(SupportedCountry::asCountryCode).toArray(String[]::new);
  }

  private static SupportedCountry getCountry(String name) {
    List<SupportedCountry> list = Arrays.asList(SupportedCountry.values());
    return list.stream().filter(m -> m.name().equals(name)).findAny().orElse(NOT_SUPPORTED);
  }

  public int getDeliveryCodeNormalizedLength() {
    return deliveryCodeNormalizedLength;
  }

  public int getDeliveryCodeLength() {
    return deliveryCodeLength;
  }

}
