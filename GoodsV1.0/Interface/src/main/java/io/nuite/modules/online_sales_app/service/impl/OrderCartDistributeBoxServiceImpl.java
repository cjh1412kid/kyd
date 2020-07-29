package io.nuite.modules.online_sales_app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.online_sales_app.dao.OrderCartDistributeBoxDao;
import io.nuite.modules.online_sales_app.entity.OrderCartDistributeBoxEntity;
import io.nuite.modules.online_sales_app.service.OrderCartDistributeBoxService;


@Service
public class OrderCartDistributeBoxServiceImpl extends ServiceImpl<OrderCartDistributeBoxDao, OrderCartDistributeBoxEntity> implements OrderCartDistributeBoxService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderCartDistributeBoxEntity> page = this.selectPage(
                new Query<OrderCartDistributeBoxEntity>(params).getPage(),
                new EntityWrapper<OrderCartDistributeBoxEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String, Object>> selectColorNumByPeriod(Integer period) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("period",period);
        return baseMapper.selectColorNumByPeriod(map);
    }

}
