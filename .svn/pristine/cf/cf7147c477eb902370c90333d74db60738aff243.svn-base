package io.nuite.modules.system.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/8 16:59
 * @Version: 1.0
 * @Description:
 */

public class GoodsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品序号
     */
    private Integer seq;

    /**
     * 商品编号
     */
    private String code;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 标准售价
     */
    private BigDecimal salePrice;

    /**
     * 售价1
     */
    private BigDecimal sj1;

    /**
     * 售价2
     */
    private BigDecimal sj2;

    /**
     * 售价4
     */
    private BigDecimal sj4;
    /**
     * 大类
     */
    private String categoryCode;
    /**
     * 季节
     */
    private String seasonName;
    private String seasonCode;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 附加属性1
     */
    private String sx1;
    /**
     * 附加属性2
     */
    private String sx2;
    /**
     * 附加属性3
     */
    private String sx3;
    /**
     * 附加属性4
     */
    private String sx4;
    /**
     * 附加属性5
     */
    private String sx5;
    /**
     * 附加属性6
     */
    private String sx6;
    /**
     * 附加属性7
     */
    private String sx7;
    /**
     * 附加属性8
     */
    private String sx8;
    /**
     * 附加属性9
     */
    private String sx9;
    /**
     * 附加属性10
     */
    private String sx10;
    /**
     * 附加属性11
     */
    private String sx11;
    /**
     * 附加属性12
     */
    private String sx12;
    /**
     * 附加属性13
     */
    private String sx13;
    /**
     * 附加属性14
     */
    private String sx14;
    /**
     * 附加属性15
     */
    private String sx15;
    /**
     * 附加属性16
     */
    private String sx16;

    /**
     * 品牌
     */
    private String brand;
    /**
     * 助记符
     */
    private String ZJF;

    /**
     * 批发商价格
     */
    private BigDecimal wholesalePrice;
    /**
     * 直营店价格 成本价
     */
    private BigDecimal basePrice;

    private List<GoodsStock> stocks;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getSx1() {
        return sx1;
    }

    public void setSx1(String sx1) {
        this.sx1 = sx1;
    }

    public String getSx2() {
        return sx2;
    }

    public void setSx2(String sx2) {
        this.sx2 = sx2;
    }

    public String getSx3() {
        return sx3;
    }

    public void setSx3(String sx3) {
        this.sx3 = sx3;
    }

    public String getSx4() {
        return sx4;
    }

    public void setSx4(String sx4) {
        this.sx4 = sx4;
    }

    public String getSx5() {
        return sx5;
    }

    public void setSx5(String sx5) {
        this.sx5 = sx5;
    }

    public String getSx6() {
        return sx6;
    }

    public void setSx6(String sx6) {
        this.sx6 = sx6;
    }

    public String getSx7() {
        return sx7;
    }

    public void setSx7(String sx7) {
        this.sx7 = sx7;
    }

    public String getSx8() {
        return sx8;
    }

    public void setSx8(String sx8) {
        this.sx8 = sx8;
    }

    public String getSx9() {
        return sx9;
    }

    public void setSx9(String sx9) {
        this.sx9 = sx9;
    }

    public String getSx10() {
        return sx10;
    }

    public void setSx10(String sx10) {
        this.sx10 = sx10;
    }

    public String getSx11() {
        return sx11;
    }

    public void setSx11(String sx11) {
        this.sx11 = sx11;
    }

    public String getSx12() {
        return sx12;
    }

    public void setSx12(String sx12) {
        this.sx12 = sx12;
    }

    public String getSx13() {
        return sx13;
    }

    public void setSx13(String sx13) {
        this.sx13 = sx13;
    }

    public String getSx14() {
        return sx14;
    }

    public void setSx14(String sx14) {
        this.sx14 = sx14;
    }

    public String getSx15() {
        return sx15;
    }

    public void setSx15(String sx15) {
        this.sx15 = sx15;
    }

    public String getSx16() {
        return sx16;
    }

    public void setSx16(String sx16) {
        this.sx16 = sx16;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
        if (seasonName != null) {
            if (seasonName.contains("春季")) {
                this.seasonCode = "001";
            } else if (seasonName.contains("夏季")) {
                this.seasonCode = "002";
            } else if (seasonName.contains("秋季")) {
                this.seasonCode = "003";
            } else if (seasonName.contains("冬季")) {
                this.seasonCode = "004";
            } else if (seasonName.contains("全年")) {
                this.seasonCode = "007";
            }
        }
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public List<GoodsStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<GoodsStock> stocks) {
        this.stocks = stocks;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSj1() {
        return sj1;
    }

    public void setSj1(BigDecimal sj1) {
        this.sj1 = sj1;
    }

    public BigDecimal getSj4() {
        return sj4;
    }

    public void setSj4(BigDecimal sj4) {
        this.sj4 = sj4;
    }

    public BigDecimal getSj2() {
        return sj2;
    }

    public void setSj2(BigDecimal sj2) {
        this.sj2 = sj2;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getZJF() {
        return ZJF;
    }

    public void setZJF(String ZJF) {
        this.ZJF = ZJF;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GoodsVo{");
        sb.append("seq=").append(seq);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", salePrice=").append(salePrice);
        sb.append(", sj1=").append(sj1);
        sb.append(", sj4=").append(sj4);
        sb.append(", categoryCode='").append(categoryCode).append('\'');
        sb.append(", seasonName='").append(seasonName).append('\'');
        sb.append(", seasonCode='").append(seasonCode).append('\'');
        sb.append(", year=").append(year);
        sb.append(", sx1='").append(sx1).append('\'');
        sb.append(", sx2='").append(sx2).append('\'');
        sb.append(", sx3='").append(sx3).append('\'');
        sb.append(", sx4='").append(sx4).append('\'');
        sb.append(", sx5='").append(sx5).append('\'');
        sb.append(", sx6='").append(sx6).append('\'');
        sb.append(", sx7='").append(sx7).append('\'');
        sb.append(", sx8='").append(sx8).append('\'');
        sb.append(", sx9='").append(sx9).append('\'');
        sb.append(", sx10='").append(sx10).append('\'');
        sb.append(", sx11='").append(sx11).append('\'');
        sb.append(", sx12='").append(sx12).append('\'');
        sb.append(", sx13='").append(sx13).append('\'');
        sb.append(", sx14='").append(sx14).append('\'');
        sb.append(", sx15='").append(sx15).append('\'');
        sb.append(", sx16='").append(sx16).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", ZJF='").append(ZJF).append('\'');
        sb.append(", wholesalePrice=").append(wholesalePrice);
        sb.append(", basePrice=").append(basePrice);
        sb.append(", stocks=").append(stocks);
        sb.append('}');
        return sb.toString();
    }
}
