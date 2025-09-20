package com.ximalaya.commons.model.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author gongjiawei
 * @date 2025/9/18 下午4:16
 */
public class ProductItemDto {
//    @FieldDocDescription(
//            description = "商品ID"
//    )
    private Long itemId;
//    @FieldDocDescription(
//            description = "产品ID"
//    )
    private Long productId;
//    @FieldDocDescription(
//            description = "产品分类ID"
//    )
    private Integer categoryId;
//    @FieldDocDescription(
//            description = "产品名称"
//    )
    private String productName;
//    @FieldDocDescription(
//            description = "商品名称"
//    )
    private String itemName;
//    @FieldDocDescription(
//            description = "店铺ID"
//    )
    private Long shopId;
//    @FieldDocDescription(
//            description = "产品描述"
//    )
    private String description;
//    @FieldDocDescription(
//            description = "商品单价"
//    )
    private String unitPrice;
//    @FieldDocDescription(
//            description = "商品SKU_ID"
//    )
    private String skuId;
//    @FieldDocDescription(
//            description = "产品SPU_ID"
//    )
    private String spuId;
//    @FieldDocDescription(
//            description = "产品封面图片"
//    )
    private String coverUrl;
//    @FieldDocDescription(
//            description = "商品状态,详见枚举E[product:ProductStatus]E"
//    )
    private Integer statusId;
//    @FieldDocDescription(
//            description = "退货地址ID"
//    )
    private Long returnContactId;
//    @FieldDocDescription(
//            description = "商品属性"
//    )
    private Map<Long, String> itemProperties;
//    @FieldDocDescription(
//            description = "产品属性"
//    )
    private Map<Long, String> productProperties;
//    @FieldDocDescription(
//            description = "更新时间"
//    )
    private Date lastUpdateTime;
//    @FieldDocDescription(
//            description = "产品类型ID，枚举有 1:虚拟，2:实物"
//    )
    private Integer productTypeId;
//    @FieldDocDescription(
//            description = "运费模板ID"
//    )
    private Long shippingTemplateId;
//    @FieldDocDescription(
//            description = "商品图片"
//    )
    private List<String> itemPicture;
//    @FieldDocDescription(
//            description = "店铺分类ID"
//    )
    private Long shopCategoryId;
//    @FieldDocDescription(
//            description = "供应商ID"
//    )
    private Long supplierId;
//    @FieldDocDescription(
//            description = "是否删除"
//    )
    private boolean isDeleted;
//    @FieldDocDescription(
//            description = "是否不可售卖"
//    )
    private Boolean unsalable;
//    @FieldDocDescription(
//            description = "是否不可编辑"
//    )
    private Boolean uneditable;
//    @FieldDocDescription(
//            description = "是否是组合商品"
//    )
    private Boolean composite;
    private List<ProductItemDto> subThriftProductItem;
//    @FieldDocDescription(
//            description = "子商品单价"
//    )
    private String subPrice;
//    @FieldDocDescription(
//            description = "子商品数量"
//    )
    private Integer itemNum;
//    @FieldDocDescription(
//            description = "产品详情URL"
//    )
    private String productDetailUrl;
//    @FieldDocDescription(
//            description = "商品版本号"
//    )
    private Long itemVersionId;

    public ProductItemDto() {
    }

