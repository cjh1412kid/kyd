package io.nuite.modules.order_platform_app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;
import lombok.Data;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-07-09 14:28:26
 */
@Data
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_SmallUserInfo")
public class SmallUserInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 真实名称
	 */
	@TableField(value = "RealName")
	private String realName;
	/**
	 * 电话号码
	 */
	@TableField(value = "Telephone")
	private String telephone;
	/**
	 * 是否启用 0启用，1未启用
	 */
	@TableField(value = "Isuse")
	private Integer isuse;
	/**
	 * 用户微信号
	 */
	@TableField(value = "Openid")
	private String openid;
	/**
	 * 昵称
	 */
	@TableField(value = "Nickname")
	private String nickname;
	/**
	 * 性别1男性，2是女性，0未知
	 */
	@TableField(value = "Sex")
	private Integer sex;
	/**
	 * 国家 中国为CN
	 */
	@TableField(value = "Country")
	private String country;
	/**
	 * 省份
	 */
	@TableField(value = "Province")
	private String province;
	/**
	 * 市
	 */
	@TableField(value = "City")
	private String city;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 删除标识0未删除，1删除
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;
	/**
	 * 会话密钥
	 */
	@TableField(value = "Sessionkey")
	private String sessionkey;
	/**
	 * 用户在开放平台的唯一标识符
	 */
	@TableField(value = "Unionid")
	private String unionid;
	/**
	 * 是否为管理员
	 */
	@TableField(value = "isAdmin")
	private Integer isAdmin;

	@TableField(value = "avatarUrl")
	private String avatarUrl;
	
	@TableField(value = "userSeq")
	private Integer userSeq;
}
