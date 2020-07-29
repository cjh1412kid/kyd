package io.nuite.modules.online_sales_app.utils;

public enum TopicType {
    //0:热销爆款,1:新品特推,2:商家促销,3:明星同款,4:精选专题,5:品牌活动,6:猜你喜欢
    HOT, NEW, SALE, STAR, SELECT, BRAND, LIKE;

    public static TopicType valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }
}
