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
 * @date 2019-04-18 09:30:09
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingShoppingCartDetail")
public class MeetingShoppingCartDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 订货会鞋子序号
	 */
	@TableField(value = "MeetingShoppingCart_Seq")
	private Integer meetingShoppingCartSeq;
	/**
	 * 订货会鞋子序号（冗余字段）
	 */
	@TableField(value = "MeetingGoods_Seq")
	private Integer meetingGoodsSeq;
	/**
	 * 购物车配箱序号
	 */
	@TableField(value = "MeetingShoppingCartDistributeBox_Seq")
	private Integer meetingShoppingCartDistributeBoxSeq;
	/**
	 * 颜色seq
	 */
	@TableField(value = "Color_Seq")
	private Integer colorSeq;
	/**
	 * 尺码
	 */
	@TableField(value = "Size")
	private Integer size;
	/**
	 * 选款数量
	 */
	@TableField(value = "SelectNum")
	private Integer selectNum;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;


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
	 * 设置：订货会鞋子序号
	 */
	public void setMeetingShoppingCartSeq(Integer meetingShoppingCartSeq) {
		this.meetingShoppingCartSeq = meetingShoppingCartSeq;
	}
	/**
	 * 获取：订货会鞋子序号
	 */
	public Integer getMeetingShoppingCartSeq() {
		return meetingShoppingCartSeq;
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
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	/**
	 * 设置：选款数量
	 */
	public void setSelectNum(Integer selectNum) {
		this.selectNum = selectNum;
	}
	/**
	 * 获取：选款数量
	 */
	public Integer getSelectNum() {
		return selectNum;
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
	public Integer getMeetingGoodsSeq() {
		return meetingGoodsSeq;
	}
	public void setMeetingGoodsSeq(Integer meetingGoodsSeq) {
		this.meetingGoodsSeq = meetingGoodsSeq;
	}
	public Integer getMeetingShoppingCartDistributeBoxSeq() {
		return meetingShoppingCartDistributeBoxSeq;
	}
	public void setMeetingShoppingCartDistributeBoxSeq(Integer meetingShoppingCartDistributeBoxSeq) {
		this.meetingShoppingCartDistributeBoxSeq = meetingShoppingCartDistributeBoxSeq;
	}
	
}
