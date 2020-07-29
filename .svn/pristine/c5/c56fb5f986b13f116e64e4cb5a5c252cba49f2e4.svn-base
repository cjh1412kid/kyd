package io.nuite.modules.online_sales_app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.online_sales_app.dao.SalesShoppingCartDistributeBoxDao;
import io.nuite.modules.online_sales_app.entity.SalesShoppingCartDistributeBoxEntity;
import io.nuite.modules.online_sales_app.service.SalesShoppingCartDistributeBoxService;


@Service
public class SalesCartDistributeBoxServiceImpl extends ServiceImpl<SalesShoppingCartDistributeBoxDao, SalesShoppingCartDistributeBoxEntity> implements SalesShoppingCartDistributeBoxService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SalesShoppingCartDistributeBoxEntity> page = this.selectPage(
                new Query<SalesShoppingCartDistributeBoxEntity>(params).getPage(),
                new EntityWrapper<SalesShoppingCartDistributeBoxEntity>()
        );

        return new PageUtils(page);
    }

}
