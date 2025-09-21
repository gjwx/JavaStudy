package com.ximalaya.openapi.admin.entity;

import com.ximalaya.business.payable.authorization.query.api.dto.UserPaidAlbumViewDto;
import com.ximalaya.openapi.distribution.commodity.api.thrift.iface.TPromotionPackageView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  商品包信息
 * @author : Danny Liu(幸程)
 * @date : Created in 11:02 of 2020/1/16
 * @version : 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GradientActivityInfos extends QueryParam {
    private List<Long> packAlbumIds;
    private List<Long> userPaidAlbumIds;
    private List<Long> paidAlbumIdsOfPack;
    private List<Long> notPaidAlbumIdsOfPack;
    private TPromotionPackageView packageView;
    private List<UserPaidAlbumViewDto> userPaidAlbumViewDto;
}
