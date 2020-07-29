package io.nuite.modules.system.erp.entity;

import java.io.Serializable;

/**
 * 商品库存包装类
 */

public class GoodsStock implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private String goodsCode;

    /**
     * 颜色id
     */
    private String colorCode;

    private String colorName;

    /**
     * 尺码id
     */
    private String sizeCode;

    private String sizeName;

    /**
     * 库存 在库数量
     */
    private Integer count;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GoodsStock{");
        sb.append("goodsCode='").append(goodsCode).append('\'');
        sb.append(", colorCode='").append(colorCode).append('\'');
        sb.append(", colorName='").append(colorName).append('\'');
        sb.append(", sizeCode='").append(sizeCode).append('\'');
        sb.append(", sizeName='").append(sizeName).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
