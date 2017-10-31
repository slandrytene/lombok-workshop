package com.example.lombokworkshop.model;

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

  protected BaseCoupon(CouponType couponType) {
    setCouponType(couponType);
  }

  public LocalizedString getBanner() {
    return this.banner;
  }

  public void setBanner(LocalizedString banner) {
    this.banner = banner;
  }

  public DateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(DateTime startTime) {
    this.startTime = startTime;
  }

  public DateTime getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(DateTime expiryTime) {
    this.expiryTime = expiryTime;
  }

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  public String getReferenceType() {
    return referenceType;
  }

  public void setReferenceType(String referenceType) {
    this.referenceType = referenceType;
  }

  public LocalizedString getReferenceName() {
    return referenceName;
  }

  public void setReferenceName(LocalizedString referenceName) {
    this.referenceName = referenceName;
  }

  public boolean isExpired() {
    DateTimeZone couponTimeZone = getExpiryTime().getZone();
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

  public LocalizedString getTitle() {
    return title;
  }

  public void setTitle(LocalizedString title) {
    this.title = title;
  }

  public LocalizedString getConditionsUrl() {
    return conditionsUrl;
  }

  public void setConditionsUrl(LocalizedString conditionsUrl) {
    this.conditionsUrl = conditionsUrl;
  }

  public CouponType getCouponType() {
    return couponType;
  }

  private void setCouponType(CouponType couponType) {
    this.couponType = couponType;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public boolean isBeforeTax() {
    return beforeTax;
  }

  public void setBeforeTax(boolean beforeTax) {
    this.beforeTax = beforeTax;
  }

  public boolean isTargetAll() {
    return targetAll;
  }

  public void setTargetAll(boolean targetAll) {
    this.targetAll = targetAll;
  }

  public LocalizedString getPresentationMessage() {
    return presentationMessage;
  }

  public void setPresentationMessage(LocalizedString presentationMessage) {
    this.presentationMessage = presentationMessage;
  }

  public static abstract class BaseCouponAbstractBuilder<T extends BaseCoupon, B extends AbstractBuilder<T, B>> extends
      AbstractBuilder<T, B> {

    protected BaseCouponAbstractBuilder(Class<? extends T> mClass) {
      super((Class<T>) mClass);
    }

    public B withBanner(LocalizedString banner) {
      this.model.setBanner(banner);
      return thisBuilder;
    }

    public B withTitle(LocalizedString title) {
      this.model.setTitle(title);
      return thisBuilder;
    }

    public B withConditionsUrl(LocalizedString conditionsUrl) {
      this.model.setConditionsUrl(conditionsUrl);
      return thisBuilder;
    }

    public B withStartTime(DateTime startTime) {
      this.model.setStartTime(startTime);
      return thisBuilder;
    }

    public B withExpiryTime(DateTime expiryTime) {
      this.model.setExpiryTime(expiryTime);
      return thisBuilder;
    }

    public B withReferenceId(String referenceId) {
      this.model.setReferenceId(referenceId);
      return thisBuilder;
    }

    public B withReferenceType(String referenceType) {
      this.model.setReferenceType(referenceType);
      return thisBuilder;
    }

    public B withReferenceName(LocalizedString referenceName) {
      this.model.setReferenceName(referenceName);
      return thisBuilder;
    }

    public B withExternalReference(ExternalRef externalReference) {
      this.model.setExternalRef(externalReference);
      return thisBuilder;
    }

    public B withApiKey(String apiKey) {
      this.model.setApiKey(apiKey);
      return thisBuilder;
    }

    public B withTargetAll(boolean targetAll) {
      this.model.setTargetAll(targetAll);
      return thisBuilder;
    }

    public B withPresentationMessage(LocalizedString presentationMessage) {
      this.model.setPresentationMessage(presentationMessage);
      return thisBuilder;
    }


    @Override
    public B copy(T source) {
      withBanner(source.getBanner());
      withTitle(source.getTitle());
      withConditionsUrl(source.getConditionsUrl());
      withStartTime(source.getExpiryTime());
      withExpiryTime(source.getExpiryTime());
      withReferenceId(source.getReferenceId());
      withReferenceType(source.getReferenceType());
      withReferenceName(source.getReferenceName());
      withExternalReference(source.getExternalRef());
      withApiKey(source.getApiKey());
      withTargetAll(source.isTargetAll());
      withPresentationMessage(source.getPresentationMessage());
      return thisBuilder;
    }
  }
}
