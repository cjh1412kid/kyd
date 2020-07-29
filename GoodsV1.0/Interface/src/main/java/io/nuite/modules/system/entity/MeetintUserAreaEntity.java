package io.nuite.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-24 16:45:05
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetintUserArea")
public class MeetintUserAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * $column.comments
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
	/**
	 * 区域名称，用于订货会上给每个用户分配一个区域
	 */
	@TableField(value = "MeetingArea_Seq")
	private Integer meetingAreaSeq;
	/**
	 * $column.comments
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * $column.comments
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;

	/**
	 * 设置：${column.comments}
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getUserSeq() {
		return userSeq;
	}
	/**
	 * 设置：区域名称，用于订货会上给每个用户分配一个区域
	 */
	public void setMeetingAreaSeq(Integer meetingAreaSeq) {
		this.meetingAreaSeq = meetingAreaSeq;
	}
	/**
	 * 获取：区域名称，用于订货会上给每个用户分配一个区域
	 */
	public Integer getMeetingAreaSeq() {
		return meetingAreaSeq;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getInputTime() {
		return inputTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setDel(Integer del) {
		this.del = del;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getDel() {
		return del;
	}
}
