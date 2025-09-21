package com.ximalaya.openapi.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradientActivityPaidAlbumResult {

    @JsonProperty("free_track_ids")
    @JsonInclude(Include.NON_NULL)
    private List<Long> freeTrackIds;

    @JsonProperty("pay_track_ids")
    @JsonInclude(Include.NON_NULL)
    private List<Long> needPaidTrackIds;

    @JsonProperty("user_bought_track_ids")
    @JsonInclude(Include.NON_NULL)
    private List<Long> userBoughtTrackIds;

    @JsonProperty("total_price")
    @JsonInclude(Include.NON_NULL)
    private String discountPrice;

    @JsonProperty("total_gradient_discount_price")
    @JsonInclude(Include.NON_NULL)
    private String discountPriceWithGradientActivity;
}
