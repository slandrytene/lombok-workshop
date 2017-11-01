package com.example.lombokworkshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DBRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.convert.LazyLoadingProxy;

@JsonIgnoreProperties(value = {"type"}, allowGetters = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseModel extends Object implements Serializable {

  @Id
  protected ObjectId id;

  @CreatedDate
  private DateTime createdAt;
  //	@LastModifiedDate
  private DateTime updatedAt;


  public BaseModel(ObjectId id) {
    this();
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
  public DBRef getDBRef() {
    return new DBRef(this.getType(), this.id);
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
  public String toString() {
    return super.toString() + "#Id:" + this.getId();
  }


}
