package com.example.lombokworkshop.model;

public class AmountCoupon extends BaseCoupon {

  private static final String TYPE = "coupon";

  private Amount amountValue;
  private Amount minAmount;

  public AmountCoupon() {
    super(CouponType.AMOUNT_COUPON);
  }

  public Amount getAmountValue() {
    return amountValue;
  }

  public void setAmountValue(Amount amountValue) {
    this.amountValue = amountValue;
  }

  public Amount getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(Amount minAmount) {
    this.minAmount = minAmount;
  }

  @Override
  public String getType() {
    return AmountCoupon.TYPE;
  }


  public static Builder builder() {
    return new Builder();
  }

  public static abstract class AbstractBuilder<T extends AmountCoupon, B extends BaseCouponAbstractBuilder<T, B>> extends
      BaseCouponAbstractBuilder<T, B> {

    protected AbstractBuilder(Class<? extends T> couponType) {
      super(couponType);
    }

    public B withAmountValue(Amount value) {
      this.model.setAmountValue(value);
      return thisBuilder;
    }

    public B withMinAmountValue(Amount minAmount) {
      this.model.setMinAmount(minAmount);
      return thisBuilder;
    }

    public B copy(T source) {
      super.copy(source);
      withAmountValue(source.getAmountValue());
      withMinAmountValue(source.getMinAmount());
      return thisBuilder;
    }

  }

  public static class Builder extends AmountCoupon.AbstractBuilder<AmountCoupon, Builder> {

    public Builder() {
      super(AmountCoupon.class);
    }
    // ADD PROPERTIES ACCESSORS TO AmountCoupon.AbstractBuilder only (NOT HERE)
  }

  @Override
  public String toString() {
    return "AmountCoupon{" +
        "amountValue=" + amountValue +
        ", minAmount=" + minAmount +
        ", id=" + id +
        '}';
  }
}
