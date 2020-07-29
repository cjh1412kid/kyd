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
 * @date 2019-04-23 17:38:15
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingDevice")
public class MeetingDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * $column.comments
	 */
	@TableField(value = "DeviceName")
	private String deviceName;
	/**
	 * 设备类型 1专用手机  0BLEBeacon
	 */
	@TableField(value = "DeviceType")
	private Integer deviceType;
	/**
	 * 设备地址
	 */
	@TableField(value = "Address")
	private String address;
	/**
	 * BLE设备的UUID（BLE设备不能为空）
	 */
	@TableField(value = "UUID")
	private String uUID;
	/**
	 * $column.comments
	 */
	@TableField(value = "CompanySeq")
	private Integer companySeq;
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
	 * 绑定用户序号
	 */
	@TableField(value = "BindUserSeq")
	private Integer bindUserSeq;
	
	/**
	 * 绑定用户账号
	 */
	@TableField(value = "BindAccountName")
	private String bindAccountName;
	
	

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
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * 设置：设备类型 1专用手机  0BLEBeacon
	 */
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * 获取：设备类型 1专用手机  0BLEBeacon
	 */
	public Integer getDeviceType() {
		return deviceType;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getAddress() {
		return address;
	}
	public String getuUID() {
		return uUID;
	}
	public void setuUID(String uUID) {
		this.uUID = uUID;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getCompanySeq() {
		return companySeq;
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
	public Integer getBindUserSeq() {
		return bindUserSeq;
	}
	public void setBindUserSeq(Integer bindUserSeq) {
		this.bindUserSeq = bindUserSeq;
	}
	public String getBindAccountName() {
		return bindAccountName;
	}
	public void setBindAccountName(String bindAccountName) {
		this.bindAccountName = bindAccountName;
	}
}
