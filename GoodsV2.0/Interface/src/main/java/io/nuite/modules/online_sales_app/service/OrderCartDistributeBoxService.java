package io.nuite.modules.online_sales_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.entity.OrderCartDistributeBoxEntity;


public interface OrderCartDistributeBoxService extends IService<OrderCartDistributeBoxEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取颜色详情
     * @param period
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> selectColorNumByPeriod(Integer period) throws Exception;
}

