package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 0
 *
 * @author admin
 * @email
 * @date 2018-04-11 11:29:58
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM + "YHSR_OP_ShoesInfo")
public class ShoesInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;
    /**
     * 鞋子序号(外键:YHSR_Goods_Shoes表)
     */
    @TableField(value = "Shoes_Seq")
    private Integer shoesSeq;
    /**
     * 贴牌商价格
     */
    @TableField(value = "OemPrice")
    private BigDecimal oemPrice;
    /**
     * 批发商价格
     */
    @TableField(value = "WholesalerPrice")
    private BigDecimal wholesalerPrice;
    /**
     * 直营店价格
     */
    @TableField(value = "StorePrice")
    private BigDecimal storePrice;
    /**
     * 销售价格
     */
    @TableField(value = "SalePrice")
    private BigDecimal salePrice;
    /**
     * 人工热卖爆款推荐(0:否, 1:是    最多三个)
     */
    @TableField(value = "IsHotSale")
    private Integer isHotSale;
    /**
     * 人工推荐新品推荐(0:否, 1:是    最多5个)
     */
    @TableField(value = "IsNewest")
    private Integer isNewest;

    /**
     * 鞋子搜索次数
     */
    @TableField(value = "SearchTimes")
    private Integer searchTimes;
    /**
     * 是否上架（0:下架 1:上架）
     */
    @TableField(value = "OnSale")
    private Integer onSale;
    /**
     * 上架时间
     */
    @TableField(value = "OnSaleTime")
    private Date onSaleTime;
    /**
     * 下架时间
     */
    @TableField(value = "OffSaleTime")
    private Date offSaleTime;
    /**
     * 备注
     */
    @TableField(value = "Remark")
    private String remark;
    /**
     * 删除标识(0:未删除,1:已删除)
     */
    @TableLogic
    @TableField(value = "Del")
    private Integer del;

    /**
     * 设置：序号(主键)
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取：序号(主键)
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置：鞋子序号(外键:YHSR_Goods_Shoes表)
     */
    public void setShoesSeq(Integer shoesSeq) {
        this.shoesSeq = shoesSeq;
    }

    /**
     * 获取：鞋子序号(外键:YHSR_Goods_Shoes表)
     */
    public Integer getShoesSeq() {
        return shoesSeq;
    }

    public BigDecimal getOemPrice() {
		return oemPrice;
	}

	public void setOemPrice(BigDecimal oemPrice) {
		this.oemPrice = oemPrice;
	}

	public BigDecimal getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(BigDecimal wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	public BigDecimal getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(BigDecimal storePrice) {
		this.storePrice = storePrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public Date getOnSaleTime() {
		return onSaleTime;
	}

	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}

	public Date getOffSaleTime() {
        return offSaleTime;
    }

    public void setOffSaleTime(Date offSaleTime) {
        this.offSaleTime = offSaleTime;
    }

    /**
     * 设置：人工热卖爆款推荐(0:否, 1:是    最多三个)
     */
    public void setIsHotSale(Integer isHotSale) {
        this.isHotSale = isHotSale;
    }

    /**
     * 获取：人工热卖爆款推荐(0:否, 1:是    最多三个)
     */
    public Integer getIsHotSale() {
        return isHotSale;
    }

    /**
     * 设置：人工推荐新品推荐(0:否, 1:是    最多5个)
     */
    public void setIsNewest(Integer isNewest) {
        this.isNewest = isNewest;
    }

    /**
     * 获取：人工推荐新品推荐(0:否, 1:是    最多5个)
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

    public Integer getSearchTimes() {
        return searchTimes;
    }

    public void setSearchTimes(Integer searchTimes) {
        this.searchTimes = searchTimes;
    }

    /**
     * 设置：删除标识(0:未删除,1:已删除)
     */
    public void setDel(Integer del) {
        this.del = del;
    }

    /**
     * 获取：删除标识(0:未删除,1:已删除)
     */
    public Integer getDel() {
        return del;
    }
}
