package com.example.lombokworkshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

public enum SupportedCurrency implements BaseEnum {
  CAD(SupportedCountry.CA), USD(SupportedCountry.US);

  private SupportedCountry supportedCountry;

  private SupportedCurrency(SupportedCountry supportedCountry) {
    this.supportedCountry = supportedCountry;
  }

  public SupportedCountry getSupportedCountry() {
    return this.supportedCountry;
  }

  @Override
  public String getMessagePrefix() {
    return "currency";
  }

  @JsonCreator
  public static SupportedCurrency fromString(String key) {
    return key == null ? null : SupportedCurrency.valueOf(key.toUpperCase());
  }

  @Override
  @JsonValue
  public String asString() {
    return name();
  }

  private static Stream<SupportedCurrency> supportedCurrencies() {
    return Arrays.stream(values());
  }

  public static SupportedCurrency fromCountryCode(SupportedCountry supportedCountry) {
    Preconditions.checkNotNull(supportedCountry);
    Optional<SupportedCurrency> corresponding = supportedCurrencies().filter(supportedCurrency -> supportedCurrency.getSupportedCountry() == supportedCountry)
        .findFirst();
    if (!corresponding.isPresent()) {
      throw new NoSuchElementException("unknown currency for country : " + supportedCountry.asCountryCode());
    } else {
      return corresponding.get();
    }
  }

  public String asCurrencyCode() {
    return name();
  }

  public String format(double value) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    Currency currency = Currency.getInstance(asCurrencyCode());
    formatter.setCurrency(currency);
    return formatter.format(value);
  }
}
