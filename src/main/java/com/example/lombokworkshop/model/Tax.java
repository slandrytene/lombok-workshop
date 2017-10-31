package com.example.lombokworkshop.model;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Tax {

  private String taxTypeId;
  private Amount basePrice;
  private BigDecimal percentage;
  private LocalizedString name;
  private boolean applicable;

  public Tax() {
  }

  public Tax(String taxTypeId, LocalizedString name, Amount basePrice, BigDecimal percentage, boolean applicable) {
    this.taxTypeId = taxTypeId;
    this.applicable = applicable;
    this.setName(name);
    this.setPercentage(percentage);
    this.setBasePrice(basePrice);
  }

  public Amount getAmount() {
    if (!isApplicable()) {
      return new Amount(this.getBasePrice().getCurrencyCode());
    } else if (this.getPercentage() != null) {
      return new Amount(this.getBasePrice().multiply(this.getPercentage()));
    }
    return null;
  }

  public Amount getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(Amount basePrice) {
    this.basePrice = basePrice;
  }

  public LocalizedString getName() {
    return name;
  }

  public void setName(LocalizedString name) {
    this.name = name;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }

  public String getTaxTypeId() {
    return taxTypeId;
  }

  public boolean isApplicable() {
    return applicable;
  }

  public void setApplicable(boolean applicable) {
    this.applicable = applicable;
  }

  public static Tax copy(Tax currentTax) {
    return new Tax(currentTax.getTaxTypeId(), currentTax.getName(), currentTax.getBasePrice(), currentTax.getPercentage(), currentTax.isApplicable());
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
