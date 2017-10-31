package com.example.lombokworkshop.model;

import org.bson.types.ObjectId;

public abstract class EmbeddedBaseModel extends BaseModel {

  public EmbeddedBaseModel() {
    super(ObjectId.get());
  }
}
