package io.nuite.modules.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

/**
 * 订货会管理
 *
 * @author yangchuang
 * @date 2019-04-16 16:13:34
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM + "YHSR_OP_Meeting" )
public class MeetingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 序号
     */
    @TableId(value = "Seq" )
    private Integer seq;
    /**
     * 订货会管理名称
     */
    @TableField(value = "Name" )
    private String name;
    /**
     * 开始时间
     */
    @TableField(value = "StartTime" )
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField(value = "EndTime" )
    private Date endTime;
    /**
     * 创建时间
     */
    @TableField(value = "InputTime" )
    private Date inputTime;
    /**
     * 公司序号
     */
    @TableField(value = "CompanySeq" )
    private Integer companySeq;
    /**
     * 年份
     */
    @TableField(value = "Year" )
    private Integer year;
    
	/**
	 * 订货会地址
	 */
	@TableField(value = "Address")
	private String address;
	/**
	 * 经度
	 */
	@TableField(value = "Lng")
	private String lng;
	/**
	 * 纬度
	 */
	@TableField(value = "Lat")
	private String lat;
    /**
     * 范围半径（米）
     */
    @TableField(value = "Radius" )
    private Integer radius;
    /**
     * 是否删除（1删除 0未删除）
     */
    @TableLogic
    @TableField(value = "Del" )
    private Integer del;
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public Integer getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public Integer getRadius() {
		return radius;
	}
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
}
