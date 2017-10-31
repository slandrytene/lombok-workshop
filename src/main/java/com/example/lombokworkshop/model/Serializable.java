package com.example.lombokworkshop.model;

import org.joda.time.DateTime;

public interface Serializable {

  public abstract String getType();

  public String getId();

  public DateTime getUpdatedAt();

  public DateTime getCreatedAt();
}
