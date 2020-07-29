package io.nuite.modules.online_sales_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 0
 *
 * @author admin
 * @email
 * @date 2018-04-16 15:41:03
 */
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_ShoesInfo")
public class ShoesInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;
    /**
     * 鞋子序号
     */
    @TableField(value = "Shoes_Seq")
    private Integer shoesSeq;
    /**
     * 吊牌价
     */
    @TableField(value = "TagPrice")
    private BigDecimal tagPrice;
    /**
     * 零售价
     */
    @TableField(value = "SalePrice")
    private BigDecimal salePrice;
    /**
     * 人工热销爆款推荐 0 不是 1是 最多3个
     */
    @TableField(value = "IsHotSale")
    private Integer isHotSale;
    /**
     * 人工推荐新品推荐 0 不是 1是 最多5个
     */
    @TableField(value = "IsNewest")
    private Integer isNewest;
    /**
     * 备注
     */
    @TableField(value = "Remark")
    private String remark;
    /**
     * 是否删除
     */
    @TableLogic
    @TableField(value = "Del")
    private Integer del;

    @TableField(value = "OnSale")
    private Integer onSale;

    /**
     * 货品小程序码
     */
    @TableField(value = "WxQRCode")
    private String wxQRCode;

    /**
     * 生产厂家
     */
    @TableField(value = "Factory")
    private String factory;

    /**
     * 浏览次数
     */
    @TableField(value = "Browse_Num")
    private Integer browseNum;
    
    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    /**
     * 设置：
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取：
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置：鞋子序号
     */
    public void setShoesSeq(Integer shoesSeq) {
        this.shoesSeq = shoesSeq;
    }

    /**
     * 获取：鞋子序号
     */
    public Integer getShoesSeq() {
        return shoesSeq;
    }

    public BigDecimal getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(BigDecimal tagPrice) {
		this.tagPrice = tagPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	/**
     * 设置：人工热销爆款推荐 0 不是 1是 最多3个
     */
    public void setIsHotSale(Integer isHotSale) {
        this.isHotSale = isHotSale;
    }

    /**
     * 获取：人工热销爆款推荐 0 不是 1是 最多3个
     */
    public Integer getIsHotSale() {
        return isHotSale;
    }

    /**
     * 设置：人工推荐新品推荐 0 不是 1是 最多5个
     */
    public void setIsNewest(Integer isNewest) {
        this.isNewest = isNewest;
    }

    /**
     * 获取：人工推荐新品推荐 0 不是 1是 最多5个
     */
    public Integer getIsNewest() {
        return isNewest;
    }

    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置：是否删除
     */
    public void setDel(Integer del) {
        this.del = del;
    }

    /**
     * 获取：是否删除
     */
    public Integer getDel() {
        return del;
    }


    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public String getWxQRCode() {
        return wxQRCode;
    }

    public void setWxQRCode(String wxQRCode) {
        this.wxQRCode = wxQRCode;
    }

	public Integer getBrowseNum() {
		return browseNum;
	}

	public void setBrowseNum(Integer browseNum) {
		this.browseNum = browseNum;
	}
    
}
