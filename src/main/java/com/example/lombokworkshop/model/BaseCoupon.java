package com.example.lombokworkshop.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coupon")
@CompoundIndexes({@CompoundIndex(name = "startTime", def = "{'startTime': -1}"),
    @CompoundIndex(name = "expiryTime", def = "{'expiryTime': -1}"),
    @CompoundIndex(name = "externalRef", def = "{'externalRef._id': 1, 'externalRef.source': 1}"),
    @CompoundIndex(name = "deleted", def = "{'deleted': 1}"),
    @CompoundIndex(name = "couponType", def = "{'couponType': 1}"),
    @CompoundIndex(name = "referenceId", def = "{'referenceId': 1}"),
    @CompoundIndex(name = "notificationStatus", def = "{'notificationStatus': 1}")
})
@Getter
@Setter
public abstract class BaseCoupon extends BaseModel implements ICoupon, ModelWithExternalReference,
    SoftDeletable {

  private LocalizedString title;
  private LocalizedString conditionsUrl;
  private LocalizedString banner;
  private LocalizedString presentationMessage;
  private DateTime startTime;
  private DateTime expiryTime;
  private String referenceId;
  private String referenceType;
  private LocalizedString referenceName;
  private ExternalRef externalRef;
  private boolean deleted = false;
  private CouponType couponType;
  private String apiKey;
  private boolean beforeTax = false;
  private boolean targetAll = false;


  public BaseCoupon(ObjectId id, DateTime createdAt, DateTime updatedAt,
      LocalizedString title, LocalizedString conditionsUrl,
      LocalizedString banner, LocalizedString presentationMessage, DateTime startTime,
      DateTime expiryTime, String referenceId, String referenceType,
      LocalizedString referenceName, ExternalRef externalRef, boolean deleted,
      CouponType couponType, String apiKey, boolean beforeTax, boolean targetAll) {
    super(id, createdAt, updatedAt);
    this.title = title;
    this.conditionsUrl = conditionsUrl;
    this.banner = banner;
    this.presentationMessage = presentationMessage;
    this.startTime = startTime;
    this.expiryTime = expiryTime;
    this.referenceId = referenceId;
    this.referenceType = referenceType;
    this.referenceName = referenceName;
    this.externalRef = externalRef;
    this.deleted = deleted;
    this.couponType = couponType;
    this.apiKey = apiKey;
    this.beforeTax = beforeTax;
    this.targetAll = targetAll;
  }

  protected BaseCoupon(CouponType couponType) {
    this.couponType = couponType;
  }


  public boolean isExpired() {
    DateTimeZone couponTimeZone = expiryTime.getZone();
    return expiryTime.isBefore(DateTime.now().toDateTime(couponTimeZone));
  }

  @Override
  public ExternalRef getExternalRef() {
    return externalRef;
  }

  @Override
  public void setExternalRef(ExternalRef externalRef) {
    this.externalRef = externalRef;
  }

  @Override
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  @Override
  public boolean isDeleted() {
    return deleted;
  }

}
