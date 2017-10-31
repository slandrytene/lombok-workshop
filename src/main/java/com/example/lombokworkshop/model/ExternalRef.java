package com.example.lombokworkshop.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Field;

public class ExternalRef {

  @Field(value = "_id")
  private String id;
  private String source;
  private String updatedAt;

  public String getSource() {
    return source;
  }

  protected void setSource(String source) {
    this.source = source;
  }

  public String getId() {
    return id;
  }

  protected void setId(String id) {
    this.id = id;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  protected void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean matches(ExternalRef other) {
    return (other != null) && source.equals(other.getSource()) && id.equals(other.getId());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("source", source)
        .append("id", id)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ExternalRef that = (ExternalRef) o;

    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }
    return source != null ? source.equals(that.source) : that.source == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (source != null ? source.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder extends BaseBuilder<ExternalRef, Builder> {

    public Builder() {
      super(ExternalRef.class);
    }

    public Builder withSource(String externalType) {
      this.model.setSource(externalType);
      return thisBuilder;
    }

    public Builder withId(String externalId) {
      this.model.setId(externalId);
      return thisBuilder;
    }

    public Builder withUpdatedAt(String updatedAt) {
      this.model.setUpdatedAt(updatedAt);
      return thisBuilder;
    }

    @Override
    public Builder copy(ExternalRef source) {
      withSource(source.getSource());
      withId(source.getId());
      withUpdatedAt(source.getUpdatedAt());
      return thisBuilder;
    }
  }
}

