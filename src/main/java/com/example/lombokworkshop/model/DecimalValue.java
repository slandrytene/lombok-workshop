package com.example.lombokworkshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.stream.Stream;
import org.springframework.data.mongodb.core.mapping.Field;

public class DecimalValue {

  protected static final int DEFAULT_SCALE = 2;

  public static final int MAX_PRECISION_SCALE = 4;

  public static final String BASE_DOCUMENT_KEY = "base";
  public static final String SCALE_DOCUMENT_KEY = "scale";

  public static final DecimalValue ONE_HUNDRED_PERCENT = new DecimalValue(10000, 2);

  @Field(BASE_DOCUMENT_KEY)
  protected BigInteger base;

  @Field(SCALE_DOCUMENT_KEY)
  protected int scale;

  public DecimalValue() {
    this(0, DEFAULT_SCALE);
  }

  public DecimalValue(long base, int scale) {
    this.base = BigInteger.valueOf(base);
    this.scale = scale;
  }

  public DecimalValue(BigInteger base, int scale) {
    this.base = base;
    this.scale = scale;
  }

  public void rebase(int newScale) {
    DecimalValue rebased = rebase(this, newScale);
    this.base = rebased.base;
    this.scale = rebased.scale;
  }

  public static DecimalValue rebase(DecimalValue decimalValue, int newScale) {
    if (newScale < decimalValue.scale) {
      return new DecimalValue(decimalValue.base
          .divide(BigInteger.valueOf((int) Math.pow(10, (double) decimalValue.scale - newScale))), newScale);
    }
    return new DecimalValue(decimalValue.base
        .multiply(BigInteger.valueOf((int) Math.pow(10, (double) newScale - decimalValue.scale))), newScale);
  }

  public DecimalValue add(DecimalValue augend) {

    if (this.scale != augend.scale) {
      DecimalValue rebasedAugend = rebase(augend, this.scale);
      return this.add(rebasedAugend);
    }
    return new DecimalValue(this.base.add(augend.base), this.scale);
  }

  public DecimalValue subtract(DecimalValue subtrahend) {

    if (this.scale != subtrahend.scale) {
      DecimalValue rebasedSubtrahend = rebase(subtrahend, this.scale);
      return this.subtract(rebasedSubtrahend);
    }
    return new DecimalValue(this.base.subtract(subtrahend.base), this.scale);
  }

  public DecimalValue abs() {
    return new DecimalValue(this.base.abs(), this.scale);
  }

  public DecimalValue multiply(int multiplicand) {
    return new DecimalValue(this.base.multiply(BigInteger.valueOf(multiplicand)), this.scale);
  }

  public DecimalValue multiply(BigDecimal multiplicand) {
    return new DecimalValue(
        new BigDecimal(this.base).multiply(multiplicand).setScale(0, RoundingMode.HALF_UP)
            .toBigInteger(),
        this.scale);
  }

  public DecimalValue multiply(DecimalValue multiplicand) {
    return new DecimalValue(
        new BigDecimal(this.base).multiply(new BigDecimal(multiplicand.getBase())).setScale(0)
            .toBigInteger(),
        this.scale);
  }

  public DecimalValue divide(int divisor) {
    return new DecimalValue(this.base.divide(BigInteger.valueOf(divisor)), this.scale);
  }

  public DecimalValue divide(BigDecimal divisor) {
    return new DecimalValue(
        new BigDecimal(this.base).divide(divisor).setScale(0, RoundingMode.HALF_UP).toBigInteger(),
        this.scale);
  }

  public static DecimalValue getCumulativePercentageValue(Stream<DecimalValue> values,
      RoundingMode roundingMode) {

    DecimalValue totalPercentageValue =
        values.reduce(
            (s1, s2) ->
                s1.add(s2.multiply(ONE_HUNDRED_PERCENT.subtract(s1))
                    .divide(ONE_HUNDRED_PERCENT.getBase().intValue()))
        ).orElseGet(DecimalValue::new);

    if (roundingMode != null) {
      int originalScale = totalPercentageValue.getScale();
      totalPercentageValue =
          new DecimalValue(
              new BigDecimal(totalPercentageValue.getBase(), totalPercentageValue.getScale())
                  .setScale(0, roundingMode).toBigInteger(),
              0);
      totalPercentageValue.rebase(originalScale);
    }

    return totalPercentageValue;
  }

  public DecimalValue negate() {
    return new DecimalValue(this.base.negate(), this.scale);
  }

  public int compareTo(DecimalValue comparent) {
    if (this.scale != comparent.scale) {
      DecimalValue rebasedValue = rebase(comparent, this.scale);
      return this.compareTo(rebasedValue);
    }

    return this.base.compareTo(comparent.base);
  }

  @JsonIgnore
  public boolean isZero() {
    return this.base.equals(BigInteger.ZERO);
  }

  public double doubleValue() {
    return this.base.doubleValue() * Math.pow(10, -1 * (double) this.scale);
  }

  public BigInteger getBase() {
    return base;
  }

  public int getScale() {
    return scale;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder extends BaseBuilder<DecimalValue, DecimalValue.Builder> {

    public Builder() {
      super(DecimalValue.class);
    }

    public Builder withBase(BigInteger base) {
      this.model.base = BigInteger.valueOf(base.longValue());
      return thisBuilder;
    }

    public Builder withBase(long base) {
      this.model.base = BigInteger.valueOf(base);
      return thisBuilder;
    }

    public Builder withScale(int scale) {
      this.model.scale = scale;
      return thisBuilder;
    }

    @Override
    public Builder copy(DecimalValue source) {
      withBase(source.getBase().longValue());
      withScale(source.getScale());
      return thisBuilder;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DecimalValue decimalValue = (DecimalValue) o;

    if (this.scale != decimalValue.scale) {
      decimalValue = rebase(decimalValue, this.scale);
    }

    return this.base.equals(decimalValue.getBase());

  }

  @Override
  public int hashCode() {
    int result = base.intValue();
    result = 31 * result + scale;
    return result;
  }

  @Override
  public String toString() {
    return "{ " + "\"base\": " + base + ", \"scale\": " + scale + " }";
  }

}

