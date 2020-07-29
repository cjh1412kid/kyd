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
@TableName(DatabaseNames.SR_BASE + "YHSR_Goods_Shoes")
public class GoodsShoesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号(主键)
     */
    @TableId
    @TableField(value = "Seq")
    private Integer seq;
    /**
     * 公司序号
     */
    @TableField(value = "Company_Seq")
    private Integer companySeq;
    /**
     * 波次序号(外键:YHSR_Goods_Period表)
     */
    @TableField(value = "Period_Seq")
    private Integer periodSeq;
    /**
     * 鞋子类别序号
     */
    @TableField(value = "Category_Seq")
    private Integer categorySeq;

    /**
     * 鞋子类别名称
     */
    @TableField(exist = false)
    private String categoryName;
    /**
     * 货品名称
     */
    @TableField(value = "GoodName")
    private String goodName;
    /**
     * 货号
     */
    @TableField(value = "GoodID")
    private String goodID;
    /**
     * 颜色
     */
    @Deprecated
    @TableField(value = "Color")
    private String color;
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
     * 流行元素
     */
    @TableField(value = "PopularElement")
    private String popularElement;
    /**
     * 鞋底材质
     */
    @TableField(value = "SoleMaterial")
    private String soleMaterial;
    /**
     * 闭合方式
     */
    @TableField(value = "CloseForm")
    private String closeForm;
    /**
     * 鞋跟形状
     */
    @TableField(value = "HeelShape")
    private String heelShape;
    /**
     * 鞋头风格
     */
    @TableField(value = "ToeStyle")
    private String toeStyle;
    /**
     * 鞋跟高度
     */
    @TableField(value = "HeelHeight")
    private String heelHeight;
    /**
     * 商品介绍
     */
    @TableField(value = "Introduce")
    private String introduce;
    /**
     * 产品详细描述
     */
    @TableField(value = "Description")
    private String description;
    /**
     * 视频介绍
     */
    @TableField(value = "Video")
    private String video;
    /**
     * 图片（主）
     */
    @TableField(value = "Img1")
    private String img1;
    /**
     * 图片2
     */
    @TableField(value = "Img2")
    private String img2;
    /**
     * 图片3
     */
    @TableField(value = "Img3")
    private String img3;
    /**
     * 图片4
     */
    @TableField(value = "Img4")
    private String img4;
    /**
     * 图片5
     */
    @TableField(value = "Img5")
    private String img5;
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
     * 属性1~20
     */
    @TableField(value = "SX1")
    private String SX1;
    @TableField(value = "SX2")
    private String SX2;
    @TableField(value = "SX3")
    private String SX3;
    @TableField(value = "SX4")
    private String SX4;
    @TableField(value = "SX5")
    private String SX5;
    @TableField(value = "SX6")
    private String SX6;
    @TableField(value = "SX7")
    private String SX7;
    @TableField(value = "SX8")
    private String SX8;
    @TableField(value = "SX9")
    private String SX9;
    @TableField(value = "SX10")
    private String SX10;
    @TableField(value = "SX11")
    private String SX11;
    @TableField(value = "SX12")
    private String SX12;
    @TableField(value = "SX13")
    private String SX13;
    @TableField(value = "SX14")
    private String SX14;
    @TableField(value = "SX15")
    private String SX15;
    @TableField(value = "SX16")
    private String SX16;
    @TableField(value = "SX17")
    private String SX17;
    @TableField(value = "SX18")
    private String SX18;
    @TableField(value = "SX19")
    private String SX19;
    @TableField(value = "SX20")
    private String SX20;

    /**
     * 鞋子品牌
     */
    @TableField(value = "ShoesBrand")
    private String shoesBrand;

    /**
     * 助记符
     */
    @TableField(value = "Mnemonic")
    private String mnemonic;

    /**
     * 订货会货品序号
     */
    @TableField(value = "MeetingGoods_Seq")
    private Integer meetingGoodsSeq;
    /**
     * 订货平台是否上架
     */
    @TableField(exist = false)
    private Boolean orderPlatformOnSale;

    /**
     * 分销平台是否上架
     */
    @TableField(exist = false)
    private Boolean onlineSaleOnSale;

    public Integer getMeetingGoodsSeq() {
        return meetingGoodsSeq;
    }

    public void setMeetingGoodsSeq(Integer meetingGoodsSeq) {
        this.meetingGoodsSeq = meetingGoodsSeq;
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
     * 设置：波次序号(外键:YHSR_Goods_Period表)
     */
    public void setPeriodSeq(Integer periodSeq) {
        this.periodSeq = periodSeq;
    }

    /**
     * 获取：波次序号(外键:YHSR_Goods_Period表)
     */
    public Integer getPeriodSeq() {
        return periodSeq;
    }

    /**
     * 设置：鞋子类别序号
     */
    public void setCategorySeq(Integer categorySeq) {
        this.categorySeq = categorySeq;
    }

    /**
     * 获取：鞋子类别序号
     */
    public Integer getCategorySeq() {
        return categorySeq;
    }

    /**
     * 设置：货品名称
     */
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /**
     * 获取：货品名称
     */
    public String getGoodName() {
        return goodName;
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
     * 设置：颜色
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 获取：颜色
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置：鞋面材质
     */
    public void setSurfaceMaterial(String surfaceMaterial) {
        this.surfaceMaterial = surfaceMaterial;
    }

    /**
     * 获取：鞋面材质
     */
    public String getSurfaceMaterial() {
        return surfaceMaterial;
    }

    /**
     * 设置：鞋里材质
     */
    public void setInnerMaterial(String innerMaterial) {
        this.innerMaterial = innerMaterial;
    }

    /**
     * 获取：鞋里材质
     */
    public String getInnerMaterial() {
        return innerMaterial;
    }

    /**
     * 设置：流行元素
     */
    public void setPopularElement(String popularElement) {
        this.popularElement = popularElement;
    }

    /**
     * 获取：流行元素
     */
    public String getPopularElement() {
        return popularElement;
    }

    /**
     * 设置：鞋底材质
     */
    public void setSoleMaterial(String soleMaterial) {
        this.soleMaterial = soleMaterial;
    }

    /**
     * 获取：鞋底材质
     */
    public String getSoleMaterial() {
        return soleMaterial;
    }

    /**
     * 设置：闭合方式
     */
    public void setCloseForm(String closeForm) {
        this.closeForm = closeForm;
    }

    /**
     * 获取：闭合方式
     */
    public String getCloseForm() {
        return closeForm;
    }

    /**
     * 设置：鞋跟形状
     */
    public void setHeelShape(String heelShape) {
        this.heelShape = heelShape;
    }

    /**
     * 获取：鞋跟形状
     */
    public String getHeelShape() {
        return heelShape;
    }

    /**
     * 设置：鞋头风格
     */
    public void setToeStyle(String toeStyle) {
        this.toeStyle = toeStyle;
    }

    /**
     * 获取：鞋头风格
     */
    public String getToeStyle() {
        return toeStyle;
    }

    /**
     * 设置：鞋跟高度
     */
    public void setHeelHeight(String heelHeight) {
        this.heelHeight = heelHeight;
    }

    /**
     * 获取：鞋跟高度
     */
    public String getHeelHeight() {
        return heelHeight;
    }

    /**
     * 设置：商品介绍
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 获取：商品介绍
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置：产品详细描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取：产品详细描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置：视频介绍
     */
    public void setVideo(String video) {
        this.video = video;
    }

    /**
     * 获取：视频介绍
     */
    public String getVideo() {
        return video;
    }

    /**
     * 设置：图片（主）
     */
    public void setImg1(String img1) {
        this.img1 = img1;
    }

    /**
     * 获取：图片（主）
     */
    public String getImg1() {
        return img1;
    }

    /**
     * 设置：图片2
     */
    public void setImg2(String img2) {
        this.img2 = img2;
    }

    /**
     * 获取：图片2
     */
    public String getImg2() {
        return img2;
    }

    /**
     * 设置：图片3
     */
    public void setImg3(String img3) {
        this.img3 = img3;
    }

    /**
     * 获取：图片3
     */
    public String getImg3() {
        return img3;
    }

    /**
     * 设置：图片4
     */
    public void setImg4(String img4) {
        this.img4 = img4;
    }

    /**
     * 获取：图片4
     */
    public String getImg4() {
        return img4;
    }

    /**
     * 设置：图片5
     */
    public void setImg5(String img5) {
        this.img5 = img5;
    }

    /**
     * 获取：图片5
     */
    public String getImg5() {
        return img5;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean isOrderPlatformOnSale() {
        return orderPlatformOnSale;
    }

    public void setOrderPlatformOnSale(Boolean orderPlatformOnSale) {
        this.orderPlatformOnSale = orderPlatformOnSale;
    }

    public Boolean isOnlineSaleOnSale() {
        return onlineSaleOnSale;
    }

    public void setOnlineSaleOnSale(Boolean onlineSaleOnSale) {
        this.onlineSaleOnSale = onlineSaleOnSale;
    }

    public Integer getCompanySeq() {
        return companySeq;
    }

    public void setCompanySeq(Integer companySeq) {
        this.companySeq = companySeq;
    }

    public String getSX1() {
        return SX1;
    }

    public void setSX1(String sX1) {
        SX1 = sX1;
    }

    public String getSX2() {
        return SX2;
    }

    public void setSX2(String sX2) {
        SX2 = sX2;
    }

    public String getSX3() {
        return SX3;
    }

    public void setSX3(String sX3) {
        SX3 = sX3;
    }

    public String getSX4() {
        return SX4;
    }

    public void setSX4(String sX4) {
        SX4 = sX4;
    }

    public String getSX5() {
        return SX5;
    }

    public void setSX5(String sX5) {
        SX5 = sX5;
    }

    public String getSX6() {
        return SX6;
    }

    public void setSX6(String sX6) {
        SX6 = sX6;
    }

    public String getSX7() {
        return SX7;
    }

    public void setSX7(String sX7) {
        SX7 = sX7;
    }

    public String getSX8() {
        return SX8;
    }

    public void setSX8(String sX8) {
        SX8 = sX8;
    }

    public String getSX9() {
        return SX9;
    }

    public void setSX9(String sX9) {
        SX9 = sX9;
    }

    public String getSX10() {
        return SX10;
    }

    public void setSX10(String sX10) {
        SX10 = sX10;
    }

    public String getSX11() {
        return SX11;
    }

    public void setSX11(String sX11) {
        SX11 = sX11;
    }

    public String getSX12() {
        return SX12;
    }

    public void setSX12(String sX12) {
        SX12 = sX12;
    }

    public String getSX13() {
        return SX13;
    }

    public void setSX13(String sX13) {
        SX13 = sX13;
    }

    public String getSX14() {
        return SX14;
    }

    public void setSX14(String sX14) {
        SX14 = sX14;
    }

    public String getSX15() {
        return SX15;
    }

    public void setSX15(String sX15) {
        SX15 = sX15;
    }

    public String getSX16() {
        return SX16;
    }

    public void setSX16(String sX16) {
        SX16 = sX16;
    }

    public String getSX17() {
        return SX17;
    }

    public void setSX17(String sX17) {
        SX17 = sX17;
    }

    public String getSX18() {
        return SX18;
    }

    public void setSX18(String sX18) {
        SX18 = sX18;
    }

    public String getSX19() {
        return SX19;
    }

    public void setSX19(String sX19) {
        SX19 = sX19;
    }

    public String getSX20() {
        return SX20;
    }

    public void setSX20(String sX20) {
        SX20 = sX20;
    }

    public String getShoesBrand() {
        return shoesBrand;
    }

    public void setShoesBrand(String shoesBrand) {
        this.shoesBrand = shoesBrand;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}
