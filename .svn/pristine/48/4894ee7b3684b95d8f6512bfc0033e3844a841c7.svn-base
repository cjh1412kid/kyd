package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-05-15 14:04:11
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingShoppingCartDistributeBox")
public class MeetingShoppingCartDistributeBoxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 购物车序号
	 */
	@TableField(value = "MeetingShoppingCart_Seq")
	private Integer meetingShoppingCartSeq;
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
	 * 设置：购物车序号
	 */
	public void setMeetingShoppingCartSeq(Integer meetingShoppingCartSeq) {
		this.meetingShoppingCartSeq = meetingShoppingCartSeq;
	}
	/**
	 * 获取：购物车序号
	 */
	public Integer getMeetingShoppingCartSeq() {
		return meetingShoppingCartSeq;
	}
	/**
	 * 设置：订货会鞋子序号(冗余字段)
	 */
	public void setMeetingGoodsSeq(Integer meetingGoodsSeq) {
		this.meetingGoodsSeq = meetingGoodsSeq;
	}
	/**
	 * 获取：订货会鞋子序号(冗余字段)
	 */
	public Integer getMeetingGoodsSeq() {
		return meetingGoodsSeq;
	}
	/**
	 * 设置：颜色seq
	 */
	public void setColorSeq(Integer colorSeq) {
		this.colorSeq = colorSeq;
	}
	/**
	 * 获取：颜色seq
	 */
	public Integer getColorSeq() {
		return colorSeq;
	}
	/**
	 * 设置：每箱数量（配件） （冗余字段）
	 */
	public void setPerBoxNum(Integer perBoxNum) {
		this.perBoxNum = perBoxNum;
	}
	/**
	 * 获取：每箱数量（配件） （冗余字段）
	 */
	public Integer getPerBoxNum() {
		return perBoxNum;
	}
	/**
	 * 设置：箱数（件数）
	 */
	public void setBoxCount(Integer boxCount) {
		this.boxCount = boxCount;
	}
	/**
	 * 获取：箱数（件数）
	 */
	public Integer getBoxCount() {
		return boxCount;
	}
	/**
	 * 设置：本颜色总数量
	 */
	public void setColorTotalNum(Integer colorTotalNum) {
		this.colorTotalNum = colorTotalNum;
	}
	/**
	 * 获取：本颜色总数量
	 */
	public Integer getColorTotalNum() {
		return colorTotalNum;
	}
	/**
	 * 设置：插入时间
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	/**
	 * 获取：插入时间
	 */
	public Date getInputTime() {
		return inputTime;
	}
	public Integer getAllocatedType() {
		return allocatedType;
	}
	public void setAllocatedType(Integer allocatedType) {
		this.allocatedType = allocatedType;
	}
}
