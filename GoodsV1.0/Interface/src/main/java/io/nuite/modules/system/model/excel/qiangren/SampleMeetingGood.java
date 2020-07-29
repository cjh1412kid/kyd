package io.nuite.modules.system.model.excel.qiangren;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class SampleMeetingGood implements Serializable{

	private static final long serialVersionUID = 1L;

	@Excel(name = "货品货号")
	private String goodsId;

	@Excel(name="最小尺码")
	private Integer minSize;
	 
	@Excel(name="最大尺码")
	private Integer maxSize;
	 
	@Excel(name="颜色1")
	private String color1;

	@Excel(name="颜色2")
	private String color2;

	@Excel(name="颜色3")
	private String color3;

	@Excel(name="颜色4")
	private String color4;

	@Excel(name="颜色5")
	private String color5;

	@Excel(name="颜色6")
	private String color6;

	@Excel(name="颜色7")
	private String color7;

	@Excel(name="颜色8")
	private String color8;
	 
	@Excel(name = "订货会代码")
	private String sessionCode;

	@Excel(name = "面料")
	private String surfaceMaterial;

	@Excel(name = "里料")
	private String innerMaterial;

	@Excel(name = "底料")
	private String soleMaterial;

	@Excel(name = "价格")
	private String price;

	@Excel(name = "厂家")
	private String factory;

	@Excel(name = "厂家货号")
	private String factoryGoodId;

	@Excel(name = "供应价")
	private String factoryPrice;

	@Excel(name = "大类")
	private String firstCategory;

	@Excel(name = "小类")
	private String secondCategory;

	@Excel(name = "风格")
	private String thirdCategory;



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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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

	public String getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	public String getColor1() {
		return color1;
	}

	public void setColor1(String color1) {
		this.color1 = color1;
	}

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	public String getColor3() {
		return color3;
	}

	public void setColor3(String color3) {
		this.color3 = color3;
	}

	public String getColor4() {
		return color4;
	}

	public void setColor4(String color4) {
		this.color4 = color4;
	}

	public String getColor5() {
		return color5;
	}

	public void setColor5(String color5) {
		this.color5 = color5;
	}

	public String getColor6() {
		return color6;
	}

	public void setColor6(String color6) {
		this.color6 = color6;
	}

	public String getColor7() {
		return color7;
	}

	public void setColor7(String color7) {
		this.color7 = color7;
	}

	public String getColor8() {
		return color8;
	}

	public void setColor8(String color8) {
		this.color8 = color8;
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

	public String getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(String factoryPrice) {
		this.factoryPrice = factoryPrice;
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
}
