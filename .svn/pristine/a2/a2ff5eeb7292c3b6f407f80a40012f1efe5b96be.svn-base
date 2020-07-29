package io.nuite.modules.order_platform_app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-05-31 15:09:26
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingOrderCartDistributeBox")
public class MeetingOrderCartDistributeBoxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 购物车序号
	 */
	@TableField(value = "MeetingOrderCart_Seq")
	private Integer meetingOrderCartSeq;
	/**
	 * 订货会鞋子序号(冗余字段)
	 */
	@TableField(value = "MeetingGoods_Seq")
	private Integer meetingGoodsSeq;
	/**
	 * 颜色seq
	 */
	@TableField(value = "Color_Seq")
	private Integer colorSeq;
	/**
	 * 每箱数量（配件） （冗余字段）
	 */
	@TableField(value = "PerBoxNum")
	private Integer perBoxNum;
	/**
	 * 箱数（件数）
	 */
	@TableField(value = "BoxCount")
	private Integer boxCount;
	/**
	 * 本颜色总数量
	 */
	@TableField(value = "ColorTotalNum")
	private Integer colorTotalNum;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 配码类型 1：代码 2：具体数值
	 */
	@TableField(value = "AllocatedType")
	private Integer allocatedType;
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getMeetingOrderCartSeq() {
		return meetingOrderCartSeq;
	}
	public void setMeetingOrderCartSeq(Integer meetingOrderCartSeq) {
		this.meetingOrderCartSeq = meetingOrderCartSeq;
	}
	public Integer getMeetingGoodsSeq() {
		return meetingGoodsSeq;
	}
	public void setMeetingGoodsSeq(Integer meetingGoodsSeq) {
		this.meetingGoodsSeq = meetingGoodsSeq;
	}
	public Integer getColorSeq() {
		return colorSeq;
	}
	public void setColorSeq(Integer colorSeq) {
		this.colorSeq = colorSeq;
	}
	public Integer getPerBoxNum() {
		return perBoxNum;
	}
	public void setPerBoxNum(Integer perBoxNum) {
		this.perBoxNum = perBoxNum;
	}
	public Integer getBoxCount() {
		return boxCount;
	}
	public void setBoxCount(Integer boxCount) {
		this.boxCount = boxCount;
	}
	public Integer getColorTotalNum() {
		return colorTotalNum;
	}
	public void setColorTotalNum(Integer colorTotalNum) {
		this.colorTotalNum = colorTotalNum;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public Integer getAllocatedType() {
		return allocatedType;
	}
	public void setAllocatedType(Integer allocatedType) {
		this.allocatedType = allocatedType;
	}


}
