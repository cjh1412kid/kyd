package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import io.nuite.datasources.DatabaseNames;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 配码模版
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-05-16 14:04:21
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingUserSizeAllotCodeHistory")
public class MeetingUserSizeAllotCodeHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 配码模版序号
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 用户序号
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
	/**
	 * 配码代码（逗号分隔）
	 */
	@TableField(value = "SizeAllotCode")
	private String sizeAllotCode;
	/**
	 * 最小尺码
	 */
	@TableField(value = "MinSize")
	private Integer minSize;
	/**
	 * 最大尺码
	 */
	@TableField(value = "MaxSize")
	private Integer maxSize;
	/**
	 * 插入时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
	/**
	 * 0：未删除 1：已删除
	 */
	@TableLogic
	@TableField(value = "Del")
	private Integer del;

	public List<Integer> getSizeCode() {
		return sizeCode;
	}

	public void setSizeCode(List<Integer> sizeCode) {
		this.sizeCode = sizeCode;
	}
	@TableField(exist = false)
	private List<Integer> sizeCode;

	public List<CodeHistoryVO> getSizeAndNum() {
		return sizeAndNum;
	}

	public void setSizeAndNum(List<CodeHistoryVO> sizeAndNum) {
		this.sizeAndNum = sizeAndNum;
	}

	@TableField(exist = false)
	private List<CodeHistoryVO> sizeAndNum;

	/**
	 * 设置：配码模版序号
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * 获取：配码模版序号
	 */
	public Integer getSeq() {
		return seq;
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
	/**
	 * 设置：最小尺码
	 */
	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}
	/**
	 * 获取：最小尺码
	 */
	public Integer getMinSize() {
		return minSize;
	}
	/**
	 * 设置：最大尺码
	 */
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	/**
	 * 获取：最大尺码
	 */
	public Integer getMaxSize() {
		return maxSize;
	}
	/**
	 * 设置：配码代码（逗号分隔）
	 */
	public void setSizeAllotCode(String sizeAllotCode) {
		this.sizeAllotCode = sizeAllotCode;
	}
	/**
	 * 获取：配码代码（逗号分隔）
	 */
	public String getSizeAllotCode() {
		return sizeAllotCode;
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
	 * 设置：0：未删除 1：已删除
	 */
	public void setDel(Integer del) {
		this.del = del;
	}
	/**
	 * 获取：0：未删除 1：已删除
	 */
	public Integer getDel() {
		return del;
	}
}
