package io.nuite.modules.order_platform_app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.nuite.datasources.DatabaseNames;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-16 10:12:39
 */
@TableName(DatabaseNames.SR_ORDER_PLATFORM+"YHSR_OP_MeetingGoods")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingGoodsEntity implements Serializable {
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
	 * 货号
	 */
	@TableField(value = "GoodID")
	private String goodID;
	/**
	 * 图片（主）
	 */
	@TableField(value = "Img")
	private String img;
	/**
	 * 可选颜色（Seq逗号分隔）
	 */
	@TableField(value = "OptionalColorSeqs")
	private String optionalColorSeqs;
	/**
	 * 可选颜色名称（逗号分隔）
	 */
	@TableField(exist = false)
	private String optionalColorNames;
	/**
	 * 可选颜色图片列表
	 */
	@TableField(exist = false)
	private List<Map<String, Object>> optionalColorsImgs;
	/**
	 * 可选最小尺码
	 */
	@TableField(value = "MinSize")
	private Integer minSize;
	/**
	 * 可选最大尺码
	 */
	@TableField(value = "MaxSize")
	private Integer maxSize;
	/**
	 * 录入人Seq
	 */
	@TableField(value = "User_Seq")
	private Integer userSeq;
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
	 * 鞋面材质
	 */
	@TableField(value = "SurfaceMaterial")
	private String surfaceMaterial;
	/**
	 * 鞋里材质
	 */
	@TableField(value = "InnerMaterial")
	private String innerMaterial;
	/**
	 * 鞋底材质
	 */
	@TableField(value = "SoleMaterial")
	private String soleMaterial;
	/**
	 * 厂家
	 */
	@TableField(value = "Factory")
	private String factory;
	/**
	 * 厂家货号
	 */
	@TableField(value = "FactoryGoodID")
	private String factoryGoodId;
	/**
	 * 供应价
	 */
	@TableField(value = "FactoryPrice")
	private BigDecimal factoryPrice;
	/**
	 * 类别序号
	 */
	@TableField(value = "Category_Seq")
	private Integer categorySeq;
	/**
	 * 关注度
	 */
	@TableField(value = "Attention")
	private Integer attention;
	/**
	 * 订货量
	 */
	@TableField(value = "Order_Quantity")
	private Integer orderQuantity;
	/**
	 * 总价
	 */
	@TableField(exist = false)
	private BigDecimal totalMoney;
	/**
	 * 大类
	 */
	@TableField(exist = false)
	private String firstCategory;
	/**
	 * 小类
	 */
	@TableField(exist = false)
	private String secondCategory;
	/**
	 * 风格
	 */
	@TableField(exist = false)
	private String thirdCategory;

	/**
	 * 颜色1
	 */
	@TableField(exist = false)
	private Integer color1;

	/**
	 * 颜色2
	 */
	@TableField(exist = false)
	private Integer color2;

	/**
	 * 颜色3
	 */
	@TableField(exist = false)
	private Integer color3;

	/**
	 * 颜色4
	 */
	@TableField(exist = false)
	private Integer color4;

	/**
	 * 颜色5
	 */
	@TableField(exist = false)
	private Integer color5;

	/**
	 * 颜色6
	 */
	@TableField(exist = false)
	private Integer color6;

	/**
	 * 颜色7
	 */
	@TableField(exist = false)
	private Integer color7;

	/**
	 * 颜色8
	 */
	@TableField(exist = false)
	private Integer color8;

	/**
	 * 颜色序号
	 */
	@TableField(exist = false)
	private List<Integer> colorSeqs;

	/**
	 * 订货会名称
	 */
	@TableField(exist = false)
	private String meetingName;

	/**
	 * 类别名称
	 */
	@TableField(exist = false)
	private String categoryName;

	/**
	 * 货品图片
	 */
	@TableField(exist = false)
	private MultipartFile goodImg;





	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getSurfaceMaterial() {
		return surfaceMaterial;
	}

	public void setSurfaceMaterial(String surfaceMaterial) {
		this.surfaceMaterial = surfaceMaterial;
	}

	public String getInnerMaterial() {
		return innerMaterial;
	}

	public void setInnerMaterial(String innerMaterial) {
		this.innerMaterial = innerMaterial;
	}

	public String getSoleMaterial() {
		return soleMaterial;
	}

	public void setSoleMaterial(String soleMaterial) {
		this.soleMaterial = soleMaterial;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 价格
	 */
	@TableField(value = "Price")
	private BigDecimal price;

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
	 * 设置：货号
	 */
	public void setGoodID(String goodID) {
		this.goodID = goodID;
	}
	/**
	 * 获取：货号
	 */
	public String getGoodID() {
		return goodID;
	}
	/**
	 * 设置：图片（主）
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：图片（主）
	 */
	public String getImg() {
		return img;
	}
	/**
	 * 设置：可选颜色（Seq逗号分隔）
	 */
	public void setOptionalColorSeqs(String optionalColorSeqs) {
		this.optionalColorSeqs = optionalColorSeqs;
	}
	/**
	 * 获取：可选颜色（Seq逗号分隔）
	 */
	public String getOptionalColorSeqs() {
		return optionalColorSeqs;
	}
	public Integer getMinSize() {
		return minSize;
	}
	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}
	public Integer getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
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
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	public String getOptionalColorNames() {
		return optionalColorNames;
	}
	public void setOptionalColorNames(String optionalColorNames) {
		this.optionalColorNames = optionalColorNames;
	}
	public List<Map<String, Object>> getOptionalColorsImgs() {
		return optionalColorsImgs;
	}
	public void setOptionalColorsImgs(List<Map<String, Object>> optionalColorsImgs) {
		this.optionalColorsImgs = optionalColorsImgs;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getFactoryGoodId() {
		return factoryGoodId;
	}

	public void setFactoryGoodId(String factoryGoodId) {
		this.factoryGoodId = factoryGoodId;
	}

	public BigDecimal getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(BigDecimal factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public Integer getCategorySeq() {
		return categorySeq;
	}

	public void setCategorySeq(Integer categorySeq) {
		this.categorySeq = categorySeq;
	}

	public String getFirstCategory() {
		return firstCategory;
	}

	public void setFirstCategory(String firstCategory) {
		this.firstCategory = firstCategory;
	}

	public String getSecondCategory() {
		return secondCategory;
	}

	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}

	public String getThirdCategory() {
		return thirdCategory;
	}

	public void setThirdCategory(String thirdCategory) {
		this.thirdCategory = thirdCategory;
	}

	public Integer getAttention() {
		return attention;
	}

	public void setAttention(Integer attention) {
		this.attention = attention;
	}

	public Integer getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Integer getColor1() {
		return color1;
	}

	public void setColor1(Integer color1) {
		this.color1 = color1;
	}

	public Integer getColor2() {
		return color2;
	}

	public void setColor2(Integer color2) {
		this.color2 = color2;
	}

	public Integer getColor3() {
		return color3;
	}

	public void setColor3(Integer color3) {
		this.color3 = color3;
	}

	public Integer getColor4() {
		return color4;
	}

	public void setColor4(Integer color4) {
		this.color4 = color4;
	}

	public Integer getColor5() {
		return color5;
	}

	public void setColor5(Integer color5) {
		this.color5 = color5;
	}

	public Integer getColor6() {
		return color6;
	}

	public void setColor6(Integer color6) {
		this.color6 = color6;
	}

	public Integer getColor7() {
		return color7;
	}

	public void setColor7(Integer color7) {
		this.color7 = color7;
	}

	public Integer getColor8() {
		return color8;
	}

	public void setColor8(Integer color8) {
		this.color8 = color8;
	}

	public List<Integer> getColorSeqs() {
		return colorSeqs;
	}

	public void setColorSeqs(List<Integer> colorSeqs) {
		this.colorSeqs = colorSeqs;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public MultipartFile getGoodImg() {
		return goodImg;
	}

	public void setGoodImg(MultipartFile goodImg) {
		this.goodImg = goodImg;
	}
}
