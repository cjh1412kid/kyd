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
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingOrderCartDetail")
public class MeetingOrderCartDetailEntity implements Serializable {
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
	 * 购物车配箱序号
	 */
	@TableField(value = "MeetingOrderCartDistributeBox_Seq")
	private Integer meetingOrderCartDistributeBoxSeq;
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
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getSelectNum() {
		return selectNum;
	}
	public void setSelectNum(Integer selectNum) {
		this.selectNum = selectNum;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public Integer getMeetingOrderCartDistributeBoxSeq() {
		return meetingOrderCartDistributeBoxSeq;
	}
	public void setMeetingOrderCartDistributeBoxSeq(Integer meetingOrderCartDistributeBoxSeq) {
		this.meetingOrderCartDistributeBoxSeq = meetingOrderCartDistributeBoxSeq;
	}


}
