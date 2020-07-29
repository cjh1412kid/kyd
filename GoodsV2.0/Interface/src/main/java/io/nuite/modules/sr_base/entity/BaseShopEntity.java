package io.nuite.modules.sr_base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:38:09
 */
@TableName(DatabaseNames.SR_BASE+"YHSR_Base_Shop")
public class BaseShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 区域名称
	 */
	@TableField(exist = false)
	private String areaName;
	/**
     * 门店类型
     */
	@TableField(exist = false)
	private String typeOfStore;
	/**
	 * 序号(主键)
	 */
	@TableId
	@TableField(value = "Seq")
	private Integer seq;
	/**
	 * 关联区域表序号(外键:YHSR_Base_Area表)
	 */
	@TableField(value = "Area_seq")
	private Integer areaSeq;
	/**
	 * 门店编号
	 */
	@TableField(value = "Id")
	private String id;
	/**
	 * 店名
	 */
	@TableField(value = "Name")
	private String name;
	/**
	 * 地址
	 */
	@TableField(value = "Address")
	private String address;
	/**
	 * 纬度
	 */
	@TableField(value = "Lat")
	private String lat;
	/**
	 * 经度
	 */
	@TableField(value = "Lng")
	private String lng;
	/**
	 * 安装时间
	 */
	@TableField(value = "InstallDate")
	private Date installDate;
	/**
	 * 备注
	 */
	@TableField(value = "Remark")
	private String remark;
	/**
	 * 入库时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 门店类型(0:直营,1:联营,2:加盟)
	 */
	@TableField(value = "ShopTypeFlag")
	private Integer shopTypeFlag;
	/**
	 * 删除标识(0:未删除,1:已删除)
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;

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
	 * 设置：关联区域表序号(外键:YHSR_Base_Area表)
	 */
	public void setAreaSeq(Integer areaSeq) {
		this.areaSeq = areaSeq;
	}
	/**
	 * 获取：关联区域表序号(外键:YHSR_Base_Area表)
	 */
	public Integer getAreaSeq() {
		return areaSeq;
	}
	/**
	 * 设置：门店编号
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：门店编号
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：店名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：店名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	/**
	 * 设置：安装时间
	 */
	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	/**
	 * 获取：安装时间
	 */
	public Date getInstallDate() {
		return installDate;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：入库时间
	 */
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	/**
	 * 获取：入库时间
	 */
	public Date getInputTime() {
		return inputTime;
	}
	/**
	 * 设置：门店类型(0:普通店,1:集合店)
	 */
	public void setShopTypeFlag(Integer shopTypeFlag) {
		this.shopTypeFlag = shopTypeFlag;
	}
	/**
	 * 获取：门店类型(0:普通店,1:集合店)
	 */
	public Integer getShopTypeFlag() {
		return shopTypeFlag;
	}
	/**
	 * 设置：删除标识(0:未删除,1:已删除)
	 */
	public void setDel(Integer del) {
		this.del = del;
	}
	/**
	 * 获取：删除标识(0:未删除,1:已删除)
	 */
	public Integer getDel() {
		return del;
	}
    public String getTypeOfStore() {
        return typeOfStore;
    }
    public void setTypeOfStore(String typeOfStore) {
        this.typeOfStore = typeOfStore;
    }
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    @Override
    public String toString() {
        return "BaseShopEntity [areaName=" + areaName + ", typeOfStore=" + typeOfStore + ", seq=" + seq + ", areaSeq="
                + areaSeq + ", id=" + id + ", name=" + name + ", address=" + address + ", lat=" + lat + ", lng=" + lng
                + ", installDate=" + installDate + ", remark=" + remark + ", inputTime=" + inputTime + ", shopTypeFlag="
                + shopTypeFlag + ", del=" + del + "]";
    }
    
	
}
