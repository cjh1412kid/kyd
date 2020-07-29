package io.nuite.modules.sr_base.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

@TableName(DatabaseNames.SR_BASE + "YHSR_Home_Carousel")
public class HomeCarouselEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;
    
    /**
     * 品牌Seq
     */
    @TableField(value = "Brand_Seq")
    private Integer brandSeq;

    /**
     * 轮播图名称
     */
    @TableField(value = "Image")
    private String image;

    /**
     * 轮播图类别，1：单个鞋子，2：鞋子分类， 3：波次
     */
    @TableField(value = "Type")
    private Integer type;

    /**
     * 关联的类型的序号 （鞋子的Seq或是分类的Seq）
     */
    @TableField(value = "Link_Seq")
    private String linkSeq;

    /**
     * 插入时间
     */
    @TableField(value = "InputTime")
    private Date inputTime;

    /**
     * 删除标识
     */
    @TableLogic
    @TableField(value = "Del")
    private Integer del;

    
    
    
    
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getBrandSeq() {
		return brandSeq;
	}

	public void setBrandSeq(Integer brandSeq) {
		this.brandSeq = brandSeq;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLinkSeq() {
		return linkSeq;
	}

	public void setLinkSeq(String linkSeq) {
		this.linkSeq = linkSeq;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

}
