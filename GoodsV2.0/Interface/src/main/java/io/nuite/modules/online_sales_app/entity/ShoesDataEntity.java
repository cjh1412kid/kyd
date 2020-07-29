package io.nuite.modules.online_sales_app.entity;

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
 * @date 2018-04-16 15:41:03
 */
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_ShoesData")
public class ShoesDataEntity implements Serializable {
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
     * 颜色外键
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
     * 库存
     */
    @TableField(value = "Stock")
    private Integer stock;

    /**
     * 预设扣除库存
     */
    @TableField(value = "SetStock")
    private Integer setStock;
    /**
     * 是否删除
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
     * 尺码名称
     */
    @TableField(exist = false)
    private String sizeName;

    // --兼容旧版本 2018.07.31-- //
    /**
     * 尺码（旧版本）
     */
    @TableField(exist = false)
    private String size;
    // --兼容旧版本 2018.07.31-- //

    /**
     * 颜色代码
     */
    @TableField(exist = false)
    private String code;

    /**
     * 尺码代码
     */
    @TableField(exist = false)
    private String sizeCode;

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
     * 设置：库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取：库存
     */
    public Integer getStock() {
        return stock;
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

    public Integer getSetStock() {
        return setStock;
    }

    public void setSetStock(Integer setStock) {
        this.setStock = setStock;
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

    // --兼容旧版本 2018.07.31-- //
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    // --兼容旧版本 2018.07.31-- //

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
