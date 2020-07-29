package io.nuite.modules.system.entity.online_sale;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

/**
 * 0
 * 
 * @author admin
 * @email
 * @date 2018-04-11 11:29:58
 */
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_ShoesInfo")
public class OnlineShoesInfoEntity implements Serializable {
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
     * 货品所属新款或是活动等类型
     */
    @TableField(value = "Topic_Seq")
    private Integer topicSeq;
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

    public void setTopicSeq(Integer topicSeq) {
        this.topicSeq = topicSeq;
    }

    public Integer getTopicSeq() {
        return topicSeq;
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

}
