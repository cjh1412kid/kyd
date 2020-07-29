package io.nuite.modules.system.service.order_platform;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.system.model.GoodsShoesForm;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface OrderProductManagementService {

    /**
     * 货品的列表
     */
    PageUtils getGoodsList(Long UserSeq, Map<String, Object> params);

    /**
     * 根据seq查询子类别列表
     */
    List<GoodsCategoryEntity> getCategoryByParentSeq(Integer seq);

    /**
     * 根据seq删除信息
     */
    int delete(Integer seq);

    /**
     * 添加鞋子的信息
     */
    void addGoods(GoodsShoesForm goodsShoesForm, Integer companySeq) throws Exception;

    /**
     * 根据Seq返回鞋子的详细信息
     */
    GoodsShoesForm edit(Integer seq);

    /**
     * 修改鞋子信息
     */
    List<String> updateGoods(GoodsShoesForm goodsShoesForm) throws Exception;

    /**
     * 获取当前用户波次列表 按年分类
     */
    Map getUserPeriods(BaseUserEntity userEntity);

    /**
     * 获取当前用户的所有鞋子分类
     */
    List getUserCategory(BaseUserEntity userEntity);

    /**
     * 新增波次
     */
    void savePeriod(BaseUserEntity userEntity, GoodsPeriodEntity goodsPeriodEntity);

    void upAndDown(Long userSeq, Integer goodsSeq, int platform, int updown);

    /**
     * 生成小程序qrcode
     *
     * @param companySeq
     * @param brandSeq
     */
    Map<String, File> getGoodsWxQRCode(Integer userSeq, Integer companySeq, Map<String, Object> params);

    String getGoodsWxQRCodeZip(Integer companySeq, Integer brandSeq);

    
	List<Map<String, Object>> getMiniAppCodeExcelShoesList(Integer userSeq, Integer companySeq, Map<String, Object> params);
}
