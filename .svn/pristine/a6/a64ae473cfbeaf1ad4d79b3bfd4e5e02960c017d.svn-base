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
 * @date 2019-04-19 16:41:20
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingPermission")
public class MeetingPermissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * $column.comments
	 */
	@TableField(value = "Meeting_Seq")
	private Integer meetingSeq;
	/**
	 * $column.comments
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
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
	public void setMeetingSeq(Integer meetingSeq) {
		this.meetingSeq = meetingSeq;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getMeetingSeq() {
		return meetingSeq;
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
