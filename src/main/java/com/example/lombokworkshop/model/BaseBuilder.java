package com.example.lombokworkshop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseBuilder<M, B extends BaseBuilder<M, B>> {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  protected Class<? extends M> mClass;
  protected B thisBuilder;
  protected M model;

  public BaseBuilder(Class<? extends M> mClass) {
    this.mClass = mClass;
    try {
      this.model = create();
      thisBuilder = (B) this;

    } catch (IllegalAccessException | InstantiationException e) {
      log.warn(e.getMessage(), e);

    }
  }

  public M getModel() {
    return model;
  }

  public void setModel(M model) {
    this.model = model;
  }

  protected M create() throws IllegalAccessException, InstantiationException {
    return mClass.newInstance();
  }

  public abstract B copy(M source);

  public M build() {
    M instance = this.model;
    try {
      this.model = create();

    } catch (IllegalAccessException | InstantiationException e) {
      log.warn(e.getMessage(), e);

    }
    return instance;
  }
}

