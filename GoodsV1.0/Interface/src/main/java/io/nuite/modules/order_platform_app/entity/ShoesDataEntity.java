package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;

/**
 * 0
 *
 * @author admin
 * @email
 * @date 2018-04-11 11:29:58
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM + "YHSR_OP_ShoesData")
public class ShoesDataEntity implements Serializable {
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
     * 颜色序号(外键:YHSR_Goods_Color表)
     */
    @TableField(value = "Color_Seq")
    private Integer colorSeq;
    /**
     * 尺码
     */
    @TableField(value = "Size_Seq")
    private Integer sizeSeq;
    /**
     * 总数量
     */
    @TableField(value = "Num")
    private Integer num;
    /**
     * 库存修改时间
     */
    @TableField(value = "StockDate")
    private Date stockDate;
    /**
     * 库存量
     */
    @TableField(value = "Stock")
    private Integer stock;
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

    //自定义字段
    /**
     * 颜色名称
     */
    @TableField(exist = false)
    private String colorName;

    /**
     * 颜色代码
     */
    @TableField(exist = false)
    private String code;

    /**
     * 尺码名称
     */
    @TableField(exist = false)
    private String sizeName;
    /**
     * 尺码代码
     */
    @TableField(exist = false)
    private String sizeCode;

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

    public Integer getColorSeq() {
        return colorSeq;
    }

    public void setColorSeq(Integer colorSeq) {
        this.colorSeq = colorSeq;
    }

    public Integer getSizeSeq() {
        return sizeSeq;
    }

    public void setSizeSeq(Integer sizeSeq) {
        this.sizeSeq = sizeSeq;
    }

    /**
     * 设置：总数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取：总数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置：库存修改时间
     */
    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }

    /**
     * 获取：库存修改时间
     */
    public Date getStockDate() {
        return stockDate;
    }

    /**
     * 设置：库存量
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取：库存量
     */
    public Integer getStock() {
        return stock;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }
}
