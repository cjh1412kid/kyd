package io.nuite.modules.order_platform_app.entity;

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
 * @date 2019-04-18 09:30:08
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingShoppingCart")
public class MeetingShoppingCartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 订货会序号
	 */
	@TableField(value = "Meeting_Seq")
	private Integer meetingSeq;
	/**
	 * 用户Seq(外键:YHSR_Base_User表)
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
	/**
	 * 订货会鞋子序号
	 */
	@TableField(value = "MeetingGoods_Seq")
	private Integer meetingGoodsSeq;
	/**
	 * 货号
	 */
	@TableField(value = "MeetingGoodsID")
	private String meetingGoodsID;
	/**
	 * 用户贴牌货号
	 */
	@TableField(value = "UserGoodID")
	private String userGoodID;
	/**
	 * 总选款数量
	 */
	@TableField(value = "TotalSelectNum")
	private Integer totalSelectNum;
	/**
	 * 每箱数量（配件）
	 */
	@TableField(value = "PerBoxNum")
	private Integer perBoxNum;
	/**
	 * 是否已配码，0否  1是
	 */
	@TableField(value = "IsAllocated")
	private Integer isAllocated;
	/**
	 * 是否勾选，0不勾选 1勾选
	 */
	@TableField(value = "IsChecked")
	private Integer isChecked;
	/**
	 * 预设喜欢的颜色seqs(将在配码页面直接展示出该颜色)
	 */
	@TableField(value = "PreLikeColorSeqs")
	private String preLikeColorSeqs;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 删除标识(0:未删除,1:已删除)
	 */
	//@TableLogic
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
	 * 设置：订货会序号
	 */
	public void setMeetingSeq(Integer meetingSeq) {
		this.meetingSeq = meetingSeq;
	}
	/**
	 * 获取：订货会序号
	 */
	public Integer getMeetingSeq() {
		return meetingSeq;
	}
	/**
	 * 设置：用户Seq(外键:YHSR_Base_User表)
	 */
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	/**
	 * 获取：用户Seq(外键:YHSR_Base_User表)
	 */
	public Integer getUserSeq() {
		return userSeq;
	}
	/**
	 * 设置：订货会鞋子序号
	 */
	public void setMeetingGoodsSeq(Integer meetingGoodsSeq) {
		this.meetingGoodsSeq = meetingGoodsSeq;
	}
	/**
	 * 获取：订货会鞋子序号
	 */
	public Integer getMeetingGoodsSeq() {
		return meetingGoodsSeq;
	}
	/**
	 * 设置：总选款数量
	 */
	public void setTotalSelectNum(Integer totalSelectNum) {
		this.totalSelectNum = totalSelectNum;
	}
	/**
	 * 获取：总选款数量
	 */
	public Integer getTotalSelectNum() {
		return totalSelectNum;
	}
	/**
	 * 设置：是否勾选，0不勾选 1勾选
	 */
	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}
	/**
	 * 获取：是否勾选，0不勾选 1勾选
	 */
	public Integer getIsChecked() {
		return isChecked;
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
	public Integer getPerBoxNum() {
		return perBoxNum;
	}
	public void setPerBoxNum(Integer perBoxNum) {
		this.perBoxNum = perBoxNum;
	}
	public Integer getIsAllocated() {
		return isAllocated;
	}
	public void setIsAllocated(Integer isAllocated) {
		this.isAllocated = isAllocated;
	}
	public String getMeetingGoodsID() {
		return meetingGoodsID;
	}
	public void setMeetingGoodsID(String meetingGoodsID) {
		this.meetingGoodsID = meetingGoodsID;
	}
	public String getUserGoodID() {
		return userGoodID;
	}
	public void setUserGoodID(String userGoodID) {
		this.userGoodID = userGoodID;
	}
	public String getPreLikeColorSeqs() {
		return preLikeColorSeqs;
	}
	public void setPreLikeColorSeqs(String preLikeColorSeqs) {
		this.preLikeColorSeqs = preLikeColorSeqs;
	}
}
