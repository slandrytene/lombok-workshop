package com.example.lombokworkshop.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mmayantz on 2016-05-29.
 */
@Document
@CompoundIndexes({
    @CompoundIndex(name = "apiKey", def = "{'apiKey': 1}"),
    @CompoundIndex(name = "sku", def = "{'sku': 1}"),
    @CompoundIndex(name = "external_UID", def = "{'externalRef.id': 1}")
})
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InventoryItem extends BaseModel implements ModelWithExternalReference {

    private String apiKey;
    private String name;
    private String sku;
    private ExternalRef externalRef;

    @Version
    private Long version;

    private int quantity;

    private static final String TYPE = "inventory";

    @Builder
    private InventoryItem(ObjectId id, DateTime createdAt, DateTime updatedAt,
        String apiKey, String name, String sku,
        ExternalRef externalRef, Long version, int quantity) {
        super(id, createdAt, updatedAt);
        this.apiKey = apiKey;
        this.name = name;
        this.sku = sku;
        this.externalRef = externalRef;
        this.version = version;
        this.quantity = quantity;
    }

    @Override
    public ExternalRef getExternalRef() {
        return externalRef;
    }

    @Override
    public void setExternalRef(ExternalRef externalRef) {
        this.externalRef = externalRef;
    }

    public String getType(){
        return InventoryItem.TYPE;
    }

}
