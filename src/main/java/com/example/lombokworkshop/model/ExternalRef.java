package com.example.lombokworkshop.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Getter
@Builder
public class ExternalRef {

  @Field(value = "_id")
  private String id;
  private String source;
  private String updatedAt;

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

}

