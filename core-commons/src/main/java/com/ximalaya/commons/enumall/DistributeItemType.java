package com.ximalaya.commons.enumall;

public enum DistributeItemType {
    /**
     *  id为itemId的情况
     */
    DEFAULT(0),
    /**
     *  id为声音id的情况
     */
    TRACK(1),
    /**
     *  id为专辑id的情况
     */
    ALBUM(2),
    /**
     *  会员
     */
    MEMBER(3),
    /**
     *  喜点
     */
    XI_COIN(4),
    /**
     *  商品包
     */
    PACKAGE_PRODUCT(5),
    /**
     * 小喜马会员
     */
    XXM_MEMBER(6),
    /**
     * 喜钻
     */
    XI_BEAN(7),
    /**
     *  小喜马硬件端会员
     */
    XXM_HARDWARE_SIDE_MEMBER(8),
    /**
     * 垂类会员
     */
    CLASSIFY_VIP(9),
    /**
     * 绘本会员
     */
    PAINTING_VIP(10),
    /**
     * 极速版金币
     */
    GOLD_COIN(11),
    /**
     * 实物
     */
    PHYSICAL_GOODS(12),
    /**
     *  组合商品
     */
    COMBINED_GOODS(13),
    /**
     * AI按次计费
     */
    AI_CHARGE_BY_PER_TIMES(15),
    /**
     * AI按量计费商品
     */
    AI_CHARGE_BY_VOLUME(16),
    /**
     * AI按时计费商品
     */
    AI_CHARGE_BY_TIME(17),
    /**
     * 大师课会员
     */
    MASTER_CLASS_MEMBER(20),
    /**
     * 白金vip
     */
    PLATINUM_VIP_MEMBER(21),
    /**
     * 动态商品配置
     */
    DYNAMICS_DISTRIBUTE_ITME(-1);

    private final int value;

    private DistributeItemType(int value) {
        this.value = value;
    }

    /**
     * Get the integer value of this enum value, as defined in the Thrift IDL.
     */
    public int getValue() {
        return value;
    }

    /**
     * Find the enum type by its integer value, as defined in the Thrift IDL.
     * @return null if the value is not found.
     */
    public static DistributeItemType findByValue(int value) {
        switch(value) {
            case 0: return DEFAULT;
            case 1: return TRACK;
            case 2: return ALBUM;
            case 3: return MEMBER;
            case 4: return XI_COIN;
            case 5: return PACKAGE_PRODUCT;
            case 6: return XXM_MEMBER;
            case 7: return XI_BEAN;
            case 8: return XXM_HARDWARE_SIDE_MEMBER;
            case 9: return CLASSIFY_VIP;
            case 10: return PAINTING_VIP;
            case 11: return GOLD_COIN;
            case 12: return PHYSICAL_GOODS;
            case 13: return COMBINED_GOODS;
            case 15: return AI_CHARGE_BY_PER_TIMES;
            case 16: return AI_CHARGE_BY_VOLUME;
            case 17: return AI_CHARGE_BY_TIME;
            case 20: return MASTER_CLASS_MEMBER;
            case 21: return PLATINUM_VIP_MEMBER;
            case -1: return DYNAMICS_DISTRIBUTE_ITME;
            default: return null;
        }
    }
}
