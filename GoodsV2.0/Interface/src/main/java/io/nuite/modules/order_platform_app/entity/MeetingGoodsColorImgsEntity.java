package io.nuite.modules.order_platform_app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import io.nuite.datasources.DatabaseNames;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-05-30 09:01:29
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingGoodsColorImgs")
public class MeetingGoodsColorImgsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号(主键)
	 */
	@TableId(value = "Seq")
	private Integer seq;
	/**
	 * 订货会鞋子序号
	 */
	@TableField(value = "MeetingGoods_Seq")
	private Integer meetingGoodsSeq;
	/**
	 * 颜色seq
	 */
	@TableField(value = "ColorSeq")
	private Integer colorSeq;
	/**
	 * 图片（多个图片逗号分隔）
	 */
	@TableField(value = "Imgs")
	private String imgs;
	/**
	 * 入库时间
	 */
	@TableField(value = "InputTime")
	private Date inputTime;
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
	 * 设置：颜色seq
	 */
	public void setColorSeq(Integer colorSeq) {
		this.colorSeq = colorSeq;
	}
	/**
	 * 获取：颜色seq
	 */
	public Integer getColorSeq() {
		return colorSeq;
	}
	/**
	 * 设置：图片（多个图片逗号分隔）
	 */
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	/**
	 * 获取：图片（多个图片逗号分隔）
	 */
	public String getImgs() {
		return imgs;
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
