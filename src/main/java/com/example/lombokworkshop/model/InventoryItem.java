package com.example.lombokworkshop.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(Include.NON_NULL)
public class InventoryItem extends BaseModel implements ModelWithExternalReference {

    private String apiKey;
    private String name;
    private String sku;

    private ExternalRef externalRef;

    @Version
    private Long version;

    private int quantity;

    private static final String TYPE = "inventory";

    public InventoryItem() {
        super();
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

    public String getApiKey() {
        return apiKey;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable(){
        return this.getQuantity() > 0;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
            "apiKey='" + apiKey + '\'' +
            ", name='" + name + '\'' +
            ", sku='" + sku + '\'' +
            ", externalRef=" + externalRef +
            ", version=" + version +
            ", quantity=" + quantity +
            ", id=" + id +
            '}';
    }
}
