package io.nuite.modules.online_sales_app.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.entity.SalesShoppingCartDetailEntity;


public interface SalesShoppingCartDetailService extends IService<SalesShoppingCartDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

