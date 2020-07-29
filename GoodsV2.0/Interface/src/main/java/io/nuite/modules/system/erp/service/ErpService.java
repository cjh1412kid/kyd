package io.nuite.modules.system.erp.service;

import io.nuite.modules.sr_base.entity.BaseESmartEntity;

/**
 * @Author: yangchuang
 * @Date: 2018/8/7 17:29
 * @Version: 1.0
 * @Description:
 */
public interface ErpService {

    void selectSizes(BaseESmartEntity baseESmartEntity, String url);

    void selectColors(BaseESmartEntity baseESmartEntity, String url);

    void selectCategorys(BaseESmartEntity baseESmartEntity, String url);

    void selectGoodsSX(BaseESmartEntity baseESmartEntity, String url);

    void selectKEHUs(BaseESmartEntity baseESmartEntity, String url);

    void selectGoodsStock(BaseESmartEntity baseESmartEntity, String url);

    void selectOrder(BaseESmartEntity baseESmartEntity, String url);

    int importOrder(Integer companySeq, Integer orderSeq);

    void selectGoods(BaseESmartEntity baseESmartEntity, String url);

    void selectBrands(BaseESmartEntity baseESmartEntity, String url);

    void importGoodsToERP(BaseESmartEntity baseESmartEntity, String url);
}
