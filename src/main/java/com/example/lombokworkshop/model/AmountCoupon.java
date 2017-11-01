package com.example.lombokworkshop.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;


@Data
public class AmountCoupon extends BaseCoupon {

  private static final String TYPE = "coupon";

  private Amount amountValue;
  private Amount minAmount;

  public AmountCoupon() {
    super(CouponType.AMOUNT_COUPON);
  }

  @Builder
  private AmountCoupon(ObjectId id, DateTime createdAt, DateTime updatedAt,
      LocalizedString title, LocalizedString conditionsUrl,
      LocalizedString banner, LocalizedString presentationMessage, DateTime startTime,
      DateTime expiryTime, String referenceId, String referenceType,
      LocalizedString referenceName, ExternalRef externalRef, boolean deleted,
      CouponType couponType, String apiKey, boolean beforeTax, boolean targetAll, Amount amountValue, Amount minAmount) {
    super(id, createdAt, updatedAt, title, conditionsUrl, banner, presentationMessage, startTime, expiryTime, referenceId, referenceType, referenceName,
        externalRef, deleted, couponType, apiKey, beforeTax, targetAll);
    this.amountValue = amountValue;
    this.minAmount = minAmount;
  }

  @Override
  public String getType() {
    return AmountCoupon.TYPE;
  }

  public static AmountCouponBuilder copy(AmountCoupon source) {
    return builder()
        .id(new ObjectId(source.getId()))
        .createdAt(source.getCreatedAt())
        .updatedAt(source.getUpdatedAt())
        .title(source.getTitle())
        .conditionsUrl(source.getConditionsUrl())
        .banner(source.getBanner())
        .presentationMessage(source.getPresentationMessage())
        .startTime(source.getStartTime())
        .expiryTime(source.getExpiryTime())
        .referenceId(source.getReferenceId())
        .referenceType(source.getReferenceType())
        .referenceName(source.getReferenceName())
        .externalRef(source.getExternalRef())
        .deleted(source.isDeleted())
        .couponType(source.getCouponType())
        .apiKey(source.getApiKey())
        .beforeTax(source.isBeforeTax())
        .targetAll(source.isTargetAll())
        .amountValue(source.getAmountValue())
        .minAmount(source.getMinAmount());
  }

}
