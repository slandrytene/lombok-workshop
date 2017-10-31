package com.example.lombokworkshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Builder
public class Amount {

  private DecimalValue decimalValue;

  private SupportedCurrency currencyCode;

  private static final int defaultScale = 2;

  public Amount() {
    // We keep this because of Mongo Object builder
    this(new DecimalValue(BigInteger.ZERO, defaultScale), (SupportedCurrency) null);
  }

  public Amount(SupportedCurrency currency) {
    this(new DecimalValue(BigInteger.ZERO, defaultScale), currency);
  }

  public Amount(String currencyCode) {
    this(new DecimalValue(BigInteger.ZERO, defaultScale), currencyCode);
  }

  public Amount(long base, SupportedCurrency currency) {
    this(new DecimalValue(BigInteger.valueOf(base), defaultScale), currency);
  }

  public Amount(long base, String currencyCode) {
    this(new DecimalValue(BigInteger.valueOf(base), defaultScale), currencyCode);
  }

  public Amount(long base, int scale, SupportedCurrency currency) {
    this(base, scale, currency.asCurrencyCode());
  }

  public Amount(long base, int scale, String currencyCode) {
    this(new DecimalValue(BigInteger.valueOf(base), scale), currencyCode);
  }

  public Amount(BigInteger base, SupportedCurrency currency) {
    this(new DecimalValue(base, defaultScale), currency);
  }

  public Amount(Amount amount) {
    this(amount.getDecimalValue(), amount.getCurrencyCode());
  }

  public Amount(BigInteger base, String currencyCode) {
    this(new DecimalValue(base, defaultScale), currencyCode);
  }

  @JsonCreator
  public Amount(@JsonProperty(value = "decimal_value", required = true) DecimalValue decimalValue,
      @JsonProperty(value = "currency_code", required = true) String currencyCode) {
    this.decimalValue = decimalValue;
    this.currencyCode = getCurrency(currencyCode);
  }

  private SupportedCurrency getCurrency(String currencyCode) {
    if (currencyCode == null) {
      return null;
    }

    SupportedCurrency currency = SupportedCurrency.fromString(currencyCode);
    if (currency == null) {
      throw new IllegalArgumentException(
          "The currency submitted for Amount creation is not supported");
    }

    return currency;
  }

  public Amount(DecimalValue decimalValue, SupportedCurrency currency) {
    this.decimalValue = decimalValue;
    this.currencyCode = currency;
  }

  public void rebase(int newScale) {
    Amount newAmount = rebase(this, newScale);
    this.decimalValue = newAmount.decimalValue;
    this.currencyCode = newAmount.currencyCode;
  }

  public static Amount rebase(Amount amount, int newScale) {
    return new Amount(DecimalValue.rebase(amount.getDecimalValue(), newScale),
        amount.getCurrency());
  }

  private void validateCurrency(Amount other) {
    if (this.currencyCode == null || other.getCurrency() == null) {
      throw new IllegalArgumentException(
          "An amount must have a currency in order to be manipulated!");
    }

    if (!this.currencyCode.equals(other.getCurrency())) {
      throw new IllegalArgumentException(String
          .format("Currency conversions are not supported! (%s to %s)", other.getCurrency(),
              this.currencyCode));
    }
  }

  public Amount add(Amount augend) {
    if (augend == null) {
      return this;
    }

    validateCurrency(augend);

    return new Amount(this.decimalValue.add(augend.getDecimalValue()), this.currencyCode);
  }

  public Amount subtract(Amount subtrahend) {
    if (subtrahend == null) {
      return this;
    }

    validateCurrency(subtrahend);

    return new Amount(this.decimalValue.subtract(subtrahend.getDecimalValue()), this.currencyCode);
  }

  public Amount abs() {
    return new Amount(this.decimalValue.abs(), this.currencyCode);
  }

  public Amount multiply(int multiplicand) {
    return new Amount(this.decimalValue.multiply(multiplicand), this.currencyCode);
  }

  public Amount multiply(BigDecimal multiplicand) {
    return new Amount(this.decimalValue.multiply(multiplicand), this.currencyCode);
  }

  public Amount multiply(DecimalValue multiplicand) {
    return this.multiply(new BigDecimal(multiplicand.getBase(), multiplicand.getScale()));
  }

  public static Amount valueOf(int value, SupportedCurrency currency) {
    return new Amount(value, defaultScale, currency);
  }

  public Amount negate() {
    return new Amount(this.decimalValue.negate(), this.currencyCode);
  }

  public static Amount computeIfPresent(Amount amount, Function<Amount, Amount> compute) {
    return Optional.ofNullable(amount).map(compute::apply)
        .orElse(null);
  }

  public int compareTo(Amount comparent) {
    if (!this.currencyCode.equals(comparent.getCurrency())) {
      throw new IllegalArgumentException(
          "It is not possible to compare two amount of different currencies");
    }

    return this.decimalValue.compareTo(comparent.getDecimalValue());
  }

  @JsonIgnore
  public boolean isZero() {
    return getDecimalValue().isZero();
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public double doubleValue() {
    return this.decimalValue.doubleValue();
  }

  @JsonIgnore
  public SupportedCurrency getCurrency() {
    return currencyCode;
  }

  @JsonProperty
  public String getCurrencyCode() {
    return currencyCode.asCurrencyCode();
  }

  public DecimalValue getDecimalValue() {
    return decimalValue;
  }


  @Override
  public String toString() {
    return (this.currencyCode != null) ? this.currencyCode.format(doubleValue())
        : String.valueOf(doubleValue());
  }
}
