package io.nuite.modules.online_sales_app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.online_sales_app.dao.SalesShoppingCartDetailDao;
import io.nuite.modules.online_sales_app.entity.SalesShoppingCartDetailEntity;
import io.nuite.modules.online_sales_app.service.SalesShoppingCartDetailService;


@Service
public class SalesShoppingCartDetailServiceImpl extends ServiceImpl<SalesShoppingCartDetailDao, SalesShoppingCartDetailEntity> implements SalesShoppingCartDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SalesShoppingCartDetailEntity> page = this.selectPage(
                new Query<SalesShoppingCartDetailEntity>(params).getPage(),
                new EntityWrapper<SalesShoppingCartDetailEntity>()
        );

        return new PageUtils(page);
    }

}
