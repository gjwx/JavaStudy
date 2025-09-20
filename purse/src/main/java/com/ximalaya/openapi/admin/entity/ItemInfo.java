package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo {
    @JsonProperty("item_id")
    private String itemId;
    @JsonProperty("quantity")
    private int quantity;

}
