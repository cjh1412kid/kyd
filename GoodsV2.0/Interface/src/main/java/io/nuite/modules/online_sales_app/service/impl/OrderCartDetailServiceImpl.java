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
import io.nuite.modules.online_sales_app.dao.OrderCartDetailDao;
import io.nuite.modules.online_sales_app.entity.OrderCartDetailEntity;
import io.nuite.modules.online_sales_app.service.OrderCartDetailService;


@Service
public class OrderCartDetailServiceImpl extends ServiceImpl<OrderCartDetailDao, OrderCartDetailEntity> implements OrderCartDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderCartDetailEntity> page = this.selectPage(
                new Query<OrderCartDetailEntity>(params).getPage(),
                new EntityWrapper<OrderCartDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public String selectSize(Integer orderCartDetailSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("orderCartDetailSeq",orderCartDetailSeq);
        return baseMapper.selectSize(map);
    }

    @Override
    public List<Map<String, Object>> selectSizeNum(Integer shoesSeq, Integer colorSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("shoesSeq",shoesSeq);
        map.put("colorSeq",colorSeq);
        return baseMapper.selectSizeNum(map);
    }
}