    public Long getItemId() {
        return this.itemId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getItemName() {
        return this.itemName;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUnitPrice() {
        return this.unitPrice;
    }

    public String getSkuId() {
        return this.skuId;
    }

    public String getSpuId() {
        return this.spuId;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public Integer getStatusId() {
        return this.statusId;
    }

    public Long getReturnContactId() {
        return this.returnContactId;
    }

    public Map<Long, String> getItemProperties() {
        return this.itemProperties;
    }

    public Map<Long, String> getProductProperties() {
        return this.productProperties;
    }

    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public Integer getProductTypeId() {
        return this.productTypeId;
    }

    public Long getShippingTemplateId() {
        return this.shippingTemplateId;
    }

    public List<String> getItemPicture() {
        return this.itemPicture;
    }

    public Long getShopCategoryId() {
        return this.shopCategoryId;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public Boolean getUnsalable() {
        return this.unsalable;
    }

    public Boolean getUneditable() {
        return this.uneditable;
    }

    public Boolean getComposite() {
        return this.composite;
    }

    public List<ProductItemDto> getSubThriftProductItem() {
        return this.subThriftProductItem;
    }

    public String getSubPrice() {
        return this.subPrice;
    }

    public Integer getItemNum() {
        return this.itemNum;
    }

    public String getProductDetailUrl() {
        return this.productDetailUrl;
    }

    public Long getItemVersionId() {
        return this.itemVersionId;
    }

    public void setItemId(final Long itemId) {
        this.itemId = itemId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    public void setCategoryId(final Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    public void setShopId(final Long shopId) {
        this.shopId = shopId;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setUnitPrice(final String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setSkuId(final String skuId) {
        this.skuId = skuId;
    }

    public void setSpuId(final String spuId) {
        this.spuId = spuId;
    }

    public void setCoverUrl(final String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setStatusId(final Integer statusId) {
        this.statusId = statusId;
    }

    public void setReturnContactId(final Long returnContactId) {
        this.returnContactId = returnContactId;
    }

    public void setItemProperties(final Map<Long, String> itemProperties) {
        this.itemProperties = itemProperties;
    }

    public void setProductProperties(final Map<Long, String> productProperties) {
        this.productProperties = productProperties;
    }

    public void setLastUpdateTime(final Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setProductTypeId(final Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public void setShippingTemplateId(final Long shippingTemplateId) {
        this.shippingTemplateId = shippingTemplateId;
    }

    public void setItemPicture(final List<String> itemPicture) {
        this.itemPicture = itemPicture;
    }

    public void setShopCategoryId(final Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public void setSupplierId(final Long supplierId) {
        this.supplierId = supplierId;
    }

    public void setDeleted(final boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setUnsalable(final Boolean unsalable) {
        this.unsalable = unsalable;
    }

    public void setUneditable(final Boolean uneditable) {
        this.uneditable = uneditable;
    }

    public void setComposite(final Boolean composite) {
        this.composite = composite;
    }

    public void setSubThriftProductItem(final List<ProductItemDto> subThriftProductItem) {
        this.subThriftProductItem = subThriftProductItem;
    }

    public void setSubPrice(final String subPrice) {
        this.subPrice = subPrice;
    }

    public void setItemNum(final Integer itemNum) {
        this.itemNum = itemNum;
    }

    public void setProductDetailUrl(final String productDetailUrl) {
        this.productDetailUrl = productDetailUrl;
    }

    public void setItemVersionId(final Long itemVersionId) {
        this.itemVersionId = itemVersionId;
    }
@Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ProductItemDto)) {
            return false;
        } else {
            ProductItemDto other = (ProductItemDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label363: {
                    Object this$itemId = this.getItemId();
                    Object other$itemId = other.getItemId();
                    if (this$itemId == null) {
                        if (other$itemId == null) {
                            break label363;
                        }
                    } else if (this$itemId.equals(other$itemId)) {
                        break label363;
                    }

                    return false;
                }

                Object this$productId = this.getProductId();
                Object other$productId = other.getProductId();
                if (this$productId == null) {
                    if (other$productId != null) {
                        return false;
                    }
                } else if (!this$productId.equals(other$productId)) {
                    return false;
                }

                Object this$categoryId = this.getCategoryId();
                Object other$categoryId = other.getCategoryId();
                if (this$categoryId == null) {
                    if (other$categoryId != null) {
                        return false;
                    }
                } else if (!this$categoryId.equals(other$categoryId)) {
                    return false;
                }

                label342: {
                    Object this$productName = this.getProductName();
                    Object other$productName = other.getProductName();
                    if (this$productName == null) {
                        if (other$productName == null) {
                            break label342;
                        }
                    } else if (this$productName.equals(other$productName)) {
                        break label342;
                    }

                    return false;
                }

                label335: {
                    Object this$itemName = this.getItemName();
                    Object other$itemName = other.getItemName();
                    if (this$itemName == null) {
                        if (other$itemName == null) {
                            break label335;
                        }
                    } else if (this$itemName.equals(other$itemName)) {
                        break label335;
                    }

                    return false;
                }

                label328: {
                    Object this$shopId = this.getShopId();
                    Object other$shopId = other.getShopId();
                    if (this$shopId == null) {
                        if (other$shopId == null) {
                            break label328;
                        }
                    } else if (this$shopId.equals(other$shopId)) {
                        break label328;
                    }

                    return false;
                }

                Object this$description = this.getDescription();
                Object other$description = other.getDescription();
                if (this$description == null) {
                    if (other$description != null) {
                        return false;
                    }
                } else if (!this$description.equals(other$description)) {
                    return false;
                }

                label314: {
                    Object this$unitPrice = this.getUnitPrice();
                    Object other$unitPrice = other.getUnitPrice();
                    if (this$unitPrice == null) {
                        if (other$unitPrice == null) {
                            break label314;
                        }
                    } else if (this$unitPrice.equals(other$unitPrice)) {
                        break label314;
                    }

                    return false;
                }

                Object this$skuId = this.getSkuId();
                Object other$skuId = other.getSkuId();
                if (this$skuId == null) {
                    if (other$skuId != null) {
                        return false;
                    }
                } else if (!this$skuId.equals(other$skuId)) {
                    return false;
                }

                label300: {
                    Object this$spuId = this.getSpuId();
                    Object other$spuId = other.getSpuId();
                    if (this$spuId == null) {
                        if (other$spuId == null) {
                            break label300;
                        }
                    } else if (this$spuId.equals(other$spuId)) {
                        break label300;
                    }

                    return false;
                }

                Object this$coverUrl = this.getCoverUrl();
                Object other$coverUrl = other.getCoverUrl();
                if (this$coverUrl == null) {
                    if (other$coverUrl != null) {
                        return false;
                    }
                } else if (!this$coverUrl.equals(other$coverUrl)) {
                    return false;
                }

                Object this$statusId = this.getStatusId();
                Object other$statusId = other.getStatusId();
                if (this$statusId == null) {
                    if (other$statusId != null) {
                        return false;
                    }
                } else if (!this$statusId.equals(other$statusId)) {
                    return false;
                }

                label279: {
                    Object this$returnContactId = this.getReturnContactId();
                    Object other$returnContactId = other.getReturnContactId();
                    if (this$returnContactId == null) {
                        if (other$returnContactId == null) {
                            break label279;
                        }
                    } else if (this$returnContactId.equals(other$returnContactId)) {
                        break label279;
                    }

                    return false;
                }

                label272: {
                    Object this$itemProperties = this.getItemProperties();
                    Object other$itemProperties = other.getItemProperties();
                    if (this$itemProperties == null) {
                        if (other$itemProperties == null) {
                            break label272;
                        }
                    } else if (this$itemProperties.equals(other$itemProperties)) {
                        break label272;
                    }

                    return false;
                }

                Object this$productProperties = this.getProductProperties();
                Object other$productProperties = other.getProductProperties();
                if (this$productProperties == null) {
                    if (other$productProperties != null) {
                        return false;
                    }
                } else if (!this$productProperties.equals(other$productProperties)) {
                    return false;
                }

                Object this$lastUpdateTime = this.getLastUpdateTime();
                Object other$lastUpdateTime = other.getLastUpdateTime();
                if (this$lastUpdateTime == null) {
                    if (other$lastUpdateTime != null) {
                        return false;
                    }
                } else if (!this$lastUpdateTime.equals(other$lastUpdateTime)) {
                    return false;
                }

                label251: {
                    Object this$productTypeId = this.getProductTypeId();
                    Object other$productTypeId = other.getProductTypeId();
                    if (this$productTypeId == null) {
                        if (other$productTypeId == null) {
                            break label251;
                        }
                    } else if (this$productTypeId.equals(other$productTypeId)) {
                        break label251;
                    }

                    return false;
                }

                Object this$shippingTemplateId = this.getShippingTemplateId();
                Object other$shippingTemplateId = other.getShippingTemplateId();
                if (this$shippingTemplateId == null) {
                    if (other$shippingTemplateId != null) {
                        return false;
                    }
                } else if (!this$shippingTemplateId.equals(other$shippingTemplateId)) {
                    return false;
                }

                Object this$itemPicture = this.getItemPicture();
                Object other$itemPicture = other.getItemPicture();
                if (this$itemPicture == null) {
                    if (other$itemPicture != null) {
                        return false;
                    }
                } else if (!this$itemPicture.equals(other$itemPicture)) {
                    return false;
                }

                label230: {
                    Object this$shopCategoryId = this.getShopCategoryId();
                    Object other$shopCategoryId = other.getShopCategoryId();
                    if (this$shopCategoryId == null) {
                        if (other$shopCategoryId == null) {
                            break label230;
                        }
                    } else if (this$shopCategoryId.equals(other$shopCategoryId)) {
                        break label230;
                    }

                    return false;
                }

                label223: {
                    Object this$supplierId = this.getSupplierId();
                    Object other$supplierId = other.getSupplierId();
                    if (this$supplierId == null) {
                        if (other$supplierId == null) {
                            break label223;
                        }
                    } else if (this$supplierId.equals(other$supplierId)) {
                        break label223;
                    }

                    return false;
                }

                if (this.isDeleted() != other.isDeleted()) {
                    return false;
                } else {
                    label215: {
                        Object this$unsalable = this.getUnsalable();
                        Object other$unsalable = other.getUnsalable();
                        if (this$unsalable == null) {
                            if (other$unsalable == null) {
                                break label215;
                            }
                        } else if (this$unsalable.equals(other$unsalable)) {
                            break label215;
                        }

                        return false;
                    }

                    label208: {
                        Object this$uneditable = this.getUneditable();
                        Object other$uneditable = other.getUneditable();
                        if (this$uneditable == null) {
                            if (other$uneditable == null) {
                                break label208;
                            }
                        } else if (this$uneditable.equals(other$uneditable)) {
                            break label208;
                        }

                        return false;
                    }

                    Object this$composite = this.getComposite();
                    Object other$composite = other.getComposite();
                    if (this$composite == null) {
                        if (other$composite != null) {
                            return false;
                        }
                    } else if (!this$composite.equals(other$composite)) {
                        return false;
                    }

                    Object this$subThriftProductItem = this.getSubThriftProductItem();
                    Object other$subThriftProductItem = other.getSubThriftProductItem();
                    if (this$subThriftProductItem == null) {
                        if (other$subThriftProductItem != null) {
                            return false;
                        }
                    } else if (!this$subThriftProductItem.equals(other$subThriftProductItem)) {
                        return false;
                    }

                    label187: {
                        Object this$subPrice = this.getSubPrice();
                        Object other$subPrice = other.getSubPrice();
                        if (this$subPrice == null) {
                            if (other$subPrice == null) {
                                break label187;
                            }
                        } else if (this$subPrice.equals(other$subPrice)) {
                            break label187;
                        }

                        return false;
                    }

                    label180: {
                        Object this$itemNum = this.getItemNum();
                        Object other$itemNum = other.getItemNum();
                        if (this$itemNum == null) {
                            if (other$itemNum == null) {
                                break label180;
                            }
                        } else if (this$itemNum.equals(other$itemNum)) {
                            break label180;
                        }

                        return false;
                    }

                    Object this$productDetailUrl = this.getProductDetailUrl();
                    Object other$productDetailUrl = other.getProductDetailUrl();
                    if (this$productDetailUrl == null) {
                        if (other$productDetailUrl != null) {
                            return false;
                        }
                    } else if (!this$productDetailUrl.equals(other$productDetailUrl)) {
                        return false;
                    }

                    Object this$itemVersionId = this.getItemVersionId();
                    Object other$itemVersionId = other.getItemVersionId();
                    if (this$itemVersionId == null) {
                        if (other$itemVersionId != null) {
                            return false;
                        }
                    } else if (!this$itemVersionId.equals(other$itemVersionId)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductItemDto;
    }
@Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $itemId = this.getItemId();
        result = result * 59 + ($itemId == null ? 43 : $itemId.hashCode());
        Object $productId = this.getProductId();
        result = result * 59 + ($productId == null ? 43 : $productId.hashCode());
        Object $categoryId = this.getCategoryId();
        result = result * 59 + ($categoryId == null ? 43 : $categoryId.hashCode());
        Object $productName = this.getProductName();
        result = result * 59 + ($productName == null ? 43 : $productName.hashCode());
        Object $itemName = this.getItemName();
        result = result * 59 + ($itemName == null ? 43 : $itemName.hashCode());
        Object $shopId = this.getShopId();
        result = result * 59 + ($shopId == null ? 43 : $shopId.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $unitPrice = this.getUnitPrice();
        result = result * 59 + ($unitPrice == null ? 43 : $unitPrice.hashCode());
        Object $skuId = this.getSkuId();
        result = result * 59 + ($skuId == null ? 43 : $skuId.hashCode());
        Object $spuId = this.getSpuId();
        result = result * 59 + ($spuId == null ? 43 : $spuId.hashCode());
        Object $coverUrl = this.getCoverUrl();
        result = result * 59 + ($coverUrl == null ? 43 : $coverUrl.hashCode());
        Object $statusId = this.getStatusId();
        result = result * 59 + ($statusId == null ? 43 : $statusId.hashCode());
        Object $returnContactId = this.getReturnContactId();
        result = result * 59 + ($returnContactId == null ? 43 : $returnContactId.hashCode());
        Object $itemProperties = this.getItemProperties();
        result = result * 59 + ($itemProperties == null ? 43 : $itemProperties.hashCode());
        Object $productProperties = this.getProductProperties();
        result = result * 59 + ($productProperties == null ? 43 : $productProperties.hashCode());
        Object $lastUpdateTime = this.getLastUpdateTime();
        result = result * 59 + ($lastUpdateTime == null ? 43 : $lastUpdateTime.hashCode());
        Object $productTypeId = this.getProductTypeId();
        result = result * 59 + ($productTypeId == null ? 43 : $productTypeId.hashCode());
        Object $shippingTemplateId = this.getShippingTemplateId();
        result = result * 59 + ($shippingTemplateId == null ? 43 : $shippingTemplateId.hashCode());
        Object $itemPicture = this.getItemPicture();
        result = result * 59 + ($itemPicture == null ? 43 : $itemPicture.hashCode());
        Object $shopCategoryId = this.getShopCategoryId();
        result = result * 59 + ($shopCategoryId == null ? 43 : $shopCategoryId.hashCode());
        Object $supplierId = this.getSupplierId();
        result = result * 59 + ($supplierId == null ? 43 : $supplierId.hashCode());
        result = result * 59 + (this.isDeleted() ? 79 : 97);
        Object $unsalable = this.getUnsalable();
        result = result * 59 + ($unsalable == null ? 43 : $unsalable.hashCode());
        Object $uneditable = this.getUneditable();
        result = result * 59 + ($uneditable == null ? 43 : $uneditable.hashCode());
        Object $composite = this.getComposite();
        result = result * 59 + ($composite == null ? 43 : $composite.hashCode());
        Object $subThriftProductItem = this.getSubThriftProductItem();
        result = result * 59 + ($subThriftProductItem == null ? 43 : $subThriftProductItem.hashCode());
        Object $subPrice = this.getSubPrice();
        result = result * 59 + ($subPrice == null ? 43 : $subPrice.hashCode());
        Object $itemNum = this.getItemNum();
        result = result * 59 + ($itemNum == null ? 43 : $itemNum.hashCode());
        Object $productDetailUrl = this.getProductDetailUrl();
        result = result * 59 + ($productDetailUrl == null ? 43 : $productDetailUrl.hashCode());
        Object $itemVersionId = this.getItemVersionId();
        result = result * 59 + ($itemVersionId == null ? 43 : $itemVersionId.hashCode());
        return result;
    }
@Override
    public String toString() {
        return "ProductItemDto(itemId=" + this.getItemId() + ", productId=" + this.getProductId() + ", categoryId=" + this.getCategoryId() + ", productName=" + this.getProductName() + ", itemName=" + this.getItemName() + ", shopId=" + this.getShopId() + ", description=" + this.getDescription() + ", unitPrice=" + this.getUnitPrice() + ", skuId=" + this.getSkuId() + ", spuId=" + this.getSpuId() + ", coverUrl=" + this.getCoverUrl() + ", statusId=" + this.getStatusId() + ", returnContactId=" + this.getReturnContactId() + ", itemProperties=" + this.getItemProperties() + ", productProperties=" + this.getProductProperties() + ", lastUpdateTime=" + this.getLastUpdateTime() + ", productTypeId=" + this.getProductTypeId() + ", shippingTemplateId=" + this.getShippingTemplateId() + ", itemPicture=" + this.getItemPicture() + ", shopCategoryId=" + this.getShopCategoryId() + ", supplierId=" + this.getSupplierId() + ", isDeleted=" + this.isDeleted() + ", unsalable=" + this.getUnsalable() + ", uneditable=" + this.getUneditable() + ", composite=" + this.getComposite() + ", subThriftProductItem=" + this.getSubThriftProductItem() + ", subPrice=" + this.getSubPrice() + ", itemNum=" + this.getItemNum() + ", productDetailUrl=" + this.getProductDetailUrl() + ", itemVersionId=" + this.getItemVersionId() + ")";
    }
}
