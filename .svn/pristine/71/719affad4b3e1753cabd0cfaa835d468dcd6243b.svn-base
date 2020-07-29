package io.nuite.modules.sr_base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author admin
 * @email
 * @date 2018-04-11 11:38:09
 */

/**
 * @author LL
 */
@TableName(DatabaseNames.SR_BASE + "YHSR_Base_User")
public class BaseUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;
    /**
     * 账号
     */
    @TableField(value = "AccountName")
    private String accountName;
    /**
     * 密码
     */
    @TableField(value = "Password")
    private String password;
    /**
     * 公司序号(外键:YHSR_Base_Company表)
     */
    @TableField(value = "Company_Seq")
    private Integer companySeq;
    /**
     * 品牌序号
     */
    @TableField(value = "Brand_Seq")
    private Integer brandSeq;

    /**
     * 账号所属类型：1.工厂，2.分公司，3.代理商，4.工厂子账号
     * {@link io.nuite.common.utils.Constant.UserAttachType}
     */
    @TableField(value = "AttachType")
    private Integer attachType;

    /**
     * 账号所属类型为分公司时为分公司seq，类型为代理商时为代理商seq，其他情况为null
     */
    @TableField(value = "Attach_Seq")
    private Integer attachSeq;

    /**
     * 账号销售类型：1.工厂，2.贴牌商，3.批发商，4.直营店
     * {@link io.nuite.common.utils.Constant.UserSaleType}
     */
    @TableField(value = "SaleType")
    private Integer saleType;

    /**
     * 门店序号
     */
    @TableField(value = "Shop_Seq")
    private String shopSeq;
    /**
     * 姓名
     */
    @TableField(value = "UserName")
    private String userName;
    /**
     * 电话
     */
    @TableField(value = "Telephone")
    private String telephone;

    /**
     * 地址
     */
    @TableField(value = "Address")
    private String address;
    /**
     * 头像
     */
    @TableField(value = "HeadImg")
    private String headImg;
    /**
     * 融云平台token
     */
    @TableField(value = "RongCloudToken")
    private String rongCloudToken;
    /**
     * 极光推送平台token
     */
    @TableField(value = "JPushToken")
    private String jPushToken;
    /**
     * 亲加直播平台token
     */
    @TableField(value = "GotyeToken")
    private String gotyeToken;
    /**
     * 插入时间
     */
    @TableField(value = "InputTime")
    private Date inputTime;
    /**
     * 删除标识(0:未删除,1:已删除)
     */
    @TableLogic
    @TableField(value = "Del")
    private Integer del;

    // --自定义字段-- //
    /**
     * 是否禁用
     */
    @TableField(exist = false)
    private Integer isDisable;
    /**
     * 门店（用于判断用户是否是直营店的用户）
     */
    @TableField(exist = false)
    private String shopName;
    /**
     * 公司名称
     */
    @TableField(exist = false)
    private String companyName;
    /**
     * 公司类别序号
     */
    @TableField(exist = false)
    private Integer companyTypeSeq;
    /**
     * 品牌名称
     */
    @TableField(exist = false)
    private String brandName;
    /**
     * 有效期
     */
    @TableField(exist = false)
    private Date effectiveDate;

    /**
     * 公司的描述
     */
    @TableField(exist = false)
    private String remark;

    /**
     * 公司地址
     */
    @TableField(exist = false)
    private String companyAddress;

    /**
     * 工厂管理员seq
     */
    @TableField(exist = false)
    private Integer adminUserSeq;

    /**
     * 所属类型名称
     */
    @TableField(exist = false)
    private String attachTypeName;

    /**
     * 所属类型公司名称
     */
    @TableField(exist = false)
    private String attachCompanyName;

    /**
     * 所属公司/代理的描述
     */
    @TableField(exist = false)
    private String attachRemark;
    
    /**
     * 权限集合
     */
    @TableField(exist = false)
    private Set<String> permissions;
    
    /**
     * （所属工厂的）订货计划权限（0:无权限 1:有权限）
     */
    @TableField(exist = false)
    private Integer meetingPlanPermission;
	/**
	 * 扫码提醒（0:不提醒,1:提醒）
	 */
	@TableField(exist = false)
	private Integer scanRemind;
	/**
	 * 下单提醒（0:不提醒,1:提醒）
	 */
	@TableField(exist = false)
	private Integer orderRemind;

    
    
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

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
     * 设置：账号
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取：账号
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置：密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：公司序号(外键:YHSR_Base_Company表)
     */
    public void setCompanySeq(Integer companySeq) {
        this.companySeq = companySeq;
    }

    /**
     * 获取：公司序号(外键:YHSR_Base_Company表)
     */
    public Integer getCompanySeq() {
        return companySeq;
    }

    /**
     * 设置：品牌序号
     */
    public void setBrandSeq(Integer brandSeq) {
        this.brandSeq = brandSeq;
    }

    /**
     * 获取：品牌序号
     */
    public Integer getBrandSeq() {
        return brandSeq;
    }

    public String getShopSeq() {
		return shopSeq;
	}

	public void setShopSeq(String shopSeq) {
		this.shopSeq = shopSeq;
	}

	/**
     * 设置：姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取：姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置：电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取：电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置：头像
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * 获取：头像
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getRongCloudToken() {
        return rongCloudToken;
    }

    public void setRongCloudToken(String rongCloudToken) {
        this.rongCloudToken = rongCloudToken;
    }

    public String getjPushToken() {
        return jPushToken;
    }

    public void setjPushToken(String jPushToken) {
        this.jPushToken = jPushToken;
    }

    public String getGotyeToken() {
        return gotyeToken;
    }

    public void setGotyeToken(String gotyeToken) {
        this.gotyeToken = gotyeToken;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyTypeSeq() {
        return companyTypeSeq;
    }

    public void setCompanyTypeSeq(Integer companyTypeSeq) {
        this.companyTypeSeq = companyTypeSeq;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public Integer getAttachType() {
        return attachType;
    }

    public void setAttachType(Integer attachType) {
        this.attachType = attachType;
    }

    public Integer getAttachSeq() {
        return attachSeq;
    }

    public void setAttachSeq(Integer attachSeq) {
        this.attachSeq = attachSeq;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public Integer getAdminUserSeq() {
        return adminUserSeq;
    }

    public void setAdminUserSeq(Integer adminUserSeq) {
        this.adminUserSeq = adminUserSeq;
    }

    public String getAttachTypeName() {
        return attachTypeName;
    }

    public void setAttachTypeName(String attachTypeName) {
        this.attachTypeName = attachTypeName;
    }

    public String getAttachRemark() {
        return attachRemark;
    }

    public void setAttachRemark(String attachRemark) {
        this.attachRemark = attachRemark;
    }

    public String getAttachCompanyName() {
        return attachCompanyName;
    }

    public void setAttachCompanyName(String attachCompanyName) {
        this.attachCompanyName = attachCompanyName;
    }

    public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMeetingPlanPermission() {
		return meetingPlanPermission;
	}

	public void setMeetingPlanPermission(Integer meetingPlanPermission) {
		this.meetingPlanPermission = meetingPlanPermission;
	}

	public Integer getScanRemind() {
		return scanRemind;
	}

	public void setScanRemind(Integer scanRemind) {
		this.scanRemind = scanRemind;
	}

	public Integer getOrderRemind() {
		return orderRemind;
	}

	public void setOrderRemind(Integer orderRemind) {
		this.orderRemind = orderRemind;
	}

	@Override
    public String toString() {
        return "BaseUserEntity [seq=" + seq + ", accountName=" + accountName + ", password=" + password
                + ", companySeq=" + companySeq + ", brandSeq=" + brandSeq + ", attachType=" + attachType
                + ", attachSeq=" + attachSeq + ", saleType=" + saleType + ", shopSeq=" + shopSeq + ", userName="
                + userName + ", telephone=" + telephone + ", headImg=" + headImg
                + ", rongCloudToken=" + rongCloudToken + ", jPushToken=" + jPushToken + ", gotyeToken=" + gotyeToken
                + ", inputTime=" + inputTime + ", del=" + del + ", isDisable=" + isDisable + ", shopName=" + shopName
                + ", companyName=" + companyName + ", companyTypeSeq=" + companyTypeSeq + ", brandName=" + brandName
                + ", effectiveDate=" + effectiveDate + ", remark=" + remark + ", companyAddress=" + companyAddress
                + ", adminUserSeq=" + adminUserSeq + ", attachTypeName=" + attachTypeName + ", attachCompanyName="
                + attachCompanyName + ", attachRemark=" + attachRemark + "]";
    }

}
