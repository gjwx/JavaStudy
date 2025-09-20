package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *   获取内容包中已购专辑信息
 * @author : Danny Liu(幸程)
 * @date : Created in 14:59 of 2020/1/2
 * @version : 1.0
 */
@JsonPropertyOrder({"pack_price", "paid_item_of_pack", "not_paid_item_of_pack"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackPaidAlbumResult {

    /**
     * 获取用户的商品包价格 = 商品包价格 - 商品包内用户已购买的专辑价格
     */
    @JsonProperty("pack_price")
    @JsonInclude(Include.NON_NULL)
    private String needPaidMoney;

    @JsonProperty("paid_item_of_pack")
    @JsonInclude(Include.NON_NULL)
    private List<PromotionItemDTO> paidItemIdsOfPack;

    @JsonProperty("not_paid_item_of_pack")
    @JsonInclude(Include.NON_NULL)
    private List<PromotionItemDTO> notPaidItemIdsOfPack;
}
