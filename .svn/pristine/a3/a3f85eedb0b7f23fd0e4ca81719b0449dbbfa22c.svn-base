package com.nuite.modules.erp.service;

import com.nuite.modules.erp.bserp.entity.*;
import com.nuite.modules.erp.entity.vo.GoodsDataVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/2 16:56
 * @Version: 1.0
 * @Description:
 */
public interface CommonErpService {

    List<GoodsSizeEntity> getAllGoodsSizes();

    List<GoodsColorEntity> getAllGoodsColor();

    List<GoodsCategoryEntity> getAllGoodsCategory();

    List getAllSXandOption();

    List getAllKeHu();

    List<GoodsDataVo> getStockInfo(List<String> goodIds);

    List<Dingdan> getAllDingdan(Date startDate);

    boolean saveOrderData(String data);

    List getAllGoods();

    void changeOrderStock(String data);

    List<Brand> getAllBrands();

    boolean saveGoods(String goods);
}
