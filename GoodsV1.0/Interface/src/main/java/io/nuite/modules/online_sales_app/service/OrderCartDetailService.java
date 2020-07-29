package io.nuite.modules.online_sales_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.entity.OrderCartDetailEntity;


public interface OrderCartDetailService extends IService<OrderCartDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取尺码
     * @param orderCartDetailSeq
     * @return
     * @throws Exception
     */
    String selectSize(Integer orderCartDetailSeq) throws Exception;

    /**
     * 获取尺码详情
     * @param shoesSeq
     * @param colorSeq
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectSizeNum(Integer shoesSeq, Integer colorSeq) throws Exception;
}

