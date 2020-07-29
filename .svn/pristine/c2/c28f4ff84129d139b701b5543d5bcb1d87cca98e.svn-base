package io.nuite.modules.order_platform_app.entity;

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
 * @date 2018-04-11 11:29:58
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_UserJurisdiction")
public class UserJurisdictionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId
	@TableField(value = "Seq")
	private Integer seq;
	/**
	 * 用户序号(外键:YHSR_Base_User表)
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
	/**
	 * 创建人
	 */
	@TableField(value = "CreateUser_Seq")
	private Integer createUserSeq;
	/**
	 * 有效日期
	 */
	@TableField(value = "EffectiveDate")
	private Date effectiveDate;
	/**
	 * 是否停用(0:可用, 1:停用)
	 */
	@TableField(value = "IsDisable")
	private Integer isDisable;
	/**
	 * 可创建用户数(默认为0:最后一级,只能使用系统)
	 */
	@TableField(value = "IntOfCreateUsers")
	private Integer intOfCreateUsers;
	/**
	 * 已创建用户数(如等于"可创建用户数",则不能在创建用户)
	 */
	@TableField(value = "AlreadyCreateUsers")
	private Integer alreadyCreateUsers;
	/**
	 * 是否为管理员(0:是, 1:否)
	 */
	@TableField(value = "IsAdministrator")
	private Integer isAdministrator;
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
	 * 设置：用户序号(外键:YHSR_Base_User表)
	 */
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	/**
	 * 获取：用户序号(外键:YHSR_Base_User表)
	 */
	public Integer getUserSeq() {
		return userSeq;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateUserSeq(Integer createUserSeq) {
		this.createUserSeq = createUserSeq;
	}
	/**
	 * 获取：创建人
	 */
	public Integer getCreateUserSeq() {
		return createUserSeq;
	}
	/**
	 * 设置：有效日期
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * 获取：有效日期
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * 设置：是否停用(0:可用, 1:停用)
	 */
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
	/**
	 * 获取：是否停用(0:可用, 1:停用)
	 */
	public Integer getIsDisable() {
		return isDisable;
	}
	/**
	 * 设置：可创建用户数(默认为0:最后一级,只能使用系统)
	 */
	public void setIntOfCreateUsers(Integer intOfCreateUsers) {
		this.intOfCreateUsers = intOfCreateUsers;
	}
	/**
	 * 获取：可创建用户数(默认为0:最后一级,只能使用系统)
	 */
	public Integer getIntOfCreateUsers() {
		return intOfCreateUsers;
	}
	/**
	 * 设置：已创建用户数(如等于"可创建用户数",则不能在创建用户)
	 */
	public void setAlreadyCreateUsers(Integer alreadyCreateUsers) {
		this.alreadyCreateUsers = alreadyCreateUsers;
	}
	/**
	 * 获取：已创建用户数(如等于"可创建用户数",则不能在创建用户)
	 */
	public Integer getAlreadyCreateUsers() {
		return alreadyCreateUsers;
	}
	/**
	 * 设置：是否为管理员(0:不是, 1:是)
	 */
	public void setIsAdministrator(Integer isAdministrator) {
		this.isAdministrator = isAdministrator;
	}
	/**
	 * 获取：是否为管理员(0:是, 1:否)
	 */
	public Integer getIsAdministrator() {
		return isAdministrator;
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
}
