package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户非法行为记录
 * 
 * @author yy
 * @email barryhippo@163.com
 * @date 2019-09-02 16:18:15
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingUserIllegalActs")
public class MeetingUserIllegalActsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 公司序号
	 */
	@TableField(value = "Company_Seq")
	private Integer companySeq;
	/**
	 * 用户序号
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
	/**
	 * 订货会序号
	 */
	@TableField(value = "Meeting_Seq")
	private Integer meetingSeq;
	/**
	 * 扫码入场时间
	 */
	@TableField(value = "ScanTime")
	private Date scanTime;
	/**
	 * 状态 (0：扫码入场 1：锁定 2：解除锁定）
	 */
	@TableField(value = "State")
	private Integer state;
	/**
	 * 锁定时间
	 */
	@TableField(value = "LockTime")
	private Date lockTime;
	/**
	 * 解除锁定时间
	 */
	@TableField(value = "UnlockTime")
	private Date unlockTime;
	/**
	 * 创建时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 是否删除（1删除 0未删除）
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;

	/**
	 * 设置：序号
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * 获取：序号
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * 设置：公司序号
	 */
	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}
	/**
	 * 获取：公司序号
	 */
	public Integer getCompanySeq() {
		return companySeq;
	}
	/**
	 * 设置：用户序号
	 */
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	/**
	 * 获取：用户序号
	 */
	public Integer getUserSeq() {
		return userSeq;
	}
	public Integer getMeetingSeq() {
		return meetingSeq;
	}
	public void setMeetingSeq(Integer meetingSeq) {
		this.meetingSeq = meetingSeq;
	}
	/**
	 * 设置：扫码入场时间
	 */
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	/**
	 * 获取：扫码入场时间
	 */
	public Date getScanTime() {
		return scanTime;
	}
	/**
	 * 设置：状态 (0：扫码入场 1：锁定 2：解除锁定）
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：状态 (0：扫码入场 1：锁定 2：解除锁定）
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 设置：锁定时间
	 */
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
	/**
	 * 获取：锁定时间
	 */
	public Date getLockTime() {
		return lockTime;
	}
	/**
	 * 设置：解除锁定时间
	 */
	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}
	/**
	 * 获取：解除锁定时间
	 */
	public Date getUnlockTime() {
		return unlockTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getInputTime() {
		return inputTime;
	}
	/**
	 * 设置：是否删除（1删除 0未删除）
	 */
	public void setDel(Integer del) {
		this.del = del;
	}
	/**
	 * 获取：是否删除（1删除 0未删除）
	 */
	public Integer getDel() {
		return del;
	}
}
