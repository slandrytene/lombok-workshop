package com.example.lombokworkshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.convert.LazyLoadingProxy;

@JsonIgnoreProperties(value = {"type"}, allowGetters = true)
public abstract class BaseModel extends Object implements Serializable {

  @Id
  protected ObjectId id;

  @CreatedDate
  private DateTime createdAt;
  //	@LastModifiedDate
  private DateTime updatedAt;

  public BaseModel() {
    super();
  }

  public BaseModel(ObjectId id) {
    this();
    this.setId(id);
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  @Override
  public String getId() {
    if (this.id == null) {
      return null;
    }

    return this.id.toString();
  }

  @JsonIgnore
  public ObjectId getObjectId() {
    return this.id;
  }

  @JsonIgnore
  public DBRef getDBRef() {
    return new DBRef(this.getType(), this.getObjectId());
  }

  public static DBRef getDBRef(BaseModel model) {
    if (model instanceof LazyLoadingProxy) {
      return ((LazyLoadingProxy) model).toDBRef();
    }
    return model.getDBRef();
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = new ObjectId(id);
  }

  @Override
  public DateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public DateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return super.toString() + "#Id:" + this.getId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BaseModel baseModel = (BaseModel) o;

    return id != null ? id.equals(baseModel.id) : baseModel.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  public abstract static class AbstractBuilder<M extends BaseModel, B extends AbstractBuilder<M, B>> extends BaseBuilder<M, B> {

    protected AbstractBuilder(Class<M> mClass) {
      super(mClass);
    }

    public B withId(String id) {
      this.model.setId(id);
      return thisBuilder;
    }

    public B withExternalRef(ExternalRef externalRef) {
      if (this.model instanceof ModelWithExternalReference) {
        ((ModelWithExternalReference) this.model).setExternalRef(externalRef);
      } else {
        throw new IllegalStateException("Trying to set external reference on model without one");
      }
      return thisBuilder;
    }

    public B withCreatedAt(String createdAt) {
      return withCreatedAt(DateTime.parse(createdAt));
    }

    public B withCreatedAt(DateTime createdAt) {
      this.model.setCreatedAt(createdAt);
      return thisBuilder;
    }

    public B withUpdatedAt(String updatedAt) {
      return withUpdatedAt(DateTime.parse(updatedAt));
    }

    public B withUpdatedAt(DateTime updatedAt) {
      this.model.setUpdatedAt(updatedAt);
      return thisBuilder;
    }

    @Override
    public B copy(M source) {
      withId(source.getId());
      withCreatedAt(source.getCreatedAt());
      withUpdatedAt(source.getUpdatedAt());
      if (source instanceof ModelWithExternalReference) {
        withExternalRef(((ModelWithExternalReference) source).getExternalRef());
      }
      return thisBuilder;
    }
  }
}
