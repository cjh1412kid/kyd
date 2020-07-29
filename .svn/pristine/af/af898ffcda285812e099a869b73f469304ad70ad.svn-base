package io.nuite.modules.sr_base.service;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;

import java.util.List;
import java.util.Map;

public interface GoodsShoesService extends IService<GoodsShoesEntity> {

    GoodsShoesEntity getGoodsByGoodId(Integer companySeq, String goodid);
    /**
     * 查找属性选项是否与商品有关联
     * @param companySeq
     * @param sxid 属性列名
     * @param optCode 属性选项编码
     * @return 关系属性选项的商品数
     */
    int getCountBySXAndOption(Integer companySeq, String sxid, String optCode);

    /**
     * 订单下货品
     * @param orderSeq
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getOrderGoods(Integer orderSeq) throws Exception;
}
